/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementacion del repositorio de ventas contra PostgreSQL (JDBC).
 *
 * Es la "base de datos real" que reemplaza a VentaRepositorioMemoria. Como
 * implementa el mismo contrato, los controladores y la logica no cambian.
 *
 * La lectura de productos y stock se delega en InventarioRepositorio, para no
 * repetir el SQL del modulo 2. El registro de una venta y su anulacion se
 * ejecutan en una unica transaccion: o se guarda todo, o no se guarda nada.
 *
 * @author krate
 */
public class VentaRepositorioSQL implements VentaRepositorio {

    // Columnas comunes de VENTA, con el motivo de anulacion por si existe.
    private static final String COLUMNAS =
            "v.id_venta, u.usuario, v.fecha, v.valor_total, v.estado, an.motivo";

    // From compartido por todas las consultas de venta.
    private static final String DESDE_VENTA =
            "FROM venta v "
            + "LEFT JOIN usuario u ON u.id_usuario = v.id_usuario "
            + "LEFT JOIN anulacion_venta an ON an.id_venta = v.id_venta ";

    private final InventarioRepositorio inventario;

    public VentaRepositorioSQL() {
        this(new InventarioRepositorioSQL());
    }

    public VentaRepositorioSQL(InventarioRepositorio inventario) {
        this.inventario = inventario;
    }

    // ----------------------------------------------------------------
    // Productos y stock (delegados al modulo de inventario)
    // ----------------------------------------------------------------
    @Override
    public List<Insumo> listarInsumos() {
        return inventario.listarInsumos();
    }

    @Override
    public Stock obtenerStock(int idInsumo) {
        return inventario.obtenerStock(idInsumo);
    }

    // ----------------------------------------------------------------
    // Registro de venta
    // ----------------------------------------------------------------
    @Override
    public boolean guardarVenta(Venta venta) {
        if (venta == null || venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            return false;
        }

        // Orden ascendente por insumo: evita interbloqueos entre dos ventas
        // simultaneas que comparten productos.
        List<DetalleVenta> detalles = new ArrayList<>(venta.getDetalles());
        detalles.sort(Comparator.comparingInt(DetalleVenta::getIdInsumo));

        Connection con = null;
        try {
            con = ConexionBD.abrir();
            con.setAutoCommit(false);

            // Bloquear el stock de cada producto antes de validar, para que
            // nadie lo modifique mientras se confirma la venta.
            for (DetalleVenta d : detalles) {
                int disponible = leerCantidadBloqueada(con, d.getIdInsumo());
                if (disponible < 0 || disponible < d.getCantidad()) {
                    con.rollback();
                    return false;
                }
            }

            int idVenta = insertarVenta(con, venta);
            if (idVenta <= 0) {
                con.rollback();
                return false;
            }
            venta.setIdVenta(idVenta);

            for (DetalleVenta d : detalles) {
                d.setIdVenta(idVenta);
                if (!insertarDetalle(con, d)) {
                    con.rollback();
                    return false;
                }
            }

            // Actualizar el stock de cada producto vendido
            for (DetalleVenta d : detalles) {
                if (!actualizarStock(con, d.getIdInsumo(), d.getCantidad(), "-")) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;
        } catch (SQLException ex) {
            rollbackSilencioso(con);
            reportar("guardar la venta", ex);
            return false;
        } finally {
            cerrar(con);
        }
    }

    private int insertarVenta(Connection con, Venta venta) throws SQLException {
        String sql = "INSERT INTO venta (id_usuario, fecha, valor_total, estado) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Integer idUsuario = buscarIdUsuario(con, venta.getUsuario());
            if (idUsuario == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, idUsuario);
            }
            LocalDateTime fecha = venta.getFecha() == null ? LocalDateTime.now() : venta.getFecha();
            ps.setTimestamp(2, Timestamp.valueOf(fecha));
            ps.setDouble(3, venta.getValorTotal());
            ps.setString(4, (venta.getEstado() == null
                    ? EstadoVenta.REGISTRADA : venta.getEstado()).name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private boolean insertarDetalle(Connection con, DetalleVenta d) throws SQLException {
        String sql = "INSERT INTO detalle_venta "
                + "(id_venta, id_insumo, cantidad, precio_unitario, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdVenta());
            ps.setInt(2, d.getIdInsumo());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getSubtotal());
            return ps.executeUpdate() == 1;
        }
    }

