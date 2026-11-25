/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Logica del caso de uso "Consulta de stock actual" (HU-203).
 *
 * Es una consulta de solo lectura: lista los insumos con su stock, permite
 * buscarlos por codigo o nombre, filtrar los que estan en stock bajo y generar
 * el detalle de un insumo (nombre, codigo, cantidad, unidad, nivel minimo y
 * estado). No modifica nada del inventario.
 *
 * @author krate
 */
public class ConsultaStock {

    private final InventarioRepositorio rep;

    public ConsultaStock() {
        this(new InventarioRepositorioMemoria());
    }

    public ConsultaStock(InventarioRepositorio rep) {
        this.rep = rep;
    }

    /**
     * Indica si hay insumos registrados en el sistema.
     *
     * @return true si existe al menos un insumo
     */
    public boolean hayInsumos() {
        return !rep.listarInsumos().isEmpty();
    }

    /**
     * Devuelve el detalle de stock de todos los insumos.
     *
     * @return lista de detalles
     */
    public List<DetalleStock> listar() {
        List<DetalleStock> resultado = new ArrayList<>();
        for (Insumo insumo : rep.listarInsumos()) {
            Stock stock = rep.obtenerStock(insumo.getIdInsumo());
            if (stock != null) {
                resultado.add(new DetalleStock(insumo, stock));
            }
        }
        return resultado;
    }

    /**
     * Busca los insumos cuyo codigo o nombre coincida con el identificador.
     *
     * @param identificador codigo o nombre
     * @return lista de detalles que coinciden (vacia si no hay)
     */
    public List<DetalleStock> buscar(String identificador) {
        List<DetalleStock> resultado = new ArrayList<>();
        for (Insumo insumo : rep.buscarCoincidencias(identificador)) {
            Stock stock = rep.obtenerStock(insumo.getIdInsumo());
            if (stock != null) {
                resultado.add(new DetalleStock(insumo, stock));
            }
        }
        return resultado;
    }

    /**
     * Devuelve solo los insumos con stock en o por debajo del nivel minimo.
     *
     * @return lista de detalles en stock bajo o agotados
     */
    public List<DetalleStock> filtrarStockBajo() {
        List<DetalleStock> resultado = new ArrayList<>();
        for (DetalleStock d : listar()) {
            if (d.getEstado() != EstadoInsumo.NORMAL) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    /**
     * Vista de solo lectura que une un insumo con su stock y su estado. Es el
     * "detalle" del modelo de dominio.
     */
    public static class DetalleStock {

        private final Insumo insumo;
        private final Stock stock;

        public DetalleStock(Insumo insumo, Stock stock) {
            this.insumo = insumo;
            this.stock = stock;
        }

        public Insumo getInsumo() {
            return insumo;
        }

        public Stock getStock() {
            return stock;
        }

        public EstadoInsumo getEstado() {
            return stock.getEstado();
        }

        public String comoTexto() {
            return insumo.getCodigo() + " | " + insumo.getNombre()
                    + " | disponible: " + stock.getCantidadDisponible()
                    + " " + insumo.getUnidadMedida()
                    + " | minimo: " + stock.getNivelMinimo()
                    + " | " + getEstado().getDescripcion();
        }
    }
}
