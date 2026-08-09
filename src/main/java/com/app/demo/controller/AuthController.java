package com.app.demo.controller;

import com.app.demo.model.LoginRequest;
import com.app.demo.model.LoginResponse;
import com.app.demo.repository.BibliotecarioRepository;
import com.app.demo.repository.UsuarioRepository;
import com.app.demo.model.Bibliotecario;
import com.app.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final BibliotecarioRepository bibliotecarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                           BibliotecarioRepository bibliotecarioRepository,
                           UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getSenha())
            );

            String role = auth.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("DESCONHECIDO");

            String nome = buscarNomePorEmail(loginRequest.getEmail());

            LoginResponse response = new LoginResponse(nome, loginRequest.getEmail(), role);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Email ou senha inválidos.", HttpStatus.UNAUTHORIZED);
        }
    }

    private String buscarNomePorEmail(String email) {
        Bibliotecario bibliotecario = bibliotecarioRepository.findByEmail(email);
        if (bibliotecario != null) {
            return bibliotecario.getNome();
        }
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            return usuario.getNome();
        }
        return null;
    }
}