    // ----------------------------------------------------------------
    // Anulacion de venta
    // ----------------------------------------------------------------
    @Override
    public boolean anularVenta(int idVenta, String motivo, String credencialAdministrador) {
        if (idVenta <= 0 || motivo == null || motivo.trim().isEmpty()) {
            return false;
        }

        Connection con = null;
        try {
            con = ConexionBD.abrir();
            con.setAutoCommit(false);

            // Verificar el estado bloqueando la fila de la venta
            if (!ventaAnulable(con, idVenta)) {
                con.rollback();
                return false;
            }

            // 1) Registrar la anulacion con su motivo
            String sqlAnulacion = "INSERT INTO anulacion_venta "
                    + "(id_venta, id_administrador, motivo, fecha) VALUES (?, ?, ?, now())";
            try (PreparedStatement ps = con.prepareStatement(sqlAnulacion)) {
                ps.setInt(1, idVenta);
                Integer idAdmin = buscarIdUsuario(con, credencialAdministrador);
                if (idAdmin == null) {
                    ps.setNull(2, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(2, idAdmin);
                }
                ps.setString(3, motivo.trim());
                ps.executeUpdate();
            }

            // 2) Marcar la venta como anulada (conservandola en el historial)
            String sqlVenta = "UPDATE venta SET estado = 'ANULADA' WHERE id_venta = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlVenta)) {
                ps.setInt(1, idVenta);
                if (ps.executeUpdate() != 1) {
                    con.rollback();
                    return false;
                }
            }

            // 3) Devolver los productos al inventario
            String sqlDetalle = "SELECT id_insumo, cantidad FROM detalle_venta "
                    + "WHERE id_venta = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                ps.setInt(1, idVenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        if (!actualizarStock(con, rs.getInt("id_insumo"),
                                rs.getInt("cantidad"), "+")) {
                            con.rollback();
                            return false;
                        }
                    }
                }
            }

            con.commit();
            return true;
        } catch (SQLException ex) {
            rollbackSilencioso(con);
            reportar("anular la venta", ex);
            return false;
        } finally {
            cerrar(con);
        }
    }

    /**
     * Indica si la venta existe y aun no esta anulada, bloqueando su fila.
     */
    private boolean ventaAnulable(Connection con, int idVenta) throws SQLException {
        String sql = "SELECT estado FROM venta WHERE id_venta = ? FOR UPDATE";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        && !EstadoVenta.ANULADA.name().equals(rs.getString(1));
            }
        }
    }

    // ----------------------------------------------------------------
    // Consultas de historial
    // ----------------------------------------------------------------
    @Override
    public List<Venta> listarVentas() {
        String sql = "SELECT " + COLUMNAS + " " + DESDE_VENTA
                + "ORDER BY v.fecha DESC, v.id_venta DESC";
        return consultarVentas(sql);
    }

    @Override
    public List<Venta> listarVentasPorFecha(LocalDate desde, LocalDate hasta) {
        String sql = "SELECT " + COLUMNAS + " " + DESDE_VENTA
                + "WHERE v.fecha >= ? AND v.fecha < ? "
                + "ORDER BY v.fecha DESC, v.id_venta DESC";
        // hasta se amplia un dia para incluirlo completo
        return consultarVentas(sql,
                Timestamp.valueOf(desde.atStartOfDay()),
                Timestamp.valueOf(hasta.plusDays(1).atStartOfDay()));
    }

    @Override
    public List<Venta> listarVentasPorInsumo(int idInsumo) {
        // DISTINCT: una venta con varias lineas del mismo producto no se repite
        String sql = "SELECT DISTINCT " + COLUMNAS + " " + DESDE_VENTA
                + "JOIN detalle_venta dv ON dv.id_venta = v.id_venta "
                + "WHERE dv.id_insumo = ? "
                + "ORDER BY v.fecha DESC, v.id_venta DESC";
        return consultarVentas(sql, idInsumo);
    }

    @Override
    public Venta buscarVenta(int idVenta) {
        String sql = "SELECT " + COLUMNAS + " " + DESDE_VENTA
                + "WHERE v.id_venta = ?";
        List<Venta> lista = consultarVentas(sql, idVenta);
        if (lista.isEmpty()) {
            return null;
        }
        Venta venta = lista.get(0);
        venta.setDetalles(listarDetalles(venta.getIdVenta()));
        return venta;
    }

    @Override
    public List<DetalleVenta> listarDetalles(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT dv.id_detalle, dv.id_venta, dv.id_insumo, "
                + "i.codigo, i.nombre, dv.cantidad, dv.precio_unitario, dv.subtotal "
                + "FROM detalle_venta dv "
                + "JOIN insumo i ON i.id_insumo = dv.id_insumo "
                + "WHERE dv.id_venta = ? ORDER BY dv.id_detalle";
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta d = new DetalleVenta();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdVenta(rs.getInt("id_venta"));
                    d.setIdInsumo(rs.getInt("id_insumo"));
                    d.setCodigoProducto(rs.getString("codigo"));
                    d.setNombreProducto(rs.getString("nombre"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    d.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(d);
                }
            }
        } catch (SQLException ex) {
            reportar("listar el detalle de la venta", ex);
        }
        return lista;
    }

    private List<Venta> consultarVentas(String sql, Object... parametros) {
        List<Venta> lista = new ArrayList<>();
        try (Connection con = ConexionBD.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        } catch (SQLException ex) {
            reportar("listar ventas", ex);
        }
        return lista;
    }

    // ----------------------------------------------------------------
    // Utilidades
    // ----------------------------------------------------------------
    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setIdVenta(rs.getInt("id_venta"));
        venta.setUsuario(rs.getString("usuario"));
        Timestamp fecha = rs.getTimestamp("fecha");
        if (fecha != null) {
            venta.setFecha(fecha.toLocalDateTime());
        }
        venta.setValorTotal(rs.getDouble("valor_total"));
        venta.setEstado(EstadoVenta.desdeTexto(rs.getString("estado")));
        venta.setMotivoAnulacion(rs.getString("motivo"));
        return venta;
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

    private boolean actualizarStock(Connection con, int idInsumo, int cantidad, String signo)
            throws SQLException {
        String sql = "UPDATE stock "
                + "SET cantidad_disponible = cantidad_disponible " + signo + " ?, "
                + "fecha_actualizacion = now() WHERE id_insumo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idInsumo);
            return ps.executeUpdate() == 1;
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
