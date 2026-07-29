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

    public Editora salvar(Editora editora) {
        return editoraRepository.save(editora);
    }

    public List<Editora> listarTodos() {
        return editoraRepository.findAll();
    }

    public Optional<Editora> buscarPorId(int id) {
        return editoraRepository.findById(id);
    }

    public List<Editora> buscarPorNome(String nome) {
        return editoraRepository.findByNome(nome);
    }

    public List<Editora> buscarPorPrefixoEditorial(String prefixoEditorial) {
        return editoraRepository.findByPrefixoEditorial(prefixoEditorial);
    }

    public Editora atualizar(Editora editora) {
        return editoraRepository.save(editora);
    }

    public void deletar(int id) {
        editoraRepository.deleteById(id);
    }
}