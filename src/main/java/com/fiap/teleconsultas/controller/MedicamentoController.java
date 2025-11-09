package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Medicamento;
import com.fiap.teleconsultas.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller REST para gerenciamento de Medicamentos
 * Fornece endpoints para operações CRUD com medicamentos
 */
@RestController
@RequestMapping("/medicamentos")
@Validated
@CrossOrigin(origins = "*")
public class MedicamentoController {
    
    @Autowired
    private MedicamentoService medicamentoService;
    
    /**
     * Lista todos os medicamentos
     * GET /medicamentos
     */
    @GetMapping
    public ResponseEntity<List<Medicamento>> listarTodos() {
        List<Medicamento> medicamentos = medicamentoService.listarTodos();
        return ResponseEntity.ok(medicamentos);
    }
    
    /**
     * Busca medicamento por ID
     * GET /medicamentos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Long id) {
        Medicamento medicamento = medicamentoService.buscarPorId(id);
        return ResponseEntity.ok(medicamento);
    }
    
    /**
     * Busca medicamento por nome
     * GET /medicamentos/nome/{nome}
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Medicamento> buscarPorNome(@PathVariable String nome) {
        Medicamento medicamento = medicamentoService.buscarPorNome(nome);
        return ResponseEntity.ok(medicamento);
    }
    
    /**
     * Busca medicamentos por nome parcial
     * GET /medicamentos/buscar?nome=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Medicamento>> buscarPorNomeParcial(@RequestParam String nome) {
        List<Medicamento> medicamentos = medicamentoService.buscarPorNomeParcial(nome);
        return ResponseEntity.ok(medicamentos);
    }
    
    /**
     * Cadastra novo medicamento
     * POST /medicamentos
     */
    @PostMapping
    public ResponseEntity<Medicamento> cadastrar(@Valid @RequestBody Medicamento medicamento) {
        Medicamento novoMedicamento = medicamentoService.cadastrar(medicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedicamento);
    }
    
    /**
     * Atualiza medicamento existente
     * PUT /medicamentos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizar(@PathVariable Long id, 
                                                 @Valid @RequestBody Medicamento medicamento) {
        Medicamento medicamentoAtualizado = medicamentoService.atualizar(id, medicamento);
        return ResponseEntity.ok(medicamentoAtualizado);
    }
    
    /**
     * Deleta medicamento
     * DELETE /medicamentos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Conta total de medicamentos
     * GET /medicamentos/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> contarTotal() {
        long total = medicamentoService.contarTotal();
        return ResponseEntity.ok(total);
    }
    
    /**
     * Lista formas farmacêuticas disponíveis
     * GET /medicamentos/formas-farmaceuticas
     */
    @GetMapping("/formas-farmaceuticas")
    public ResponseEntity<List<String>> listarFormasFarmaceuticas() {
        List<String> formas = medicamentoService.listarFormasFarmaceuticas();
        return ResponseEntity.ok(formas);
    }
}
