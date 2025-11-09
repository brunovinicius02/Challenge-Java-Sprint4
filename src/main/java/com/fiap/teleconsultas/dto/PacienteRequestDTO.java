package com.fiap.teleconsultas.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * DTO para criação e atualização de Paciente
 */
public class PacienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @Pattern(regexp = "\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}|\\d{10,11}", 
             message = "Telefone inválido. Use formato: (11) 98765-4321")
    private String telefone;

    @Pattern(regexp = "[MFO]", message = "Sexo deve ser M, F ou O")
    private String sexo;

    public PacienteRequestDTO() {}

    public PacienteRequestDTO(String nome, String cpf, String email, Date dataNascimento, 
                              String telefone, String sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
    }

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
