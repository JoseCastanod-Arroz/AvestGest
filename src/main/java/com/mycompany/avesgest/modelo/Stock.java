/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDateTime;

/**
 * Maneja la cantidad disponible de un insumo (tabla STOCK del modelo
 * relacional).
 *
 * La cantidad disponible equivale a la suma de las entradas menos las salidas
 * registradas, y se actualiza con cada movimiento. Tambien guarda el nivel
 * minimo configurado para el insumo, usado para generar las alertas de stock
 * bajo.
 *
 * @author krate
 */
public class Stock {

    private int idInsumo;
    private int cantidadDisponible;
    private int nivelMinimo;
    private LocalDateTime fechaActualizacion;

    public Stock() {
    }

    public Stock(int idInsumo, int cantidadDisponible, int nivelMinimo) {
        this.idInsumo = idInsumo;
        this.cantidadDisponible = cantidadDisponible;
        this.nivelMinimo = nivelMinimo;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public int getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(int nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Suma una cantidad al stock disponible (registro de entrada).
     *
     * @param cantidad unidades que ingresan
     */
    public void sumar(int cantidad) {
        setCantidadDisponible(cantidadDisponible + cantidad);
    }

    /**
     * Resta una cantidad al stock disponible (registro de salida o venta).
     *
     * @param cantidad unidades que salen
     */
    public void restar(int cantidad) {
        setCantidadDisponible(cantidadDisponible - cantidad);
    }

    /**
     * Indica si una salida de cierta cantidad es posible sin dejar el stock
     * negativo.
     *
     * @param cantidad unidades que se quieren retirar
     * @return true si hay stock suficiente
     */
    public boolean haySuficiente(int cantidad) {
        return cantidad <= cantidadDisponible;
    }

    /**
     * Indica si el stock esta en el nivel minimo o por debajo.
     *
     * @return true si el insumo esta en alerta de stock bajo
     */
    public boolean enNivelMinimo() {
        return cantidadDisponible <= nivelMinimo;
    }

    public EstadoInsumo getEstado() {
        return EstadoInsumo.calcular(cantidadDisponible, nivelMinimo);
    }
}
