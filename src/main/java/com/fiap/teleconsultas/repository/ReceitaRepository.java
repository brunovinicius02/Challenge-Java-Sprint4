package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Receita;
import com.fiap.teleconsultas.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Receita
 * Fornece operações de acesso a dados para TB_RECEITA
 */
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    
    /**
     * Busca receita por consulta
     */
    Optional<Receita> findByConsulta(Consulta consulta);
    
    /**
     * Busca receita por ID da consulta
     */
    @Query("SELECT r FROM Receita r WHERE r.consulta.id = :consultaId")
    Optional<Receita> findByConsultaId(@Param("consultaId") Long consultaId);
    
    /**
     * Busca receitas por paciente
     */
    @Query("SELECT r FROM Receita r WHERE r.consulta.paciente.id = :pacienteId ORDER BY r.dataEmissao DESC")
    List<Receita> findByPacienteId(@Param("pacienteId") Long pacienteId);
    
    /**
     * Busca receitas por médico
     */
    @Query("SELECT r FROM Receita r WHERE r.consulta.medico.id = :medicoId ORDER BY r.dataEmissao DESC")
    List<Receita> findByMedicoId(@Param("medicoId") Long medicoId);
    
    /**
     * Busca receitas por período
     */
    @Query("SELECT r FROM Receita r WHERE r.dataEmissao BETWEEN :dataInicio AND :dataFim ORDER BY r.dataEmissao DESC")
    List<Receita> findByPeriodo(@Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim);
    
    /**
     * Busca receitas com itens (fetch join para evitar N+1)
     */
    @Query("SELECT DISTINCT r FROM Receita r LEFT JOIN FETCH r.itens WHERE r.id = :id")
    Optional<Receita> findByIdWithItens(@Param("id") Long id);
    
    /**
     * Conta receitas por paciente
     */
    @Query("SELECT COUNT(r) FROM Receita r WHERE r.consulta.paciente.id = :pacienteId")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);
}
