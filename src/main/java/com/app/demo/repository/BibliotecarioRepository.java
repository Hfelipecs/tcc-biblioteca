package com.app.demo.repository;

import com.app.demo.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Integer> {
    Bibliotecario findByMatricula(String matricula);
    @Query("SELECT b FROM Bibliotecario b WHERE b.Email = :email")
    Bibliotecario findByEmail(@Param("email") String email);
}