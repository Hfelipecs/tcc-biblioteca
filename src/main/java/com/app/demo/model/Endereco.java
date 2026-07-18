package com.app.demo.model;

import jakarta.persistence.*;

@Entity
public class Endereco extends AbstractEntity {

    private String Logradouro;
    private String Numero;
    private String Bairro;
    private String CEP;

    public Endereco() {}


    public String getNumero() {
        return Numero;
    }
    public void setNumero(String numero) {
        Numero = numero;
    }
    public String getBairro() {
        return Bairro;
    }
    public void setBairro(String bairro) {
        Bairro = bairro;
    }
    public String getLogradouro() {
        return Logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public String getCEP() {
        return CEP;
    }
    public void setCEP(String cep) {
        CEP = cep;
    }
}