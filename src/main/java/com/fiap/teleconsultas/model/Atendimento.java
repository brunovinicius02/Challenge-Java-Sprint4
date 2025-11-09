package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Entidade JPA representando um Atendimento
 * Mapeada para a tabela TB_ATENDIMENTO do Oracle
 */
@Entity
@Table(name = "TB_ATENDIMENTO")
public class Atendimento {

    @Id
    @Column(name = "id_atendimento")
    private Long id;

    @NotBlank(message = "Tipo de atendimento é obrigatório")
    @Size(max = 20)
    @Column(name = "tipo_atendimento", nullable = false, length = 20)
    private String tipoAtendimento;

    @NotNull(message = "Data de abertura é obrigatória")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_abertura", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataAbertura;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    // Construtores
    public Atendimento() {}

    public Atendimento(Long id, String tipoAtendimento, Date dataAbertura, String status) {
        this.id = id;
        this.tipoAtendimento = tipoAtendimento;
        this.dataAbertura = dataAbertura;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atendimento that = (Atendimento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Atendimento{" +
                "id=" + id +
                ", tipoAtendimento='" + tipoAtendimento + '\'' +
                ", dataAbertura=" + dataAbertura +
                ", status='" + status + '\'' +
                '}';
    }
}
