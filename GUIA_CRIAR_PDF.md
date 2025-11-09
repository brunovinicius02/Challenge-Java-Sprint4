# 📄 Guia para Criar a Documentação PDF

## 📋 Checklist de Conteúdo

### 1. Capa (1 página)
```
TeleconsultasHC
Sistema de Gestão de Teleconsultas

Equipe: [Nome da Equipe]

Integrantes:
- [Nome 1] - RM[XXXXX]
- [Nome 2] - RM[XXXXX]  
- [Nome 3] - RM[XXXXX]

Nome da Solução: TeleconsultasHC
API RESTful para Gestão de Teleconsultas

FIAP - Análise e Desenvolvimento de Sistemas
Novembro 2025
```

---

### 2. Objetivo e Escopo do Projeto (1-2 páginas)

**Copiar do README.md:**

**Objetivo:**
Sistema completo de gestão de teleconsultas desenvolvido em Java com Spring Boot, focado no controle de absenteísmo e otimização do agendamento de consultas médicas.

**Escopo:**
O sistema TeleconsultasHC foi desenvolvido para:
- Gerenciar cadastros de pacientes e médicos
- Controlar agendamentos de consultas
- Monitorar e reduzir o absenteísmo através de:
  * Registro automático de faltas
  * Bloqueio de pacientes com alto índice de faltas (3+)
  * Estatísticas de comparecimento
  * Validações de horário e disponibilidade

**Limites do Sistema:**
- Não integra com sistemas de pagamento
- Não inclui prontuário eletrônico
- Não possui sistema de autenticação (pode ser adicionado futuramente)
- Foco exclusivo em teleconsultas (não presenciais)

---

### 3. Descrição das Funcionalidades (1-2 páginas)

**Principais funcionalidades implementadas:**

#### Gestão de Pacientes
- Cadastro completo com validação de CPF
- Consulta por ID, CPF ou nome
- Atualização de dados cadastrais
- Exclusão de registros
- Controle de histórico de faltas

#### Gestão de Médicos
- Cadastro com validação de CRM
- Busca por especialidade
- Listagem de especialidades disponíveis
- Gerenciamento de agenda

#### Gestão de Consultas
- Agendamento inteligente com validações:
  * Horário comercial (8h-18h)
  * Dias úteis apenas
  * Sem conflitos de horário
  * Verificação de disponibilidade
- Workflow completo: Agendada → Confirmada → Realizada
- Registro de faltas e cancelamentos
- Bloqueio automático após 3 faltas

#### Controle de Absenteísmo
- Estatísticas individuais por paciente
- Estatísticas gerais do sistema
- Taxa de comparecimento
- Classificação de risco (BAIXO, MÉDIO, ALTO)

---

### 4. Tabela de Endpoints (3-4 páginas)

**IMPORTANTE: Usar o arquivo ENDPOINTS.md como base**

Criar tabelas organizadas por recurso:

#### Tabela 1: Endpoints de Pacientes

| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/api/pacientes` | Listar todos | 200 |
| GET | `/api/pacientes/{id}` | Buscar por ID | 200, 404 |
| GET | `/api/pacientes/cpf/{cpf}` | Buscar por CPF | 200, 404 |
| POST | `/api/pacientes` | Cadastrar novo | 201, 400 |
| PUT | `/api/pacientes/{id}` | Atualizar | 200, 404 |
| DELETE | `/api/pacientes/{id}` | Deletar | 204, 404 |

#### Tabela 2: Endpoints de Médicos

| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/api/medicos` | Listar todos | 200 |
| GET | `/api/medicos/{id}` | Buscar por ID | 200, 404 |
| GET | `/api/medicos/especialidade/{esp}` | Buscar por especialidade | 200 |
| POST | `/api/medicos` | Cadastrar novo | 201, 400 |
| PUT | `/api/medicos/{id}` | Atualizar | 200, 404 |
| DELETE | `/api/medicos/{id}` | Deletar | 204, 404 |

#### Tabela 3: Endpoints de Consultas (Principal)

| Método | URI | Descrição | Status |
|--------|-----|-----------|--------|
| GET | `/api/consultas` | Listar todas | 200 |
| GET | `/api/consultas/{id}` | Buscar por ID | 200, 404 |
| GET | `/api/consultas/paciente/{id}` | Por paciente | 200 |
| GET | `/api/consultas/medico/{id}` | Por médico | 200 |
| GET | `/api/consultas/hoje` | Consultas do dia | 200 |
| POST | `/api/consultas` | Agendar | 201, 400 |
| PATCH | `/api/consultas/{id}/confirmar` | Confirmar | 200, 409 |
| PATCH | `/api/consultas/{id}/realizar` | Realizar | 200, 409 |
| PATCH | `/api/consultas/{id}/falta` | Registrar falta | 200, 409 |
| GET | `/api/consultas/estatisticas/geral` | Estatísticas gerais | 200 |

