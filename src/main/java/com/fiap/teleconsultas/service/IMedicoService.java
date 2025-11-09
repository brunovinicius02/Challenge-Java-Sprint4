package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.model.Medico;
import java.util.List;

/**
 * Interface para serviços de Médico
 * Segue padrão SOLID - Interface Segregation Principle
 */
public interface IMedicoService {

    List<Medico> listarTodos();
    
    Medico buscarPorId(Long id);
    
    Medico buscarPorCrm(String crm);
    
    List<Medico> buscarPorNome(String nome);
    
    List<Medico> buscarPorEspecialidade(String especialidade);
    
    List<String> listarEspecialidades();
    
    Medico cadastrar(Medico medico);
    
    Medico atualizar(Long id, Medico medico);
    
    Medico atualizarEspecialidade(Long id, String especialidade);
    
    void deletar(Long id);
    
    long contar();
}
