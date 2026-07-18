package com.app.demo.services;

import com.app.demo.model.Livro;
import com.app.demo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // CREATE
    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    // READ - todos
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // READ - por ID
    public Optional<Livro> buscarPorId(int id) {
        return livroRepository.findById(id);
    }

    // READ - por título
    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    // READ - por ISBN
    public Livro buscarPorISBN(String isbn) {
        return livroRepository.findByISBN(isbn);
    }

    // UPDATE
    public Livro atualizar(Livro livro) {
        return livroRepository.save(livro);
    }

    // DELETE
    public void deletar(int id) {
        livroRepository.deleteById(id);
    }
}