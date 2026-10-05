package com.app.demo.services;

import com.app.demo.model.Endereco;
import com.app.demo.model.Usuario;
import com.app.demo.repository.UsuarioRepository;
import com.app.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        if (usuario.getEndereco() != null && usuario.getEndereco().getID() != 0) {
            Endereco endereco = enderecoRepository.findById(usuario.getEndereco().getID())
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            usuario.setEndereco(endereco);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario buscarPorCPF(String cpf) {
        return usuarioRepository.findByCPF(cpf);
    }

    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario desbanir(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        usuario.setTotalmenteInapto(false);
        usuario.setQuantidadeDesvios(0);
        usuario.setDataFimBloqueio(null);
        return usuarioRepository.save(usuario);
    }

    public void deletar(int id) {
        usuarioRepository.deleteById(id);
    }
}