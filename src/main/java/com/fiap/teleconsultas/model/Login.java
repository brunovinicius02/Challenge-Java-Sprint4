package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Entidade JPA representando o Login do sistema
 * Mapeada para a tabela TB_LOGIN do Oracle
 */
@Entity
@Table(name = "TB_LOGIN")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Login {

    @Id
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email_paciente", unique = true, length = 100)
    private String emailPaciente;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 60, message = "Senha deve ter entre 6 e 60 caracteres")
    @Column(name = "senha", nullable = false, length = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", insertable = false, updatable = false)
    @JsonIgnoreProperties({"login"})
    private Paciente paciente;

    // Construtores
    public Login() {}

    public Login(Long idPaciente, String nome, String emailPaciente, String senha) {
        this.idPaciente = idPaciente;
        this.nome = nome;
        this.emailPaciente = emailPaciente;
        this.senha = senha;
    }

    // Getters e Setters
    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(idPaciente, login.idPaciente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPaciente);
    }

    @Override
    public String toString() {
        return "Login{" +
                "idPaciente=" + idPaciente +
                ", nome='" + nome + '\'' +
                ", emailPaciente='" + emailPaciente + '\'' +
                '}';
    }
}
