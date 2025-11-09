package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de Médico no banco de dados
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    /**
     * Buscar médico por CRM
     */
    Optional<Medico> findByCrm(String crm);

    /**
     * Verificar se existe médico com CRM
     */
    boolean existsByCrm(String crm);

    /**
     * Buscar médicos por especialidade
     */
    List<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade);

    /**
     * Buscar médicos por especialidade exata
     */
    List<Medico> findByEspecialidade(String especialidade);

    /**
     * Buscar médicos por nome (case insensitive, partial match)
     */
    List<Medico> findByNomeContainingIgnoreCase(String nome);

    /**
     * Listar todas as especialidades distintas
     */
    @Query("SELECT DISTINCT m.especialidade FROM Medico m ORDER BY m.especialidade")
    List<String> findAllEspecialidades();

    /**
     * Buscar médicos ordenados por nome
     */
    List<Medico> findAllByOrderByNomeAsc();

    /**
     * Verificar se existe médico com CRM diferente do ID atual (para validação de update)
     */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Medico m WHERE m.crm = :crm AND m.id <> :id")
    boolean existsByCrmAndIdNot(String crm, Long id);
}
