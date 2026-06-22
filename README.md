# Cenário 2 — Sistema de Oficina Mecânica

## Tabelas identificadas

### cliente
```sql
CREATE TABLE cliente (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    telefone  VARCHAR(20)  NOT NULL
);
```

### veiculo
```sql
CREATE TABLE veiculo (
    id         SERIAL PRIMARY KEY,
    placa      VARCHAR(10)  NOT NULL,
    modelo     VARCHAR(100) NOT NULL,
    ano        INTEGER      NOT NULL,
    id_cliente INTEGER      NOT NULL REFERENCES cliente(id)
);
```

### ordem_servico
```sql
CREATE TABLE ordem_servico (
    id         SERIAL PRIMARY KEY,
    id_veiculo INTEGER        NOT NULL REFERENCES veiculo(id),
    descricao  VARCHAR(300)   NOT NULL,
    valor      NUMERIC(10, 2) NOT NULL,
    status     VARCHAR(20)    NOT NULL
);
```

## Regras de negócio

1. Um cliente pode ter mais de um veículo cadastrado.
2. Não é permitido abrir uma ordem de serviço para um veículo que não esteja cadastrado.
3. O valor do serviço não pode ser negativo.
4. A ordem de serviço possui status: `ABERTA` ou `CONCLUIDA`.
5. É possível consultar todo o histórico de manutenções de um veículo específico.

## Fluxo simulado na Main

`Cliente` → `Veículo` → `Ordem de Serviço`
