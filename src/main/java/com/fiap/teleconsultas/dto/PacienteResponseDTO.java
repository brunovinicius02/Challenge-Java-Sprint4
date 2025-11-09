package com.fiap.teleconsultas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * DTO para resposta de Paciente
 */
public class PacienteResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    
    private String telefone;
    private String sexo;

    public PacienteResponseDTO() {}

    public PacienteResponseDTO(Long id, String nome, String cpf, String email, 
                               Date dataNascimento, String telefone, String sexo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}
