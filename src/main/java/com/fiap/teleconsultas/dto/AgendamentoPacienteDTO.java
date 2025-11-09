package com.fiap.teleconsultas.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO para receber dados de agendamento vindos do frontend
 */
public class AgendamentoPacienteDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Idade é obrigatória")
    private String idade;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Modalidade é obrigatória")
    @Pattern(regexp = "Presencial|Telemedicina", 
             message = "Modalidade deve ser 'Presencial' ou 'Telemedicina'")
    private String modalidade;

    @NotBlank(message = "Procedimento é obrigatório")
    private String procedimento;

    @NotBlank(message = "Data é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String data;

    @NotBlank(message = "Hora é obrigatória")
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Hora deve estar no formato HH:mm")
    private String hora;

    @NotBlank(message = "Unidade é obrigatória")
    private String unidade;

    // Construtores
    public AgendamentoPacienteDTO() {}

    public AgendamentoPacienteDTO(String nome, String idade, String telefone, String modalidade, 
                                   String procedimento, String data, String hora, String unidade) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.modalidade = modalidade;
        this.procedimento = procedimento;
        this.data = data;
        this.hora = hora;
        this.unidade = unidade;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return "AgendamentoPacienteDTO{" +
                "nome='" + nome + '\'' +
                ", idade='" + idade + '\'' +
                ", telefone='" + telefone + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", procedimento='" + procedimento + '\'' +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", unidade='" + unidade + '\'' +
                '}';
    }
}
