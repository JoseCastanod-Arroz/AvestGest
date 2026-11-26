/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Representa una linea del detalle de una venta (tabla DETALLE_VENTA del
 * diccionario de datos, seccion 10.4.6).
 *
 * Guarda el insumo vendido, la cantidad, el precio unitario que tenia el
 * producto en el momento de la venta y el subtotal de la linea. Los campos
 * codigoProducto y nombreProducto son de solo lectura: los completa el
 * repositorio para mostrarlos en la vista y no se almacenan.
 *
 * @author krate
 */
public class DetalleVenta {

    private int idDetalle;
    private int idVenta;
    private int idInsumo;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Datos del insumo solo para presentacion en la vista
    private String codigoProducto;
    private String nombreProducto;

    public DetalleVenta() {
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
