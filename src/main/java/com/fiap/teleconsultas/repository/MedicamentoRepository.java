package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Medicamento
 * Fornece operações de acesso a dados para TB_MEDICAMENTO
 */
@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    
    /**
     * Busca medicamento por nome
     */
    Optional<Medicamento> findByNome(String nome);
    
    /**
     * Busca medicamentos por nome contendo texto (case insensitive)
     */
    List<Medicamento> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca medicamentos por forma farmacêutica
     */
    List<Medicamento> findByFormaFarmaceutica(String formaFarmaceutica);
    
    /**
     * Busca medicamentos por concentração
     */
    List<Medicamento> findByConcentracao(String concentracao);
    
    /**
     * Verifica se existe medicamento com o nome
     */
    boolean existsByNome(String nome);
    
    /**
     * Busca medicamentos ordenados por nome
     */
    List<Medicamento> findAllByOrderByNomeAsc();
    
    /**
     * Conta medicamentos por forma farmacêutica
     */
    @Query("SELECT COUNT(m) FROM Medicamento m WHERE m.formaFarmaceutica = :forma")
    Long countByFormaFarmaceutica(@Param("forma") String formaFarmaceutica);
}
