CREATE TABLE Mensagem (
    id serial PRIMARY KEY,
    emissor integer NOT NULL,
    destinatario integer NOT NULL,
    dataEnvio timestamp NOT NULL,
    conteudo varchar(255) NOT NULL,
    FOREIGN KEY (emissor) REFERENCES Usuario(id),
    FOREIGN KEY (destinatario) REFERENCES Usuario(id)
);