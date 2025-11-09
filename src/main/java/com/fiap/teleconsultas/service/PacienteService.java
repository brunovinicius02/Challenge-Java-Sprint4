package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.exception.BusinessException;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.model.Paciente;
import com.fiap.teleconsultas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service para regras de negócio de Paciente
 */
@Service
@Transactional
public class PacienteService implements IPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    /**
     * Listar todos os pacientes
     */
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Buscar paciente por ID
     */
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "id", id));
    }

    /**
     * Buscar paciente por CPF
     */
    public Paciente buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "CPF", cpf));
    }

    /**
     * Buscar pacientes por nome
     */
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Cadastrar novo paciente
     */
    public Paciente cadastrar(Paciente paciente) {
        // Validar CPF
        if (!paciente.isCpfValido()) {
            throw new BusinessException("CPF inválido: " + paciente.getCpf());
        }

        // Verificar se CPF já existe
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new BusinessException("Já existe um paciente cadastrado com o CPF: " + paciente.getCpf());
        }

        // Normalizar CPF (remover formatação)
        paciente.setCpf(paciente.getCpf().replaceAll("[^\\d]", ""));

        // Normalizar nome (primeira letra maiúscula)
        paciente.setNome(capitalizarNome(paciente.getNome()));

        // ID será gerado automaticamente pela sequence SEQ_PACIENTE
        return pacienteRepository.save(paciente);
    }

    /**
     * Atualizar paciente existente
     */
    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {
        // Verificar se paciente existe
        Paciente pacienteExistente = buscarPorId(id);

        // Validar CPF
        if (!pacienteAtualizado.isCpfValido()) {
            throw new BusinessException("CPF inválido: " + pacienteAtualizado.getCpf());
        }

        // Verificar se CPF já existe para outro paciente
        String cpfNormalizado = pacienteAtualizado.getCpf().replaceAll("[^\\d]", "");
        if (pacienteRepository.existsByCpfAndIdNot(cpfNormalizado, id)) {
            throw new BusinessException("Já existe outro paciente cadastrado com o CPF: " + cpfNormalizado);
        }

        // Atualizar dados
        pacienteExistente.setNome(capitalizarNome(pacienteAtualizado.getNome()));
        pacienteExistente.setCpf(cpfNormalizado);
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());

        return pacienteRepository.save(pacienteExistente);
    }

    /**
     * Deletar paciente
     */
    public void deletar(Long id) {
        Paciente paciente = buscarPorId(id);
        pacienteRepository.delete(paciente);
    }

    /**
     * Atualizar telefone do paciente
     */
    public Paciente atualizarTelefone(Long id, String novoTelefone) {
        Paciente paciente = buscarPorId(id);
        
        // Validar formato do telefone
        if (!novoTelefone.matches("\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}")) {
            throw new BusinessException("Formato de telefone inválido: " + novoTelefone);
        }
        
        paciente.setTelefone(novoTelefone);
        return pacienteRepository.save(paciente);
    }

    /**
     * Verificar se paciente existe
     */
    public boolean existe(Long id) {
        return pacienteRepository.existsById(id);
    }

    /**
     * Contar total de pacientes
     */
    public long contarTotal() {
        return pacienteRepository.count();
    }

    // Métodos auxiliares privados

    /**
     * Capitalizar nome (primeira letra de cada palavra em maiúscula)
     */
    private String capitalizarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return nome;
        }

        String[] palavras = nome.trim().toLowerCase().split("\\s+");
        StringBuilder nomeCapitalizado = new StringBuilder();

        for (String palavra : palavras) {
            if (palavra.length() > 0) {
                nomeCapitalizado.append(Character.toUpperCase(palavra.charAt(0)))
                                .append(palavra.substring(1))
                                .append(" ");
            }
        }

        return nomeCapitalizado.toString().trim();
    }

    /**
     * Contar total de pacientes
     */
    public long contar() {
        return pacienteRepository.count();
    }
}
