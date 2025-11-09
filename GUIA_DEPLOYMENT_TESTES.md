# 🚀 Guia de Deployment e Testes - TeleconsultasHC

## 📋 Pré-requisitos

Antes de iniciar, verifique se você tem instalado:

- ✅ Java JDK 17 ou superior
- ✅ Maven 3.6 ou superior
- ✅ IDE (IntelliJ IDEA, Eclipse, ou VS Code com extensões Java)
- ✅ Acesso ao Oracle Database FIAP
- ✅ Git (para clonar o repositório)

### Verificar instalações:

```bash
# Verificar Java
java -version
# Deve mostrar: java version "17.x.x" ou superior

# Verificar Maven
mvn -version
# Deve mostrar: Apache Maven 3.6.x ou superior
```

---

## 🔧 Configuração Inicial

### Passo 1: Obter o Código

#### Opção A: Clonar do GitHub
```bash
git clone [URL_DO_REPOSITORIO]
cd TeleconsultasHC-SpringBoot
```

#### Opção B: Descompactar ZIP
```bash
unzip TeleconsultasHC-SpringBoot.zip
cd TeleconsultasHC-SpringBoot
```

### Passo 2: Configurar Banco de Dados

1. Abra o arquivo de configuração:
```bash
src/main/resources/application.properties
```

2. Edite as credenciais do Oracle:
```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=SEU_RM_AQUI
spring.datasource.password=SUA_SENHA_AQUI
```

**Importante:** Substitua `SEU_RM_AQUI` e `SUA_SENHA_AQUI` pelas suas credenciais FIAP.

### Passo 3: Instalar Dependências

```bash
mvn clean install
```

**O que isso faz:**
- Baixa todas as dependências do projeto
- Compila o código
- Executa testes (se houver)
- Cria o arquivo .jar executável

**Tempo estimado:** 2-5 minutos (primeira vez)

---

## ▶️ Executando a Aplicação

### Método 1: Via Maven (Recomendado para desenvolvimento)

```bash
mvn spring-boot:run
```

### Método 2: Via JAR

```bash
# 1. Gerar o JAR
mvn clean package

# 2. Executar o JAR
java -jar target/teleconsultas-hc-1.0.0.jar
```

### Método 3: Via IDE

**IntelliJ IDEA:**
1. Abrir o projeto
2. Localizar `TeleconsultasHCApplication.java`
3. Clicar com botão direito → Run

**Eclipse:**
1. Import → Existing Maven Project
2. Selecionar a pasta do projeto
3. Run As → Spring Boot App

### Verificar se iniciou corretamente

Você deve ver no console:

```
========================================
✓ API TeleconsultasHC Iniciada!
========================================
📍 URL Base: http://localhost:8080
📚 Swagger: http://localhost:8080/swagger-ui.html
📋 API Docs: http://localhost:8080/api-docs
========================================
```

---

## 🧪 Testando a API

### 1. Swagger UI (Recomendado)

#### Acessar:
```
http://localhost:8080/swagger-ui.html
```

#### Testar endpoint passo a passo:

**Exemplo: Cadastrar um Paciente**

1. Localizar "Pacientes" na lista de controllers
2. Clicar para expandir
3. Encontrar `POST /api/pacientes`
4. Clicar em "Try it out"
5. Preencher o JSON:
```json
{
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "(11) 98765-4321"
}
```
6. Clicar em "Execute"
7. Verificar resposta 201 Created

**Exemplo: Listar Pacientes**

1. Encontrar `GET /api/pacientes`
2. Clicar em "Try it out"
3. Clicar em "Execute"
4. Verificar resposta 200 OK com lista de pacientes

---

### 2. Testando com cURL

#### Cadastrar Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "cpf": "98765432100",
    "telefone": "(11) 91234-5678"
  }'
```

#### Listar Pacientes
```bash
curl -X GET http://localhost:8080/api/pacientes
```

#### Buscar Paciente por ID
```bash
curl -X GET http://localhost:8080/api/pacientes/1
```

#### Cadastrar Médico
```bash
curl -X POST http://localhost:8080/api/medicos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Dr. Carlos Oliveira",
    "crm": "CRM/SP123456",
    "especialidade": "Cardiologia"
  }'
