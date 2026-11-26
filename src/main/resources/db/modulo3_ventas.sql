-- ============================================================
--  AvesGest - Modulo 3: Gestion de Ventas
--  Script de creacion de tablas (PostgreSQL).
--
--  Basado en el modelo relacional y el diccionario de datos
--  del documento del proyecto (secciones 10.4.4 y 10.4.6).
--
--  Los productos que se venden son los insumos del Modulo 2, por
--  lo que este script se ejecuta DESPUES de modulo2_inventario.sql.
--
--  Orden de ejecucion:
--    1) Como superusuario postgres:   \i 00_crear_bd_usuario.sql
--    2) Conectarse como la app:        \c avesgest avesgest
--    3) Crear el inventario:           \i modulo2_inventario.sql
--    4) Crear las ventas:              \i modulo3_ventas.sql
-- ============================================================


-- ============================================================
--  VENTA - cabecera de cada venta registrada (diccionario VENTA)
-- ============================================================

CREATE TABLE IF NOT EXISTS venta (
    id_venta    INTEGER       PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_usuario  INTEGER       REFERENCES usuario(id_usuario),  -- quien la registro
    fecha       TIMESTAMP     NOT NULL DEFAULT now(),
    valor_total NUMERIC(12,2) NOT NULL CHECK (valor_total >= 0),
    estado      VARCHAR(20)   NOT NULL
                           CHECK (estado IN ('REGISTRADA', 'ANULADA'))
);


-- ============================================================
--  DETALLE_VENTA - productos que componen cada venta
--  El producto es un insumo del Modulo 2 (FK id_insumo).
-- ============================================================

CREATE TABLE IF NOT EXISTS detalle_venta (
    id_detalle      INTEGER       PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_venta        INTEGER       NOT NULL REFERENCES venta(id_venta),
    id_insumo       INTEGER       NOT NULL REFERENCES insumo(id_insumo),
    cantidad        INTEGER       NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL CHECK (precio_unitario >= 0),
    subtotal        NUMERIC(12,2) NOT NULL CHECK (subtotal >= 0)
);


-- ============================================================
--  ANULACION_VENTA - motivo de anulacion de una venta
--  Relacion 1:1 con venta (una venta solo se anula una vez).
-- ============================================================

CREATE TABLE IF NOT EXISTS anulacion_venta (
    id_anulacion     INTEGER   PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_venta         INTEGER   NOT NULL UNIQUE REFERENCES venta(id_venta),
    id_administrador INTEGER   REFERENCES usuario(id_usuario),
    motivo           TEXT      NOT NULL CHECK (length(trim(motivo)) > 0),
    fecha            TIMESTAMP NOT NULL DEFAULT now()
);


-- ============================================================
--  INDICES para las consultas del modulo (historial y filtro)
-- ============================================================

CREATE INDEX IF NOT EXISTS idx_venta_fecha    ON venta (fecha);
CREATE INDEX IF NOT EXISTS idx_venta_estado   ON venta (estado);
CREATE INDEX IF NOT EXISTS idx_detalle_venta  ON detalle_venta (id_venta);
CREATE INDEX IF NOT EXISTS idx_detalle_insumo ON detalle_venta (id_insumo);


-- ============================================================
--  DATOS DE PRUEBA
--  Se cargan solo si la tabla venta esta vacia, para que el
--  script pueda re-ejecutarse sin duplicar registros.
--  El stock de los insumos se ajusta igual que en una venta real:
--  las ventas ANULADAS nunca descontaron inventario.
-- ============================================================

DO
$$
DECLARE
    v_id_venta   INTEGER;
    v_precio     NUMERIC;
    v_id_insumo  INTEGER;
BEGIN
    IF EXISTS (SELECT 1 FROM venta) THEN
        RETURN;
    END IF;

    -- -------------------------------------------------------
    -- Venta 1: REGISTRADA (2 cafes y 10 vasos) -> descuenta stock
    -- -------------------------------------------------------
    SELECT precio, id_insumo INTO v_precio, v_id_insumo
      FROM insumo WHERE codigo = 'CAF001';

    IF v_precio IS NOT NULL THEN
        INSERT INTO venta (id_usuario, fecha, valor_total, estado)
        VALUES (NULL, now() - interval '1 day',
                (2 * v_precio)
              + (10 * COALESCE((SELECT precio FROM insumo WHERE codigo = 'VAS001'), 0)),
                'REGISTRADA')
        RETURNING id_venta INTO v_id_venta;

        INSERT INTO detalle_venta
               (id_venta, id_insumo, cantidad, precio_unitario, subtotal)
        SELECT v_id_venta, id_insumo, 2, precio, 2 * precio
          FROM insumo WHERE codigo = 'CAF001';

        INSERT INTO detalle_venta
               (id_venta, id_insumo, cantidad, precio_unitario, subtotal)
        SELECT v_id_venta, id_insumo, 10, precio, 10 * precio
          FROM insumo WHERE codigo = 'VAS001';

        UPDATE stock
           SET cantidad_disponible = cantidad_disponible - 2,
               fecha_actualizacion = now()
         WHERE id_insumo = v_id_insumo;

        UPDATE stock
           SET cantidad_disponible = cantidad_disponible - 10,
               fecha_actualizacion = now()
         WHERE id_insumo = (SELECT id_insumo FROM insumo WHERE codigo = 'VAS001');
    END IF;

    -- -------------------------------------------------------
    -- Venta 2: ANULADA (1 leche) -> el stock no cambio
    -- -------------------------------------------------------
    SELECT precio, id_insumo INTO v_precio, v_id_insumo
      FROM insumo WHERE codigo = 'LEC001';

    IF v_precio IS NOT NULL THEN
        INSERT INTO venta (id_usuario, fecha, valor_total, estado)
        VALUES (NULL, now(), v_precio, 'ANULADA')
        RETURNING id_venta INTO v_id_venta;

        INSERT INTO detalle_venta
               (id_venta, id_insumo, cantidad, precio_unitario, subtotal)
        SELECT v_id_venta, id_insumo, 1, precio, precio
          FROM insumo WHERE codigo = 'LEC001';

        INSERT INTO anulacion_venta (id_venta, id_administrador, motivo, fecha)
        VALUES (v_id_venta, NULL,
                'Registro por error: la venta no se realizo', now());
    END IF;
END
$$;
