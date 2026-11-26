/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * "Base de datos" simulada en memoria para el modulo de ventas.
 *
 * Guarda ventas, sus detalles y sus anulaciones en listas quemadas, y usa un
 * InventarioRepositorio para leer y descontar el stock de los productos, de
 * modo que se comporta igual que la version SQL: registrar una venta baja el
 * stock y anularla lo devuelve.
 *
 * Nota: como en el resto del proyecto, cada operacion crea su propio
 * repositorio si no se le pasa uno; el controlador comparte una unica
 * instancia entre todas las operaciones del modulo para mantener el stock
 * consistente mientras la ventana esta abierta.
 *
 * @author krate
 */
public class VentaRepositorioMemoria implements VentaRepositorio {

    private final InventarioRepositorio inventario;
    private final List<Venta> ventas;
    private final List<AnulacionVenta> anulaciones;
    private int secuenciaVenta;
    private int secuenciaDetalle;
    private int secuenciaAnulacion;

    public VentaRepositorioMemoria() {
        this(new InventarioRepositorioMemoria());
    }

    public VentaRepositorioMemoria(InventarioRepositorio inventario) {
        this.inventario = inventario;
        this.ventas = new ArrayList<>();
        this.anulaciones = new ArrayList<>();
        this.secuenciaVenta = 0;
        this.secuenciaDetalle = 0;
        this.secuenciaAnulacion = 0;
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        Insumo cafe = inventario.buscarInsumo(1);
        Insumo leche = inventario.buscarInsumo(2);
        Insumo vaso = inventario.buscarInsumo(4);
        if (cafe == null || leche == null || vaso == null) {
            return;
        }

        // Venta registrada: su stock ya quedo descontado
        Venta venta1 = nuevaVenta(LocalDateTime.now().minusDays(1),
                EstadoVenta.REGISTRADA);
        venta1.getDetalles().add(nuevoDetalle(venta1, cafe, 2));
        venta1.getDetalles().add(nuevoDetalle(venta1, vaso, 10));
        venta1.setValorTotal(totalDe(venta1));
        descontar(cafe.getIdInsumo(), 2);
        descontar(vaso.getIdInsumo(), 10);
        ventas.add(venta1);

        // Venta anulada: nunca desconto stock y conserva su motivo
        Venta venta2 = nuevaVenta(LocalDateTime.now(), EstadoVenta.ANULADA);
        venta2.getDetalles().add(nuevoDetalle(venta2, leche, 1));
        venta2.setValorTotal(totalDe(venta2));
        venta2.setMotivoAnulacion("Registro por error: la venta no se realizo");
        ventas.add(venta2);

        AnulacionVenta anulacion = new AnulacionVenta();
        anulacion.setIdAnulacion(++secuenciaAnulacion);
        anulacion.setIdVenta(venta2.getIdVenta());
        anulacion.setMotivo(venta2.getMotivoAnulacion());
        anulacion.setFecha(venta2.getFecha());
        anulaciones.add(anulacion);
    }

    private Venta nuevaVenta(LocalDateTime fecha, EstadoVenta estado) {
        Venta venta = new Venta();
        venta.setIdVenta(++secuenciaVenta);
        venta.setUsuario("admin");
        venta.setFecha(fecha);
        venta.setEstado(estado);
        venta.setValorTotal(0);
        return venta;
    }

    private DetalleVenta nuevoDetalle(Venta venta, Insumo insumo, int cantidad) {
        DetalleVenta d = new DetalleVenta();
        d.setIdDetalle(++secuenciaDetalle);
        d.setIdVenta(venta.getIdVenta());
        d.setIdInsumo(insumo.getIdInsumo());
        d.setCodigoProducto(insumo.getCodigo());
        d.setNombreProducto(insumo.getNombre());
        d.setCantidad(cantidad);
        d.setPrecioUnitario(insumo.getPrecio());
        d.setSubtotal(cantidad * insumo.getPrecio());
        return d;
    }

    private double totalDe(Venta venta) {
        double total = 0;
        for (DetalleVenta d : venta.getDetalles()) {
            total += d.getSubtotal();
        }
        return total;
    }

