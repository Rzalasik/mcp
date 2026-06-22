-- Clínica Veterinária — Script de criação do banco de dados
-- Banco: clinica_veterinaria

CREATE TABLE IF NOT EXISTS tutor (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    endereco  VARCHAR(200) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS animal (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    especie   VARCHAR(50)  NOT NULL,
    raca      VARCHAR(50)  NOT NULL,
    id_tutor  INTEGER      NOT NULL REFERENCES tutor(id)
);

CREATE TABLE IF NOT EXISTS consulta (
    id        SERIAL PRIMARY KEY,
    id_animal INTEGER        NOT NULL REFERENCES animal(id),
    data      DATE           NOT NULL,
    motivo    VARCHAR(200)   NOT NULL,
    valor     NUMERIC(10, 2) NOT NULL CHECK (valor >= 0)
);
