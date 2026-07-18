package com.app.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Pessoa {

    private int QtdLivros;
    private boolean ElegivelParaFazerEmprestimo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco Endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL)
    private List<Emprestimo> Emprestimos = new ArrayList<>();

    public Usuario() {}

    public int getQtdLivros() { return QtdLivros; }
    public void setQtdLivros(int qtdLivros) { QtdLivros = qtdLivros; }
    public boolean isElegivelParaFazerEmprestimo() { return ElegivelParaFazerEmprestimo; }
    public void setElegivelParaFazerEmprestimo(boolean elegivel) { ElegivelParaFazerEmprestimo = elegivel; }
    public Endereco getEndereco() { return Endereco; }
    public void setEndereco(Endereco endereco) { Endereco = endereco; }
    public List<Emprestimo> getEmprestimos() { return Emprestimos; }
    public void setEmprestimos(List<Emprestimo> emprestimos) { Emprestimos = emprestimos; }
}