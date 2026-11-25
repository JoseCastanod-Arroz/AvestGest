/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Representa un insumo o producto del negocio tal como estaria almacenado en la
 * base de datos (tabla INSUMO del modelo relacional).
 *
 * Contiene la informacion fija del insumo: codigo, nombre, categoria, unidad de
 * medida y precio. El stock disponible y el nivel minimo se manejan aparte, en
 * la clase Stock, porque cambian de forma independiente a la edicion del
 * insumo.
 *
 * @author krate
 */
public class Insumo {

    private int idInsumo;
    private String codigo;
    private String nombre;
    private String categoria;
    private String unidadMedida;
    private double precio;

    public Insumo() {
    }

    public Insumo(int idInsumo, String codigo, String nombre, String categoria,
            String unidadMedida, double precio) {
        this.idInsumo = idInsumo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.unidadMedida = unidadMedida;
        this.precio = precio;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
