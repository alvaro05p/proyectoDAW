CREATE DATABASE hand2hand;

USE hand2hand;

CREATE TABLE coches (
    id_coche INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    distancia INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    ano INT NOT NULL
);

CREATE TABLE motos (
    id_moto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    distancia INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    ano INT NOT NULL
);

CREATE TABLE hogar (
    id_hogar INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    distancia INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    ano INT NOT NULL
);

CREATE TABLE deporte (
    id_deporte INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    distancia INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    ano INT NOT NULL
);

CREATE TABLE moda (
    id_moda INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    distancia INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    ano INT NOT NULL
);
