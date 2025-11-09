package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.dto.AgendamentoPacienteDTO;
import com.fiap.teleconsultas.dto.AgendamentoResponseDTO;
import com.fiap.teleconsultas.exception.BusinessException;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.model.Consulta;
import com.fiap.teleconsultas.model.Medico;
import com.fiap.teleconsultas.model.Paciente;
import com.fiap.teleconsultas.repository.ConsultaRepository;
import com.fiap.teleconsultas.repository.MedicoRepository;
import com.fiap.teleconsultas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para agendamento de consultas pelo paciente
 */
@Service
public class AgendamentoPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    /**
     * Cria um agendamento a partir dos dados do formulário do frontend
     */
    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoPacienteDTO dto) {
        // 1. Buscar ou criar paciente
        Paciente paciente = buscarOuCriarPaciente(dto);

        // 2. Buscar médico pela especialidade (procedimento)
        Medico medico = buscarMedicoPorProcedimento(dto.getProcedimento());

        // 3. Validar horário
        LocalDateTime dataHoraConsulta = montarDataHora(dto.getData(), dto.getHora());
        validarHorario(dataHoraConsulta);

        // 4. Verificar conflitos de horário
        verificarConflitos(paciente, medico, dataHoraConsulta);

        // 5. Criar consulta
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setTipoConsulta(dto.getModalidade());
        consulta.setDataConsulta(dataHoraConsulta);
        consulta.setStatusConsulta("Agendada");
        consulta.setProcedimento(dto.getProcedimento());
        consulta.setUnidade(dto.getUnidade());

        consulta = consultaRepository.save(consulta);

        // 6. Retornar DTO de resposta
        return converterParaResponse(consulta, dto.getIdade());
    }

    /**
     * Lista todos os agendamentos (consultas)
     */
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(c -> converterParaResponse(c, calcularIdade(c.getPaciente().getDataNascimento())))
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos por paciente (telefone)
     */
    public List<AgendamentoResponseDTO> buscarPorTelefone(String telefone) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByTelefone(telefone);
        if (pacienteOpt.isEmpty()) {
            return List.of();
        }

        List<Consulta> consultas = consultaRepository.findByPaciente(pacienteOpt.get());
        return consultas.stream()
                .map(c -> converterParaResponse(c, calcularIdade(c.getPaciente().getDataNascimento())))
                .collect(Collectors.toList());
    }

    /**
     * Busca ou cria um paciente baseado nos dados do formulário
     */
    private Paciente buscarOuCriarPaciente(AgendamentoPacienteDTO dto) {
        // Tenta buscar por telefone
        Optional<Paciente> pacienteExistente = pacienteRepository.findByTelefone(dto.getTelefone());
        
        if (pacienteExistente.isPresent()) {
            return pacienteExistente.get();
        }

        // Gera CPF temporário único baseado no telefone + timestamp
        String cpfBase = dto.getTelefone().replaceAll("[^\\d]", "");
        String timestamp = String.valueOf(System.currentTimeMillis());
        String cpfTemp = (cpfBase + timestamp).replaceAll("[^\\d]", "");
        
        if (cpfTemp.length() < 11) {
            cpfTemp = String.format("%011d", Long.parseLong(cpfTemp));
        } else if (cpfTemp.length() > 11) {
            cpfTemp = cpfTemp.substring(cpfTemp.length() - 11);
        }

        // Verifica se CPF já existe
        while (pacienteRepository.existsByCpf(cpfTemp)) {
            cpfTemp = String.valueOf(Long.parseLong(cpfTemp) + 1);
        }

        // Email temporário único
        String emailTemp = cpfTemp + "@temp.com";
        
        // Cria novo paciente
        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome(dto.getNome());
        novoPaciente.setTelefone(dto.getTelefone());
        novoPaciente.setCpf(cpfTemp);
        novoPaciente.setEmail(emailTemp);

        // Calcula data de nascimento aproximada baseada na idade
        try {
            int idade = Integer.parseInt(dto.getIdade());
            LocalDate dataNasc = LocalDate.now().minusYears(idade);
            novoPaciente.setDataNascimento(java.sql.Date.valueOf(dataNasc));
        } catch (Exception e) {
            // Se idade inválida, usa 30 anos como padrão
            LocalDate dataNasc = LocalDate.now().minusYears(30);
            novoPaciente.setDataNascimento(java.sql.Date.valueOf(dataNasc));
        }

        // Sexo padrão
        novoPaciente.setSexo("O");

        try {
            Paciente pacienteSalvo = pacienteRepository.save(novoPaciente);
            pacienteRepository.flush(); // ⭐ FORÇA COMMIT IMEDIATO
            return pacienteSalvo;
        } catch (Exception e) {
            throw new BusinessException("Erro ao criar paciente: " + e.getMessage());
        }
    }

    /**
     * Busca um médico pela especialidade (procedimento)
     */
    private Medico buscarMedicoPorProcedimento(String procedimento) {
        List<Medico> medicos = medicoRepository.findByEspecialidade(procedimento);
        
        if (medicos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum médico encontrado para o procedimento: " + procedimento);
        }

        // Retorna o primeiro médico disponível
        return medicos.get(0);
    }

    /**
     * Monta LocalDateTime a partir de data e hora strings
     */
    private LocalDateTime montarDataHora(String data, String hora) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String dataHoraStr = data + " " + hora;
            return LocalDateTime.parse(dataHoraStr, formatter);
        } catch (Exception e) {
            throw new BusinessException("Formato de data/hora inválido");
        }
    }

    /**
     * Valida se o horário está dentro das regras de negócio
     */
    private void validarHorario(LocalDateTime dataHora) {
        // Não pode ser no passado
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Data da consulta não pode ser no passado");
        }

        // Horário comercial: 8h às 18h
        int hora = dataHora.getHour();
        if (hora < 8 || hora >= 18) {
            throw new BusinessException("Horário deve estar entre 08:00 e 18:00");
        }

        // Apenas dias úteis (segunda a sexta)
        int diaSemana = dataHora.getDayOfWeek().getValue();
        if (diaSemana > 5) {
            throw new BusinessException("Agendamentos são permitidos apenas em dias úteis (segunda a sexta)");
        }
    }

    /**
     * Verifica conflitos de horário
     */
    private void verificarConflitos(Paciente paciente, Medico medico, LocalDateTime dataHora) {
        // Verifica se médico tem horário livre
        List<Consulta> consultasMedico = consultaRepository
                .findByMedicoAndDataConsulta(medico, dataHora);
        
        if (!consultasMedico.isEmpty()) {
            throw new BusinessException("Médico já possui consulta agendada neste horário");
        }

        // Verifica se paciente tem horário livre
        List<Consulta> consultasPaciente = consultaRepository
                .findByPacienteAndDataConsulta(paciente, dataHora);
        
        if (!consultasPaciente.isEmpty()) {
            throw new BusinessException("Paciente já possui consulta agendada neste horário");
        }
    }

    /**
     * Converte Consulta para AgendamentoResponseDTO
     */
    private AgendamentoResponseDTO converterParaResponse(Consulta consulta, String idade) {
        AgendamentoResponseDTO response = new AgendamentoResponseDTO();
        response.setId(consulta.getId());
        response.setNome(consulta.getPaciente().getNome());
        response.setIdade(idade);
        response.setTelefone(consulta.getPaciente().getTelefone());
        response.setTipo("Consulta"); // Sempre consulta por enquanto
        response.setModalidade(consulta.getTipoConsulta());
        response.setProcedimento(consulta.getProcedimento());
        
        // Formata data e hora
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        response.setData(consulta.getDataConsulta().format(dateFormatter));
        response.setHora(consulta.getDataConsulta().format(timeFormatter));
        
        response.setUnidade(consulta.getUnidade());
        response.setStatus(mapearStatus(consulta.getStatusConsulta()));
        
        return response;
    }

    /**
     * Mapeia status do backend para frontend
     */
    private String mapearStatus(String statusBackend) {
        switch (statusBackend) {
            case "Agendada":
            case "Confirmada":
                return "AGENDADA";
            case "Cancelada":
                return "CANCELADA";
            case "Realizada":
                return "AGENDADA"; // Frontend não tem status "Realizada"
            default:
                return "AGENDADA";
        }
    }

    /**
     * Calcula idade a partir da data de nascimento
     */
    private String calcularIdade(Date dataNascimento) {
        if (dataNascimento == null) {
            return "0";
        }
        LocalDate nascimento = new java.sql.Date(dataNascimento.getTime()).toLocalDate();
        LocalDate hoje = LocalDate.now();
        Period periodo = Period.between(nascimento, hoje);
        return String.valueOf(periodo.getYears());
    }
}
