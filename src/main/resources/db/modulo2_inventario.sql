-- ============================================================
--  AvesGest - Modulo 2: Gestion de Inventario
--  Script de creacion de base de datos y tablas (PostgreSQL)
--
--  Basado en el modelo relacional y el diccionario de datos
--  del documento del proyecto (secciones 9.5.5 y 9.5.6).
--
--  Orden de ejecucion:
--    1) Como superusuario postgres:   \i 00_crear_bd_usuario.sql
--    2) Conectarse como la app:        \c avesgest avesgest
--    3) Crear las tablas (este script):\i modulo2_inventario.sql
--
--  Al conectarse como el usuario 'avesgest' (dueño de la BD), las
--  tablas quedan de su propiedad y no hace falta otorgar permisos.
-- ============================================================


-- ============================================================
--  USUARIOS (Modulo 1) - requerido por la trazabilidad del inventario
--  Se incluye una version minima por si la BD aun no lo tiene.
-- ============================================================

CREATE TABLE IF NOT EXISTS estado_usuario (
    id_estado   SMALLINT     PRIMARY KEY,
    nombre      VARCHAR(20)  NOT NULL UNIQUE   -- ACTIVO, INACTIVO
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario       INTEGER      PRIMARY KEY,
    usuario          VARCHAR(50)  NOT NULL UNIQUE,  -- credencial de ingreso
    clave_hash       VARCHAR(255) NOT NULL,
    id_estado        SMALLINT     REFERENCES estado_usuario(id_estado),
    id_administrador INTEGER      REFERENCES usuario(id_usuario)
);


-- ============================================================
--  CATALOGOS DEL INVENTARIO
-- ============================================================

-- Situacion del stock de un insumo: NORMAL, STOCK_BAJO, AGOTADO
CREATE TABLE IF NOT EXISTS estado_insumo (
    id_estado_insumo SMALLINT     PRIMARY KEY,
    nombre           VARCHAR(20)  NOT NULL UNIQUE
);

-- Tipo de movimiento: ENTRADA (suma) o SALIDA (descuenta)
CREATE TABLE IF NOT EXISTS tipo_movimiento (
    id_tipo SMALLINT     PRIMARY KEY,
    nombre  VARCHAR(20)  NOT NULL UNIQUE
);

-- Motivo de una salida: consumo, vencimiento, perdida, daño
CREATE TABLE IF NOT EXISTS motivo_salida (
    id_motivo SMALLINT     PRIMARY KEY,
    nombre    VARCHAR(30)  NOT NULL UNIQUE
);


-- ============================================================
--  INSUMO Y STOCK
-- ============================================================

-- Informacion fija del insumo
CREATE TABLE IF NOT EXISTS insumo (
    id_insumo        INTEGER       PRIMARY KEY,
    codigo           VARCHAR(20)   NOT NULL UNIQUE,
    nombre           VARCHAR(100)  NOT NULL UNIQUE,
    categoria        VARCHAR(50),
    unidad_medida    VARCHAR(20),
    precio           NUMERIC(10,2) NOT NULL CHECK (precio > 0),
    id_estado_insumo SMALLINT      REFERENCES estado_insumo(id_estado_insumo)
);

-- Cantidad disponible de cada insumo (suma de entradas menos salidas)
CREATE TABLE IF NOT EXISTS stock (
    id_insumo           INTEGER     PRIMARY KEY REFERENCES insumo(id_insumo),
    cantidad_disponible INTEGER     NOT NULL DEFAULT 0 CHECK (cantidad_disponible >= 0),
    fecha_actualizacion TIMESTAMP   NOT NULL DEFAULT now()
);

-- Nivel minimo configurado por insumo (con trazabilidad del cambio)
CREATE TABLE IF NOT EXISTS nivel_minimo (
    id_nivel           INTEGER    PRIMARY KEY,
    id_insumo          INTEGER    NOT NULL REFERENCES insumo(id_insumo),
    id_usuario         INTEGER    REFERENCES usuario(id_usuario),
    cantidad_minima    INTEGER    NOT NULL CHECK (cantidad_minima >= 0),
    fecha_configuracion TIMESTAMP NOT NULL DEFAULT now()
);


-- ============================================================
--  MOVIMIENTOS DE INVENTARIO (historial, no se modifica)
-- ============================================================

CREATE TABLE IF NOT EXISTS movimiento_inventario (
    id_movimiento  INTEGER     PRIMARY KEY,
    id_insumo      INTEGER     NOT NULL REFERENCES insumo(id_insumo),
    id_tipo        SMALLINT    NOT NULL REFERENCES tipo_movimiento(id_tipo),
    id_motivo      SMALLINT    REFERENCES motivo_salida(id_motivo),  -- vacio en entradas
    id_usuario     INTEGER     REFERENCES usuario(id_usuario),
    cantidad       INTEGER     NOT NULL CHECK (cantidad > 0),
    fecha          DATE        NOT NULL,
    proveedor      VARCHAR(100),   -- opcional, solo en entradas
    comentario     TEXT,           -- opcional
    fecha_registro TIMESTAMP   NOT NULL DEFAULT now()
);


-- ============================================================
--  DATOS BASE DE LOS CATALOGOS
-- ============================================================

INSERT INTO estado_insumo (id_estado_insumo, nombre) VALUES
    (1, 'NORMAL'),
    (2, 'STOCK_BAJO'),
    (3, 'AGOTADO')
ON CONFLICT (id_estado_insumo) DO NOTHING;

INSERT INTO tipo_movimiento (id_tipo, nombre) VALUES
    (1, 'ENTRADA'),
    (2, 'SALIDA')
ON CONFLICT (id_tipo) DO NOTHING;

INSERT INTO motivo_salida (id_motivo, nombre) VALUES
    (1, 'Consumo'),
    (2, 'Vencimiento'),
    (3, 'Perdida'),
    (4, 'Daño')
ON CONFLICT (id_motivo) DO NOTHING;


-- ============================================================
--  DATOS DE PRUEBA (equivalen a los quemados en memoria)
-- ============================================================

INSERT INTO insumo (id_insumo, codigo, nombre, categoria, unidad_medida, precio, id_estado_insumo) VALUES
    (1, 'CAF001', 'Cafe de origen',   'Bebidas',   'kg',     35000.00, 1),
    (2, 'LEC001', 'Leche entera',     'Bebidas',   'litro',   4500.00, 2),
    (3, 'AZU001', 'Azucar',           'Abarrotes', 'kg',      3800.00, 3),
    (4, 'VAS001', 'Vaso desechable',  'Empaques',  'unidad',   150.00, 1)
ON CONFLICT (id_insumo) DO NOTHING;

INSERT INTO stock (id_insumo, cantidad_disponible) VALUES
    (1, 20),
    (2, 8),
    (3, 0),
    (4, 500)
ON CONFLICT (id_insumo) DO NOTHING;

INSERT INTO nivel_minimo (id_nivel, id_insumo, cantidad_minima) VALUES
    (1, 1, 5),
    (2, 2, 10),
    (3, 3, 3),
    (4, 4, 100)
ON CONFLICT (id_nivel) DO NOTHING;
