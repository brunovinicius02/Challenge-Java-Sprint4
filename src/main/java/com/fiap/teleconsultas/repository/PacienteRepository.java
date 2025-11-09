package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de Paciente no banco de dados
 * Spring Data JPA gera automaticamente a implementação
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Buscar paciente por CPF
     */
    Optional<Paciente> findByCpf(String cpf);

    /**
     * Verificar se existe paciente com CPF
     */
    boolean existsByCpf(String cpf);

    /**
     * Buscar pacientes por nome (case insensitive, partial match)
     */
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    /**
     * Buscar pacientes ordenados por nome
     */
    List<Paciente> findAllByOrderByNomeAsc();

    /**
     * Verificar se existe paciente com CPF diferente do ID atual (para validação de update)
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Paciente p WHERE p.cpf = :cpf AND p.id <> :id")
    boolean existsByCpfAndIdNot(String cpf, Long id);

    /**
     * Buscar paciente por telefone
     */
    Optional<Paciente> findByTelefone(String telefone);
}
