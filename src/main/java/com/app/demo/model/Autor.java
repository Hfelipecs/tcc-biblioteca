package com.app.demo.model;

import jakarta.persistence.Entity;

@Entity
public class Autor extends AbstractEntity {

    private String IDAutor;
    private String NomeAutor;
    private String Nacionalidade;

    public Autor() {}

    public String getIDAutor() { return IDAutor; }
    public void setIDAutor(String idAutor) { IDAutor = idAutor; }
    public String getNomeAutor() { return NomeAutor; }
    public void setNomeAutor(String nomeAutor) { NomeAutor = nomeAutor; }
    public String getNacionalidade() { return Nacionalidade; }
    public void setNacionalidade(String nacionalidade) { Nacionalidade = nacionalidade; }
}