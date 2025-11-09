package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Receita;
import com.fiap.teleconsultas.model.ReceitaItem;
import com.fiap.teleconsultas.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Controller REST para gerenciamento de Receitas
 * Fornece endpoints para operações com receitas médicas
 */
@RestController
@RequestMapping("/receitas")
@Validated
@CrossOrigin(origins = "*")
public class ReceitaController {
    
    @Autowired
    private ReceitaService receitaService;
    
    /**
     * Lista todas as receitas
     * GET /receitas
     */
    @GetMapping
    public ResponseEntity<List<Receita>> listarTodas() {
        List<Receita> receitas = receitaService.listarTodas();
        return ResponseEntity.ok(receitas);
    }
    
    /**
     * Busca receita por ID
     * GET /receitas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscarPorId(@PathVariable Long id) {
        Receita receita = receitaService.buscarPorId(id);
        return ResponseEntity.ok(receita);
    }
    
    /**
     * Busca receita por consulta
     * GET /receitas/consulta/{consultaId}
     */
    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<Receita> buscarPorConsulta(@PathVariable Long consultaId) {
        Receita receita = receitaService.buscarPorConsulta(consultaId);
        return ResponseEntity.ok(receita);
    }
    
    /**
     * Busca receitas por paciente
     * GET /receitas/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Receita>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<Receita> receitas = receitaService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(receitas);
    }
    
    /**
     * Busca receitas por médico
     * GET /receitas/medico/{medicoId}
     */
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<Receita>> buscarPorMedico(@PathVariable Long medicoId) {
        List<Receita> receitas = receitaService.buscarPorMedico(medicoId);
        return ResponseEntity.ok(receitas);
    }
    
    /**
     * Cria nova receita para consulta
     * POST /receitas/consulta/{consultaId}
     */
    @PostMapping("/consulta/{consultaId}")
    public ResponseEntity<Receita> criar(@PathVariable Long consultaId,
                                         @Valid @RequestBody Receita receita) {
        Receita novaReceita = receitaService.criar(consultaId, receita);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReceita);
    }
    
    /**
     * Adiciona item à receita
     * POST /receitas/{receitaId}/itens
     */
    @PostMapping("/{receitaId}/itens")
    public ResponseEntity<Receita> adicionarItem(@PathVariable Long receitaId,
                                                 @Valid @RequestBody ReceitaItem item) {
        Receita receita = receitaService.adicionarItem(receitaId, item);
        return ResponseEntity.ok(receita);
    }
    
    /**
     * Remove item da receita
     * DELETE /receitas/{receitaId}/itens/{itemId}
     */
    @DeleteMapping("/{receitaId}/itens/{itemId}")
    public ResponseEntity<Receita> removerItem(@PathVariable Long receitaId,
                                               @PathVariable Long itemId) {
        Receita receita = receitaService.removerItem(receitaId, itemId);
        return ResponseEntity.ok(receita);
    }
    
    /**
     * Atualiza observações da receita
     * PATCH /receitas/{id}/observacoes
     */
    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Receita> atualizarObservacoes(@PathVariable Long id,
                                                        @RequestBody String observacoes) {
        Receita receita = receitaService.atualizarObservacoes(id, observacoes);
        return ResponseEntity.ok(receita);
    }
    
    /**
     * Deleta receita
     * DELETE /receitas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        receitaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Busca receitas por período
     * GET /receitas/periodo?dataInicio=yyyy-MM-dd&dataFim=yyyy-MM-dd
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<Receita>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim) {
        List<Receita> receitas = receitaService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(receitas);
    }
    
    /**
     * Conta receitas por paciente
     * GET /receitas/count/paciente/{pacienteId}
     */
    @GetMapping("/count/paciente/{pacienteId}")
    public ResponseEntity<Long> contarPorPaciente(@PathVariable Long pacienteId) {
        long total = receitaService.contarPorPaciente(pacienteId);
        return ResponseEntity.ok(total);
    }
}
