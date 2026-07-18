package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Bibliotecario;
import com.app.demo.services.BibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    @Autowired
    public BibliotecarioController(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(bibliotecarioService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Bibliotecario bibliotecario = verificarSeBibliotecarioExiste(id);
        return new ResponseEntity<>(bibliotecario, HttpStatus.OK);
    }

    @GetMapping(path = "/matricula/{matricula}")
    public ResponseEntity<?> buscarPorMatricula(@PathVariable String matricula) {
        Bibliotecario bibliotecario = bibliotecarioService.buscarPorMatricula(matricula);
        if (bibliotecario == null) throw new ResourceNotFoundException(
                "Bibliotecário não encontrado para a matrícula: " + matricula);
        return new ResponseEntity<>(bibliotecario, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Bibliotecario bibliotecario) {
        return new ResponseEntity<>(bibliotecarioService.salvar(bibliotecario), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Bibliotecario bibliotecario) {
        verificarSeBibliotecarioExiste(bibliotecario.getID());
        return new ResponseEntity<>(bibliotecarioService.atualizar(bibliotecario), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeBibliotecarioExiste(id);
        bibliotecarioService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Bibliotecario verificarSeBibliotecarioExiste(int id) {
        return bibliotecarioService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bibliotecário não encontrado para o ID: " + id));
    }
}