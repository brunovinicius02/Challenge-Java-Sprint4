package com.fiap.teleconsultas.service;

import com.fiap.teleconsultas.exception.BusinessException;
import com.fiap.teleconsultas.exception.ResourceNotFoundException;
import com.fiap.teleconsultas.model.Medico;
import com.fiap.teleconsultas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service para regras de negócio de Médico
 */
@Service
@Transactional
public class MedicoService implements IMedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * Listar todos os médicos
     */
    public List<Medico> listarTodos() {
        return medicoRepository.findAllByOrderByNomeAsc();
    }

    /**
     * Buscar médico por ID
     */
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico", "id", id));
    }

    /**
     * Buscar médico por CRM
     */
    public Medico buscarPorCrm(String crm) {
        return medicoRepository.findByCrm(crm)
                .orElseThrow(() -> new ResourceNotFoundException("Médico", "CRM", crm));
    }

    /**
     * Buscar médicos por nome
     */
    public List<Medico> buscarPorNome(String nome) {
        return medicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Buscar médicos por especialidade
     */
    public List<Medico> buscarPorEspecialidade(String especialidade) {
        return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

    /**
     * Listar todas as especialidades cadastradas
     */
    public List<String> listarEspecialidades() {
        return medicoRepository.findAllEspecialidades();
    }

    /**
     * Cadastrar novo médico
     */
    public Medico cadastrar(Medico medico) {
        // Validar CRM
        if (!medico.isCrmValido()) {
            throw new BusinessException("CRM inválido: " + medico.getCrm() + 
                                      ". Use o formato: CRM/SP123456");
        }

        // Verificar se CRM já existe
        if (medicoRepository.existsByCrm(medico.getCrm())) {
            throw new BusinessException("Já existe um médico cadastrado com o CRM: " + medico.getCrm());
        }

        // Normalizar CRM (converter para maiúsculas)
        medico.setCrm(medico.getCrm().toUpperCase());

        // Normalizar nome
        medico.setNome(capitalizarNome(medico.getNome()));

        // Normalizar especialidade
        medico.setEspecialidade(capitalizarNome(medico.getEspecialidade()));

        // ID será gerado automaticamente pela sequence SEQ_MEDICO

        return medicoRepository.save(medico);
    }

    /**
     * Atualizar médico existente
     */
    public Medico atualizar(Long id, Medico medicoAtualizado) {
        // Verificar se médico existe
        Medico medicoExistente = buscarPorId(id);

        // Validar CRM
        if (!medicoAtualizado.isCrmValido()) {
            throw new BusinessException("CRM inválido: " + medicoAtualizado.getCrm() + 
                                      ". Use o formato: CRM/SP123456");
        }

        // Verificar se CRM já existe para outro médico
        String crmNormalizado = medicoAtualizado.getCrm().toUpperCase();
        if (medicoRepository.existsByCrmAndIdNot(crmNormalizado, id)) {
            throw new BusinessException("Já existe outro médico cadastrado com o CRM: " + crmNormalizado);
        }

        // Atualizar dados
        medicoExistente.setNome(capitalizarNome(medicoAtualizado.getNome()));
        medicoExistente.setCrm(crmNormalizado);
        medicoExistente.setEspecialidade(capitalizarNome(medicoAtualizado.getEspecialidade()));

        return medicoRepository.save(medicoExistente);
    }

    /**
     * Deletar médico
     */
    public void deletar(Long id) {
        Medico medico = buscarPorId(id);
        medicoRepository.delete(medico);
    }

    /**
     * Atualizar especialidade do médico
     */
    public Medico atualizarEspecialidade(Long id, String novaEspecialidade) {
        Medico medico = buscarPorId(id);
        
        if (novaEspecialidade == null || novaEspecialidade.trim().isEmpty()) {
            throw new BusinessException("Especialidade não pode ser vazia");
        }
        
        medico.setEspecialidade(capitalizarNome(novaEspecialidade));
        return medicoRepository.save(medico);
    }

    /**
     * Verificar se médico existe
     */
    public boolean existe(Long id) {
        return medicoRepository.existsById(id);
    }

    /**
     * Contar total de médicos
     */
    public long contarTotal() {
        return medicoRepository.count();
    }

    /**
     * Contar médicos por especialidade
     */
    public long contarPorEspecialidade(String especialidade) {
        return medicoRepository.findByEspecialidadeContainingIgnoreCase(especialidade).size();
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
     * Contar total de médicos
     */
    public long contar() {
        return medicoRepository.count();
    }
}
