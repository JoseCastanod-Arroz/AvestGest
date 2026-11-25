/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Motivo por el que sale un insumo del inventario (tabla MOTIVO_SALIDA del
 * modelo relacional). Se usa solo en los registros de salida; en las entradas
 * no aplica.
 *
 * @author krate
 */
public enum MotivoSalida {

    CONSUMO("Consumo"),
    VENCIMIENTO("Vencimiento"),
    PERDIDA("Perdida"),
    DANIO("Daño");

    private final String nombre;

    MotivoSalida(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
