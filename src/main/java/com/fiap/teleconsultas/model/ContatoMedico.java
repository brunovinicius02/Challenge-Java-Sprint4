package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 * Entidade JPA representando um Contato de Médico
 * Mapeada para a tabela TB_CONTATO_MEDICO do Oracle
 */
@Entity
@Table(name = "TB_CONTATO_MEDICO")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContatoMedico {

    @Id
    @Column(name = "id_contato_medico")
    private Long id;

    @NotNull(message = "Médico é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @NotBlank(message = "Tipo de contato é obrigatório")
    @Pattern(regexp = "EMAIL|CELULAR|WHATSAPP", 
             message = "Tipo de contato deve ser EMAIL, CELULAR ou WHATSAPP")
    @Column(name = "tipo_contato", nullable = false, length = 20)
    private String tipoContato;

    @Size(max = 50, message = "Valor do contato deve ter no máximo 50 caracteres")
    @Column(name = "valor_contato", length = 50)
    private String valorContato;

    // Construtores
    public ContatoMedico() {}

    public ContatoMedico(Medico medico, String tipoContato, String valorContato) {
        this.medico = medico;
        this.tipoContato = tipoContato;
        this.valorContato = valorContato;
    }

    public ContatoMedico(Long id, Medico medico, String tipoContato, String valorContato) {
        this.id = id;
        this.medico = medico;
        this.tipoContato = tipoContato;
        this.valorContato = valorContato;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(String tipoContato) {
        this.tipoContato = tipoContato;
    }

    public String getValorContato() {
        return valorContato;
    }

    public void setValorContato(String valorContato) {
        this.valorContato = valorContato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoMedico that = (ContatoMedico) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ContatoMedico{" +
                "id=" + id +
                ", medico=" + (medico != null ? medico.getNome() : null) +
                ", tipoContato='" + tipoContato + '\'' +
                ", valorContato='" + valorContato + '\'' +
                '}';
    }
}
