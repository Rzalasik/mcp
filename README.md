# Cenário 3 — Sistema de Escola de Cursos Livres

## Tabelas identificadas

### aluno
```sql
CREATE TABLE aluno (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(150) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);
```

### curso
```sql
CREATE TABLE curso (
    id                  SERIAL PRIMARY KEY,
    nome                VARCHAR(100) NOT NULL,
    descricao           VARCHAR(300) NOT NULL,
    carga_horaria       INTEGER      NOT NULL,
    vagas_totais        INTEGER      NOT NULL,
    vagas_disponiveis   INTEGER      NOT NULL
);
```

### matricula
```sql
CREATE TABLE matricula (
    id              SERIAL PRIMARY KEY,
    id_aluno        INTEGER        NOT NULL REFERENCES aluno(id),
    id_curso        INTEGER        NOT NULL REFERENCES curso(id),
    data_matricula  DATE           NOT NULL,
    valor           NUMERIC(10, 2) NOT NULL
);
```

## Regras de negócio

1. Não é permitido matricular um aluno em um curso que já atingiu o limite de vagas.
2. Não é permitido matricular o mesmo aluno duas vezes no mesmo curso.
3. O valor da matrícula não pode ser negativo.
4. Não é possível matricular um aluno que não esteja cadastrado.
5. Não é possível matricular em um curso que não esteja cadastrado.
6. Ao realizar uma matrícula, o número de vagas disponíveis do curso é decrementado.
7. É possível consultar todos os alunos matriculados em um curso e todos os cursos de um aluno.

## Fluxo simulado na Main

`Aluno` → `Curso` → `Matrícula`
