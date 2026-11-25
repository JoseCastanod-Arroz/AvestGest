/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Logica del caso de uso "Registrar entrada de insumos" (HU-201).
 *
 * Valida los datos del formulario, confirma el registro y suma la cantidad al
 * stock del insumo, dejando trazabilidad con el usuario que la registro.
 *
 * @author krate
 */
public class RegistroEntrada {

    private int idInsumo;
    private int cantidad;
    private LocalDate fecha;
    private String proveedor;
    private String comentario;
    private String usuario;

    private final InventarioRepositorio rep;
    private final Mensaje msj;

    public RegistroEntrada() {
        this(new InventarioRepositorioMemoria());
    }

    public RegistroEntrada(InventarioRepositorio rep) {
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

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public Stock obtenerStock(int idInsumo) {
        return rep.obtenerStock(idInsumo);
    }

    /**
     * Valida los datos obligatorios del formulario de entrada.
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
        if (fecha == null) {
            msj.mostrarError("Debe indicar la fecha de la entrada");
            return false;
        }
        if (fecha.isAfter(LocalDate.now())) {
            msj.mostrarError("La fecha no puede ser posterior a hoy");
            return false;
        }
        return true;
    }

    /**
     * Genera el texto de resumen que se muestra antes de confirmar.
     *
     * @return resumen de la entrada
     */
    public String generarResumen() {
        Insumo insumo = rep.buscarInsumo(idInsumo);
        return "Resumen de la entrada\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Cantidad: " + cantidad + "\n"
                + "Fecha: " + fecha + "\n"
                + "Proveedor: " + (vacio(proveedor) ? "(sin proveedor)" : proveedor) + "\n"
                + "Comentario: " + (vacio(comentario) ? "(sin comentario)" : comentario) + "\n"
                + "Registrado por: " + usuario;
    }

    /**
     * Confirma el registro: guarda la entrada y suma la cantidad al stock.
     *
     * @return true si se registro correctamente
     */
    public boolean confirmar() {
        if (!validarDatos()) {
            return false;
        }
        MovimientoInventario entrada = MovimientoInventario.entrada(
                idInsumo, cantidad, fecha, proveedor, comentario, usuario);
        if (!rep.registrarEntrada(entrada)) {
            msj.mostrarError("Error al guardar la entrada. El stock se mantiene sin cambios");
            return false;
        }
        Insumo insumo = rep.buscarInsumo(idInsumo);
        Stock stock = rep.obtenerStock(idInsumo);
        msj.mostrar("Entrada registrada correctamente.\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Nuevo stock: " + (stock == null ? "?" : stock.getCantidadDisponible()));
        return true;
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
