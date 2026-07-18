package com.app.demo.config;

import com.app.demo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Usuarios so podem fazer GET
                .requestMatchers(HttpMethod.GET, "/livros/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.GET, "/editoras/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
                .requestMatchers(HttpMethod.GET, "/emprestimos/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")

                // Apenas Bibliotecario pode criar, atualizar e deletar
                .requestMatchers("/livros/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/autores/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/editoras/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/emprestimos/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/usuarios/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/bibliotecarios/**").hasRole("BIBLIOTECARIO")
                .requestMatchers("/enderecos/**").hasRole("BIBLIOTECARIO")

                // Qualquer outra requisição precisa estar autenticado
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService)
               .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}