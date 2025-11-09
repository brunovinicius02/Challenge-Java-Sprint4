package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Entidade JPA representando uma Consulta
 * Mapeada para a tabela TB_CONSULTA do Oracle
 */
@Entity
@Table(name = "TB_CONSULTA")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_seq")
    @SequenceGenerator(name = "consulta_seq", sequenceName = "SEQ_CONSULTA", allocationSize = 1)
    @Column(name = "id_consulta")
    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @NotNull(message = "Médico é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @NotBlank(message = "Tipo de consulta é obrigatório")
    @Pattern(regexp = "Presencial|Telemedicina", 
             message = "Tipo de consulta deve ser 'Presencial' ou 'Telemedicina'")
    @Column(name = "tipo_consulta", nullable = false, length = 20)
    private String tipoConsulta;

    @NotBlank(message = "Status da consulta é obrigatório")
    @Pattern(regexp = "Agendada|Realizada|Cancelada", 
             message = "Status deve ser 'Agendada', 'Realizada' ou 'Cancelada'")
    @Column(name = "status_consulta", nullable = false, length = 20)
    private String statusConsulta;

    @NotNull(message = "Data da consulta é obrigatória")
    @Column(name = "data_consulta", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataConsulta;

    @Size(max = 200, message = "Diagnóstico deve ter no máximo 200 caracteres")
    @Column(name = "diagnostico", length = 200)
    private String diagnostico;

    @Size(max = 4000, message = "Observações devem ter no máximo 4000 caracteres")
    @Column(name = "observacoes", length = 4000)
    private String observacoes;

    @Size(max = 4000, message = "Recomendações devem ter no máximo 4000 caracteres")
    @Column(name = "recomendacoes", length = 4000)
    private String recomendacoes;

    @Size(max = 100, message = "Procedimento deve ter no máximo 100 caracteres")
    @Column(name = "procedimento", length = 100)
    private String procedimento;

    @Size(max = 200, message = "Unidade deve ter no máximo 200 caracteres")
    @Column(name = "unidade", length = 200)
    private String unidade;

    // Construtores
    public Consulta() {
        this.statusConsulta = "Agendada"; // Status inicial padrão
    }

    public Consulta(Paciente paciente, Medico medico, String tipoConsulta, LocalDateTime dataConsulta) {
        this.paciente = paciente;
        this.medico = medico;
        this.tipoConsulta = tipoConsulta;
        this.dataConsulta = dataConsulta;
        this.statusConsulta = "Agendada";
    }

    public Consulta(Long id, Paciente paciente, Medico medico, String tipoConsulta, 
                   String statusConsulta, LocalDateTime dataConsulta, String diagnostico, 
                   String observacoes, String recomendacoes) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.tipoConsulta = tipoConsulta;
        this.statusConsulta = statusConsulta;
        this.dataConsulta = dataConsulta;
        this.diagnostico = diagnostico;
        this.observacoes = observacoes;
        this.recomendacoes = recomendacoes;
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(String statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(String recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    // Métodos auxiliares
    
    /**
     * Confirma a consulta
     */
    public void confirmar() {
        if (!"Agendada".equals(this.statusConsulta)) {
            throw new IllegalStateException("Apenas consultas agendadas podem ser confirmadas");
        }
        this.statusConsulta = "Agendada"; // Mantém como agendada mas confirmada
    }

    /**
     * Realiza a consulta
     */
    public void realizar() {
        if (!"Agendada".equals(this.statusConsulta)) {
            throw new IllegalStateException("Apenas consultas agendadas podem ser realizadas");
        }
        this.statusConsulta = "Realizada";
    }

    /**
     * Cancela a consulta
     */
    public void cancelar() {
        if ("Realizada".equals(this.statusConsulta)) {
            throw new IllegalStateException("Consultas realizadas não podem ser canceladas");
        }
        this.statusConsulta = "Cancelada";
    }

    /**
     * Verifica se a consulta pode ser cancelada (24h de antecedência)
     */
    public boolean podeCancelar() {
        if ("Realizada".equals(this.statusConsulta) || "Cancelada".equals(this.statusConsulta)) {
            return false;
        }
        
        LocalDateTime agora = LocalDateTime.now();
        long horasRestantes = ChronoUnit.HOURS.between(agora, this.dataConsulta);
        
        return horasRestantes >= 24;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", paciente=" + (paciente != null ? paciente.getNome() : null) +
                ", medico=" + (medico != null ? medico.getNome() : null) +
                ", tipoConsulta='" + tipoConsulta + '\'' +
                ", statusConsulta='" + statusConsulta + '\'' +
                ", dataConsulta=" + dataConsulta +
                ", diagnostico='" + diagnostico + '\'' +
                '}';
    }
}
