package com.app.demo.services;

import com.app.demo.model.Emprestimo;
import com.app.demo.model.Usuario;
import com.app.demo.repository.EmprestimoRepository;
import com.app.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private static final int LIMITE_LIVROS_USUARIO = 3;
    private static final int PRAZO_DEVOLUCAO_DIAS = 7;
    private static final int LIMITE_DESVIOS_PARA_BANIMENTO = 4;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um novo empréstimo. Valida:
     * 1) se o usuário está elegível (não banido, não bloqueado);
     * 2) se a soma dos livros já emprestados + os novos não ultrapassa 3;
     * A data prevista de devolução é sempre calculada automaticamente
     * (data do empréstimo + 7 dias) - o cliente nunca a informa.
     */
    public Emprestimo salvar(Emprestimo emprestimo) {
        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getID())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (!usuario.isElegivelParaFazerEmprestimo()) {
            throw new RuntimeException(
                    "Usuário não está apto para realizar um novo empréstimo. Status atual: "
                            + usuario.getStatus());
        }

        if (emprestimo.getLivros() == null || emprestimo.getLivros().isEmpty()) {
            throw new RuntimeException("O empréstimo deve conter pelo menos um livro.");
        }

        int totalAposEmprestimo = usuario.getQtdLivros() + emprestimo.getLivros().size();
        if (totalAposEmprestimo > LIMITE_LIVROS_USUARIO) {
            throw new RuntimeException(
                    "O usuário não pode ter mais de " + LIMITE_LIVROS_USUARIO
                            + " livros emprestados simultaneamente.");
        }

        Date agora = new Date();
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(agora);
        emprestimo.setDataPrevistaDevolucao(somarDias(agora, PRAZO_DEVOLUCAO_DIAS));
        emprestimo.setStatus("ATIVO");

        Emprestimo salvo = emprestimoRepository.save(emprestimo);

        usuario.setQtdLivros(totalAposEmprestimo);
        usuarioRepository.save(usuario);

        return salvo;
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Optional<Emprestimo> buscarPorId(int id) {
        return emprestimoRepository.findById(id);
    }

    public List<Emprestimo> buscarPorUsuario(int usuarioId) {
        return emprestimoRepository.findByUsuario_ID(usuarioId);
    }

    public List<Emprestimo> buscarPorStatus(String status) {
        return emprestimoRepository.findByStatus(status);
    }

    public Emprestimo atualizar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

   
    public Emprestimo registrarDevolucao(int id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));

        Date agora = new Date();
        emprestimo.setDataEfetivaDevolucao(agora);
        emprestimo.setStatus("DEVOLVIDO");

        Usuario usuario = usuarioRepository.findById(emprestimo.getUsuario().getID())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        int novaQuantidade = usuario.getQtdLivros() - emprestimo.getLivros().size();
        usuario.setQtdLivros(Math.max(novaQuantidade, 0));

        boolean atrasado = agora.after(emprestimo.getDataPrevistaDevolucao());
        if (atrasado) {
            aplicarDesvio(usuario, agora);
        }

        usuarioRepository.save(usuario);
        return emprestimoRepository.save(emprestimo);
    }

    private void aplicarDesvio(Usuario usuario, Date dataDevolucao) {
        int novosDesvios = usuario.getQuantidadeDesvios() + 1;
        usuario.setQuantidadeDesvios(novosDesvios);

        if (novosDesvios >= LIMITE_DESVIOS_PARA_BANIMENTO) {
            usuario.setTotalmenteInapto(true);
            usuario.setDataFimBloqueio(null);
        } else {
            int diasBloqueio = calcularDiasBloqueio(novosDesvios);
            usuario.setDataFimBloqueio(somarDias(dataDevolucao, diasBloqueio));
        }
    }

    private int calcularDiasBloqueio(int quantidadeDesvios) {
        switch (quantidadeDesvios) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 7;
            default: return 7;
        }
    }

    private Date somarDias(Date data, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }

    public void deletar(int id) {
        emprestimoRepository.deleteById(id);
    }
}