**Códigos de Status HTTP:**
- 200: OK
- 201: Created
- 204: No Content
- 400: Bad Request
- 404: Not Found
- 409: Conflict
- 500: Internal Server Error

---

### 5. Protótipo - Prints das Telas (3-5 páginas)

**Como obter os prints:**

1. **Inicie o projeto:**
   ```bash
   mvn spring-boot:run
   ```

2. **Acesse o Swagger UI:**
   ```
   http://localhost:8080/swagger-ui.html
   ```

3. **Tire prints de:**

   **Print 1 - Tela Principal do Swagger:**
   - Visão geral com todos os controllers (Pacientes, Médicos, Consultas)
   
   **Print 2 - Endpoints de Pacientes:**
   - Expandir o controller de Pacientes
   - Mostrar todos os endpoints disponíveis
   
   **Print 3 - Exemplo de Requisição (POST Paciente):**
   - Clicar em POST /api/pacientes
   - "Try it out"
   - Mostrar o JSON de exemplo:
   ```json
   {
     "nome": "João Silva",
     "cpf": "12345678900",
     "telefone": "(11) 98765-4321"
   }
   ```
   
   **Print 4 - Exemplo de Resposta:**
   - Após executar, mostrar a resposta 201 Created com o objeto criado
   
   **Print 5 - Endpoints de Consultas:**
   - Mostrar os endpoints principais de consultas
   
   **Print 6 - Estatísticas do Paciente:**
   - GET /api/consultas/estatisticas/paciente/{id}
   - Mostrar a resposta com as estatísticas

**Adicione legendas explicativas em cada print:**
- "Tela principal da API com documentação automática"
- "Exemplo de cadastro de paciente com validações"
- "Resposta bem-sucedida após criação"
- etc.

---

### 6. Modelo Entidade-Relacionamento (MER) (1 página)

**Desenhar o diagrama com estas entidades e relacionamentos:**

```
┌─────────────────┐
│    PACIENTES    │
├─────────────────┤
│ 🔑 id           │
│    nome         │
│    cpf (UK)     │
│    telefone     │
└─────────────────┘
         │
         │ 1
         │
         │
         │ N
         ▼
┌─────────────────┐
│    CONSULTAS    │
├─────────────────┤
│ 🔑 id           │
│ 🔗 paciente_id  │──────┐
│ 🔗 medico_id    │      │
│    data_hora    │      │
│    status       │      │
└─────────────────┘      │
         │               │
         │ N             │ 1
         │               │
         ▼               ▼
┌─────────────────┐  ┌─────────────────┐
│     MEDICOS     │  │    PACIENTES    │
├─────────────────┤  └─────────────────┘
│ 🔑 id           │
│    nome         │
│    crm (UK)     │
│    especialidade│
└─────────────────┘
```

**Legenda:**
- 🔑 = Chave Primária
- 🔗 = Chave Estrangeira
- UK = Unique Key (Restrição de unicidade)

**Relacionamentos:**
- Um PACIENTE pode ter N CONSULTAS (1:N)
- Um MÉDICO pode ter N CONSULTAS (1:N)
- Uma CONSULTA pertence a UM PACIENTE e UM MÉDICO

**Atributos Importantes:**
- Status da consulta: 'Agendada', 'Confirmada', 'Realizada', 'Cancelada', 'Falta'

---

### 7. Diagrama de Classes (1-2 páginas)

**Desenhar estrutura simplificada:**

