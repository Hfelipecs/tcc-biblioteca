package com.app.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario extends Pessoa {

    private int QtdLivros;

    // Contador cumulativo de desvios (devoluções em atraso). Nunca reseta sozinho - só é zerado manualmente quando o bibliotecário desbane o usuário.
    private int QuantidadeDesvios;

    // Data até quando o usuário está temporariamente bloqueado. Null = sem bloqueio ativo.
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date DataFimBloqueio;

    // Banimento definitivo (a partir do 4º desvio). Só o bibliotecário reverte.
    private boolean TotalmenteInapto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco Endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL)
    private List<Emprestimo> Emprestimos = new ArrayList<>();

    public Usuario() {}

    public int getQtdLivros() { return QtdLivros; }
    public void setQtdLivros(int qtdLivros) { QtdLivros = qtdLivros; }

    public int getQuantidadeDesvios() { return QuantidadeDesvios; }
    public void setQuantidadeDesvios(int quantidadeDesvios) { QuantidadeDesvios = quantidadeDesvios; }

    public Date getDataFimBloqueio() { return DataFimBloqueio; }
    public void setDataFimBloqueio(Date dataFimBloqueio) { DataFimBloqueio = dataFimBloqueio; }

    public boolean isTotalmenteInapto() { return TotalmenteInapto; }
    public void setTotalmenteInapto(boolean totalmenteInapto) { TotalmenteInapto = totalmenteInapto; }

    public Endereco getEndereco() { return Endereco; }
    public void setEndereco(Endereco endereco) { Endereco = endereco; }

    public List<Emprestimo> getEmprestimos() { return Emprestimos; }
    public void setEmprestimos(List<Emprestimo> emprestimos) { Emprestimos = emprestimos; }

    @Transient
    public boolean isElegivelParaFazerEmprestimo() {
        if (TotalmenteInapto) return false;
        if (DataFimBloqueio != null && DataFimBloqueio.after(new Date())) return false;
        return QtdLivros < 3;
    }

    @Transient
    public String getStatus() {
        if (TotalmenteInapto) {
            return "Totalmente inapto";
        }
        if (DataFimBloqueio != null && DataFimBloqueio.after(new Date())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return "Inapto até " + sdf.format(DataFimBloqueio);
        }
        if (QuantidadeDesvios > 0) {
            return "Apto com " + QuantidadeDesvios + " desvio(s)";
        }
        return "Apto";
    }
}