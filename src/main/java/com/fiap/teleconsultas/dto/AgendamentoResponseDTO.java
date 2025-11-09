package com.fiap.teleconsultas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO para retornar dados de consulta para o frontend
 */
public class AgendamentoResponseDTO {

    private Long id;
    private String nome;
    private String idade;
    private String telefone;
    private String tipo; // "Consulta" ou "Exame"
    private String modalidade; // "Presencial" ou "Telemedicina"
    private String procedimento;
    private String data;
    private String hora;
    private String unidade;
    private String status;
    private String motivoCancel;

    // Construtores
    public AgendamentoResponseDTO() {}

    public AgendamentoResponseDTO(Long id, String nome, String idade, String telefone, 
                                   String tipo, String modalidade, String procedimento, 
                                   String data, String hora, String unidade, String status) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.tipo = tipo;
        this.modalidade = modalidade;
        this.procedimento = procedimento;
        this.data = data;
        this.hora = hora;
        this.unidade = unidade;
        this.status = status;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivoCancel() {
        return motivoCancel;
    }

    public void setMotivoCancel(String motivoCancel) {
        this.motivoCancel = motivoCancel;
    }

    @Override
    public String toString() {
        return "AgendamentoResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade='" + idade + '\'' +
                ", telefone='" + telefone + '\'' +
                ", tipo='" + tipo + '\'' +
                ", modalidade='" + modalidade + '\'' +
                ", procedimento='" + procedimento + '\'' +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", unidade='" + unidade + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
