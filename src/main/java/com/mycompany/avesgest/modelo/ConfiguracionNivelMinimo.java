/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.List;

/**
 * Logica del caso de uso "Configuracion de nivel minimo de stock" (HU-204).
 *
 * Operacion exclusiva del administrador. Permite cambiar el nivel minimo de un
 * insumo sin tocar su stock ni sus movimientos, y alerta si al aplicar el nuevo
 * nivel el stock queda en o por debajo del minimo.
 *
 * @author krate
 */
public class ConfiguracionNivelMinimo {

    private int idInsumo;
    private int nuevoNivel;
    private String usuario;

    private final InventarioRepositorio rep;
    private final Mensaje msj;

    public ConfiguracionNivelMinimo() {
        this(new InventarioRepositorioMemoria());
    }

    public ConfiguracionNivelMinimo(InventarioRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public void setNuevoNivel(int nuevoNivel) {
        this.nuevoNivel = nuevoNivel;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Insumo> listarInsumos() {
        return rep.listarInsumos();
    }

    public Insumo buscarInsumo(int idInsumo) {
        return rep.buscarInsumo(idInsumo);
    }

    public Stock obtenerStock(int idInsumo) {
        return rep.obtenerStock(idInsumo);
    }

    /**
     * Valida el nuevo nivel minimo y que de verdad cambie respecto al actual.
     *
     * @return true si se puede continuar con el cambio
     */
    public boolean validar() {
        if (idInsumo <= 0) {
            msj.mostrarError("Debe seleccionar un insumo");
            return false;
        }
        if (nuevoNivel < 0) {
            msj.mostrarError("El nivel minimo debe ser un numero igual o mayor que cero");
            return false;
        }
        Stock stock = rep.obtenerStock(idInsumo);
        if (stock == null) {
            msj.mostrarError("El insumo seleccionado no existe");
            return false;
        }
        if (stock.getNivelMinimo() == nuevoNivel) {
            msj.mostrar("No hay cambios para guardar: el nivel minimo ya es " + nuevoNivel);
            return false;
        }
        return true;
    }

    /**
     * Genera el resumen con el nivel anterior y el nuevo.
     *
     * @return resumen del cambio
     */
    public String generarResumen() {
        Insumo insumo = rep.buscarInsumo(idInsumo);
        Stock stock = rep.obtenerStock(idInsumo);
        return "Resumen del cambio de nivel minimo\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Nivel minimo anterior: " + (stock == null ? "?" : stock.getNivelMinimo()) + "\n"
                + "Nivel minimo nuevo: " + nuevoNivel + "\n"
                + "Modificado por: " + usuario;
    }

    /**
     * Confirma el cambio: guarda el nuevo nivel minimo y alerta si el stock
     * actual queda en ese nivel o por debajo.
     *
     * @return true si se guardo correctamente
     */
    public boolean confirmar() {
        if (!validar()) {
            return false;
        }
        if (!rep.actualizarNivelMinimo(idInsumo, nuevoNivel)) {
            msj.mostrarError("No se pudo guardar el nivel minimo");
            return false;
        }
        Insumo insumo = rep.buscarInsumo(idInsumo);
        Stock stock = rep.obtenerStock(idInsumo);
        msj.mostrar("Nivel minimo configurado correctamente.\n"
                + "Insumo: " + (insumo == null ? idInsumo : insumo.getNombre()) + "\n"
                + "Nuevo nivel minimo: " + nuevoNivel);
        if (stock != null && stock.enNivelMinimo()) {
            msj.mostrarError("Alerta de stock bajo: el stock actual ("
                    + stock.getCantidadDisponible()
                    + ") esta en el nivel minimo o por debajo");
        }
        return true;
    }
}
