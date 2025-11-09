package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

/**
 * Entidade JPA representando um Médico
 * Mapeada para a tabela TB_MEDICO do Oracle
 */
@Entity
@Table(name = "TB_MEDICO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medico_seq")
    @SequenceGenerator(name = "medico_seq", sequenceName = "SEQ_MEDICO", allocationSize = 1)
    @Column(name = "id_medico")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "nome_medico", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "\\d{4,10}|[A-Z]{2}-?\\d{4,10}", 
             message = "CRM inválido. Use formato: 123456 ou SP-123456")
    @Column(name = "crm_medico", nullable = false, unique = true, length = 20)
    private String crm;

    @Size(max = 100, message = "Especialidade deve ter no máximo 100 caracteres")
    @Column(name = "especialidade", length = 100)
    private String especialidade;

    // Construtores
    public Medico() {}

    public Medico(String nome, String crm, String especialidade) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Medico(Long id, String nome, String crm, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // Métodos auxiliares
    
    /**
     * Normaliza o CRM removendo formatação
     */
    public void normalizarCrm() {
        if (this.crm != null) {
            // Remove espaços e hifens, mantém letras e números
            this.crm = this.crm.replaceAll("[\\s-]", "").toUpperCase();
        }
    }

    /**
     * Valida o formato do CRM
     */
    @JsonIgnore
    public boolean isCrmValido() {
        if (crm == null) return false;
        
        // Aceita formatos: 123456 ou SP123456 ou SP-123456
        return crm.matches("\\d{4,10}") || 
               crm.matches("[A-Z]{2}-?\\d{4,10}");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return Objects.equals(id, medico.id) && 
               Objects.equals(crm, medico.crm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, crm);
    }

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
