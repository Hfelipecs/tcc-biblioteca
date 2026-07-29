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

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(int id) {
        return autorRepository.findById(id);
    }

    public List<Autor> buscarPorNome(String nomeAutor) {
        return autorRepository.findByNomeAutor(nomeAutor);
    }

    public List<Autor> buscarPorNacionalidade(String nacionalidade) {
        return autorRepository.findByNacionalidade(nacionalidade);
    }

    public Autor atualizar(Autor autor) {
        return autorRepository.save(autor);
    }

    public void deletar(int id) {
        autorRepository.deleteById(id);
    }
}