package com.fiap.teleconsultas.controller;

import com.fiap.teleconsultas.model.Paciente;
import com.fiap.teleconsultas.model.ProntuarioPaciente;
import com.fiap.teleconsultas.model.Receita;
import com.fiap.teleconsultas.repository.PacienteRepository;
import com.fiap.teleconsultas.repository.ProntuarioPacienteRepository;
import com.fiap.teleconsultas.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller para buscar dados do paciente (receitas, prontuários)
 */
@RestController
@RequestMapping("/api/paciente-dados")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://imrea-assistente.vercel.app"
    "https://challenge-java-sprint4-production.up.railway.app"
})
public class PacienteDadosController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ProntuarioPacienteRepository prontuarioRepository;

    /**
     * Busca dados completos do paciente por telefone
     * GET /api/paciente-dados/telefone/{telefone}
     */
    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<?> buscarDadosPorTelefone(@PathVariable String telefone) {
        // Tenta buscar com o telefone original primeiro (pode estar formatado no banco)
        Optional<Paciente> pacienteOpt = pacienteRepository.findByTelefone(telefone);
        
        // Se não encontrar, tenta remover formatação
        if (pacienteOpt.isEmpty()) {
            String telefoneSemFormatacao = telefone.replaceAll("[^0-9]", "");
            pacienteOpt = pacienteRepository.findByTelefone(telefoneSemFormatacao);
        }
        
        // Se ainda não encontrar, tenta adicionar formatação padrão (11)XXXXX-XXXX
        if (pacienteOpt.isEmpty()) {
            String telefoneSemFormatacao = telefone.replaceAll("[^0-9]", "");
            if (telefoneSemFormatacao.length() == 11) {
                String telefoneFormatado = String.format("(%s)%s-%s",
                    telefoneSemFormatacao.substring(0, 2),
                    telefoneSemFormatacao.substring(2, 7),
                    telefoneSemFormatacao.substring(7)
                );
                pacienteOpt = pacienteRepository.findByTelefone(telefoneFormatado);
            }
        }
        
        if (pacienteOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "encontrado", false,
                "mensagem", "Paciente não encontrado"
            ));
        }

        Paciente paciente = pacienteOpt.get();
        
        // Buscar receitas do paciente através das consultas
        List<Receita> receitas = receitaRepository.findByPacienteId(paciente.getId());
        
        // Buscar prontuários do paciente
        List<ProntuarioPaciente> prontuarios = prontuarioRepository.findByPaciente_Id(paciente.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("encontrado", true);
        response.put("paciente", Map.of(
            "id", paciente.getId(),
            "nome", paciente.getNome(),
            "cpf", paciente.getCpf(),
            "email", paciente.getEmail(),
            "telefone", paciente.getTelefone(),
            "dataNascimento", paciente.getDataNascimento(),
            "sexo", paciente.getSexo()
        ));
        
        // Formatar receitas
        List<Map<String, Object>> receitasFormatadas = new ArrayList<>();
        for (Receita receita : receitas) {
            Map<String, Object> receitaMap = new HashMap<>();
            receitaMap.put("id", receita.getId());
            receitaMap.put("dataEmissao", receita.getDataEmissao());
            receitaMap.put("observacoes", receita.getObservacoes());
            receitaMap.put("medico", receita.getConsulta().getMedico().getNome());
            receitaMap.put("especialidade", receita.getConsulta().getMedico().getEspecialidade());
            
            // Itens da receita
            List<Map<String, Object>> itens = new ArrayList<>();
            if (receita.getItens() != null) {
                receita.getItens().forEach(item -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("medicamento", item.getMedicamento().getNome());
                    itemMap.put("dosagem", item.getDosagem());
                    itemMap.put("frequencia", item.getFrequencia());
                    itemMap.put("duracaoDias", item.getDuracaoDias());
                    itemMap.put("instrucoes", item.getInstrucoes());
                    itens.add(itemMap);
                });
            }
            receitaMap.put("itens", itens);
            receitasFormatadas.add(receitaMap);
        }
        response.put("receitas", receitasFormatadas);
        
        // Formatar prontuários
        List<Map<String, Object>> prontuariosFormatados = new ArrayList<>();
        for (ProntuarioPaciente pront : prontuarios) {
            Map<String, Object> prontMap = new HashMap<>();
            prontMap.put("id", pront.getId());
            prontMap.put("descricao", pront.getDescricao());
            prontMap.put("dataRegistro", pront.getDataRegistro());
            prontMap.put("medico", pront.getMedico().getNome());
            prontMap.put("especialidade", pront.getMedico().getEspecialidade());
            prontuariosFormatados.add(prontMap);
        }
        response.put("prontuarios", prontuariosFormatados);
        
        return ResponseEntity.ok(response);
    }
}
