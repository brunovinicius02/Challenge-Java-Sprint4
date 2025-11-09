package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Consulta;
import com.fiap.teleconsultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller REST para gerenciamento de Consultas
 */
@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Endpoints para gerenciamento de consultas e controle de absenteísmo")
@CrossOrigin(origins = "*")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    /**
     * GET /api/consultas - Listar todas as consultas
     */
    @GetMapping
    @Operation(summary = "Listar todas as consultas", description = "Retorna lista de todas as consultas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Consulta>> listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/{id} - Buscar consulta por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID", description = "Retorna uma consulta específica pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta encontrada"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        Consulta consulta = consultaService.buscarPorId(id);
        return ResponseEntity.ok(consulta);
    }

    /**
     * GET /api/consultas/paciente/{pacienteId} - Buscar consultas por paciente
     */
    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Buscar consultas por paciente", description = "Retorna todas as consultas de um paciente específico")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/medico/{medicoId} - Buscar consultas por médico
     */
    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Buscar consultas por médico", description = "Retorna todas as consultas de um médico específico")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarPorMedico(@PathVariable Long medicoId) {
        List<Consulta> consultas = consultaService.buscarPorMedico(medicoId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/status/{status} - Buscar consultas por status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar consultas por status", description = "Retorna consultas com status específico (Agendada, Confirmada, Realizada, Cancelada, Falta)")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarPorStatus(@PathVariable String status) {
        List<Consulta> consultas = consultaService.buscarPorStatus(status);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/paciente/{pacienteId}/futuras - Consultas futuras do paciente
     */
    @GetMapping("/paciente/{pacienteId}/futuras")
    @Operation(summary = "Consultas futuras do paciente", description = "Retorna consultas futuras agendadas para um paciente")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarConsultasFuturasPorPaciente(@PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.buscarConsultasFuturasPorPaciente(pacienteId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/medico/{medicoId}/futuras - Consultas futuras do médico
     */
    @GetMapping("/medico/{medicoId}/futuras")
    @Operation(summary = "Consultas futuras do médico", description = "Retorna consultas futuras agendadas para um médico")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarConsultasFuturasPorMedico(@PathVariable Long medicoId) {
        List<Consulta> consultas = consultaService.buscarConsultasFuturasPorMedico(medicoId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/periodo - Buscar consultas em período
     */
    @GetMapping("/periodo")
    @Operation(summary = "Buscar consultas por período", description = "Retorna consultas em um período específico")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<Consulta> consultas = consultaService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(consultas);
    }

    /**
     * GET /api/consultas/hoje - Consultas do dia
     */
    @GetMapping("/hoje")
    @Operation(summary = "Consultas do dia", description = "Retorna todas as consultas agendadas para hoje")
    @ApiResponse(responseCode = "200", description = "Consultas retornadas com sucesso")
    public ResponseEntity<List<Consulta>> buscarConsultasDoDia() {
        List<Consulta> consultas = consultaService.buscarConsultasDoDia();
        return ResponseEntity.ok(consultas);
    }

    /**
     * POST /api/consultas - Agendar nova consulta
     */
    @PostMapping
    @Operation(summary = "Agendar consulta", description = "Cria um novo agendamento de consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário"),
        @ApiResponse(responseCode = "404", description = "Paciente ou médico não encontrado")
    })
    public ResponseEntity<Consulta> agendar(@Valid @RequestBody Consulta consulta) {
        Consulta novaConsulta = consultaService.agendar(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }

    /**
     * PUT /api/consultas/{id} - Atualizar consulta
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta", description = "Atualiza dados de uma consulta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou consulta não pode ser atualizada")
    })
    public ResponseEntity<Consulta> atualizar(@PathVariable Long id, 
                                              @Valid @RequestBody Consulta consulta) {
        Consulta consultaAtualizada = consultaService.atualizar(id, consulta);
        return ResponseEntity.ok(consultaAtualizada);
    }

    /**
     * PATCH /api/consultas/{id}/confirmar - Confirmar consulta
     */
    @PatchMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar consulta", description = "Confirma uma consulta agendada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta confirmada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "409", description = "Consulta não pode ser confirmada")
    })
    public ResponseEntity<Consulta> confirmar(@PathVariable Long id) {
        Consulta consultaConfirmada = consultaService.confirmar(id);
        return ResponseEntity.ok(consultaConfirmada);
    }

    /**
     * PATCH /api/consultas/{id}/realizar - Realizar consulta (paciente compareceu)
     */
    @PatchMapping("/{id}/realizar")
    @Operation(summary = "Realizar consulta", description = "Marca consulta como realizada (paciente compareceu)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "409", description = "Consulta não pode ser realizada")
    })
    public ResponseEntity<Consulta> realizar(@PathVariable Long id) {
        Consulta consultaRealizada = consultaService.realizar(id);
        return ResponseEntity.ok(consultaRealizada);
    }

    /**
     * PATCH /api/consultas/{id}/cancelar - Cancelar consulta
     */
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta agendada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Cancelamento fora do prazo ou consulta já realizada")
    })
    public ResponseEntity<Consulta> cancelar(@PathVariable Long id, 
                                             @RequestBody(required = false) Map<String, String> body) {
        String motivo = body != null ? body.get("motivo") : null;
        Consulta consultaCancelada = consultaService.cancelar(id, motivo);
        return ResponseEntity.ok(consultaCancelada);
    }

    /**
     * PATCH /api/consultas/{id}/falta - Registrar falta do paciente
     */
    @PatchMapping("/{id}/falta")
    @Operation(summary = "Registrar falta", description = "Registra falta do paciente na consulta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Falta registrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "409", description = "Não é possível registrar falta")
    })
    public ResponseEntity<Consulta> registrarFalta(@PathVariable Long id) {
        Consulta consultaComFalta = consultaService.registrarFalta(id);
        return ResponseEntity.ok(consultaComFalta);
    }

    /**
     * DELETE /api/consultas/{id} - Deletar consulta
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar consulta", description = "Remove uma consulta do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Consulta deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Consulta não pode ser deletada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/consultas/estatisticas/paciente/{pacienteId} - Estatísticas de absenteísmo do paciente
     */
    @GetMapping("/estatisticas/paciente/{pacienteId}")
    @Operation(summary = "Estatísticas do paciente", description = "Retorna estatísticas de absenteísmo de um paciente")
    @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso")
    public ResponseEntity<Map<String, Object>> obterEstatisticasPaciente(@PathVariable Long pacienteId) {
        Map<String, Object> estatisticas = consultaService.obterEstatisticasAbsenteismoPaciente(pacienteId);
        return ResponseEntity.ok(estatisticas);
    }

    /**
     * GET /api/consultas/estatisticas/geral - Estatísticas gerais do sistema
     */
    @GetMapping("/estatisticas/geral")
    @Operation(summary = "Estatísticas gerais", description = "Retorna estatísticas gerais do sistema de consultas")
    @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso")
    public ResponseEntity<Map<String, Object>> obterEstatisticasGerais() {
        Map<String, Object> estatisticas = consultaService.obterEstatisticasGerais();
        return ResponseEntity.ok(estatisticas);
    }
}
