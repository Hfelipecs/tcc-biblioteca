package com.app.demo.repository;

import com.app.demo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    List<Livro> findByTitulo(String titulo);
    Livro findByISBN(String isbn);
}