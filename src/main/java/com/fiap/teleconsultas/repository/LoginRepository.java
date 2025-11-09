package com.fiap.teleconsultas.repository;

import com.fiap.teleconsultas.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository para a entidade Login
 * Fornece operações de acesso a dados para TB_LOGIN
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    
    /**
     * Busca login por email do paciente
     */
    Optional<Login> findByEmailPaciente(String emailPaciente);
    
    /**
     * Busca login por nome de usuário
     */
    Optional<Login> findByNome(String nome);
    
    /**
     * Verifica se existe login com o email
     */
    boolean existsByEmailPaciente(String emailPaciente);
    
    /**
     * Verifica se existe login com o nome
     */
    boolean existsByNome(String nome);
    
    /**
     * Busca login por email e senha (para autenticação)
     */
    @Query("SELECT l FROM Login l WHERE l.emailPaciente = :email AND l.senha = :senha")
    Optional<Login> findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}
