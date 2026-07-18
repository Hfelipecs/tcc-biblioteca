package com.app.demo.controller;

import com.app.demo.error.ResourceNotFoundException;
import com.app.demo.model.Emprestimo;
import com.app.demo.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        return new ResponseEntity<>(emprestimoService.listarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Emprestimo emprestimo = verificarSeEmprestimoExiste(id);
        return new ResponseEntity<>(emprestimo, HttpStatus.OK);
    }

    @GetMapping(path = "/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable int usuarioId) {
        return new ResponseEntity<>(emprestimoService.buscarPorUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping(path = "/status/{status}")
    public ResponseEntity<?> buscarPorStatus(@PathVariable String status) {
        return new ResponseEntity<>(emprestimoService.buscarPorStatus(status), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> salvar(@Valid @RequestBody Emprestimo emprestimo) {
        try {
            return new ResponseEntity<>(emprestimoService.salvar(emprestimo), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> atualizar(@Valid @RequestBody Emprestimo emprestimo) {
        verificarSeEmprestimoExiste(emprestimo.getID());
        return new ResponseEntity<>(emprestimoService.atualizar(emprestimo), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/devolucao")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registrarDevolucao(@PathVariable int id,
                                                 @RequestParam String dataEfetivaDevolucao) {
        verificarSeEmprestimoExiste(id);
        return new ResponseEntity<>(
                emprestimoService.registrarDevolucao(id, dataEfetivaDevolucao), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        verificarSeEmprestimoExiste(id);
        emprestimoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Emprestimo verificarSeEmprestimoExiste(int id) {
        return emprestimoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empréstimo não encontrado para o ID: " + id));
    }
}