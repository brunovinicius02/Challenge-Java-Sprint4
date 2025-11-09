package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Medico;
import com.fiap.teleconsultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para gerenciamento de Médicos
 */
@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Endpoints para gerenciamento de médicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    /**
     * GET /api/medicos - Listar todos os médicos
     */
    @GetMapping
    @Operation(summary = "Listar todos os médicos", description = "Retorna lista de todos os médicos cadastrados ordenados por nome")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Medico>> listarTodos() {
        List<Medico> medicos = medicoService.listarTodos();
        return ResponseEntity.ok(medicos);
    }

    /**
     * GET /api/medicos/{id} - Buscar médico por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por ID", description = "Retorna um médico específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico encontrado"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorId(id);
        return ResponseEntity.ok(medico);
    }

    /**
     * GET /api/medicos/crm/{crm} - Buscar médico por CRM
     */
    @GetMapping("/crm/{crm}")
    @Operation(summary = "Buscar médico por CRM", description = "Retorna um médico específico pelo CRM")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico encontrado"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<Medico> buscarPorCrm(@PathVariable String crm) {
        Medico medico = medicoService.buscarPorCrm(crm);
        return ResponseEntity.ok(medico);
    }

    /**
     * GET /api/medicos/buscar?nome=xxx - Buscar médicos por nome
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar médicos por nome", description = "Retorna lista de médicos que contém o nome fornecido")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<List<Medico>> buscarPorNome(@RequestParam String nome) {
        List<Medico> medicos = medicoService.buscarPorNome(nome);
        return ResponseEntity.ok(medicos);
    }

    /**
     * GET /api/medicos/especialidade/{especialidade} - Buscar médicos por especialidade
     */
    @GetMapping("/especialidade/{especialidade}")
    @Operation(summary = "Buscar médicos por especialidade", description = "Retorna lista de médicos de uma especialidade")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<List<Medico>> buscarPorEspecialidade(@PathVariable String especialidade) {
        List<Medico> medicos = medicoService.buscarPorEspecialidade(especialidade);
        return ResponseEntity.ok(medicos);
    }

    /**
     * GET /api/medicos/especialidades - Listar todas as especialidades
     */
    @GetMapping("/especialidades")
    @Operation(summary = "Listar especialidades", description = "Retorna lista de todas as especialidades cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<String>> listarEspecialidades() {
        List<String> especialidades = medicoService.listarEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    /**
     * POST /api/medicos - Cadastrar novo médico
     */
    @PostMapping
    @Operation(summary = "Cadastrar novo médico", description = "Cria um novo médico no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Médico criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CRM já cadastrado")
    })
    public ResponseEntity<Medico> cadastrar(@Valid @RequestBody Medico medico) {
        Medico novoMedico = medicoService.cadastrar(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }

    /**
     * PUT /api/medicos/{id} - Atualizar médico
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico", description = "Atualiza os dados de um médico existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Medico> atualizar(@PathVariable Long id, 
                                           @Valid @RequestBody Medico medico) {
        Medico medicoAtualizado = medicoService.atualizar(id, medico);
        return ResponseEntity.ok(medicoAtualizado);
    }

    /**
     * PATCH /api/medicos/{id}/especialidade - Atualizar apenas a especialidade
     */
    @PatchMapping("/{id}/especialidade")
    @Operation(summary = "Atualizar especialidade", description = "Atualiza apenas a especialidade de um médico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
        @ApiResponse(responseCode = "400", description = "Especialidade inválida")
    })
    public ResponseEntity<Medico> atualizarEspecialidade(@PathVariable Long id, 
                                                         @RequestBody Map<String, String> body) {
        String novaEspecialidade = body.get("especialidade");
        Medico medicoAtualizado = medicoService.atualizarEspecialidade(id, novaEspecialidade);
        return ResponseEntity.ok(medicoAtualizado);
    }

    /**
     * DELETE /api/medicos/{id} - Deletar médico
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar médico", description = "Remove um médico do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Médico deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/medicos/count - Contar total de médicos
     */
    @GetMapping("/count")
    @Operation(summary = "Contar médicos", description = "Retorna o número total de médicos cadastrados")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Map<String, Long>> contarTotal() {
        long total = medicoService.contarTotal();
        return ResponseEntity.ok(Map.of("total", total));
    }
}
