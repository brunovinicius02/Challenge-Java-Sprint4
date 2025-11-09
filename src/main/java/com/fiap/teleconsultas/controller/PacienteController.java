package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Paciente;
import com.fiap.teleconsultas.service.PacienteService;
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
 * Controller REST para gerenciamento de Pacientes
 */
@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    /**
     * GET /api/pacientes - Listar todos os pacientes
     */
    @GetMapping
    @Operation(summary = "Listar todos os pacientes", description = "Retorna lista de todos os pacientes cadastrados ordenados por nome")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Paciente>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * GET /api/pacientes/{id} - Buscar paciente por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }

    /**
     * GET /api/pacientes/cpf/{cpf} - Buscar paciente por CPF
     */
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar paciente por CPF", description = "Retorna um paciente específico pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<Paciente> buscarPorCpf(@PathVariable String cpf) {
        Paciente paciente = pacienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(paciente);
    }

    /**
     * GET /api/pacientes/buscar?nome=xxx - Buscar pacientes por nome
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar pacientes por nome", description = "Retorna lista de pacientes que contém o nome fornecido")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<List<Paciente>> buscarPorNome(@RequestParam String nome) {
        List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * POST /api/pacientes - Cadastrar novo paciente
     */
    @PostMapping
    @Operation(summary = "Cadastrar novo paciente", description = "Cria um novo paciente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CPF já cadastrado")
    })
    public ResponseEntity<Paciente> cadastrar(@Valid @RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteService.cadastrar(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    /**
     * PUT /api/pacientes/{id} - Atualizar paciente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, 
                                              @Valid @RequestBody Paciente paciente) {
        Paciente pacienteAtualizado = pacienteService.atualizar(id, paciente);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    /**
     * PATCH /api/pacientes/{id}/telefone - Atualizar apenas o telefone
     */
    @PatchMapping("/{id}/telefone")
    @Operation(summary = "Atualizar telefone", description = "Atualiza apenas o telefone de um paciente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
        @ApiResponse(responseCode = "400", description = "Telefone inválido")
    })
    public ResponseEntity<Paciente> atualizarTelefone(@PathVariable Long id, 
                                                      @RequestBody Map<String, String> body) {
        String novoTelefone = body.get("telefone");
        Paciente pacienteAtualizado = pacienteService.atualizarTelefone(id, novoTelefone);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    /**
     * DELETE /api/pacientes/{id} - Deletar paciente
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar paciente", description = "Remove um paciente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Paciente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/pacientes/count - Contar total de pacientes
     */
    @GetMapping("/count")
    @Operation(summary = "Contar pacientes", description = "Retorna o número total de pacientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Map<String, Long>> contarTotal() {
        long total = pacienteService.contarTotal();
        return ResponseEntity.ok(Map.of("total", total));
    }
}
