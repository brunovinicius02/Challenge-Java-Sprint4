package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.model.Paciente;
import java.util.List;

/**
 * Interface para serviços de Paciente
 * Segue padrão SOLID - Interface Segregation Principle
 */
public interface IPacienteService {

    List<Paciente> listarTodos();
    
    Paciente buscarPorId(Long id);
    
    Paciente buscarPorCpf(String cpf);
    
    List<Paciente> buscarPorNome(String nome);
    
    Paciente cadastrar(Paciente paciente);
    
    Paciente atualizar(Long id, Paciente paciente);
    
    Paciente atualizarTelefone(Long id, String telefone);
    
    void deletar(Long id);
    
    long contar();
}
