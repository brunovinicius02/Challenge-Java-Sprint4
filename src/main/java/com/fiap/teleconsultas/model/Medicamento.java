package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 * Entidade JPA representando um Medicamento
 * Mapeada para a tabela TB_MEDICAMENTO do Oracle
 */
@Entity
@Table(name = "TB_MEDICAMENTO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medicamento {

    @Id
    @Column(name = "id_medicamento")
    private Long id;

    @NotBlank(message = "Nome do medicamento é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome_medicamento", nullable = false, unique = true, length = 100)
    private String nome;

    @Size(max = 50, message = "Concentração deve ter no máximo 50 caracteres")
    @Column(name = "concentracao", length = 50)
    private String concentracao;

    @Size(max = 50, message = "Forma farmacêutica deve ter no máximo 50 caracteres")
    @Column(name = "forma_farmaceutica", length = 50)
    private String formaFarmaceutica;

    // Construtores
    public Medicamento() {}

    public Medicamento(String nome, String concentracao, String formaFarmaceutica) {
        this.nome = nome;
        this.concentracao = concentracao;
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public Medicamento(Long id, String nome, String concentracao, String formaFarmaceutica) {
        this.id = id;
        this.nome = nome;
        this.concentracao = concentracao;
        this.formaFarmaceutica = formaFarmaceutica;
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

    public String getConcentracao() {
        return concentracao;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    /**
     * Retorna a descrição completa do medicamento
     */
    public String getDescricaoCompleta() {
        StringBuilder desc = new StringBuilder(nome);
        if (concentracao != null && !concentracao.isEmpty()) {
            desc.append(" ").append(concentracao);
        }
        if (formaFarmaceutica != null && !formaFarmaceutica.isEmpty()) {
            desc.append(" - ").append(formaFarmaceutica);
        }
        return desc.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", concentracao='" + concentracao + '\'' +
                ", formaFarmaceutica='" + formaFarmaceutica + '\'' +
                '}';
    }
}
