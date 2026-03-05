-- Criar banco
CREATE DATABASE biblioteca;

-- Conectar ao banco (executar no terminal)
-- \c biblioteca


-- =========================
-- Extensão para UUID
-- =========================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";


-- =========================
-- Criar tipo ENUM
-- =========================
CREATE TYPE genero_livro AS ENUM (
    'ROMANCE',
    'FICCAO',
    'FANTASIA',
    'TERROR',
    'CIENCIA',
    'BIOGRAFIA',
    'OUTRO'
);


-- =========================
-- Tabela: Autor
-- =========================
CREATE TABLE autor (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       nome VARCHAR(150) NOT NULL,
                       data_nascimento DATE,
                       nacionalidade VARCHAR(100)
);


-- =========================
-- Tabela: Livro
-- =========================
CREATE TABLE livro (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       isbn VARCHAR(20) NOT NULL UNIQUE,
                       titulo VARCHAR(200) NOT NULL,
                       data_publicacao DATE,
                       genero genero_livro,
                       preco NUMERIC(10,2) NOT NULL,
                       id_autor UUID NOT NULL,

                       CONSTRAINT fk_livro_autor
                           FOREIGN KEY (id_autor)
                               REFERENCES autor(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
);