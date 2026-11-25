/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Tipo de movimiento de inventario (tabla TIPO_MOVIMIENTO del modelo
 * relacional): una ENTRADA suma al stock y una SALIDA lo descuenta.
 *
 * @author krate
 */
public enum TipoMovimiento {

    ENTRADA("Entrada"),
    SALIDA("Salida");

    private final String nombre;

    TipoMovimiento(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
