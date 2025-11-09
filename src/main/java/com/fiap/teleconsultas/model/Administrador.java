package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 * Entidade JPA representando um Administrador
 * Mapeada para a tabela TB_ADMINISTRADOR do Oracle
 */
@Entity
@Table(name = "TB_ADMINISTRADOR")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Administrador {

    @Id
    @Column(name = "id_administrador")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "nome_administrador", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email_administrador", nullable = false, unique = true, length = 100)
    private String email;

    @Pattern(regexp = "\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}|\\d{10,11}", 
             message = "Telefone inválido. Use formato: (11) 98765-4321")
    @Column(name = "telefone_administrador", length = 20)
    private String telefone;

    @Size(max = 20, message = "Área responsável deve ter no máximo 20 caracteres")
    @Column(name = "area_responsavel", length = 20)
    private String areaResponsavel;

    // Construtores
    public Administrador() {}

    public Administrador(Long id, String nome, String email, String telefone, String areaResponsavel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.areaResponsavel = areaResponsavel;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAreaResponsavel() {
        return areaResponsavel;
    }

    public void setAreaResponsavel(String areaResponsavel) {
        this.areaResponsavel = areaResponsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrador that = (Administrador) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", areaResponsavel='" + areaResponsavel + '\'' +
                '}';
    }
}
