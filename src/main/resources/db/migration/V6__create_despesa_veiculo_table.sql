CREATE TABLE despesa_veiculo (
    id BIGSERIAL PRIMARY KEY,
    veiculo_id BIGINT NOT NULL REFERENCES veiculo(id),
    categoria VARCHAR(20) NOT NULL,
    descricao VARCHAR(250),
    valor NUMERIC(10,2) NOT NULL,
    data DATE NOT NULL DEFAULT CURRENT_DATE
);