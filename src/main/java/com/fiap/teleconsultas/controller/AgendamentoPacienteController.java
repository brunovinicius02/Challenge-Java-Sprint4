package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.dto.AgendamentoPacienteDTO;
import com.fiap.teleconsultas.dto.AgendamentoResponseDTO;
import com.fiap.teleconsultas.service.AgendamentoPacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para agendamento de consultas pelo paciente (frontend)
 * Base URL: /api/agendamentos
 */
@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*") // Permite CORS para o frontend React
public class AgendamentoPacienteController {

    @Autowired
    private AgendamentoPacienteService agendamentoService;

    /**
     * Cria um novo agendamento
     * POST /api/agendamentos
     */
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(
            @Valid @RequestBody AgendamentoPacienteDTO dto) {
        
        AgendamentoResponseDTO response = agendamentoService.criarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos os agendamentos
     * GET /api/agendamentos
     */
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos() {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Busca agendamentos por telefone do paciente
     * GET /api/agendamentos/telefone/{telefone}
     */
    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<List<AgendamentoResponseDTO>> buscarPorTelefone(
            @PathVariable String telefone) {
        
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.buscarPorTelefone(telefone);
        return ResponseEntity.ok(agendamentos);
    }
}
