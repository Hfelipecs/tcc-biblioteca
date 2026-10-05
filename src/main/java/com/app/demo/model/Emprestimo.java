package com.app.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date DataEmprestimo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date DataPrevistaDevolucao;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date DataEfetivaDevolucao;

    private String Status;

    public Emprestimo() {}

    /**
     * Verifica se ainda é possível adicionar mais um livro a ESTE
     * empréstimo específico (limite de 3 livros por empréstimo).
     */
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

    public Date getDataEmprestimo() { return DataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { DataEmprestimo = dataEmprestimo; }

    public Date getDataPrevistaDevolucao() { return DataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(Date dataPrevistaDevolucao) { DataPrevistaDevolucao = dataPrevistaDevolucao; }

    public Date getDataEfetivaDevolucao() { return DataEfetivaDevolucao; }
    public void setDataEfetivaDevolucao(Date dataEfetivaDevolucao) { DataEfetivaDevolucao = dataEfetivaDevolucao; }

    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }
}