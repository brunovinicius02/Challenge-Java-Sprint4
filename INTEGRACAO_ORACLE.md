# 📋 GUIA DE INTEGRAÇÃO - BANCO DE DADOS ORACLE

## ✅ Alterações Realizadas

### 1. **Entidades (Models) - 13 Classes Criadas/Atualizadas**

#### Entidades Atualizadas:
- **Paciente.java** - Ajustada para mapear TB_PACIENTE (adicionados: email, dataNascimento, sexo)
- **Medico.java** - Ajustada para mapear TB_MEDICO
- **Consulta.java** - Ajustada para mapear TB_CONSULTA (adicionados: diagnostico, observacoes, recomendacoes)

#### Novas Entidades Criadas:
- **Login.java** - Mapeia TB_LOGIN
- **Administrador.java** - Mapeia TB_ADMINISTRADOR
- **Assistente.java** - Mapeia TB_ASSISTENTE
- **Atendimento.java** - Mapeia TB_ATENDIMENTO
- **Medicamento.java** - Mapeia TB_MEDICAMENTO
- **ContatoMedico.java** - Mapeia TB_CONTATO_MEDICO
- **ProntuarioPaciente.java** - Mapeia TB_PRONTUARIO_PACIENTE
- **Receita.java** - Mapeia TB_RECEITA
- **ReceitaItem.java** - Mapeia TB_RECEITA_ITEM
- **Mensagem.java** - Mapeia TB_MENSAGEM

### 2. **Repositories - 3 Novos Criados**
- **LoginRepository.java** - Operações com login
- **MedicamentoRepository.java** - Operações com medicamentos
- **ReceitaRepository.java** - Operações com receitas

### 3. **Services - 2 Novos Criados**
- **MedicamentoService.java** - Lógica de negócio para medicamentos
- **ReceitaService.java** - Lógica de negócio para receitas

### 4. **Controllers - 2 Novos Criados**
- **MedicamentoController.java** - 9 endpoints REST
- **ReceitaController.java** - 12 endpoints REST

### 5. **Configurações**
- **application.properties** - Atualizado com configurações JPA/Hibernate para Oracle
- **sequences_oracle.sql** - Script para criar sequências no Oracle

---

## 🔧 Como Configurar e Executar

### Passo 1: Executar Scripts no Banco Oracle

1. **Primeiro**, execute o script do banco de dados da sua colega:
```sql
-- Execute o arquivo: imrea_assistent_2_1.sql
-- Este script cria as tabelas e insere dados de teste
```

2. **Depois**, execute o script de sequências (IMPORTANTE!):
```sql
-- Execute o arquivo: sequences_oracle.sql
-- Este script cria as sequências para geração de IDs
```

### Passo 2: Configurar Credenciais

Edite o arquivo `application.properties`:
```properties
spring.datasource.username=SEU_RM_AQUI
spring.datasource.password=SUA_SENHA_AQUI
```

### Passo 3: Compilar e Executar

```bash
# Limpar e compilar
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

---

## 📊 Estrutura do Banco vs Java

| Tabela Oracle | Classe Java | Status |
|--------------|-------------|--------|
| TB_PACIENTE | Paciente.java | ✅ Atualizada |
| TB_MEDICO | Medico.java | ✅ Atualizada |
| TB_CONSULTA | Consulta.java | ✅ Atualizada |
| TB_LOGIN | Login.java | ✅ Nova |
| TB_ADMINISTRADOR | Administrador.java | ✅ Nova |
| TB_ASSISTENTE | Assistente.java | ✅ Nova |
| TB_ATENDIMENTO | Atendimento.java | ✅ Nova |
| TB_MEDICAMENTO | Medicamento.java | ✅ Nova |
| TB_CONTATO_MEDICO | ContatoMedico.java | ✅ Nova |
| TB_PRONTUARIO_PACIENTE | ProntuarioPaciente.java | ✅ Nova |
| TB_RECEITA | Receita.java | ✅ Nova |
| TB_RECEITA_ITEM | ReceitaItem.java | ✅ Nova |
| TB_MENSAGEM | Mensagem.java | ✅ Nova |

---

## 🎯 Novos Endpoints Disponíveis

### Medicamentos
- `GET /api/medicamentos` - Lista todos
- `GET /api/medicamentos/{id}` - Busca por ID
- `GET /api/medicamentos/nome/{nome}` - Busca por nome
- `GET /api/medicamentos/buscar?nome=xxx` - Busca parcial
- `POST /api/medicamentos` - Cadastra novo
- `PUT /api/medicamentos/{id}` - Atualiza
- `DELETE /api/medicamentos/{id}` - Deleta
- `GET /api/medicamentos/count` - Conta total
- `GET /api/medicamentos/formas-farmaceuticas` - Lista formas

### Receitas
- `GET /api/receitas` - Lista todas
- `GET /api/receitas/{id}` - Busca por ID
- `GET /api/receitas/consulta/{consultaId}` - Por consulta
- `GET /api/receitas/paciente/{pacienteId}` - Por paciente
- `GET /api/receitas/medico/{medicoId}` - Por médico
- `POST /api/receitas/consulta/{consultaId}` - Cria receita
- `POST /api/receitas/{receitaId}/itens` - Adiciona item
- `DELETE /api/receitas/{receitaId}/itens/{itemId}` - Remove item
- `PATCH /api/receitas/{id}/observacoes` - Atualiza observações
- `DELETE /api/receitas/{id}` - Deleta receita
- `GET /api/receitas/periodo` - Por período
- `GET /api/receitas/count/paciente/{pacienteId}` - Conta por paciente

---

## ⚠️ Pontos de Atenção

### 1. IDs Manuais
Como o Oracle não tem auto-increment nativo, os IDs são gerenciados manualmente no código Java. As sequências foram criadas mas não estão sendo usadas automaticamente (pode ser melhorado se necessário).

### 2. Validações
Todas as entidades têm validações Bean Validation configuradas conforme as constraints do banco.

### 3. Relacionamentos
Os relacionamentos entre tabelas estão mapeados com JPA:
- @ManyToOne para chaves estrangeiras
- @OneToMany para coleções
- @OneToOne para relacionamento 1:1

### 4. Compatibilidade
Os endpoints originais (Paciente, Médico, Consulta) continuam funcionando, mas agora com os campos adicionais do banco Oracle.

---

## 📝 Checklist de Entrega

- [x] Código adaptado para banco Oracle
- [x] Todas as 13 tabelas mapeadas
- [x] Repositories criados
- [x] Services implementados
- [x] Controllers REST funcionais
- [x] Validações implementadas
- [x] Tratamento de exceções mantido
- [x] Padrão MVC seguido
- [x] Boas práticas aplicadas
- [ ] Testes realizados
- [ ] Documentação PDF gerada
- [ ] Projeto no GitHub

---

## 🚀 Próximos Passos

1. **Testar todos os endpoints** com o Postman/Swagger
2. **Criar os repositories e services restantes** se necessário
3. **Gerar o PDF da documentação** com os requisitos
4. **Subir no GitHub** com README atualizado
5. **Validar com dados reais** do banco Oracle

---

## 💡 Melhorias Futuras Possíveis

1. Implementar autenticação com JWT usando a tabela TB_LOGIN
2. Criar endpoints para todas as entidades restantes
3. Adicionar paginação nas listagens
4. Implementar cache para otimizar consultas
5. Adicionar testes unitários e de integração
6. Configurar sequences do Oracle para uso automático

---

**Projeto atualizado e pronto para integração com o banco Oracle da FIAP!**
