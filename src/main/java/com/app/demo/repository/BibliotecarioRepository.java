package com.app.demo.repository;

import com.app.demo.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Integer> {
    Bibliotecario findByMatricula(String matricula);
    Bibliotecario findByEmail(String email);
}