/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una venta registrada (tabla VENTA del diccionario de datos,
 * seccion 10.4.6).
 *
 * Guarda la informacion cabecera de la venta: quien la registro, la fecha, el
 * valor total calculado a partir del precio de los productos y su estado
 * (registrada o anulada). Los productos vendidos viven en la lista de
 * {@link DetalleVenta}, y el motivo de anulacion solo se carga cuando la venta
 * esta anulada.
 *
 * @author krate
 */
public class Venta {

    private int idVenta;
    private String usuario;              // credencial de quien registro la venta
    private LocalDateTime fecha;
    private double valorTotal;
    private EstadoVenta estado;
    private String motivoAnulacion;      // solo si esta anulada; puede ser null
    private List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.estado = EstadoVenta.REGISTRADA;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles == null ? new ArrayList<>() : detalles;
    }

    /**
     * Indica si la venta sigue vigente (fue registrada y no se anulo).
     *
     * @return true si la venta no esta anulada
     */
    public boolean estaVigente() {
        return estado != EstadoVenta.ANULADA;
    }
}
