package com.app.demo.services;

import com.app.demo.model.Emprestimo;
import com.app.demo.model.Usuario;
import com.app.demo.repository.EmprestimoRepository;
import com.app.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // CREATE - valida elegibilidade antes de salvar
    public Emprestimo salvar(Emprestimo emprestimo) {
        Usuario usuario = emprestimo.getUsuario();

        if (!usuario.isElegivelParaFazerEmprestimo()) {
            throw new RuntimeException("Usuário não está elegível para realizar empréstimos.");
        }

        if (emprestimo.getLivros() == null || emprestimo.getLivros().isEmpty()) {
            throw new RuntimeException("O empréstimo deve conter pelo menos um livro.");
        }

        Emprestimo salvo = emprestimoRepository.save(emprestimo);

        usuario.setQtdLivros(usuario.getQtdLivros() + emprestimo.getLivros().size());
        if (usuario.getQtdLivros() >= 3) {
            usuario.setElegivelParaFazerEmprestimo(false);
        }
        usuarioRepository.save(usuario);

        return salvo;
    }

    // READ - todos
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    // READ - por ID
    public Optional<Emprestimo> buscarPorId(int id) {
        return emprestimoRepository.findById(id);
    }

    // READ - por usuario
    public List<Emprestimo> buscarPorUsuario(int usuarioId) {
        return emprestimoRepository.findByUsuario_ID(usuarioId);
    }

    // READ - por status
    public List<Emprestimo> buscarPorStatus(String status) {
        return emprestimoRepository.findByStatus(status);
    }

    // UPDATE
    public Emprestimo atualizar(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    // Registrar devolução
    public Emprestimo registrarDevolucao(int id, String dataEfetivaDevolucao) {
        Optional<Emprestimo> optional = emprestimoRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Empréstimo não encontrado.");
        }

        Emprestimo emprestimo = optional.get();
        emprestimo.setDataEfetivaDevolucao(dataEfetivaDevolucao);
        emprestimo.setStatus("DEVOLVIDO");

        Usuario usuario = emprestimo.getUsuario();
        usuario.setQtdLivros(usuario.getQtdLivros() - emprestimo.getLivros().size());
        if (usuario.getQtdLivros() < 3) {
            usuario.setElegivelParaFazerEmprestimo(true);
        }
        usuarioRepository.save(usuario);

        return emprestimoRepository.save(emprestimo);
    }

    // DELETE
    public void deletar(int id) {
        emprestimoRepository.deleteById(id);
    }
}