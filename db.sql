DROP TABLE IF EXISTS clientes;

CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    identificador VARCHAR(255) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    telefono INT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE clientes
    ALTER COLUMN fecha_nacimiento TYPE DATE USING TO_DATE(fecha_nacimiento, 'YYYY/MM/DD');



select * from clientes;

drop table clientes;

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);


drop table usuarios;
-- Insertar un admin inicial
INSERT INTO usuarios (nombre, email, password, role)
VALUES ('admin', 'admin@gmail.com', '12345678', 'ADMIN');

CREATE TABLE auditoria (
    id BIGSERIAL PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL,
    accion VARCHAR(500) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT now()
);

select * from auditoria;



select * from usuarios;

ALTER TABLE clientes
ALTER COLUMN fechaNacimiento TYPE VARCHAR(10);
