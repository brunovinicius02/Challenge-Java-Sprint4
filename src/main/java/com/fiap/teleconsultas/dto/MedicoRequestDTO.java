package com.fiap.teleconsultas.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para criação e atualização de Médico
 */
public class MedicoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "\\d{4,10}|[A-Z]{2}-?\\d{4,10}", 
             message = "CRM inválido. Use formato: 123456 ou SP-123456")
    private String crm;

    @Size(max = 100, message = "Especialidade deve ter no máximo 100 caracteres")
    private String especialidade;

    public MedicoRequestDTO() {}

    public MedicoRequestDTO(String nome, String crm, String especialidade) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}
