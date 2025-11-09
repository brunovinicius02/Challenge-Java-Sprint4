package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

/**
 * Entidade JPA representando um Assistente Virtual
 * Mapeada para a tabela TB_ASSISTENTE do Oracle
 */
@Entity
@Table(name = "TB_ASSISTENTE")
public class Assistente {

    @Id
    @Column(name = "id_assistente")
    private Long id;

    @NotBlank(message = "Nome do assistente é obrigatório")
    @Size(max = 20)
    @Column(name = "nome_assistente", nullable = false, length = 20)
    private String nome;

    @Size(max = 20)
    @Column(name = "versao_assistente", length = 20)
    private String versao;

    // Construtores
    public Assistente() {}

    public Assistente(Long id, String nome, String versao) {
        this.id = id;
        this.nome = nome;
        this.versao = versao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assistente that = (Assistente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Assistente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", versao='" + versao + '\'' +
                '}';
    }
}
