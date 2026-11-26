/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Logica del caso de uso "Registrar venta" (No. 01 del documento, proceso 1).
 *
 * Maneja la canasta de productos de la venta: agrega productos validando las
 * existencias disponibles (descontando lo que ya esta en la canasta), calcula
 * el subtotal de cada linea y el total, y al confirmar guarda la venta y
 * descuenta el stock en una sola operacion.
 *
 * @author krate
 */
public class RegistroVenta {

    private String usuario;
    private LocalDateTime fecha;
    private final List<DetalleVenta> canasta;

    private final VentaRepositorio rep;
    private final Mensaje msj;

    public RegistroVenta() {
        this(new VentaRepositorioMemoria());
    }

    public RegistroVenta(VentaRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
        this.canasta = new ArrayList<>();
        this.fecha = LocalDateTime.now();
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<Insumo> listarInsumos() {
        return rep.listarInsumos();
    }

    public Stock obtenerStock(int idInsumo) {
        return rep.obtenerStock(idInsumo);
    }

    public List<DetalleVenta> getCanasta() {
        return canasta;
    }

    /**
     * Agrega un producto a la canasta.
     *
     * Valida que la cantidad sea mayor que cero y que el stock alcance para lo
     * que ya esta en la canasta mas lo que se quiere agregar. Si el producto ya
     * estaba en la canasta, se suman las cantidades en una sola linea.
     *
     * @param insumo producto seleccionado
     * @param cantidad unidades a vender
     * @return true si el producto quedo agregado
     */
    public boolean agregarProducto(Insumo insumo, int cantidad) {
        if (insumo == null) {
            msj.mostrarError("Debe seleccionar un producto");
            return false;
        }
        if (cantidad <= 0) {
            msj.mostrarError("La cantidad debe ser un numero mayor que cero");
            return false;
        }

        Stock stock = rep.obtenerStock(insumo.getIdInsumo());
        if (stock == null) {
            msj.mostrarError("El producto no tiene stock registrado");
            return false;
        }

        int yaEnCanasta = 0;
        for (DetalleVenta d : canasta) {
            if (d.getIdInsumo() == insumo.getIdInsumo()) {
                yaEnCanasta += d.getCantidad();
            }
        }
        if (!stock.haySuficiente(yaEnCanasta + cantidad)) {
            msj.mostrarError("Existencias insuficientes para \""
                    + insumo.getNombre() + "\".\n"
                    + "Disponible: " + stock.getCantidadDisponible() + "\n"
                    + "Ya agregado en la venta: " + yaEnCanasta);
            return false;
        }

        // Si ya estaba en la canasta se acumula en la misma linea
        for (DetalleVenta d : canasta) {
            if (d.getIdInsumo() == insumo.getIdInsumo()) {
                d.setCantidad(d.getCantidad() + cantidad);
                d.setSubtotal(d.getCantidad() * d.getPrecioUnitario());
                return true;
            }
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setIdInsumo(insumo.getIdInsumo());
        detalle.setCodigoProducto(insumo.getCodigo());
        detalle.setNombreProducto(insumo.getNombre());
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(insumo.getPrecio());
        detalle.setSubtotal(cantidad * insumo.getPrecio());
        canasta.add(detalle);
        return true;
    }

    /**
     * Quita una linea de la canasta para corregir la venta.
     *
     * @param indice posicion de la linea en la tabla
     * @return true si se quito la linea
     */
    public boolean quitarProducto(int indice) {
        if (indice < 0 || indice >= canasta.size()) {
            msj.mostrarError("Seleccione un producto de la venta para quitarlo");
            return false;
        }
        canasta.remove(indice);
        return true;
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleVenta d : canasta) {
            total += d.getSubtotal();
        }
        return total;
    }

    /**
     * Valida que la venta tenga al menos un producto.
     *
     * @return true si la venta esta completa
     */
    public boolean validarDatos() {
        if (canasta.isEmpty()) {
            msj.mostrarError("Debe agregar al menos un producto a la venta");
            return false;
        }
        return true;
    }

    /**
     * Genera el texto del resumen que se muestra antes de confirmar.
     *
     * @return resumen de la venta
     */
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder("Resumen de la venta\n");
        for (DetalleVenta d : canasta) {
            resumen.append("  - ").append(d.getNombreProducto())
                    .append(" x").append(d.getCantidad())
                    .append("  $").append(d.getSubtotal())
                    .append("\n");
        }
        resumen.append("Total: $").append(calcularTotal());
        resumen.append("\nRegistrado por: ").append(usuario);
        return resumen.toString();
    }

    /**
     * Confirma la venta: la guarda en la base de datos y descuenta el stock.
     * Si falla el guardado, el stock queda sin cambios y se puede reintentar.
     *
     * @return true si la venta se registro correctamente
     */
    public boolean confirmar() {
        if (!validarDatos()) {
            return false;
        }

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setFecha(fecha);
        venta.setValorTotal(calcularTotal());
        venta.setEstado(EstadoVenta.REGISTRADA);
        venta.setDetalles(new ArrayList<>(canasta));

        if (!rep.guardarVenta(venta)) {
            msj.mostrarError("Error al guardar la venta. "
                    + "El stock se mantiene sin cambios. Puede reintentar");
            return false;
        }

        msj.mostrar("Venta registrada correctamente.\n"
                + "Venta N. " + venta.getIdVenta() + "\n"
                + "Total: $" + venta.getValorTotal() + "\n"
                + "Stock actualizado.");
        canasta.clear();
        return true;
    }
}
