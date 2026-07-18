package com.app.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Bibliotecario extends Pessoa {

    private String Matricula;

    public Bibliotecario() {}

    public String getMatricula() { return Matricula; }
    public void setMatricula(String matricula) { Matricula = matricula; }
}