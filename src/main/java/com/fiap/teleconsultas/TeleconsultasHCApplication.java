package com.fiap.teleconsultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

/**
 * Classe principal da aplicação Spring Boot
 * Sistema de Gestão de Teleconsultas HC
 * 
 * @author Equipe FIAP
 * @version 1.0.0
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "API TeleconsultasHC",
        version = "1.0.0",
        description = "API RESTful para gestão de teleconsultas com controle de absenteísmo",
        contact = @Contact(
            name = "Equipe FIAP",
            email = "rm566366@fiap.com.br"
        )
    )
)
public class TeleconsultasHCApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeleconsultasHCApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("✓ API TeleconsultasHC Iniciada!");
        System.out.println("========================================");
        System.out.println("📍 URL Base: http://localhost:8080");
        System.out.println("📚 Swagger: http://localhost:8080/swagger-ui/index.html");
        System.out.println("📋 API Docs: http://localhost:8080/api-docs");
        System.out.println("========================================\n");
    }
}
