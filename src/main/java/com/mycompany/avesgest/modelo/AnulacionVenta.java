/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDateTime;

/**
 * Representa la anulacion de una venta (tabla ANULACION_VENTA del diccionario
 * de datos, seccion 10.4.6).
 *
 * Guarda que venta se anulo, que administrador la anulo, el motivo indicado y
 * la fecha exacta de la anulacion. La venta anulada no se borra del historial:
 * solo cambia de estado y aqui queda el registro del motivo.
 *
 * @author krate
 */
public class AnulacionVenta {

    private int idAnulacion;
    private int idVenta;
    private String administrador;   // credencial del administrador
    private String motivo;
    private LocalDateTime fecha;

    public AnulacionVenta() {
    }

    public int getIdAnulacion() {
        return idAnulacion;
    }

    public void setIdAnulacion(int idAnulacion) {
        this.idAnulacion = idAnulacion;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
