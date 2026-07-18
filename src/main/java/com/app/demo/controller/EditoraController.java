package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Editora;
import com.app.demo.services.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    @Autowired
    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(editoraService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Editora editora = verificarSeEditoraExiste(id);
        return new ResponseEntity<>(editora, HttpStatus.OK);
    }

    @GetMapping(path = "/nome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(editoraService.buscarPorNome(nome), HttpStatus.OK);
    }

    @GetMapping(path = "/prefixo/{prefixoEditorial}")
    public ResponseEntity<?> buscarPorPrefixo(@PathVariable String prefixoEditorial) {
        return new ResponseEntity<>(editoraService.buscarPorPrefixoEditorial(prefixoEditorial), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Editora editora) {
        return new ResponseEntity<>(editoraService.salvar(editora), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Editora editora) {
        verificarSeEditoraExiste(editora.getID());
        return new ResponseEntity<>(editoraService.atualizar(editora), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeEditoraExiste(id);
        editoraService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Editora verificarSeEditoraExiste(int id) {
        return editoraService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Editora não encontrada para o ID: " + id));
    }
}