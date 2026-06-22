-- Oficina Mecânica — Script de criação do banco de dados
-- Banco: oficina_mecanica

CREATE TABLE IF NOT EXISTS cliente (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculo (
    id         SERIAL PRIMARY KEY,
    placa      VARCHAR(10)  NOT NULL,
    modelo     VARCHAR(100) NOT NULL,
    ano        INTEGER      NOT NULL,
    id_cliente INTEGER      NOT NULL REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS ordem_servico (
    id         SERIAL PRIMARY KEY,
    id_veiculo INTEGER        NOT NULL REFERENCES veiculo(id),
    descricao  VARCHAR(300)   NOT NULL,
    valor      NUMERIC(10, 2) NOT NULL CHECK (valor >= 0),
    status     VARCHAR(20)    NOT NULL CHECK (status IN ('ABERTA', 'CONCLUIDA'))
);
