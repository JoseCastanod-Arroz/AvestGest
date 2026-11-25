/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Indica la situacion del stock de un insumo (tabla ESTADO_INSUMO del modelo
 * relacional).
 *
 * <ul>
 *   <li>NORMAL: el stock esta por encima del nivel minimo.</li>
 *   <li>STOCK_BAJO: el stock esta en el nivel minimo o por debajo.</li>
 *   <li>AGOTADO: el stock esta en cero.</li>
 * </ul>
 *
 * @author krate
 */
public enum EstadoInsumo {

    NORMAL("Normal"),
    STOCK_BAJO("Stock bajo"),
    AGOTADO("Agotado");

    private final String descripcion;

    EstadoInsumo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Calcula el estado de un insumo a partir de su stock actual y su nivel
     * minimo configurado.
     *
     * @param cantidadDisponible stock actual del insumo
     * @param nivelMinimo nivel minimo configurado
     * @return el estado correspondiente
     */
    public static EstadoInsumo calcular(int cantidadDisponible, int nivelMinimo) {
        if (cantidadDisponible <= 0) {
            return AGOTADO;
        }
        if (cantidadDisponible <= nivelMinimo) {
            return STOCK_BAJO;
        }
        return NORMAL;
    }
}
