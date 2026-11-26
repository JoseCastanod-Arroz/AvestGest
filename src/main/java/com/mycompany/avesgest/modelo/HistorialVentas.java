/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Logica de los casos de uso de consulta del modulo de ventas
 * (procesos 2, 3 y 4 del documento).
 *
 * Reune las tres formas de recuperar el historial: todas las ventas, las
 * ventas de un rango de fechas y las ventas que incluyen un producto, ademas
 * del detalle de una venta especifica. Es una consulta de solo lectura: no
 * modifica ventas ni stock.
 *
 * @author krate
 */
public class HistorialVentas {

    private final VentaRepositorio rep;
    private final Mensaje msj;

    public HistorialVentas() {
        this(new VentaRepositorioMemoria());
    }

    public HistorialVentas(VentaRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
    }

    /**
     * Devuelve todas las ventas del historial.
     *
     * @return lista de ventas
     */
    public List<Venta> listarTodas() {
        return rep.listarVentas();
    }

    /**
     * Valida que el rango de fechas este completo y sea coherente.
     *
     * @param desde fecha inicial del rango
     * @param hasta fecha final del rango
     * @return true si el rango se puede consultar
     */
    public boolean validarRangoFechas(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            msj.mostrarError("Debe indicar la fecha inicial y la fecha final "
                    + "del rango (formato AAAA-MM-DD)");
            return false;
        }
        if (desde.isAfter(hasta)) {
            msj.mostrarError("La fecha inicial no puede ser posterior "
                    + "a la fecha final");
            return false;
        }
        return true;
    }

    /**
     * Consulta las ventas dentro de un rango de fechas.
     *
     * @param desde fecha inicial (inclusiva)
     * @param hasta fecha final (inclusiva)
     * @return lista de ventas del rango
     */
    public List<Venta> consultarPorFecha(LocalDate desde, LocalDate hasta) {
        return rep.listarVentasPorFecha(desde, hasta);
    }

    /**
     * Consulta las ventas que incluyen un producto.
     *
     * @param idInsumo producto vendido
     * @return lista de ventas que contienen el producto
     */
    public List<Venta> consultarPorProducto(int idInsumo) {
        return rep.listarVentasPorInsumo(idInsumo);
    }

    /**
     * Recupera el detalle completo de una venta, incluido el motivo de
     * anulacion si la venta esta anulada.
     *
     * @param idVenta venta a consultar
     * @return la venta con sus detalles, o null si no existe
     */
    public Venta consultarDetalle(int idVenta) {
        return rep.buscarVenta(idVenta);
    }
}
