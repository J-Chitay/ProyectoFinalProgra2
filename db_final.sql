DROP TABLE IF EXISTS clientes;

CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    dpi VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    fecha_nacimiento DATE,
    activo BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select*from clientes;

ALTER TABLE clientes
ADD COLUMN password VARCHAR(255);


-- Trigger para actualizar actualizado_en automáticamente
CREATE OR REPLACE FUNCTION actualizar_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.actualizado_en = NOW();
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_cliente
BEFORE UPDATE ON clientes
FOR EACH ROW
EXECUTE FUNCTION actualizar_timestamp();



drop table clientes;



select * from clientes;

drop table clientes;

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

ALTER TABLE usuarios
ADD COLUMN activo BOOLEAN DEFAULT TRUE;


select * from usuarios;

drop table usuarios;
-- Insertar un admin inicial
INSERT INTO usuarios (nombre, email, password, role, activo)
VALUES ('admin', 'admin@gmail.com', '$2a$10$nQ3LskIJ2kK9uG3EsUCinucWOH7/hqIq/BnLE8OBm2WCdSCZKrbtK', 'ADMIN', true);

INSERT INTO usuarios (nombre, email, password, role, activo)
VALUES ('josue', 'josue@gmail.com', '$2a$10$TrYZrF1RRU4G35S46yhQxuxB5SS2ovX/OaK7PZjVh4QDqoj3gwrAW', 'ADMIN', true);

TRUNCATE TABLE usuarios RESTART IDENTITY CASCADE;

SELECT email FROM usuarios;
UPDATE usuarios SET email = TRIM(email);




CREATE TABLE auditoria (
    id BIGSERIAL PRIMARY KEY,
    usuario VARCHAR(255) NOT NULL,
    accion VARCHAR(500) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT now()
);

select * from servicios;
drop table servicios;
drop table citas;

CREATE TABLE servicios (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10,2) NOT NULL,
    duracion INT NOT NULL,
    max_concurrentes INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

select * from auditoria;



select * from usuarios;

ALTER TABLE clientes
ALTER COLUMN fechaNacimiento TYPE VARCHAR(10);


select * from citas c;


CREATE TABLE citas (
    id BIGSERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    accion TEXT, -- notas opcionales
    estado VARCHAR(20) NOT NULL, -- ejemplo: CONFIRMADA, PENDIENTE, CANCELADA

    -- Relaciones
    cliente_id BIGINT NOT NULL,
    servicio_id BIGINT NOT NULL,

    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_servicio FOREIGN KEY (servicio_id)
        REFERENCES servicios(id)
        ON DELETE RESTRICT
);

drop table citas;
