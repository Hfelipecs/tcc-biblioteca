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
    

    // CREATE
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
    // READ - todos
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // READ - por ID
    public Optional<Usuario> buscarPorId(int id) {
        return usuarioRepository.findById(id);
    }

    // READ - por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // READ - por CPF
    public Usuario buscarPorCPF(String cpf) {
        return usuarioRepository.findByCPF(cpf);
    }

    // UPDATE
    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // DELETE
    public void deletar(int id) {
        usuarioRepository.deleteById(id);
    }
}