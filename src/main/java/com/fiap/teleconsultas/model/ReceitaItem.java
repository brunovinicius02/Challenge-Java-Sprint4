package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 * Entidade JPA representando um Item de Receita
 * Mapeada para a tabela TB_RECEITA_ITEM do Oracle
 */
@Entity
@Table(name = "TB_RECEITA_ITEM")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReceitaItem {

    @Id
    @Column(name = "id_receita_item")
    private Long id;

    @NotNull(message = "Medicamento é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;

    @NotNull(message = "Receita é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receita", nullable = false)
    @JsonIgnoreProperties({"itens"})
    private Receita receita;

    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    @Column(name = "dosagem", length = 50)
    private String dosagem;

    @Size(max = 50, message = "Frequência deve ter no máximo 50 caracteres")
    @Column(name = "frequencia", length = 50)
    private String frequencia;

    @Min(value = 1, message = "Duração deve ser no mínimo 1 dia")
    @Max(value = 9999, message = "Duração deve ser no máximo 9999 dias")
    @Column(name = "duracao_dias")
    private Integer duracaoDias;

    @Size(max = 50, message = "Via de administração deve ter no máximo 50 caracteres")
    @Column(name = "via_administracao", length = 50)
    private String viaAdministracao;

    @Size(max = 500, message = "Instruções devem ter no máximo 500 caracteres")
    @Column(name = "instrucoes", length = 500)
    private String instrucoes;

    // Construtores
    public ReceitaItem() {}

    public ReceitaItem(Medicamento medicamento, String dosagem, String frequencia, 
                      Integer duracaoDias, String viaAdministracao, String instrucoes) {
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.frequencia = frequencia;
        this.duracaoDias = duracaoDias;
        this.viaAdministracao = viaAdministracao;
        this.instrucoes = instrucoes;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getDuracaoDias() {
        return duracaoDias;
    }

    public void setDuracaoDias(Integer duracaoDias) {
        this.duracaoDias = duracaoDias;
    }

    public String getViaAdministracao() {
        return viaAdministracao;
    }

    public void setViaAdministracao(String viaAdministracao) {
        this.viaAdministracao = viaAdministracao;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    /**
     * Retorna a descrição completa do item da receita
     */
    public String getDescricaoCompleta() {
        StringBuilder desc = new StringBuilder();
        
        if (medicamento != null) {
            desc.append(medicamento.getDescricaoCompleta());
        }
        
        if (dosagem != null && !dosagem.isEmpty()) {
            desc.append(" - ").append(dosagem);
        }
        
        if (frequencia != null && !frequencia.isEmpty()) {
            desc.append(" - ").append(frequencia);
        }
        
        if (duracaoDias != null) {
            desc.append(" por ").append(duracaoDias).append(" dias");
        }
        
        return desc.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceitaItem that = (ReceitaItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ReceitaItem{" +
                "id=" + id +
                ", medicamento=" + (medicamento != null ? medicamento.getNome() : null) +
                ", dosagem='" + dosagem + '\'' +
                ", frequencia='" + frequencia + '\'' +
                ", duracaoDias=" + duracaoDias +
                '}';
    }
}
