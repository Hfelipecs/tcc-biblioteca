package com.app.demo.services;

import com.app.demo.model.Bibliotecario;
import com.app.demo.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Bibliotecario salvar(Bibliotecario bibliotecario) {
        bibliotecario.setSenha(passwordEncoder.encode(bibliotecario.getSenha()));
        return bibliotecarioRepository.save(bibliotecario);
    }

    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

    public Optional<Bibliotecario> buscarPorId(int id) {
        return bibliotecarioRepository.findById(id);
    }

    public Bibliotecario buscarPorMatricula(String matricula) {
        return bibliotecarioRepository.findByMatricula(matricula);
    }

    public Bibliotecario atualizar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    public void deletar(int id) {
        bibliotecarioRepository.deleteById(id);
    }
}