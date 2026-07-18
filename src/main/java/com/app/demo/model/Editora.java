package com.app.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Editora extends AbstractEntity {

    private String Nome;
    private String PrefixoEditorial;

    public Editora() {}

    public String getNome() { return Nome; }
    public void setNome(String nome) { Nome = nome; }
    public String getPrefixoEditorial() { return PrefixoEditorial; }
    public void setPrefixoEditorial(String prefixoEditorial) { PrefixoEditorial = prefixoEditorial; }
}