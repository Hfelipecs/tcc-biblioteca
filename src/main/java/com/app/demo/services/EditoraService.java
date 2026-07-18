package com.app.demo.services;

import com.app.demo.model.Editora;
import com.app.demo.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    // CREATE
    public Editora salvar(Editora editora) {
        return editoraRepository.save(editora);
    }

    // READ - todos
    public List<Editora> listarTodos() {
        return editoraRepository.findAll();
    }

    // READ - por ID
    public Optional<Editora> buscarPorId(int id) {
        return editoraRepository.findById(id);
    }

    // READ - por nome
    public List<Editora> buscarPorNome(String nome) {
        return editoraRepository.findByNome(nome);
    }

    // READ - por prefixo editorial
    public List<Editora> buscarPorPrefixoEditorial(String prefixoEditorial) {
        return editoraRepository.findByPrefixoEditorial(prefixoEditorial);
    }

    // UPDATE
    public Editora atualizar(Editora editora) {
        return editoraRepository.save(editora);
    }

    // DELETE
    public void deletar(int id) {
        editoraRepository.deleteById(id);
    }
}