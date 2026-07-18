package com.app.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Emprestimo extends AbstractEntity {

    private static final int LIMITE_LIVROS = 3;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario Usuario;

    @ManyToMany
    @JoinTable(
        name = "emprestimo_livros",
        joinColumns = @JoinColumn(name = "emprestimo_id"),
        inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> Livros = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "bibliotecario_id")
    private Bibliotecario Bibliotecario;

    private String DataEmprestimo;
    private String DataPrevistaDevolucao;
    private String DataEfetivaDevolucao;
    private String Status;

    public Emprestimo() {}

    public boolean podeAdicionarLivro() {
        return Livros.size() < LIMITE_LIVROS;
    }

    public Usuario getUsuario() { return Usuario; }
    public void setUsuario(Usuario usuario) { Usuario = usuario; }

    public List<Livro> getLivros() { return Livros; }
    public void setLivros(List<Livro> livros) {
        if (livros.size() > LIMITE_LIVROS) {
            throw new RuntimeException("Um empréstimo pode ter no máximo " + LIMITE_LIVROS + " livros.");
        }
        Livros = livros;
    }

    public Bibliotecario getBibliotecario() { return Bibliotecario; }
    public void setBibliotecario(Bibliotecario bibliotecario) { Bibliotecario = bibliotecario; }
    public String getDataEmprestimo() { return DataEmprestimo; }
    public void setDataEmprestimo(String dataEmprestimo) { DataEmprestimo = dataEmprestimo; }
    public String getDataPrevistaDevolucao() { return DataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(String dataPrevistaDevolucao) { DataPrevistaDevolucao = dataPrevistaDevolucao; }
    public String getDataEfetivaDevolucao() { return DataEfetivaDevolucao; }
    public void setDataEfetivaDevolucao(String dataEfetivaDevolucao) { DataEfetivaDevolucao = dataEfetivaDevolucao; }
    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}