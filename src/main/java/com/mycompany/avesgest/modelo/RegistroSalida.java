/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Logica del caso de uso "Registrar salida de insumos" (HU-202).
 *
 * Valida los datos, verifica que haya stock suficiente, confirma el registro,
 * resta la cantidad del stock y alerta si el insumo queda en el nivel minimo o
 * por debajo.
 *
 * @author krate
 */
public class RegistroSalida {

    private int idInsumo;
    private int cantidad;
    private LocalDate fecha;
    private MotivoSalida motivo;
    private String comentario;
    private String usuario;

    private final InventarioRepositorio rep;
    private final Mensaje msj;

    public RegistroSalida() {
        this(new InventarioRepositorioMemoria());
    }

    public RegistroSalida(InventarioRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setMotivo(MotivoSalida motivo) {
        this.motivo = motivo;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Insumo> listarInsumos() {
        return rep.listarInsumos();
    }

    public MotivoSalida[] listarMotivos() {
        return MotivoSalida.values();
    }

    public Stock obtenerStock(int idInsumo) {
        return rep.obtenerStock(idInsumo);
    }

    /**
     * Valida los datos obligatorios del formulario de salida.
     *
     * @return true si los datos son validos
     */
    public boolean validarDatos() {
        if (idInsumo <= 0) {
            msj.mostrarError("Debe seleccionar un insumo");
            return false;
        }
        if (cantidad <= 0) {
            msj.mostrarError("La cantidad debe ser un numero mayor que cero");
            return false;
        }
        if (motivo == null) {
            msj.mostrarError("Debe seleccionar un motivo de salida");
            return false;
        }
        if (fecha == null) {
            msj.mostrarError("Debe indicar la fecha de la salida");
            return false;
        }
        if (fecha.isAfter(LocalDate.now())) {
            msj.mostrarError("La fecha no puede ser posterior a hoy");
            return false;
        }
        return true;
    }

    /**
     * Verifica que el stock alcance para la cantidad solicitada.
     *
     * @return true si hay existencias suficientes
     */
    public boolean validarExistencias() {
        Stock stock = rep.obtenerStock(idInsumo);
        if (stock == null) {
            msj.mostrarError("El insumo no tiene stock registrado");
            return false;
        }
        if (!stock.haySuficiente(cantidad)) {
            msj.mostrarError("Existencias insuficientes. Stock disponible: "
                    + stock.getCantidadDisponible());
            return false;
        }
        return true;
    }

    /**
     * Genera el texto de resumen que se muestra antes de confirmar.
     *
     * @return resumen de la salida
     */
    public String generarResumen() {
        Insumo insumo = rep.buscarInsumo(idInsumo);
        return "Resumen de la salida\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Cantidad: " + cantidad + "\n"
                + "Fecha: " + fecha + "\n"
                + "Motivo: " + motivo.getNombre() + "\n"
                + "Comentario: " + (vacio(comentario) ? "(sin comentario)" : comentario) + "\n"
                + "Registrado por: " + usuario;
    }

    /**
     * Confirma el registro: guarda la salida, resta del stock y alerta si queda
     * en nivel minimo o por debajo.
     *
     * @return true si se registro correctamente
     */
    public boolean confirmar() {
        if (!validarDatos() || !validarExistencias()) {
            return false;
        }
        MovimientoInventario salida = MovimientoInventario.salida(
                idInsumo, cantidad, fecha, motivo, comentario, usuario);
        if (!rep.registrarSalida(salida)) {
            msj.mostrarError("Error al guardar la salida. El stock se mantiene sin cambios");
            return false;
        }
        Insumo insumo = rep.buscarInsumo(idInsumo);
        Stock stock = rep.obtenerStock(idInsumo);
        msj.mostrar("Salida registrada correctamente.\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Nuevo stock: " + (stock == null ? "?" : stock.getCantidadDisponible()));
        if (stock != null && stock.enNivelMinimo()) {
            msj.mostrarError("Alerta de stock bajo: el insumo quedo en "
                    + stock.getCantidadDisponible()
                    + " (nivel minimo " + stock.getNivelMinimo() + ")");
        }
        return true;
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