```

#### Agendar Consulta
```bash
curl -X POST http://localhost:8080/api/consultas \
  -H "Content-Type: application/json" \
  -d '{
    "paciente": {"id": 1},
    "medico": {"id": 1},
    "dataHora": "2025-11-10T14:00:00"
  }'
```

---

### 3. Testando com Postman

#### Importar Collection:

1. Abrir Postman
2. Import → Link
3. Colar: `http://localhost:8080/api-docs`
4. Todos os endpoints serão importados automaticamente

#### Criar Requests Manualmente:

**Request 1: POST Paciente**
- Method: POST
- URL: `http://localhost:8080/api/pacientes`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "nome": "João Silva",
  "cpf": "12345678900",
  "telefone": "(11) 98765-4321"
}
```

**Request 2: GET Estatísticas**
- Method: GET
- URL: `http://localhost:8080/api/consultas/estatisticas/geral`

---

## 🎯 Fluxo Completo de Teste

### Cenário: Agendamento de Consulta do Início ao Fim

#### 1. Cadastrar Paciente
```bash
POST /api/pacientes
{
  "nome": "Ana Costa",
  "cpf": "11122233344",
  "telefone": "(11) 99999-8888"
}
# Resposta: Status 201, ID = 1
```

#### 2. Cadastrar Médico
```bash
POST /api/medicos
{
  "nome": "Dr. Pedro Lima",
  "crm": "CRM/RJ654321",
  "especialidade": "Pediatria"
}
# Resposta: Status 201, ID = 1
```

#### 3. Agendar Consulta
```bash
POST /api/consultas
{
  "paciente": {"id": 1},
  "medico": {"id": 1},
  "dataHora": "2025-11-15T10:00:00"
}
# Resposta: Status 201, ID = 1, status = "Agendada"
```

#### 4. Confirmar Consulta
```bash
PATCH /api/consultas/1/confirmar
# Resposta: Status 200, status = "Confirmada"
```

#### 5. Realizar Consulta (Paciente Compareceu)
```bash
PATCH /api/consultas/1/realizar
# Resposta: Status 200, status = "Realizada"
```

#### 6. Verificar Estatísticas
```bash
GET /api/consultas/estatisticas/paciente/1
# Resposta: taxaComparecimento: "100.00%"
```

---

### Cenário: Testando Regra de Bloqueio por Faltas

#### 1. Criar 3 consultas e registrar faltas
```bash
# Consulta 1
POST /api/consultas → ID = 1
PATCH /api/consultas/1/falta

# Consulta 2
POST /api/consultas → ID = 2
PATCH /api/consultas/2/falta

# Consulta 3
POST /api/consultas → ID = 3
PATCH /api/consultas/3/falta
```

#### 2. Tentar agendar nova consulta
```bash
POST /api/consultas
# Resposta: Status 400
# Mensagem: "Paciente possui 3 faltas. Limite máximo de 3 faltas atingido."
```

---

### Cenário: Testando Validações de Horário

#### 1. Tentar agendar fora do horário comercial
```bash
POST /api/consultas
{
  ...
  "dataHora": "2025-11-15T19:00:00"  # 19h = fora do horário
}
# Resposta: Status 400
# Mensagem: "Consultas devem ser agendadas entre 8h e 18h"
```

#### 2. Tentar agendar em final de semana
```bash
POST /api/consultas
{
  ...
  "dataHora": "2025-11-16T10:00:00"  # Sábado
}
# Resposta: Status 400
# Mensagem: "Consultas devem ser agendadas em dias úteis"
```

#### 3. Tentar agendar horário duplicado
```bash
# Primeiro agendamento
POST /api/consultas → Status 201

# Segundo agendamento no mesmo horário
POST /api/consultas (mesmo médico, mesmo horário)
# Resposta: Status 400
# Mensagem: "Médico já possui consulta agendada para este horário"
```

---

## 📊 Endpoints Essenciais para Demonstração

### 1. Dashboard Geral
```
GET /api/consultas/estatisticas/geral
```
Retorna visão geral do sistema

### 2. Consultas do Dia
```
GET /api/consultas/hoje
```
Lista todas as consultas agendadas para hoje

### 3. Próximas Consultas do Paciente
```
GET /api/consultas/paciente/1/futuras
```
Mostra consultas futuras de um paciente

### 4. Médicos por Especialidade
```
GET /api/medicos/especialidade/Cardiologia
```
Lista médicos de uma especialidade

