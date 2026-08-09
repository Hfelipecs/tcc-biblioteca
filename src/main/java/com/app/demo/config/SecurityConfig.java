package com.app.demo.config;

import com.app.demo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;


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
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // Libera requisições vindas de qualquer endereço
    configuration.setAllowedOriginPatterns(List.of("*"));

    configuration.setAllowedMethods(List.of(
        "GET",
        "POST",
        "PUT",
        "PATCH",
        "DELETE",
        "OPTIONS"
    ));

    configuration.setAllowedHeaders(List.of(
        "Authorization",
        "Content-Type",
        "Accept"
    ));

    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    // Aplica o CORS a todos os endpoints
    source.registerCorsConfiguration("/**", configuration);

    return source;
}  
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}
 
    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider) {
        return new org.springframework.security.authentication.ProviderManager(provider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/livros/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
            .requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
            .requestMatchers(HttpMethod.GET, "/editoras/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
            .requestMatchers(HttpMethod.GET, "/emprestimos/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
            .requestMatchers("/livros/**").hasRole("BIBLIOTECARIO")
            .requestMatchers("/autores/**").hasRole("BIBLIOTECARIO")
            .requestMatchers("/editoras/**").hasRole("BIBLIOTECARIO")
            .requestMatchers("/emprestimos/**").hasRole("BIBLIOTECARIO")
            .requestMatchers("/usuarios/**").hasRole("BIBLIOTECARIO")
           .requestMatchers("/bibliotecarios/**").hasRole("BIBLIOTECARIO")
            .requestMatchers("/enderecos/**").hasRole("BIBLIOTECARIO")
            .requestMatchers(HttpMethod.POST, "/abnt/**").hasAnyRole("USUARIO", "BIBLIOTECARIO")
            .anyRequest().authenticated()
        )
        .httpBasic(basic -> {});
    return http.build();
    }

}