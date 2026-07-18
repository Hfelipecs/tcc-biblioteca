package com.app.demo.services;

import com.app.demo.model.Bibliotecario;
import com.app.demo.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    // CREATE
    public Bibliotecario salvar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    // READ - todos
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

    // READ - por ID
    public Optional<Bibliotecario> buscarPorId(int id) {
        return bibliotecarioRepository.findById(id);
    }

    // READ - por matricula
    public Bibliotecario buscarPorMatricula(String matricula) {
        return bibliotecarioRepository.findByMatricula(matricula);
    }

    // UPDATE
    public Bibliotecario atualizar(Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    // DELETE
    public void deletar(int id) {
        bibliotecarioRepository.deleteById(id);
    }
}