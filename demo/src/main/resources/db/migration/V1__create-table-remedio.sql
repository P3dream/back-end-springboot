CREATE TABLE remedio (
    id serial PRIMARY KEY,
    nome varchar(100) NOT NULL,
    via varchar(100) NOT NULL,
    lote varchar(100) NOT NULL,
    quantidade int NOT NULL,
    validade varchar(100) NOT NULL,
    laboratorio varchar(100) NOT NULL
);