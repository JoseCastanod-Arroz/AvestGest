/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa un movimiento de inventario, sea entrada o salida (tabla
 * MOVIMIENTO_INVENTARIO del modelo relacional).
 *
 * Da trazabilidad completa a cada cambio de stock: guarda el insumo, el tipo,
 * la cantidad, la fecha del movimiento, el proveedor (entradas) o motivo
 * (salidas), un comentario opcional, el usuario responsable y la fecha y hora
 * exacta en que se guardo el registro. Una vez creado, no debe modificarse,
 * para proteger el historial.
 *
 * @author krate
 */
public class MovimientoInventario {

    private int idMovimiento;
    private int idInsumo;
    private TipoMovimiento tipo;
    private MotivoSalida motivo;   // solo en salidas; null en entradas
    private int cantidad;
    private LocalDate fecha;
    private String proveedor;      // solo en entradas; opcional
    private String comentario;     // opcional
    private String usuario;        // credencial del usuario responsable
    private LocalDateTime fechaRegistro;

    public MovimientoInventario() {
    }

    /**
     * Crea un movimiento de entrada.
     */
    public static MovimientoInventario entrada(int idInsumo, int cantidad,
            LocalDate fecha, String proveedor, String comentario, String usuario) {
        MovimientoInventario m = new MovimientoInventario();
        m.tipo = TipoMovimiento.ENTRADA;
        m.idInsumo = idInsumo;
        m.cantidad = cantidad;
        m.fecha = fecha;
        m.proveedor = proveedor;
        m.comentario = comentario;
        m.usuario = usuario;
        m.fechaRegistro = LocalDateTime.now();
        return m;
    }

    /**
     * Crea un movimiento de salida.
     */
    public static MovimientoInventario salida(int idInsumo, int cantidad,
            LocalDate fecha, MotivoSalida motivo, String comentario, String usuario) {
        MovimientoInventario m = new MovimientoInventario();
        m.tipo = TipoMovimiento.SALIDA;
        m.idInsumo = idInsumo;
        m.cantidad = cantidad;
        m.fecha = fecha;
        m.motivo = motivo;
        m.comentario = comentario;
        m.usuario = usuario;
        m.fechaRegistro = LocalDateTime.now();
        return m;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public MotivoSalida getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoSalida motivo) {
        this.motivo = motivo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
