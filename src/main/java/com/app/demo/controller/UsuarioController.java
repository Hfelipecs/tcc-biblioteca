package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Usuario;
import com.app.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(usuarioService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Usuario usuario = verificarSeUsuarioExiste(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario == null) throw new ResourceNotFoundException(
                "Usuário não encontrado para o email: " + email);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCPF(@PathVariable String cpf) {
        Usuario usuario = usuarioService.buscarPorCPF(cpf);
        if (usuario == null) throw new ResourceNotFoundException(
                "Usuário não encontrado para o CPF: " + cpf);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.salvar(usuario), HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Usuario usuario) {
        verificarSeUsuarioExiste(usuario.getID());
        return new ResponseEntity<>(usuarioService.atualizar(usuario), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeUsuarioExiste(id);
        usuarioService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Usuario verificarSeUsuarioExiste(int id) {
        return usuarioService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado para o ID: " + id));
    }
}