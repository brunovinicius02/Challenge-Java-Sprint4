package com.fiap.teleconsultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Objects;

/**
 * Entidade JPA representando um Paciente
 * Mapeada para a tabela TB_PACIENTE do Oracle
 */
@Entity
@Table(name = "TB_PACIENTE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente_seq")
    @SequenceGenerator(name = "paciente_seq", sequenceName = "SEQ_PACIENTE", allocationSize = 1)
    @Column(name = "id_paciente")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "nome_paciente", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", 
             message = "CPF deve conter 11 dígitos numéricos")
    @Column(name = "cpf_paciente", nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email_paciente", nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @Pattern(regexp = "\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}|\\d{10,11}", 
             message = "Telefone inválido. Use formato: (11) 98765-4321")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Pattern(regexp = "[MFO]", message = "Sexo deve ser M, F ou O")
    @Column(name = "sexo_paciente", length = 1)
    private String sexo;

    // Construtores
    public Paciente() {}

    public Paciente(String nome, String cpf, String email, Date dataNascimento, String telefone, String sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public Paciente(Long id, String nome, String cpf, String email, Date dataNascimento, String telefone, String sexo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    // Métodos auxiliares
    
    /**
     * Remove formatação do CPF, deixando apenas números
     */
    public void normalizarCpf() {
        if (this.cpf != null) {
            this.cpf = this.cpf.replaceAll("[^\\d]", "");
        }
    }

    /**
     * Valida se o CPF possui dígitos verificadores válidos
     */
    @JsonIgnore
    public boolean isCpfValido() {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;

            // Calcula segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;

            // Verifica se os dígitos calculados são iguais aos informados
            return (cpf.charAt(9) - '0') == digito1 && (cpf.charAt(10) - '0') == digito2;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id) && 
               Objects.equals(cpf, paciente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", telefone='" + telefone + '\'' +
                ", sexo='" + sexo + '\'' +
                '}';
    }
}
