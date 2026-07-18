package com.app.demo.services;

import com.app.demo.model.Bibliotecario;
import com.app.demo.model.Pessoa;
import com.app.demo.repository.BibliotecarioRepository;
import com.app.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Verifica se é um Bibliotecario
        Bibliotecario bibliotecario = bibliotecarioRepository.findByEmail(email);
        if (bibliotecario != null) {
            List<GrantedAuthority> authorities = AuthorityUtils
                    .createAuthorityList("ROLE_BIBLIOTECARIO");
            return new User(bibliotecario.getEmail(), bibliotecario.getSenha(), authorities);
        }

        // Verifica se é um Usuario comum
        com.app.demo.model.Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> authorities = AuthorityUtils
                    .createAuthorityList("ROLE_USUARIO");
            return new User(usuario.getEmail(), usuario.getSenha(), authorities);
        }

        throw new UsernameNotFoundException("Usuário não encontrado para o email: " + email);
    }
}