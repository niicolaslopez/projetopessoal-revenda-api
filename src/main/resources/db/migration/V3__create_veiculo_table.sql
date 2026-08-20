CREATE TABLE veiculo (
    id BIGSERIAL PRIMARY KEY,
    marca VARCHAR(21) NOT NULL,
    modelo VARCHAR (21) NOT NULL,
    ano INTEGER NOT NULL,
    preco NUMERIC (10,2) NOT NULL,
    status VARCHAR(21) NOT NULL,
    descricao VARCHAR (250),
    criado_em TIMESTAMP NOT NULL DEFAULT now()
);