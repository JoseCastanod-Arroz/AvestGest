-- ============================================================
--  AvesGest - Creacion de base de datos y usuario de aplicacion
--  Ejecutar conectado como superusuario (postgres), NO dentro
--  de la base de datos avesgest.
--
--  Uso:
--    psql -U postgres -f 00_crear_bd_usuario.sql
--  o, dentro de psql:
--    \i /ruta/.../db/00_crear_bd_usuario.sql
-- ============================================================

-- Usuario que usara la aplicacion (coincide con ConexionBD.java).
-- Cambiar la clave si se desea; recordar reflejarla en ConexionBD.
DO
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'avesgest') THEN
        CREATE ROLE avesgest LOGIN PASSWORD 'avesgest';
    END IF;
END
$$;

-- Base de datos de la aplicacion, propiedad del usuario avesgest.
-- CREATE DATABASE no se puede ejecutar dentro de un bloque DO ni de
-- una transaccion, por eso va aparte. Si ya existe, psql avisara y se
-- puede ignorar el error.
CREATE DATABASE avesgest
    WITH OWNER = avesgest
         ENCODING = 'UTF8'
         TEMPLATE = template0;

-- Permisos sobre la base de datos
GRANT ALL PRIVILEGES ON DATABASE avesgest TO avesgest;
