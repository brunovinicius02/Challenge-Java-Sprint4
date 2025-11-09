# 🏥 TeleconsultasHC - Sistema de Gestão de Teleconsultas

## 📋 Descrição do Projeto

Sistema completo de gestão de teleconsultas desenvolvido em Java com Spring Boot, focado no controle de absenteísmo e otimização do agendamento de consultas médicas.

### 🎯 Objetivo

Reduzir o absenteísmo em teleconsultas através de um sistema inteligente que:
- Monitora o histórico de comparecimento dos pacientes
- Bloqueia novos agendamentos de pacientes com alto índice de faltas
- Fornece estatísticas detalhadas sobre absenteísmo
- Facilita o gerenciamento de consultas, médicos e pacientes

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA** (Persistência)
- **Oracle Database** (Banco de dados FIAP)
- **Maven** (Gerenciamento de dependências)
- **Swagger/OpenAPI** (Documentação automática da API)
- **Bean Validation** (Validação de dados)

---

## 📦 Funcionalidades Principais

### Gestão de Pacientes
- ✅ Cadastro, consulta, atualização e exclusão de pacientes
- ✅ Validação de CPF
- ✅ Busca por nome ou CPF
- ✅ Controle de histórico de faltas

### Gestão de Médicos
- ✅ Cadastro, consulta, atualização e exclusão de médicos
- ✅ Validação de CRM
- ✅ Busca por especialidade
- ✅ Listagem de especialidades disponíveis

### Gestão de Consultas
- ✅ Agendamento com validação de conflitos
- ✅ Confirmação, realização e cancelamento de consultas
- ✅ Registro de faltas
- ✅ Validações de horário comercial e dias úteis
- ✅ Bloqueio de pacientes com 3+ faltas
- ✅ Estatísticas de absenteísmo por paciente e gerais

### Regras de Negócio
- 🔒 Limite de 3 faltas por paciente
- ⏰ Horário comercial: 8h às 18h
- 📅 Apenas dias úteis (segunda a sexta)
- ⏱️ Cancelamento com 24h de antecedência
- 🚫 Bloqueio de horários conflitantes

---

## ⚙️ Configuração e Instalação

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Acesso ao Oracle Database FIAP
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Passo 1: Clonar o repositório
```bash
git clone <url-do-repositorio>
cd TeleconsultasHC-SpringBoot
```

### Passo 2: Configurar credenciais do banco
Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_RM
spring.datasource.password=SUA_SENHA
```

### Passo 3: Instalar dependências
```bash
mvn clean install
```

### Passo 4: Executar a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📚 Documentação da API

### Swagger UI (Interface Interativa)
Acesse: `http://localhost:8080/swagger-ui.html`

### OpenAPI JSON
Acesse: `http://localhost:8080/api-docs`

---

## 🔗 Endpoints Principais

Veja o arquivo `ENDPOINTS.md` para documentação completa de todos os endpoints.

---

## 📝 Exemplos de Uso

### Cadastrar Paciente
```bash
POST /api/pacientes
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "(11) 98765-4321"
}
```

### Agendar Consulta
```bash
POST /api/consultas
Content-Type: application/json

{
  "paciente": {"id": 1},
  "medico": {"id": 1},
  "dataHora": "2025-11-10T14:00:00"
}
```

---

## 👥 Equipe

- Bruno Vinicius Barbosa – RM: 566366
- João Pedro Bitencourt Goldoni – RM: 564339
- Marina Tamagnini Magalhães – RM: 561786




