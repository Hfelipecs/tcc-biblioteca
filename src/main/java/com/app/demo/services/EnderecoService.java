package com.app.demo.services;

import com.app.demo.model.Endereco;
import com.app.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

   
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarPorId(int id) {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> buscarPorBairro(String bairro) {
        return enderecoRepository.findByBairro(bairro);
    }

    public Endereco buscarPorCEP(String cep) {
        return enderecoRepository.findByCEP(cep);
    }

    public Endereco atualizar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public void deletar(int id) {
        enderecoRepository.deleteById(id);
    }
}