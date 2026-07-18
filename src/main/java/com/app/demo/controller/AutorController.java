package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Autor;
import com.app.demo.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(autorService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Autor autor = verificarSeAutorExiste(id);
        return new ResponseEntity<>(autor, HttpStatus.OK);
    }

    @GetMapping(path = "/nome/{nomeAutor}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nomeAutor) {
        return new ResponseEntity<>(autorService.buscarPorNome(nomeAutor), HttpStatus.OK);
    }

    @GetMapping(path = "/nacionalidade/{nacionalidade}")
    public ResponseEntity<?> buscarPorNacionalidade(@PathVariable String nacionalidade) {
        return new ResponseEntity<>(autorService.buscarPorNacionalidade(nacionalidade), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Autor autor) {
        return new ResponseEntity<>(autorService.salvar(autor), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Autor autor) {
        verificarSeAutorExiste(autor.getID());
        return new ResponseEntity<>(autorService.atualizar(autor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeAutorExiste(id);
        autorService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Autor verificarSeAutorExiste(int id) {
        return autorService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Autor não encontrado para o ID: " + id));
    }
}