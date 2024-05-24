CREATE TABLE Usuario (
    id serial PRIMARY KEY,
    login varchar(100) NOT NULL,
    senha varchar(100) NOT NULL,
    isOnline boolean NOT NULL DEFAULT false
);
