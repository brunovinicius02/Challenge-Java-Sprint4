package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.ProntuarioPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProntuarioPacienteRepository extends JpaRepository<ProntuarioPaciente, Long> {
    List<ProntuarioPaciente> findByPaciente_Id(Long pacienteId);
}