### 5. Histórico do Paciente
```
GET /api/consultas/paciente/1
```
Todas as consultas de um paciente

---

## 🐛 Troubleshooting

### Erro: "Unable to connect to Oracle"

**Solução:**
1. Verificar credenciais no `application.properties`
2. Verificar conectividade com VPN FIAP (se necessário)
3. Testar conexão:
```bash
# No Oracle SQL Developer ou similar
Connection: oracle.fiap.com.br:1521:ORCL
User: SEU_RM
Password: SUA_SENHA
```

### Erro: "Port 8080 already in use"

**Solução 1:** Mudar porta no `application.properties`
```properties
server.port=8081
```

**Solução 2:** Matar processo na porta 8080
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [PID_NUMBER] /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Erro: "Failed to execute goal"

**Solução:**
```bash
# Limpar cache do Maven
mvn clean

# Reinstalar dependências
mvn clean install -U
```

### Swagger não abre

**Solução:**
1. Verificar se aplicação iniciou corretamente
2. Acessar: `http://localhost:8080/swagger-ui/index.html` (com /index.html)
3. Verificar logs para erros de inicialização

---

## 📝 Checklist de Testes para Entrega

### Testes Básicos (Obrigatório)
- [ ] Cadastrar paciente
- [ ] Listar pacientes
- [ ] Buscar paciente por ID
- [ ] Cadastrar médico
- [ ] Listar médicos
- [ ] Agendar consulta
- [ ] Confirmar consulta
- [ ] Realizar consulta
- [ ] Verificar estatísticas

### Testes de Validação (Recomendado)
- [ ] CPF inválido → Erro 400
- [ ] CRM inválido → Erro 400
- [ ] Horário fora comercial → Erro 400
- [ ] Dia não útil → Erro 400
- [ ] Horário duplicado → Erro 400
- [ ] Paciente com 3+ faltas → Bloqueio

### Testes de Endpoints (Completo)
- [ ] Todos os GETs funcionam
- [ ] Todos os POSTs funcionam
- [ ] Todos os PUTs funcionam
- [ ] Todos os PATCHs funcionam
- [ ] Todos os DELETEs funcionam

---

## 🎥 Preparando Demonstração

### Roteiro Sugerido (5 minutos):

**1. Introdução (30 segundos)**
- Mostrar Swagger UI
- Explicar estrutura da API

**2. CRUD Básico (1 minuto)**
- Cadastrar paciente
- Cadastrar médico
- Listar ambos

**3. Agendamento (1 minuto)**
- Agendar consulta
- Mostrar validações funcionando

**4. Workflow da Consulta (1 minuto)**
- Confirmar
- Realizar
- Mostrar mudança de status

**5. Controle de Absenteísmo (1 minuto)**
- Mostrar estatísticas do paciente
- Registrar falta
- Demonstrar bloqueio após 3 faltas

**6. Estatísticas Gerais (30 segundos)**
- Dashboard do sistema
- Taxa de comparecimento geral

---

## 📦 Preparando para Entrega

### 1. Limpar projeto
```bash
mvn clean
```

### 2. Gerar JAR final
```bash
mvn clean package -DskipTests
```

### 3. Estrutura final do ZIP
```
TeleconsultasHC-SpringBoot/
├── src/
├── pom.xml
├── README.md
├── ENDPOINTS.md
├── RESUMO_IMPLEMENTACAO.md
├── GUIA_CRIAR_PDF.md
└── target/
    └── teleconsultas-hc-1.0.0.jar
```

### 4. Upload no GitHub
```bash
git init
git add .
git commit -m "Entrega Final - TeleconsultasHC"
git remote add origin [URL_DO_REPOSITORIO]
git push -u origin main
```

---

## ✅ Checklist Final

- [ ] Aplicação inicia sem erros
- [ ] Swagger UI acessível
- [ ] Todos os endpoints testados
- [ ] Validações funcionando
- [ ] Estatísticas corretas
- [ ] Documentação completa
- [ ] Código no GitHub
- [ ] PDF gerado

---

**Boa sorte com a entrega! 🚀**

Se tiver dúvidas, consulte:
- README.md - Visão geral
- ENDPOINTS.md - Documentação de endpoints
- RESUMO_IMPLEMENTACAO.md - O que foi implementado
- Swagger UI - Teste interativo
