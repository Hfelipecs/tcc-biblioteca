package com.app.demo.repository;

import com.app.demo.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByBairro(String bairro);
    Endereco findByCEP(String cep);
    
}