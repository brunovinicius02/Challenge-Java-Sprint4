-- ========================================
-- SCRIPT DE DADOS DE TESTE
-- Sistema TeleconsultasHC
-- ========================================

-- IMPORTANTE: Execute este script APÓS a aplicação criar as tabelas automaticamente

-- ========================================
-- LIMPAR DADOS EXISTENTES (OPCIONAL)
-- ========================================

-- Descomente as linhas abaixo se quiser limpar os dados antes de inserir
-- DELETE FROM lembretes;
-- DELETE FROM historico_comparecimento;
-- DELETE FROM consultas;
-- DELETE FROM medicos;
-- DELETE FROM pacientes;

-- ========================================
-- INSERIR PACIENTES
-- ========================================

INSERT INTO pacientes (nome, cpf, telefone) VALUES ('João Silva Santos', '12345678900', '(11) 98765-4321');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Maria Oliveira Costa', '98765432100', '(11) 91234-5678');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Pedro Almeida Souza', '11122233344', '(11) 99999-8888');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Ana Paula Ferreira', '55566677788', '(11) 97777-6666');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Carlos Eduardo Lima', '99988877766', '(11) 96666-5555');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Juliana Santos Rocha', '44433322211', '(11) 95555-4444');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Roberto Carlos Dias', '33322211100', '(11) 94444-3333');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Fernanda Lima Silva', '22211100099', '(11) 93333-2222');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Lucas Henrique Alves', '11100099988', '(11) 92222-1111');
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Patrícia Costa Santos', '00099988877', '(11) 91111-0000');

COMMIT;

-- ========================================
-- INSERIR MÉDICOS
-- ========================================

INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dr. Carlos Pereira', 'CRM/SP123456', 'Cardiologia');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dra. Marina Santos', 'CRM/SP234567', 'Dermatologia');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dr. Roberto Lima', 'CRM/SP345678', 'Pediatria');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dra. Juliana Costa', 'CRM/SP456789', 'Ginecologia');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dr. Fernando Alves', 'CRM/SP567890', 'Ortopedia');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dra. Beatriz Rocha', 'CRM/SP678901', 'Psiquiatria');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dr. Paulo Souza', 'CRM/SP789012', 'Neurologia');
INSERT INTO medicos (nome, crm, especialidade) VALUES ('Dra. Amanda Silva', 'CRM/SP890123', 'Oftalmologia');

COMMIT;

-- ========================================
-- INSERIR CONSULTAS - MIX DE STATUS
-- ========================================

