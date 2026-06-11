# Cenário 1 — Sistema de Clínica Veterinária

## Tabelas identificadas

### tutor
```sql
CREATE TABLE tutor (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    endereco  VARCHAR(200) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);
```

### animal
```sql
CREATE TABLE animal (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    especie   VARCHAR(50)  NOT NULL,
    raca      VARCHAR(50)  NOT NULL,
    id_tutor  INTEGER      NOT NULL REFERENCES tutor(id)
);
```

### consulta
```sql
CREATE TABLE consulta (
    id        SERIAL PRIMARY KEY,
    id_animal INTEGER        NOT NULL REFERENCES animal(id),
    data      DATE           NOT NULL,
    motivo    VARCHAR(200)   NOT NULL,
    valor     NUMERIC(10, 2) NOT NULL
);
```

## Regras de negócio

1. Um tutor pode ter mais de um animal cadastrado.
2. Não é permitido registrar uma consulta para um animal que não esteja cadastrado.
3. O valor da consulta não pode ser negativo.
4. É possível consultar todas as consultas de um animal específico (histórico).
5. É possível listar todos os animais de um tutor específico.

## Fluxo simulado na Main

`Tutor` → `Animal` → `Consulta`
