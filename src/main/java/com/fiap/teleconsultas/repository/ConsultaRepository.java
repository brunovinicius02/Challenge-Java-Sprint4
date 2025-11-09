package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Consulta;
import com.fiap.teleconsultas.model.Medico;
import com.fiap.teleconsultas.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para operações de Consulta no banco de dados
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * Buscar consultas por paciente
     */
    List<Consulta> findByPaciente(Paciente paciente);

    /**
     * Buscar consultas por médico
     */
    List<Consulta> findByMedico(Medico medico);

    /**
     * Buscar consultas por status
     */
    List<Consulta> findByStatusConsulta(String status);

    /**
     * Buscar consultas futuras de um paciente
     */
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.dataConsulta > :dataAtual ORDER BY c.dataConsulta")
    List<Consulta> findConsultasFuturasPorPaciente(@Param("pacienteId") Long pacienteId, 
                                                     @Param("dataAtual") LocalDateTime dataAtual);

    /**
     * Buscar consultas futuras de um médico
     */
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :medicoId AND c.dataConsulta > :dataAtual ORDER BY c.dataConsulta")
    List<Consulta> findConsultasFuturasPorMedico(@Param("medicoId") Long medicoId, 
                                                  @Param("dataAtual") LocalDateTime dataAtual);

    /**
     * Buscar consultas em um período
     */
    @Query("SELECT c FROM Consulta c WHERE c.dataConsulta BETWEEN :inicio AND :fim ORDER BY c.dataConsulta")
    List<Consulta> findConsultasPorPeriodo(@Param("inicio") LocalDateTime inicio, 
                                            @Param("fim") LocalDateTime fim);

    /**
     * Contar faltas de um paciente
     */
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.statusConsulta = 'Falta'")
    long countFaltasPorPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Buscar consultas com falta
     */
    @Query("SELECT c FROM Consulta c WHERE c.statusConsulta = 'Falta' ORDER BY c.dataConsulta DESC")
    List<Consulta> findConsultasComFalta();

    /**
     * Taxa de comparecimento de um paciente
     */
    @Query("SELECT " +
           "CAST(SUM(CASE WHEN c.statusConsulta = 'Realizada' THEN 1 ELSE 0 END) AS double) / COUNT(c) * 100 " +
           "FROM Consulta c " +
           "WHERE c.paciente.id = :pacienteId " +
           "AND c.statusConsulta IN ('Realizada', 'Falta')")
    Double calcularTaxaComparecimentoPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Verificar conflito de horário para médico
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c " +
           "WHERE c.medico.id = :medicoId " +
           "AND c.dataConsulta = :dataHora " +
           "AND c.statusConsulta NOT IN ('Cancelada')")
    boolean existsConflitoPorMedico(@Param("medicoId") Long medicoId, 
                                     @Param("dataHora") LocalDateTime dataHora);

    /**
     * Verificar conflito de horário para paciente
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c " +
           "WHERE c.paciente.id = :pacienteId " +
           "AND c.dataConsulta = :dataHora " +
           "AND c.statusConsulta NOT IN ('Cancelada')")
    boolean existsConflitoPorPaciente(@Param("pacienteId") Long pacienteId, 
                                       @Param("dataHora") LocalDateTime dataHora);

    /**
     * Buscar consultas do dia
     */
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataConsulta) = CURRENT_DATE ORDER BY c.dataConsulta")
    List<Consulta> findConsultasDoDia();

    /**
     * Buscar consultas de um médico em uma data/hora específica
     */
    List<Consulta> findByMedicoAndDataConsulta(Medico medico, LocalDateTime dataHora);

    /**
     * Buscar consultas de um paciente em uma data/hora específica
     */
    List<Consulta> findByPacienteAndDataConsulta(Paciente paciente, LocalDateTime dataHora);
}
