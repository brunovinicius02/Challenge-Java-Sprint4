package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Objects;

/**
 * Entidade JPA representando uma Mensagem do Sistema
 * Mapeada para a tabela TB_MENSAGEM do Oracle
 */
@Entity
@Table(name = "TB_MENSAGEM")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mensagem {

    @Id
    @Column(name = "id_mensagem")
    private Long id;

    @NotNull(message = "Assistente é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_assistente", nullable = false)
    private Assistente assistente;

    @NotNull(message = "Administrador é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_administrador", nullable = false)
    private Administrador administrador;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Size(max = 200, message = "Texto da mensagem deve ter no máximo 200 caracteres")
    @Column(name = "texto_mensagem", length = 200)
    private String textoMensagem;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_envio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataEnvio;

    @NotNull(message = "Atendimento é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TB_ATENDIMENTO_id_atendimento", nullable = false)
    private Atendimento atendimento;

    // Construtores
    public Mensagem() {
        this.dataEnvio = new Date(); // Data atual por padrão
    }

    public Mensagem(Assistente assistente, Administrador administrador, Paciente paciente, 
                   String textoMensagem, Atendimento atendimento) {
        this.assistente = assistente;
        this.administrador = administrador;
        this.paciente = paciente;
        this.textoMensagem = textoMensagem;
        this.dataEnvio = new Date();
        this.atendimento = atendimento;
    }

    public Mensagem(Long id, Assistente assistente, Administrador administrador, Paciente paciente, 
                   String textoMensagem, Date dataEnvio, Atendimento atendimento) {
        this.id = id;
        this.assistente = assistente;
        this.administrador = administrador;
        this.paciente = paciente;
        this.textoMensagem = textoMensagem;
        this.dataEnvio = dataEnvio;
        this.atendimento = atendimento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assistente getAssistente() {
        return assistente;
    }

    public void setAssistente(Assistente assistente) {
        this.assistente = assistente;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getTextoMensagem() {
        return textoMensagem;
    }

    public void setTextoMensagem(String textoMensagem) {
        this.textoMensagem = textoMensagem;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return Objects.equals(id, mensagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "id=" + id +
                ", assistente=" + (assistente != null ? assistente.getNome() : null) +
                ", paciente=" + (paciente != null ? paciente.getNome() : null) +
                ", textoMensagem='" + textoMensagem + '\'' +
                ", dataEnvio=" + dataEnvio +
                '}';
    }
}