    private void descontar(int idInsumo, int cantidad) {
        Stock stock = inventario.obtenerStock(idInsumo);
        if (stock != null && stock.haySuficiente(cantidad)) {
            stock.restar(cantidad);
        }
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

        // Validar todo el stock antes de tocar nada: o entra completa, o no
        // entra
        for (DetalleVenta d : venta.getDetalles()) {
            Stock stock = inventario.obtenerStock(d.getIdInsumo());
            if (stock == null || !stock.haySuficiente(d.getCantidad())) {
                return false;
            }
        }

        venta.setIdVenta(++secuenciaVenta);
        venta.setFecha(LocalDateTime.now());
        if (venta.getEstado() == null) {
            venta.setEstado(EstadoVenta.REGISTRADA);
        }

        List<DetalleVenta> copia = new ArrayList<>();
        for (DetalleVenta d : venta.getDetalles()) {
            d.setIdDetalle(++secuenciaDetalle);
            d.setIdVenta(venta.getIdVenta());
            copia.add(d);
            descontar(d.getIdInsumo(), d.getCantidad());
        }
        venta.setDetalles(copia);
        ventas.add(venta);
        return true;
    }

    // ----------------------------------------------------------------
    // Anulacion de venta
    // ----------------------------------------------------------------
    @Override
    public boolean anularVenta(int idVenta, String motivo, String credencialAdministrador) {
        if (idVenta <= 0 || motivo == null || motivo.trim().isEmpty()) {
            return false;
        }
        Venta venta = buscarVentaEnLista(idVenta);
        if (venta == null || !venta.estaVigente()) {
            return false;
        }

        venta.setEstado(EstadoVenta.ANULADA);
        venta.setMotivoAnulacion(motivo.trim());

        AnulacionVenta anulacion = new AnulacionVenta();
        anulacion.setIdAnulacion(++secuenciaAnulacion);
        anulacion.setIdVenta(idVenta);
        anulacion.setAdministrador(credencialAdministrador);
        anulacion.setMotivo(motivo.trim());
        anulacion.setFecha(LocalDateTime.now());
        anulaciones.add(anulacion);

        // Devolver los productos al inventario
        for (DetalleVenta d : venta.getDetalles()) {
            Stock stock = inventario.obtenerStock(d.getIdInsumo());
            if (stock != null) {
                stock.sumar(d.getCantidad());
            }
        }
        return true;
    }

    // ----------------------------------------------------------------
    // Consultas de historial
    // ----------------------------------------------------------------
    @Override
    public List<Venta> listarVentas() {
        return ordenar(new ArrayList<>(ventas));
    }

    @Override
    public List<Venta> listarVentasPorFecha(LocalDate desde, LocalDate hasta) {
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getFecha() == null) {
                continue;
            }
            LocalDate fecha = v.getFecha().toLocalDate();
            if (!fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                resultado.add(v);
            }
        }
        return ordenar(resultado);
    }

    @Override
    public List<Venta> listarVentasPorInsumo(int idInsumo) {
        List<Venta> resultado = new ArrayList<>();
        for (Venta v : ventas) {
            for (DetalleVenta d : v.getDetalles()) {
                if (d.getIdInsumo() == idInsumo) {
                    resultado.add(v);
                    break;
                }
            }
        }
        return ordenar(resultado);
    }

    /**
     * Ordena de mas reciente a mas antigua, igual que la consulta SQL.
     *
     * @param lista ventas a ordenar
     * @return la misma lista, ordenada
     */
    private List<Venta> ordenar(List<Venta> lista) {
        lista.sort((a, b) -> {
            if (a.getFecha() == null || b.getFecha() == null) {
                return Integer.compare(b.getIdVenta(), a.getIdVenta());
            }
            int porFecha = b.getFecha().compareTo(a.getFecha());
            return porFecha != 0 ? porFecha
                    : Integer.compare(b.getIdVenta(), a.getIdVenta());
        });
        return lista;
    }

    @Override
    public Venta buscarVenta(int idVenta) {
        Venta original = buscarVentaEnLista(idVenta);
        if (original == null) {
            return null;
        }
        // Se devuelve una copia con sus detalles para que la vista no pueda
        // alterar el historial sin pasar por el repositorio
        Venta copia = new Venta();
        copia.setIdVenta(original.getIdVenta());
        copia.setUsuario(original.getUsuario());
        copia.setFecha(original.getFecha());
        copia.setValorTotal(original.getValorTotal());
        copia.setEstado(original.getEstado());
        copia.setMotivoAnulacion(original.getMotivoAnulacion());
        copia.setDetalles(new ArrayList<>(original.getDetalles()));
        return copia;
    }

    private Venta buscarVentaEnLista(int idVenta) {
        for (Venta v : ventas) {
            if (v.getIdVenta() == idVenta) {
                return v;
            }
        }
        return null;
    }

    @Override
    public List<DetalleVenta> listarDetalles(int idVenta) {
        List<DetalleVenta> resultado = new ArrayList<>();
        Venta venta = buscarVenta(idVenta);
        if (venta != null) {
            resultado.addAll(venta.getDetalles());
        }
        return resultado;
    }
}
