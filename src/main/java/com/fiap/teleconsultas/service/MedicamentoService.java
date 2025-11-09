package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.model.Medicamento;
import com.fiap.teleconsultas.repository.MedicamentoRepository;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service para gerenciamento de Medicamentos
 * Contém a lógica de negócio para operações com medicamentos
 */
@Service
@Transactional
public class MedicamentoService {
    
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    /**
     * Lista todos os medicamentos
     */
    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAllByOrderByNomeAsc();
    }
    
    /**
     * Busca medicamento por ID
     */
    public Medicamento buscarPorId(Long id) {
        return medicamentoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado com ID: " + id));
    }
    
    /**
     * Busca medicamento por nome
     */
    public Medicamento buscarPorNome(String nome) {
        return medicamentoRepository.findByNome(nome)
            .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado: " + nome));
    }
    
    /**
     * Busca medicamentos por nome parcial
     */
    public List<Medicamento> buscarPorNomeParcial(String nome) {
        return medicamentoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Cadastra novo medicamento
     */
    public Medicamento cadastrar(Medicamento medicamento) {
        // Valida se já existe medicamento com o mesmo nome
        if (medicamentoRepository.existsByNome(medicamento.getNome())) {
            throw new BusinessException("Já existe um medicamento cadastrado com o nome: " + medicamento.getNome());
        }
        
        // Define ID manualmente para Oracle
        Long maxId = medicamentoRepository.findAll().stream()
            .mapToLong(Medicamento::getId)
            .max()
            .orElse(0L);
        medicamento.setId(maxId + 1);
        
        return medicamentoRepository.save(medicamento);
    }
    
    /**
     * Atualiza medicamento existente
     */
    public Medicamento atualizar(Long id, Medicamento medicamentoDados) {
        Medicamento medicamento = buscarPorId(id);
        
        // Verifica se o novo nome já existe (exceto para o próprio medicamento)
        if (!medicamento.getNome().equals(medicamentoDados.getNome()) &&
            medicamentoRepository.existsByNome(medicamentoDados.getNome())) {
            throw new BusinessException("Já existe outro medicamento com o nome: " + medicamentoDados.getNome());
        }
        
        medicamento.setNome(medicamentoDados.getNome());
        medicamento.setConcentracao(medicamentoDados.getConcentracao());
        medicamento.setFormaFarmaceutica(medicamentoDados.getFormaFarmaceutica());
        
        return medicamentoRepository.save(medicamento);
    }
    
    /**
     * Deleta medicamento
     */
    public void deletar(Long id) {
        Medicamento medicamento = buscarPorId(id);
        
        // Aqui você poderia adicionar validação se o medicamento está sendo usado em alguma receita
        
        medicamentoRepository.delete(medicamento);
    }
    
    /**
     * Conta total de medicamentos
     */
    public long contarTotal() {
        return medicamentoRepository.count();
    }
    
    /**
     * Lista formas farmacêuticas únicas
     */
    public List<String> listarFormasFarmaceuticas() {
        return medicamentoRepository.findAll().stream()
            .map(Medicamento::getFormaFarmaceutica)
            .distinct()
            .sorted()
            .toList();
    }
}
