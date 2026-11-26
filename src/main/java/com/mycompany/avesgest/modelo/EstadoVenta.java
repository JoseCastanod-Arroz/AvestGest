/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Indica el estado de una venta (atributo ESTADO de la tabla VENTA del diccionario
 * de datos, seccion 10.4.6).
 *
 * <ul>
 *   <li>REGISTRADA: la venta fue guardada y su stock ya fue descontado.</li>
 *   <li>ANULADA: la venta fue anulada y sus productos volvieron al inventario.</li>
 * </ul>
 *
 * @author krate
 */
public enum EstadoVenta {

    REGISTRADA("Registrada"),
    ANULADA("Anulada");

    private final String descripcion;

    EstadoVenta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Convierte el texto guardado en la base de datos al valor del enum.
     *
     * @param texto estado leido de la columna venta.estado
     * @return el estado correspondiente; REGISTRADA si el texto no se reconoce
     */
    public static EstadoVenta desdeTexto(String texto) {
        if (texto == null) {
            return REGISTRADA;
        }
        for (EstadoVenta estado : values()) {
            if (estado.name().equalsIgnoreCase(texto.trim())) {
                return estado;
            }
        }
        return REGISTRADA;
    }
}
