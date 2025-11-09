package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Entidade JPA representando uma Receita Médica
 * Mapeada para a tabela TB_RECEITA do Oracle
 */
@Entity
@Table(name = "TB_RECEITA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Receita {

    @Id
    @Column(name = "id_receita")
    private Long id;

    @NotNull(message = "Consulta é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @NotNull(message = "Data de emissão é obrigatória")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_emissao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dataEmissao;

    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"receita"})
    private List<ReceitaItem> itens = new ArrayList<>();

    // Construtores
    public Receita() {
        this.dataEmissao = new Date(); // Data atual por padrão
    }

    public Receita(Consulta consulta, String observacoes) {
        this.consulta = consulta;
        this.dataEmissao = new Date();
        this.observacoes = observacoes;
    }

    public Receita(Long id, Consulta consulta, Date dataEmissao, String observacoes) {
        this.id = id;
        this.consulta = consulta;
        this.dataEmissao = dataEmissao;
        this.observacoes = observacoes;
    }

    // Métodos auxiliares
    public void adicionarItem(ReceitaItem item) {
        itens.add(item);
        item.setReceita(this);
    }

    public void removerItem(ReceitaItem item) {
        itens.remove(item);
        item.setReceita(null);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ReceitaItem> getItens() {
        return itens;
    }

    public void setItens(List<ReceitaItem> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receita receita = (Receita) o;
        return Objects.equals(id, receita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Receita{" +
                "id=" + id +
                ", consulta=" + (consulta != null ? consulta.getId() : null) +
                ", dataEmissao=" + dataEmissao +
                ", quantidadeItens=" + (itens != null ? itens.size() : 0) +
                '}';
    }
}
