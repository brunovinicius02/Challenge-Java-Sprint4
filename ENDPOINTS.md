# 📋 Documentação de Endpoints - API TeleconsultasHC

## Base URL
```
http://localhost:8080
```

---

## 🩺 Pacientes

### 1. Listar Todos os Pacientes
- **Método:** GET
- **URI:** `/api/pacientes`
- **Descrição:** Retorna lista de todos os pacientes cadastrados ordenados por nome
- **Status:** 200 OK
- **Resposta:**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "cpf": "12345678900",
    "telefone": "(11) 98765-4321"
  }
]
```

### 2. Buscar Paciente por ID
- **Método:** GET
- **URI:** `/api/pacientes/{id}`
- **Descrição:** Retorna um paciente específico
- **Status:** 200 OK, 404 Not Found
- **Resposta:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "(11) 98765-4321"
}
```

### 3. Buscar Paciente por CPF
- **Método:** GET
- **URI:** `/api/pacientes/cpf/{cpf}`
- **Status:** 200 OK, 404 Not Found

### 4. Buscar Pacientes por Nome
- **Método:** GET
- **URI:** `/api/pacientes/buscar?nome={nome}`
- **Status:** 200 OK

### 5. Cadastrar Paciente
- **Método:** POST
- **URI:** `/api/pacientes`
- **Status:** 201 Created, 400 Bad Request
- **Body:**
```json
{
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "(11) 98765-4321"
}
```

### 6. Atualizar Paciente
- **Método:** PUT
- **URI:** `/api/pacientes/{id}`
- **Status:** 200 OK, 404 Not Found, 400 Bad Request
- **Body:**
```json
{
  "nome": "João Silva Santos",
  "cpf": "12345678900",
  "telefone": "(11) 98765-9999"
}
```

### 7. Atualizar Telefone
- **Método:** PATCH
- **URI:** `/api/pacientes/{id}/telefone`
- **Status:** 200 OK, 404 Not Found
- **Body:**
```json
{
  "telefone": "(11) 98888-8888"
}
```

### 8. Deletar Paciente
- **Método:** DELETE
- **URI:** `/api/pacientes/{id}`
- **Status:** 204 No Content, 404 Not Found

### 9. Contar Total de Pacientes
- **Método:** GET
- **URI:** `/api/pacientes/count`
- **Status:** 200 OK
- **Resposta:**
```json
{
  "total": 42
}
```

---

## 👨‍⚕️ Médicos

### 1. Listar Todos os Médicos
- **Método:** GET
- **URI:** `/api/medicos`
- **Status:** 200 OK
- **Resposta:**
```json
[
  {
    "id": 1,
    "nome": "Dr. Maria Santos",
    "crm": "CRM/SP123456",
    "especialidade": "Cardiologia"
  }
]
```

### 2. Buscar Médico por ID
- **Método:** GET
- **URI:** `/api/medicos/{id}`
- **Status:** 200 OK, 404 Not Found

### 3. Buscar Médico por CRM
- **Método:** GET
- **URI:** `/api/medicos/crm/{crm}`
- **Status:** 200 OK, 404 Not Found

### 4. Buscar Médicos por Nome
- **Método:** GET
- **URI:** `/api/medicos/buscar?nome={nome}`
- **Status:** 200 OK

### 5. Buscar Médicos por Especialidade
- **Método:** GET
- **URI:** `/api/medicos/especialidade/{especialidade}`
- **Status:** 200 OK

### 6. Listar Especialidades
- **Método:** GET
- **URI:** `/api/medicos/especialidades`
- **Status:** 200 OK
- **Resposta:**
```json
[
  "Cardiologia",
  "Dermatologia",
  "Pediatria"
]
```

### 7. Cadastrar Médico
- **Método:** POST
- **URI:** `/api/medicos`
- **Status:** 201 Created, 400 Bad Request
- **Body:**
```json
{
  "nome": "Dr. Maria Santos",
  "crm": "CRM/SP123456",
  "especialidade": "Cardiologia"
}
```

### 8. Atualizar Médico
- **Método:** PUT
- **URI:** `/api/medicos/{id}`
- **Status:** 200 OK, 404 Not Found

### 9. Atualizar Especialidade
- **Método:** PATCH
- **URI:** `/api/medicos/{id}/especialidade`
- **Status:** 200 OK, 404 Not Found
- **Body:**
```json
{
  "especialidade": "Pediatria"
}
```

### 10. Deletar Médico
- **Método:** DELETE
- **URI:** `/api/medicos/{id}`
- **Status:** 204 No Content, 404 Not Found

### 11. Contar Total de Médicos
- **Método:** GET
- **URI:** `/api/medicos/count`
- **Status:** 200 OK

---

## 📅 Consultas

### 1. Listar Todas as Consultas
- **Método:** GET
- **URI:** `/api/consultas`
- **Status:** 200 OK
- **Resposta:**
```json
[
  {
    "id": 1,
    "paciente": {
      "id": 1,
      "nome": "João Silva"
    },
    "medico": {
      "id": 1,
      "nome": "Dr. Maria Santos"
    },
    "dataHora": "2025-11-10T14:00:00",
    "status": "Agendada"
  }
]
```

### 2. Buscar Consulta por ID
- **Método:** GET
- **URI:** `/api/consultas/{id}`
- **Status:** 200 OK, 404 Not Found

### 3. Buscar Consultas por Paciente
- **Método:** GET
- **URI:** `/api/consultas/paciente/{pacienteId}`
- **Status:** 200 OK

### 4. Buscar Consultas por Médico
- **Método:** GET
- **URI:** `/api/consultas/medico/{medicoId}`
- **Status:** 200 OK