-- Consultas Agendadas (Futuras)
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (1, 1, TO_TIMESTAMP('2025-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Agendada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (2, 2, TO_TIMESTAMP('2025-11-21 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Agendada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (3, 3, TO_TIMESTAMP('2025-11-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Confirmada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (4, 4, TO_TIMESTAMP('2025-11-23 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Confirmada');

-- Consultas Realizadas (Passadas)
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (1, 1, TO_TIMESTAMP('2025-10-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (2, 2, TO_TIMESTAMP('2025-10-16 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (3, 3, TO_TIMESTAMP('2025-10-17 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (4, 4, TO_TIMESTAMP('2025-10-18 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (5, 5, TO_TIMESTAMP('2025-10-19 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

-- Consultas com Falta
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (6, 6, TO_TIMESTAMP('2025-10-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Falta');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (6, 7, TO_TIMESTAMP('2025-10-21 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Falta');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (7, 1, TO_TIMESTAMP('2025-10-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Falta');

-- Consultas Canceladas
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (8, 2, TO_TIMESTAMP('2025-10-23 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Cancelada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (9, 3, TO_TIMESTAMP('2025-10-24 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Cancelada');

-- Mais consultas para estatísticas
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (1, 2, TO_TIMESTAMP('2025-09-10 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (1, 3, TO_TIMESTAMP('2025-09-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (2, 4, TO_TIMESTAMP('2025-09-20 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (2, 5, TO_TIMESTAMP('2025-09-25 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Falta');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (3, 6, TO_TIMESTAMP('2025-09-30 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Realizada');

COMMIT;

-- ========================================
-- VERIFICAR DADOS INSERIDOS
-- ========================================

-- Contar registros
SELECT 'Pacientes' AS Tabela, COUNT(*) AS Total FROM pacientes
UNION ALL
SELECT 'Médicos', COUNT(*) FROM medicos
UNION ALL
SELECT 'Consultas', COUNT(*) FROM consultas;

-- Estatísticas por status
SELECT status, COUNT(*) AS Quantidade 
FROM consultas 
GROUP BY status 
ORDER BY Quantidade DESC;

-- Pacientes com mais consultas
SELECT 
    p.nome,
    COUNT(c.id) AS Total_Consultas,
    SUM(CASE WHEN c.status = 'Realizada' THEN 1 ELSE 0 END) AS Comparecimentos,
    SUM(CASE WHEN c.status = 'Falta' THEN 1 ELSE 0 END) AS Faltas
FROM pacientes p
LEFT JOIN consultas c ON p.id = c.paciente_id
GROUP BY p.nome
ORDER BY Total_Consultas DESC;

-- Médicos com mais consultas
SELECT 
    m.nome,
    m.especialidade,
    COUNT(c.id) AS Total_Consultas
FROM medicos m
LEFT JOIN consultas c ON m.id = c.medico_id
GROUP BY m.nome, m.especialidade
ORDER BY Total_Consultas DESC;

-- ========================================
-- QUERIES ÚTEIS PARA TESTES
-- ========================================

-- Buscar consultas futuras
-- SELECT * FROM consultas WHERE data_hora > CURRENT_TIMESTAMP ORDER BY data_hora;

-- Buscar consultas de hoje
-- SELECT * FROM consultas WHERE TRUNC(data_hora) = TRUNC(CURRENT_TIMESTAMP);

-- Pacientes com risco de bloqueio (2 ou mais faltas)
-- SELECT 
--     p.nome,
--     COUNT(*) AS Total_Faltas
-- FROM pacientes p
-- JOIN consultas c ON p.id = c.paciente_id
-- WHERE c.status = 'Falta'
-- GROUP BY p.nome
-- HAVING COUNT(*) >= 2;

-- ========================================
-- CENÁRIOS DE TESTE ESPECÍFICOS
-- ========================================

-- Cenário 1: Paciente com histórico perfeito (100% comparecimento)
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Paciente Ideal', '11111111111', '(11) 91111-1111');
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '11111111111'), 
    1, 
    TO_TIMESTAMP('2025-09-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Realizada'
);
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '11111111111'), 
    2, 
    TO_TIMESTAMP('2025-09-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Realizada'
);
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '11111111111'), 
    3, 
    TO_TIMESTAMP('2025-10-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Realizada'
);

-- Cenário 2: Paciente no limite (exatamente 3 faltas - será bloqueado)
INSERT INTO pacientes (nome, cpf, telefone) VALUES ('Paciente No Limite', '22222222222', '(11) 92222-2222');
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '22222222222'), 
    1, 
    TO_TIMESTAMP('2025-09-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Falta'
);
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '22222222222'), 
    2, 
    TO_TIMESTAMP('2025-09-15 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Falta'
);
INSERT INTO consultas (paciente_id, medico_id, data_hora, status) 
VALUES (
    (SELECT id FROM pacientes WHERE cpf = '22222222222'), 
    3, 
    TO_TIMESTAMP('2025-10-01 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 
    'Falta'
);

COMMIT;

-- ========================================
-- MENSAGEM FINAL
-- ========================================

SELECT 'Dados de teste inseridos com sucesso!' AS Mensagem FROM DUAL;
SELECT 'Execute a aplicação e acesse o Swagger UI para testar os endpoints!' AS Proxima_Acao FROM DUAL;
