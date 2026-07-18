package com.app.demo.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends AbstractEntity {

    private String Nome;
    private String Telefone;
    private String CPF;
    private String Email;
    private String Senha;

    public Pessoa() {}

    public String getNome() { return Nome; }
    public void setNome(String nome) { Nome = nome; }
    public String getTelefone() { return Telefone; }
    public void setTelefone(String telefone) { Telefone = telefone; }
    public String getCPF() { return CPF; }
    public void setCPF(String cpf) { CPF = cpf; }
    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }
    public String getSenha() { return Senha; }
    public void setSenha(String senha) { Senha = senha; }
}