```
┌────────────────────────────┐
│       Paciente             │
├────────────────────────────┤
│ - id: Long                 │
│ - nome: String             │
│ - cpf: String              │
│ - telefone: String         │
├────────────────────────────┤
│ + validarCPF(): boolean    │
│ + getId(): Long            │
│ + getNome(): String        │
│ (getters/setters)          │
└────────────────────────────┘
             ▲
             │
             │ uses
             │
┌────────────────────────────┐
│       Consulta             │
├────────────────────────────┤
│ - id: Long                 │
│ - paciente: Paciente       │◄──────┐
│ - medico: Medico           │◄──┐   │
│ - dataHora: LocalDateTime  │   │   │
│ - status: String           │   │   │
├────────────────────────────┤   │   │
│ + confirmar(): void        │   │   │
│ + realizar(): void         │   │   │
│ + cancelar(): void         │   │   │
│ + registrarFalta(): void   │   │   │
└────────────────────────────┘   │   │
             ▲                   │   │
             │                   │   │
             │ uses              │   │
             │                   │   │
┌────────────────────────────┐   │   │
│        Medico              │   │   │
├────────────────────────────┤   │   │
│ - id: Long                 │───┘   │
│ - nome: String             │       │
│ - crm: String              │       │
│ - especialidade: String    │       │
├────────────────────────────┤       │
│ + validarCRM(): boolean    │       │
│ (getters/setters)          │       │
└────────────────────────────┘       │
                                     │
                                     │
┌───────────────────────────────────┐│
│      PacienteService              ││
├───────────────────────────────────┤│
│ - pacienteRepository              ││
├───────────────────────────────────┤│
│ + cadastrar(Paciente)             ││
│ + atualizar(Long, Paciente)       ││
│ + deletar(Long)                   ││
│ + buscarPorId(Long): Paciente     ├┘
│ + buscarPorCpf(String): Paciente  │
│ + listarTodos(): List<Paciente>   │
└───────────────────────────────────┘
             ▲
             │ uses
             │
┌───────────────────────────────────┐
│     PacienteController            │
├───────────────────────────────────┤
│ - pacienteService                 │
├───────────────────────────────────┤
│ + cadastrar(): ResponseEntity     │
│ + buscarPorId(): ResponseEntity   │
│ + listarTodos(): ResponseEntity   │
│ + atualizar(): ResponseEntity     │
│ + deletar(): ResponseEntity       │
└───────────────────────────────────┘
```

**Mostrar também a arquitetura em camadas:**

```
┌─────────────────────────────────┐
│      Controller Layer           │
│  (API REST - Endpoints)         │
│  - PacienteController           │
│  - MedicoController             │
│  - ConsultaController           │
└─────────────────────────────────┘
              ▼
┌─────────────────────────────────┐
│       Service Layer             │
│  (Regras de Negócio)            │
│  - PacienteService              │
│  - MedicoService                │
│  - ConsultaService              │
└─────────────────────────────────┘
              ▼
┌─────────────────────────────────┐
│     Repository Layer            │
│  (Acesso ao Banco)              │
│  - PacienteRepository           │
│  - MedicoRepository             │
│  - ConsultaRepository           │
└─────────────────────────────────┘
              ▼
┌─────────────────────────────────┐
│      Model Layer                │
│  (Entidades JPA)                │
│  - Paciente                     │
│  - Medico                       │
│  - Consulta                     │
└─────────────────────────────────┘
```

**Padrões de Projeto Aplicados:**
- Repository Pattern
- Service Layer Pattern
- MVC (adaptado para API)
- DTO Pattern
- Dependency Injection

---

## 🛠️ Ferramentas para Criar o PDF

### Opção 1: Microsoft Word
1. Criar documento seguindo a estrutura acima
2. Inserir prints como imagens
3. Desenhar diagramas usando SmartArt ou ferramentas de desenho
4. Salvar como PDF

### Opção 2: Google Docs
1. Seguir mesma estrutura
2. Inserir prints
3. Usar Google Drawings para diagramas
4. Download como PDF

### Opção 3: Markdown + Pandoc (mais técnico)
```bash
pandoc documentacao.md -o documentacao.pdf
```

### Opção 4: Ferramentas de Diagramação
- **draw.io** (https://app.diagrams.net/) - Para MER e Classes
- **Lucidchart** - Para diagramas profissionais
- **PlantUML** - Para gerar diagramas a partir de código

---

## ✅ Checklist Final do PDF

- [ ] Capa com todos os dados
- [ ] Sumário (opcional mas recomendado)
- [ ] Objetivo e escopo (1-2 páginas)
- [ ] Funcionalidades descritas (1-2 páginas)
- [ ] Tabela completa de endpoints (3-4 páginas)
- [ ] Prints do Swagger UI (3-5 páginas)
- [ ] Diagrama MER com relacionamentos (1 página)
- [ ] Diagrama de Classes (1-2 páginas)
- [ ] Numeração de páginas
- [ ] Boa formatação e legibilidade

**Total estimado: 12-18 páginas**

---

## 💡 Dicas Finais

1. **Qualidade dos prints:** Use resolução alta, recorte bem
2. **Legendas:** Sempre explique o que o print mostra
3. **Diagramas:** Mantenha simples e legível
4. **Formatação:** Use fontes profissionais (Arial, Times)
5. **Revisão:** Verifique ortografia e gramática

---

Boa sorte com a documentação! 🚀
