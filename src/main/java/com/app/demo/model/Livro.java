package com.app.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Livro extends AbstractEntity {

    private String Titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor Autor;

    @ElementCollection
    private List<String> Generos = new ArrayList<>();

    private String ISBN;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora Editora;

    private int Quant;
    private String Traducao;
    private int Volume;
    private String Classindicativa;

    public Livro() {}

    public String getTitulo() { return Titulo; }
    public void setTitulo(String titulo) { Titulo = titulo; }
    public Autor getAutor() { return Autor; }
    public void setAutor(Autor autor) { Autor = autor; }
    public List<String> getGeneros() { return Generos; }
    public void setGeneros(List<String> generos) { Generos = generos; }
    public String getISBN() { return ISBN; }
    public void setISBN(String isbn) { ISBN = isbn; }
    public Editora getEditora() { return Editora; }
    public void setEditora(Editora editora) { Editora = editora; }
    public int getQuant() { return Quant; }
    public void setQuant(int quant) { Quant = quant; }
    public String getTraducao() { return Traducao; }
    public void setTraducao(String traducao) { Traducao = traducao; }
    public int getVolume() { return Volume; }
    public void setVolume(int volume) { Volume = volume; }
    public String getClassindicativa() { return Classindicativa; }
    public void setClassindicativa(String classindicativa) { Classindicativa = classindicativa; }
}