package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Objects;

/**
 * Entidade JPA representando um Prontuário de Paciente
 * Mapeada para a tabela TB_PRONTUARIO_PACIENTE do Oracle
 */
@Entity
@Table(name = "TB_PRONTUARIO_PACIENTE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProntuarioPaciente {

    @Id
    @Column(name = "id_prontuario_paciente")
    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @NotNull(message = "Consulta é obrigatória")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consulta", nullable = false)
    private Consulta consulta;

    @NotNull(message = "Médico é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    @Column(name = "descricao", length = 200)
    private String descricao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_registro")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataRegistro;

    // Construtores
    public ProntuarioPaciente() {
        this.dataRegistro = new Date(); // Data atual por padrão
    }

    public ProntuarioPaciente(Paciente paciente, Consulta consulta, Medico medico, String descricao) {
        this.paciente = paciente;
        this.consulta = consulta;
        this.medico = medico;
        this.descricao = descricao;
        this.dataRegistro = new Date();
    }

    public ProntuarioPaciente(Long id, Paciente paciente, Consulta consulta, Medico medico, 
                             String descricao, Date dataRegistro) {
        this.id = id;
        this.paciente = paciente;
        this.consulta = consulta;
        this.medico = medico;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProntuarioPaciente that = (ProntuarioPaciente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProntuarioPaciente{" +
                "id=" + id +
                ", paciente=" + (paciente != null ? paciente.getNome() : null) +
                ", consulta=" + (consulta != null ? consulta.getId() : null) +
                ", medico=" + (medico != null ? medico.getNome() : null) +
                ", dataRegistro=" + dataRegistro +
                '}';
    }
}
