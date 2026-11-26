/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del repositorio de inventario contra PostgreSQL (JDBC).
 *
 * Es la "base de datos real" que reemplaza a InventarioRepositorioMemoria.
 * Como implementa el mismo contrato (InventarioRepositorio), los controladores
 * y la logica no cambian: basta instanciar esta clase en vez de la version en
 * memoria.
 *
 * Mantiene la consistencia del stock dentro de una transaccion: al registrar un
 * movimiento, inserta en movimiento_inventario y actualiza stock en el mismo
 * commit. Una salida nunca deja el stock negativo.
 *
 * @author krate
 */
public class InventarioRepositorioSQL implements InventarioRepositorio {

    @Override
    public List<Insumo> listarInsumos() {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT id_insumo, codigo, nombre, categoria, unidad_medida, precio "
                + "FROM insumo ORDER BY id_insumo";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearInsumo(rs));
            }
        } catch (SQLException ex) {
            reportar("listar insumos", ex);
        }
        return lista;
    }

    @Override
    public Insumo buscarInsumo(int idInsumo) {
        String sql = "SELECT id_insumo, codigo, nombre, categoria, unidad_medida, precio "
                + "FROM insumo WHERE id_insumo = ?";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInsumo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearInsumo(rs);
                }
            }
        } catch (SQLException ex) {
            reportar("buscar insumo", ex);
        }
        return null;
    }

    @Override
    public List<Insumo> buscarCoincidencias(String identificador) {
        List<Insumo> lista = new ArrayList<>();
        String patron = "%" + (identificador == null ? "" : identificador.trim().toLowerCase()) + "%";
        String sql = "SELECT id_insumo, codigo, nombre, categoria, unidad_medida, precio "
                + "FROM insumo "
                + "WHERE LOWER(codigo) LIKE ? OR LOWER(nombre) LIKE ? "
                + "ORDER BY id_insumo";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, patron);
            ps.setString(2, patron);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearInsumo(rs));
                }
            }
        } catch (SQLException ex) {
            reportar("buscar coincidencias", ex);
        }
        return lista;
    }

    @Override
    public boolean existeDuplicado(String codigo, String nombre, int idInsumoExcluir) {
        String sql = "SELECT 1 FROM insumo "
                + "WHERE id_insumo <> ? AND (LOWER(codigo) = LOWER(?) OR LOWER(nombre) = LOWER(?)) "
                + "LIMIT 1";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInsumoExcluir);
            ps.setString(2, codigo);
            ps.setString(3, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            reportar("verificar duplicados", ex);
        }
        return false;
    }

    @Override
    public boolean actualizarInsumo(Insumo insumo) {
        if (insumo == null) {
            return false;
        }
        // Solo cambia la informacion fija; nunca el stock ni los movimientos.
        String sql = "UPDATE insumo "
                + "SET codigo = ?, nombre = ?, categoria = ?, unidad_medida = ?, precio = ? "
                + "WHERE id_insumo = ?";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, insumo.getCodigo());
            ps.setString(2, insumo.getNombre());
            ps.setString(3, insumo.getCategoria());
            ps.setString(4, insumo.getUnidadMedida());
            ps.setDouble(5, insumo.getPrecio());
            ps.setInt(6, insumo.getIdInsumo());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            reportar("actualizar insumo", ex);
        }
        return false;
    }

    @Override
    public Stock obtenerStock(int idInsumo) {
        String sql = "SELECT s.id_insumo, s.cantidad_disponible, "
                + "COALESCE(nm.cantidad_minima, 0) AS nivel_minimo "
                + "FROM stock s "
                + "LEFT JOIN nivel_minimo nm ON nm.id_insumo = s.id_insumo "
                + "WHERE s.id_insumo = ?";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInsumo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearStock(rs);
                }
            }
        } catch (SQLException ex) {
            reportar("obtener stock", ex);
        }
        return null;
    }

    @Override
    public List<Stock> listarStock() {
        List<Stock> lista = new ArrayList<>();
        String sql = "SELECT s.id_insumo, s.cantidad_disponible, "
                + "COALESCE(nm.cantidad_minima, 0) AS nivel_minimo "
                + "FROM stock s "
                + "LEFT JOIN nivel_minimo nm ON nm.id_insumo = s.id_insumo "
                + "ORDER BY s.id_insumo";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearStock(rs));
            }
        } catch (SQLException ex) {
            reportar("listar stock", ex);
        }
        return lista;
    }

    @Override
    public boolean actualizarNivelMinimo(int idInsumo, int nivelMinimo) {
        // Si ya existe un nivel para el insumo, se actualiza; si no, se inserta.
        String update = "UPDATE nivel_minimo SET cantidad_minima = ?, fecha_configuracion = now() "
                + "WHERE id_insumo = ?";
        String insert = "INSERT INTO nivel_minimo (id_nivel, id_insumo, cantidad_minima) "
                + "VALUES (COALESCE((SELECT MAX(id_nivel) FROM nivel_minimo), 0) + 1, ?, ?)";
        try (Connection con = ConexionBD.abrir()) {
            try (PreparedStatement ps = con.prepareStatement(update)) {
                ps.setInt(1, nivelMinimo);
                ps.setInt(2, idInsumo);
                if (ps.executeUpdate() > 0) {
                    return true;
                }
            }
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, idInsumo);
                ps.setInt(2, nivelMinimo);
                return ps.executeUpdate() == 1;
            }
        } catch (SQLException ex) {
            reportar("actualizar nivel minimo", ex);
        }
        return false;
    }

    @Override
    public boolean registrarEntrada(MovimientoInventario movimiento) {
        return registrarMovimiento(movimiento, true);
    }

    @Override
    public boolean registrarSalida(MovimientoInventario movimiento) {
        return registrarMovimiento(movimiento, false);
    }

    /**
     * Inserta el movimiento y actualiza el stock dentro de una sola
     * transaccion. Para salidas, verifica stock suficiente antes de descontar.
     */
    private boolean registrarMovimiento(MovimientoInventario m, boolean esEntrada) {
        if (m == null) {
            return false;
        }
        Connection con = null;
        try {
            con = ConexionBD.abrir();
            con.setAutoCommit(false);

            if (!esEntrada) {
                // Verificar existencias bloqueando la fila del stock
                int disponible = leerCantidadBloqueada(con, m.getIdInsumo());
                if (disponible < 0 || disponible < m.getCantidad()) {
                    con.rollback();
                    return false;
                }
            }

            int idMovimiento = insertarMovimiento(con, m, esEntrada);
            if (idMovimiento <= 0) {
                con.rollback();
                return false;
            }
            m.setIdMovimiento(idMovimiento);

            // Actualizar el stock (suma o resta) y su fecha
            String signo = esEntrada ? "+" : "-";
            String sqlStock = "UPDATE stock "
                    + "SET cantidad_disponible = cantidad_disponible " + signo + " ?, "
                    + "fecha_actualizacion = now() WHERE id_insumo = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlStock)) {
                ps.setInt(1, m.getCantidad());
                ps.setInt(2, m.getIdInsumo());
                if (ps.executeUpdate() != 1) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;
        } catch (SQLException ex) {
            rollbackSilencioso(con);
            reportar(esEntrada ? "registrar entrada" : "registrar salida", ex);
            return false;
        } finally {
            cerrar(con);
        }
    }

    private int leerCantidadBloqueada(Connection con, int idInsumo) throws SQLException {
        String sql = "SELECT cantidad_disponible FROM stock WHERE id_insumo = ? FOR UPDATE";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInsumo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private int insertarMovimiento(Connection con, MovimientoInventario m, boolean esEntrada)
            throws SQLException {
        String sql = "INSERT INTO movimiento_inventario "
                + "(id_movimiento, id_insumo, id_tipo, id_motivo, id_usuario, "
                + " cantidad, fecha, proveedor, comentario, fecha_registro) "
                + "VALUES (COALESCE((SELECT MAX(id_movimiento) FROM movimiento_inventario), 0) + 1, "
                + "?, ?, ?, ?, ?, ?, ?, ?, now())";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getIdInsumo());
            ps.setInt(2, esEntrada ? 1 : 2); // tipo_movimiento: 1 ENTRADA, 2 SALIDA
            if (esEntrada || m.getMotivo() == null) {
                ps.setNull(3, java.sql.Types.SMALLINT);
            } else {
                ps.setInt(3, idMotivo(m.getMotivo()));
            }
            setIdUsuario(ps, 4, m.getUsuario());
            ps.setInt(5, m.getCantidad());
            ps.setDate(6, Date.valueOf(m.getFecha()));
            setStringOpcional(ps, 7, m.getProveedor());
            setStringOpcional(ps, 8, m.getComentario());
            ps.executeUpdate();

            // Recuperar el id insertado (via subconsulta MAX ya calculada)
            String idSql = "SELECT MAX(id_movimiento) FROM movimiento_inventario WHERE id_insumo = ?";
            try (PreparedStatement psId = con.prepareStatement(idSql)) {
                psId.setInt(1, m.getIdInsumo());
                try (ResultSet rs = psId.executeQuery()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
            }
        }
    }

    @Override
    public List<MovimientoInventario> listarMovimientos(int idInsumo) {
        List<MovimientoInventario> lista = new ArrayList<>();
        String sql = "SELECT id_movimiento, id_insumo, id_tipo, id_motivo, "
                + "cantidad, fecha, proveedor, comentario "
                + "FROM movimiento_inventario WHERE id_insumo = ? ORDER BY id_movimiento";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idInsumo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMovimiento(rs));
                }
            }
        } catch (SQLException ex) {
            reportar("listar movimientos", ex);
        }
        return lista;
    }

    // ----------------------------------------------------------------
    // Mapeo de filas a objetos del dominio
    // ----------------------------------------------------------------
    private Insumo mapearInsumo(ResultSet rs) throws SQLException {
        return new Insumo(
                rs.getInt("id_insumo"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getString("unidad_medida"),
                rs.getDouble("precio"));
    }

    private Stock mapearStock(ResultSet rs) throws SQLException {
        return new Stock(
                rs.getInt("id_insumo"),
                rs.getInt("cantidad_disponible"),
                rs.getInt("nivel_minimo"));
    }

    private MovimientoInventario mapearMovimiento(ResultSet rs) throws SQLException {
        int idTipo = rs.getInt("id_tipo");
        MovimientoInventario m;
        if (idTipo == 1) {
            m = MovimientoInventario.entrada(
                    rs.getInt("id_insumo"), rs.getInt("cantidad"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("proveedor"), rs.getString("comentario"), null);
        } else {
            m = MovimientoInventario.salida(
                    rs.getInt("id_insumo"), rs.getInt("cantidad"),
                    rs.getDate("fecha").toLocalDate(),
                    motivoDesde(rs.getInt("id_motivo")), rs.getString("comentario"), null);
        }
        m.setIdMovimiento(rs.getInt("id_movimiento"));
        return m;
    }

    private int idMotivo(MotivoSalida motivo) {
        switch (motivo) {
            case CONSUMO:     return 1;
            case VENCIMIENTO: return 2;
            case PERDIDA:     return 3;
            case DANIO:       return 4;
            default:          return 1;
        }
    }

    private MotivoSalida motivoDesde(int id) {
        switch (id) {
            case 2:  return MotivoSalida.VENCIMIENTO;
            case 3:  return MotivoSalida.PERDIDA;
            case 4:  return MotivoSalida.DANIO;
            default: return MotivoSalida.CONSUMO;
        }
    }

    // ----------------------------------------------------------------
    // Utilidades
    // ----------------------------------------------------------------
    private void setStringOpcional(PreparedStatement ps, int indice, String valor) throws SQLException {
        if (valor == null || valor.trim().isEmpty()) {
            ps.setNull(indice, java.sql.Types.VARCHAR);
        } else {
            ps.setString(indice, valor.trim());
        }
    }

    /**
     * Guarda el id del usuario responsable. Como la credencial que llega es un
     * texto (ej. "admin"), se busca su id en la tabla usuario; si no existe, se
     * guarda null para no romper la operacion.
     */
    private void setIdUsuario(PreparedStatement ps, int indice, String credencial) throws SQLException {
        Integer id = buscarIdUsuario(ps.getConnection(), credencial);
        if (id == null) {
            ps.setNull(indice, java.sql.Types.INTEGER);
        } else {
            ps.setInt(indice, id);
        }
    }

    private Integer buscarIdUsuario(Connection con, String credencial) throws SQLException {
        if (credencial == null) {
            return null;
        }
        String sql = "SELECT id_usuario FROM usuario WHERE usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, credencial);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }

    private void rollbackSilencioso(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ignore) {
                // nada mas que hacer
            }
        }
    }

    private void cerrar(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ignore) {
                // nada mas que hacer
            }
        }
    }

    private void reportar(String operacion, SQLException ex) {
        new Mensaje().mostrarError("Error de base de datos al " + operacion
                + ":\n" + ex.getMessage());
    }
}
