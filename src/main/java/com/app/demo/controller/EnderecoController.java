package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Endereco;
import com.app.demo.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(enderecoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Endereco endereco = verificarSeEnderecoExiste(id);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    @GetMapping(path = "/bairro/{bairro}")
    public ResponseEntity<?> buscarPorBairro(@PathVariable String bairro) {
        return new ResponseEntity<>(enderecoService.buscarPorBairro(bairro), HttpStatus.OK);
    }

    @GetMapping(path = "/cep/{cep}")
    public ResponseEntity<?> buscarPorCEP(@PathVariable String cep) {
        Endereco endereco = enderecoService.buscarPorCEP(cep);
        if (endereco == null) throw new ResourceNotFoundException(
                "Endereço não encontrado para o CEP: " + cep);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Endereco endereco) {
        return new ResponseEntity<>(enderecoService.salvar(endereco), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Endereco endereco) {
        verificarSeEnderecoExiste(endereco.getID());
        return new ResponseEntity<>(enderecoService.atualizar(endereco), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeEnderecoExiste(id);
        enderecoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Endereco verificarSeEnderecoExiste(int id) {
        return enderecoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Endereço não encontrado para o ID: " + id));
    }
}