package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.model.*;
import com.fiap.teleconsultas.repository.*;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Service para gerenciamento de Receitas
 * Contém a lógica de negócio para operações com receitas médicas
 */
@Service
@Transactional
public class ReceitaService {
    
    @Autowired
    private ReceitaRepository receitaRepository;
    
    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private MedicamentoRepository medicamentoRepository;
    
    /**
     * Lista todas as receitas
     */
    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }
    
    /**
     * Busca receita por ID
     */
    public Receita buscarPorId(Long id) {
        return receitaRepository.findByIdWithItens(id)
            .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com ID: " + id));
    }
    
    /**
     * Busca receita por consulta
     */
    public Receita buscarPorConsulta(Long consultaId) {
        return receitaRepository.findByConsultaId(consultaId)
            .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada para consulta: " + consultaId));
    }
    
    /**
     * Busca receitas por paciente
     */
    public List<Receita> buscarPorPaciente(Long pacienteId) {
        return receitaRepository.findByPacienteId(pacienteId);
    }
    
    /**
     * Busca receitas por médico
     */
    public List<Receita> buscarPorMedico(Long medicoId) {
        return receitaRepository.findByMedicoId(medicoId);
    }
    
    /**
     * Cria nova receita para uma consulta
     */
    public Receita criar(Long consultaId, Receita receita) {
        // Busca a consulta
        Consulta consulta = consultaRepository.findById(consultaId)
            .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada: " + consultaId));
        
        // Valida se a consulta já tem receita
        if (receitaRepository.findByConsulta(consulta).isPresent()) {
            throw new BusinessException("Esta consulta já possui uma receita cadastrada");
        }
        
        // Valida se a consulta foi realizada
        if (!"Realizada".equals(consulta.getStatusConsulta())) {
            throw new BusinessException("Receita só pode ser criada para consultas realizadas");
        }
        
        // Define ID manualmente para Oracle
        Long maxId = receitaRepository.findAll().stream()
            .mapToLong(Receita::getId)
            .max()
            .orElse(0L);
        receita.setId(maxId + 1);
        
        receita.setConsulta(consulta);
        receita.setDataEmissao(new Date());
        
        return receitaRepository.save(receita);
    }
    
    /**
     * Adiciona item à receita
     */
    public Receita adicionarItem(Long receitaId, ReceitaItem item) {
        Receita receita = buscarPorId(receitaId);
        
        // Valida o medicamento
        Medicamento medicamento = medicamentoRepository.findById(item.getMedicamento().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado"));
        
        item.setMedicamento(medicamento);
        
        // Define ID manualmente para o item
        Long maxId = receita.getItens().stream()
            .mapToLong(ReceitaItem::getId)
            .max()
            .orElse(0L);
        item.setId(maxId + 1);
        
        receita.adicionarItem(item);
        
        return receitaRepository.save(receita);
    }
    
    /**
     * Remove item da receita
     */
    public Receita removerItem(Long receitaId, Long itemId) {
        Receita receita = buscarPorId(receitaId);
        
        ReceitaItem item = receita.getItens().stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado na receita"));
        
        receita.removerItem(item);
        
        return receitaRepository.save(receita);
    }
    
    /**
     * Atualiza observações da receita
     */
    public Receita atualizarObservacoes(Long id, String observacoes) {
        Receita receita = buscarPorId(id);
        receita.setObservacoes(observacoes);
        return receitaRepository.save(receita);
    }
    
    /**
     * Deleta receita
     */
    public void deletar(Long id) {
        Receita receita = buscarPorId(id);
        receitaRepository.delete(receita);
    }
    
    /**
     * Busca receitas por período
     */
    public List<Receita> buscarPorPeriodo(Date dataInicio, Date dataFim) {
        return receitaRepository.findByPeriodo(dataInicio, dataFim);
    }
    
    /**
     * Conta receitas por paciente
     */
    public long contarPorPaciente(Long pacienteId) {
        return receitaRepository.countByPacienteId(pacienteId);
    }
}