### 5. Buscar Consultas por Status
- **Método:** GET
- **URI:** `/api/consultas/status/{status}`
- **Parâmetros:** status = Agendada | Confirmada | Realizada | Cancelada | Falta
- **Status:** 200 OK

### 6. Buscar Consultas Futuras do Paciente
- **Método:** GET
- **URI:** `/api/consultas/paciente/{pacienteId}/futuras`
- **Status:** 200 OK

### 7. Buscar Consultas Futuras do Médico
- **Método:** GET
- **URI:** `/api/consultas/medico/{medicoId}/futuras`
- **Status:** 200 OK

### 8. Buscar Consultas por Período
- **Método:** GET
- **URI:** `/api/consultas/periodo?inicio={dataInicio}&fim={dataFim}`
- **Formato das datas:** ISO 8601 (yyyy-MM-dd'T'HH:mm:ss)
- **Exemplo:** `/api/consultas/periodo?inicio=2025-11-01T00:00:00&fim=2025-11-30T23:59:59`
- **Status:** 200 OK

### 9. Buscar Consultas do Dia
- **Método:** GET
- **URI:** `/api/consultas/hoje`
- **Status:** 200 OK

### 10. Agendar Consulta
- **Método:** POST
- **URI:** `/api/consultas`
- **Status:** 201 Created, 400 Bad Request, 404 Not Found
- **Body:**
```json
{
  "paciente": {
    "id": 1
  },
  "medico": {
    "id": 1
  },
  "dataHora": "2025-11-10T14:00:00"
}
```

### 11. Atualizar Consulta
- **Método:** PUT
- **URI:** `/api/consultas/{id}`
- **Status:** 200 OK, 404 Not Found, 400 Bad Request
- **Body:**
```json
{
  "medico": {
    "id": 2
  },
  "dataHora": "2025-11-10T15:00:00"
}
```

### 12. Confirmar Consulta
- **Método:** PATCH
- **URI:** `/api/consultas/{id}/confirmar`
- **Status:** 200 OK, 404 Not Found, 409 Conflict

### 13. Realizar Consulta (Paciente Compareceu)
- **Método:** PATCH
- **URI:** `/api/consultas/{id}/realizar`
- **Status:** 200 OK, 404 Not Found, 409 Conflict

### 14. Cancelar Consulta
- **Método:** PATCH
- **URI:** `/api/consultas/{id}/cancelar`
- **Status:** 200 OK, 404 Not Found, 400 Bad Request
- **Body (opcional):**
```json
{
  "motivo": "Paciente solicitou cancelamento"
}
```

### 15. Registrar Falta
- **Método:** PATCH
- **URI:** `/api/consultas/{id}/falta`
- **Status:** 200 OK, 404 Not Found, 409 Conflict

### 16. Deletar Consulta
- **Método:** DELETE
- **URI:** `/api/consultas/{id}`
- **Status:** 204 No Content, 404 Not Found, 400 Bad Request

### 17. Estatísticas do Paciente
- **Método:** GET
- **URI:** `/api/consultas/estatisticas/paciente/{pacienteId}`
- **Status:** 200 OK
- **Resposta:**
```json
{
  "pacienteId": 1,
  "pacienteNome": "João Silva",
  "totalConsultas": 10,
  "totalComparecimentos": 8,
  "totalFaltas": 2,
  "taxaComparecimento": "80.00%",
  "statusRisco": "MÉDIO"
}
```

### 18. Estatísticas Gerais
- **Método:** GET
- **URI:** `/api/consultas/estatisticas/geral`
- **Status:** 200 OK
- **Resposta:**
```json
{
  "totalConsultas": 150,
  "consultasAgendadas": 30,
  "consultasRealizadas": 100,
  "consultasFaltosas": 15,
  "consultasCanceladas": 5,
  "taxaComparecimentoGeral": "86.96%"
}
```

---

## 📊 Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 204 | No Content - Recurso deletado com sucesso |
| 400 | Bad Request - Dados inválidos ou regra de negócio violada |
| 404 | Not Found - Recurso não encontrado |
| 409 | Conflict - Conflito de estado (ex: horário duplicado) |
| 500 | Internal Server Error - Erro interno do servidor |

---

## 🔒 Regras de Negócio Implementadas

### Pacientes
- CPF deve ser válido (validação por dígitos verificadores)
- CPF deve ser único no sistema
- Telefone deve estar no formato correto

### Médicos
- CRM deve seguir o formato CRM/UF123456
- CRM deve ser único no sistema

### Consultas
- Horário deve estar no futuro
- Horário comercial: 8h às 18h
- Apenas dias úteis (segunda a sexta)
- Não pode haver conflito de horário para o mesmo médico
- Não pode haver conflito de horário para o mesmo paciente
- Pacientes com 3+ faltas são bloqueados para novos agendamentos
- Cancelamento deve ser feito com 24h de antecedência
- Consultas realizadas não podem ser canceladas
- Status válidos: Agendada, Confirmada, Realizada, Cancelada, Falta

---

## 🧪 Exemplos de Teste com cURL

### Cadastrar Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "12345678900",
    "telefone": "(11) 98765-4321"
  }'
```

### Listar Consultas do Dia
```bash
curl -X GET http://localhost:8080/api/consultas/hoje
```

### Obter Estatísticas do Paciente
```bash
curl -X GET http://localhost:8080/api/consultas/estatisticas/paciente/1
```

---

## 📖 Documentação Interativa

Para testar todos os endpoints de forma interativa, acesse o Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

O Swagger fornece:
- ✅ Documentação completa de todos os endpoints
- ✅ Exemplos de requisição e resposta
- ✅ Possibilidade de testar os endpoints diretamente no navegador
- ✅ Validação de schemas JSON
- ✅ Descrição detalhada de parâmetros

---

**Documentação gerada automaticamente pela API TeleconsultasHC**
