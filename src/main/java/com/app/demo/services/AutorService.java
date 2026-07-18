package com.app.demo.services;

import com.app.demo.model.Autor;
import com.app.demo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    // CREATE
    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    // READ - todos
    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    // READ - por ID
    public Optional<Autor> buscarPorId(int id) {
        return autorRepository.findById(id);
    }

    // READ - por nome
    public List<Autor> buscarPorNome(String nomeAutor) {
        return autorRepository.findByNomeAutor(nomeAutor);
    }

    // READ - por nacionalidade
    public List<Autor> buscarPorNacionalidade(String nacionalidade) {
        return autorRepository.findByNacionalidade(nacionalidade);
    }

    // UPDATE
    public Autor atualizar(Autor autor) {
        return autorRepository.save(autor);
    }

    // DELETE
    public void deletar(int id) {
        autorRepository.deleteById(id);
    }
}