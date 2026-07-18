package com.app.demo.repository;

import com.app.demo.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Integer> {
    List<Editora> findByNome(String nome);
    List<Editora> findByPrefixoEditorial(String prefixoEditorial);
}