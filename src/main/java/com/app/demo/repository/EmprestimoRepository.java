package com.app.demo.repository;

import com.app.demo.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {
    List<Emprestimo> findByUsuario_ID(int usuarioId);
    List<Emprestimo> findByStatus(String status);
    List<Emprestimo> findByUsuario_IDAndStatus(int usuarioId, String status);
}