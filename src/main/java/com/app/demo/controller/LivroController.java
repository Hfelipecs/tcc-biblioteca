package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Livro;
import com.app.demo.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(livroService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Livro livro = verificarSeLivroExiste(id);
        return new ResponseEntity<>(livro, HttpStatus.OK);
    }

    @GetMapping(path = "/titulo/{titulo}")
    public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
        return new ResponseEntity<>(livroService.buscarPorTitulo(titulo), HttpStatus.OK);
    }

    @GetMapping(path = "/isbn/{isbn}")
    public ResponseEntity<?> buscarPorISBN(@PathVariable String isbn) {
        Livro livro = livroService.buscarPorISBN(isbn);
        if (livro == null) throw new ResourceNotFoundException(
                "Livro não encontrado para o ISBN: " + isbn);
        return new ResponseEntity<>(livro, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Livro livro) {
        return new ResponseEntity<>(livroService.salvar(livro), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Livro livro) {
        verificarSeLivroExiste(livro.getID());
        return new ResponseEntity<>(livroService.atualizar(livro), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeLivroExiste(id);
        livroService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Livro verificarSeLivroExiste(int id) {
        return livroService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Livro não encontrado para o ID: " + id));
    }
}