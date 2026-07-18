package com.app.demo.repository;

import com.app.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findByNomeAutor(String nomeAutor);
    List<Autor> findByNacionalidade(String nacionalidade);
}