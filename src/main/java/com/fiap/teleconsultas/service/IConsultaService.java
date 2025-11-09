package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.model.Consulta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Interface para serviços de Consulta
 * Segue padrão SOLID - Interface Segregation Principle
 */
public interface IConsultaService {

    List<Consulta> listarTodas();
    
    Consulta buscarPorId(Long id);
    
    List<Consulta> buscarPorPaciente(Long pacienteId);
    
    List<Consulta> buscarPorMedico(Long medicoId);
    
    List<Consulta> buscarPorStatus(String status);
    
    List<Consulta> buscarConsultasFuturasPorPaciente(Long pacienteId);
    
    List<Consulta> buscarConsultasFuturasPorMedico(Long medicoId);
    
    List<Consulta> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
    
    List<Consulta> buscarConsultasDoDia();
    
    Consulta agendar(Consulta consulta);
    
    Consulta atualizar(Long id, Consulta consulta);
    
    Consulta confirmar(Long id);
    
    Consulta realizar(Long id);
    
    Consulta cancelar(Long id, String motivo);
    
    Consulta registrarFalta(Long id);
    
    void deletar(Long id);
    
    Map<String, Object> obterEstatisticasPaciente(Long pacienteId);
    
    Map<String, Object> obterEstatisticasGerais();
}
