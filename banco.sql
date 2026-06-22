-- Escola de Cursos Livres — Script de criação do banco de dados
-- Banco: escola_cursos

CREATE TABLE IF NOT EXISTS aluno (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(150) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS curso (
    id                SERIAL PRIMARY KEY,
    nome              VARCHAR(100) NOT NULL,
    descricao         VARCHAR(300) NOT NULL,
    carga_horaria     INTEGER      NOT NULL,
    vagas_totais      INTEGER      NOT NULL,
    vagas_disponiveis INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS matricula (
    id              SERIAL PRIMARY KEY,
    id_aluno        INTEGER        NOT NULL REFERENCES aluno(id),
    id_curso        INTEGER        NOT NULL REFERENCES curso(id),
    data_matricula  DATE           NOT NULL,
    valor           NUMERIC(10, 2) NOT NULL CHECK (valor >= 0),
    CONSTRAINT uq_aluno_curso UNIQUE (id_aluno, id_curso)
);
