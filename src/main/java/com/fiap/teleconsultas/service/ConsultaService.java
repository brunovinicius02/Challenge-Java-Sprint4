package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.exception.BusinessException;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.model.Consulta;
import com.fiap.teleconsultas.model.Medico;
import com.fiap.teleconsultas.model.Paciente;
import com.fiap.teleconsultas.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service para regras de negócio de Consulta
 */
@Service
@Transactional
public class ConsultaService implements IConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    // Constantes de regras de negócio
    private static final int MAX_FALTAS_PERMITIDAS = 3;
    private static final int HORAS_MINIMAS_ANTECEDENCIA = 24;

    /**
     * Listar todas as consultas
     */
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    /**
     * Buscar consulta por ID
     */
    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta", "id", id));
    }

    /**
     * Buscar consultas por paciente
     */
    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        return consultaRepository.findByPaciente(paciente);
    }

    /**
     * Buscar consultas por médico
     */
    public List<Consulta> buscarPorMedico(Long medicoId) {
        Medico medico = medicoService.buscarPorId(medicoId);
        return consultaRepository.findByMedico(medico);
    }

    /**
     * Buscar consultas por status
     */
    public List<Consulta> buscarPorStatus(String status) {
        validarStatus(status);
        return consultaRepository.findByStatusConsulta(status);
    }

    /**
     * Buscar consultas futuras de um paciente
     */
    public List<Consulta> buscarConsultasFuturasPorPaciente(Long pacienteId) {
        return consultaRepository.findConsultasFuturasPorPaciente(pacienteId, LocalDateTime.now());
    }

    /**
     * Buscar consultas futuras de um médico
     */
    public List<Consulta> buscarConsultasFuturasPorMedico(Long medicoId) {
        return consultaRepository.findConsultasFuturasPorMedico(medicoId, LocalDateTime.now());
    }

    /**
     * Buscar consultas em um período
     */
    public List<Consulta> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim)) {
            throw new BusinessException("Data inicial não pode ser posterior à data final");
        }
        return consultaRepository.findConsultasPorPeriodo(inicio, fim);
    }

    /**
     * Buscar consultas do dia
     */
    public List<Consulta> buscarConsultasDoDia() {
        return consultaRepository.findConsultasDoDia();
    }

    /**
     * Agendar nova consulta
     */
    public Consulta agendar(Consulta consulta) {
        // Validar paciente
        Paciente paciente = pacienteService.buscarPorId(consulta.getPaciente().getId());
        consulta.setPaciente(paciente);

        // Validar médico
        Medico medico = medicoService.buscarPorId(consulta.getMedico().getId());
        consulta.setMedico(medico);

        // Validar data/hora
        validarDataHoraAgendamento(consulta.getDataConsulta());

        // Verificar conflito de horário para médico
        if (consultaRepository.existsConflitoPorMedico(medico.getId(), consulta.getDataConsulta())) {
            throw new BusinessException("Médico já possui consulta agendada para este horário");
        }

        // Verificar conflito de horário para paciente
        if (consultaRepository.existsConflitoPorPaciente(paciente.getId(), consulta.getDataConsulta())) {
            throw new BusinessException("Paciente já possui consulta agendada para este horário");
        }

        // Verificar histórico de faltas do paciente
        long totalFaltas = consultaRepository.countFaltasPorPaciente(paciente.getId());
        if (totalFaltas >= MAX_FALTAS_PERMITIDAS) {
            throw new BusinessException(
                String.format("Paciente possui %d faltas. Limite máximo de %d faltas atingido. " +
                            "Entre em contato com a recepção.", totalFaltas, MAX_FALTAS_PERMITIDAS)
            );
        }

        // Definir status inicial
        consulta.setStatusConsulta("Agendada");

        // ID será gerado automaticamente pela sequence SEQ_CONSULTA
        return consultaRepository.save(consulta);
    }

    /**
     * Atualizar consulta
     */
    public Consulta atualizar(Long id, Consulta consultaAtualizada) {
        Consulta consultaExistente = buscarPorId(id);

        // Não permitir atualização de consultas já realizadas ou canceladas
        if ("Realizada".equals(consultaExistente.getStatusConsulta()) || 
            "Cancelada".equals(consultaExistente.getStatusConsulta())) {
            throw new BusinessException("Não é possível atualizar consulta com status: " + 
                                      consultaExistente.getStatusConsulta());
        }

        // Atualizar médico se fornecido
        if (consultaAtualizada.getMedico() != null && consultaAtualizada.getMedico().getId() != null) {
            Medico medico = medicoService.buscarPorId(consultaAtualizada.getMedico().getId());
            consultaExistente.setMedico(medico);
        }

        // Atualizar data/hora se fornecida
        if (consultaAtualizada.getDataConsulta() != null) {
            validarDataHoraAgendamento(consultaAtualizada.getDataConsulta());
            consultaExistente.setDataConsulta(consultaAtualizada.getDataConsulta());
        }

        return consultaRepository.save(consultaExistente);
    }

    /**
     * Confirmar consulta
     */
    public Consulta confirmar(Long id) {
        Consulta consulta = buscarPorId(id);
        consulta.confirmar();
        return consultaRepository.save(consulta);
    }

    /**
     * Realizar consulta (paciente compareceu)
     */
    public Consulta realizar(Long id) {
        Consulta consulta = buscarPorId(id);
        consulta.realizar();
        return consultaRepository.save(consulta);
    }

    /**
     * Cancelar consulta
     */
    public Consulta cancelar(Long id, String motivo) {
        Consulta consulta = buscarPorId(id);

        // Validar antecedência mínima para cancelamento
        LocalDateTime dataHoraConsulta = consulta.getDataConsulta();
        LocalDateTime agora = LocalDateTime.now();
        long horasRestantes = java.time.Duration.between(agora, dataHoraConsulta).toHours();

        if (horasRestantes < HORAS_MINIMAS_ANTECEDENCIA) {
            throw new BusinessException(
                String.format("Cancelamento deve ser feito com pelo menos %d horas de antecedência. " +
                            "Restam apenas %d horas.", HORAS_MINIMAS_ANTECEDENCIA, horasRestantes)
            );
        }

        consulta.cancelar();
        return consultaRepository.save(consulta);
    }

    /**
     * Registrar falta do paciente
     */
    public Consulta registrarFalta(Long id) {
        Consulta consulta = buscarPorId(id);
        // Alterando status para "Falta" já que não existe método registrarFalta() na entidade
        consulta.setStatusConsulta("Falta");
        return consultaRepository.save(consulta);
    }

    /**
     * Deletar consulta (apenas agendadas e com antecedência)
     */
    public void deletar(Long id) {
        Consulta consulta = buscarPorId(id);

        if ("Realizada".equals(consulta.getStatusConsulta())) {
            throw new BusinessException("Não é possível deletar consulta já realizada");
        }

        consultaRepository.delete(consulta);
    }

    /**
     * Obter estatísticas de absenteísmo de um paciente
     */
    public Map<String, Object> obterEstatisticasAbsenteismoPaciente(Long pacienteId) {
        Paciente paciente = pacienteService.buscarPorId(pacienteId);
        
        List<Consulta> todasConsultas = consultaRepository.findByPaciente(paciente);
        long totalConsultas = todasConsultas.stream()
            .filter(c -> "Realizada".equals(c.getStatusConsulta()) || "Falta".equals(c.getStatusConsulta()))
            .count();
        
        long totalFaltas = consultaRepository.countFaltasPorPaciente(pacienteId);
        long totalComparecimentos = totalConsultas - totalFaltas;
        
        Double taxaComparecimento = totalConsultas > 0 ? 
            (totalComparecimentos * 100.0 / totalConsultas) : 0.0;
        
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("pacienteId", pacienteId);
        estatisticas.put("pacienteNome", paciente.getNome());
        estatisticas.put("totalConsultas", totalConsultas);
        estatisticas.put("totalComparecimentos", totalComparecimentos);
        estatisticas.put("totalFaltas", totalFaltas);
        estatisticas.put("taxaComparecimento", String.format("%.2f%%", taxaComparecimento));
        estatisticas.put("statusRisco", totalFaltas >= MAX_FALTAS_PERMITIDAS ? "ALTO" : 
                                       totalFaltas >= 2 ? "MÉDIO" : "BAIXO");
        
        return estatisticas;
    }

    /**
     * Alias para obterEstatisticasAbsenteismoPaciente (compatibilidade com interface)
     */
    public Map<String, Object> obterEstatisticasPaciente(Long pacienteId) {
        return obterEstatisticasAbsenteismoPaciente(pacienteId);
    }

    /**
     * Obter estatísticas gerais do sistema
     */
    public Map<String, Object> obterEstatisticasGerais() {
        List<Consulta> todasConsultas = consultaRepository.findAll();
        
        long totalAgendadas = todasConsultas.stream()
            .filter(c -> "Agendada".equals(c.getStatusConsulta()) || "Confirmada".equals(c.getStatusConsulta()))
            .count();
        
        long totalRealizadas = todasConsultas.stream()
            .filter(c -> "Realizada".equals(c.getStatusConsulta()))
            .count();
        
        long totalFaltas = consultaRepository.findConsultasComFalta().size();
        long totalCanceladas = todasConsultas.stream()
            .filter(c -> "Cancelada".equals(c.getStatusConsulta()))
            .count();
        
        long totalComputavel = totalRealizadas + totalFaltas;
        Double taxaComparecimentoGeral = totalComputavel > 0 ?
            (totalRealizadas * 100.0 / totalComputavel) : 0.0;
        
        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("totalConsultas", todasConsultas.size());
        estatisticas.put("consultasAgendadas", totalAgendadas);
        estatisticas.put("consultasRealizadas", totalRealizadas);
        estatisticas.put("consultasFaltosas", totalFaltas);
        estatisticas.put("consultasCanceladas", totalCanceladas);
        estatisticas.put("taxaComparecimentoGeral", String.format("%.2f%%", taxaComparecimentoGeral));
        
        return estatisticas;
    }

    // Métodos auxiliares privados

    /**
     * Validar data/hora do agendamento
     */
    private void validarDataHoraAgendamento(LocalDateTime dataHora) {
        if (dataHora == null) {
            throw new BusinessException("Data e hora são obrigatórias");
        }

        LocalDateTime agora = LocalDateTime.now();
        
        if (dataHora.isBefore(agora)) {
            throw new BusinessException("Data e hora devem ser no futuro");
        }

        // Validar horário comercial (8h às 18h)
        int hora = dataHora.getHour();
        if (hora < 8 || hora >= 18) {
            throw new BusinessException("Consultas devem ser agendadas entre 8h e 18h");
        }

        // Validar dias úteis (segunda a sexta)
        int diaSemana = dataHora.getDayOfWeek().getValue();
        if (diaSemana == 6 || diaSemana == 7) {
            throw new BusinessException("Consultas devem ser agendadas em dias úteis (segunda a sexta)");
        }
    }

    /**
     * Validar status da consulta
     */
    private void validarStatus(String status) {
        List<String> statusValidos = List.of("Agendada", "Confirmada", "Realizada", "Cancelada", "Falta");
        if (!statusValidos.contains(status)) {
            throw new BusinessException("Status inválido: " + status + 
                                      ". Use um dos seguintes: " + String.join(", ", statusValidos));
        }
    }
}
