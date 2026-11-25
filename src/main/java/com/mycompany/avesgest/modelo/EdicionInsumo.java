/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.List;

/**
 * Logica del caso de uso "Edicion de informacion de insumo" (HU-205).
 *
 * Operacion exclusiva del administrador. Permite corregir nombre, codigo,
 * categoria, unidad de medida y precio de un insumo. No modifica el stock ni
 * los movimientos ya registrados, e impide codigos o nombres duplicados.
 *
 * @author krate
 */
public class EdicionInsumo {

    private int idInsumo;
    private String codigo;
    private String nombre;
    private String categoria;
    private String unidadMedida;
    private double precio;

    private String usuario;

    private final InventarioRepositorio rep;
    private final Mensaje msj;

    public EdicionInsumo() {
        this(new InventarioRepositorioMemoria());
    }

    public EdicionInsumo(InventarioRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
     * Valida los datos de la edicion: obligatorios, precio positivo y sin
     * duplicados de codigo o nombre con otros insumos.
     *
     * @return true si los datos son validos
     */
    public boolean validar() {
        if (idInsumo <= 0) {
            msj.mostrarError("Debe seleccionar un insumo");
            return false;
        }
        if (vacio(codigo) || vacio(nombre) || vacio(categoria) || vacio(unidadMedida)) {
            msj.mostrarError("Nombre, codigo, categoria y unidad de medida son obligatorios");
            return false;
        }
        if (precio <= 0) {
            msj.mostrarError("El precio debe ser un numero mayor que cero");
            return false;
        }
        if (rep.existeDuplicado(codigo, nombre, idInsumo)) {
            msj.mostrarError("El codigo o el nombre ya pertenecen a otro insumo");
            return false;
        }
        if (!hayCambios()) {
            msj.mostrar("No hay cambios para guardar");
            return false;
        }
        return true;
    }

    /**
     * Indica si los datos nuevos difieren de los actuales del insumo.
     *
     * @return true si hubo cambios
     */
    public boolean hayCambios() {
        Insumo actual = rep.buscarInsumo(idInsumo);
        if (actual == null) {
            return false;
        }
        return !actual.getCodigo().equals(codigo)
                || !actual.getNombre().equals(nombre)
                || !iguales(actual.getCategoria(), categoria)
                || !iguales(actual.getUnidadMedida(), unidadMedida)
                || actual.getPrecio() != precio;
    }

    /**
     * Genera el resumen con los datos anteriores y los nuevos.
     *
     * @return resumen de la edicion
     */
    public String generarResumen() {
        Insumo actual = rep.buscarInsumo(idInsumo);
        StringBuilder sb = new StringBuilder("Resumen de la edicion\n");
        if (actual != null) {
            sb.append("Codigo: ").append(actual.getCodigo()).append(" -> ").append(codigo).append("\n");
            sb.append("Nombre: ").append(actual.getNombre()).append(" -> ").append(nombre).append("\n");
            sb.append("Categoria: ").append(actual.getCategoria()).append(" -> ").append(categoria).append("\n");
            sb.append("Unidad: ").append(actual.getUnidadMedida()).append(" -> ").append(unidadMedida).append("\n");
            sb.append("Precio: ").append(actual.getPrecio()).append(" -> ").append(precio).append("\n");
        }
        sb.append("Modificado por: ").append(usuario);
        return sb.toString();
    }

    /**
     * Confirma la edicion: guarda los datos nuevos sin tocar el stock ni los
     * movimientos.
     *
     * @return true si se guardo correctamente
     */
    public boolean confirmar() {
        if (!validar()) {
            return false;
        }
        Insumo editado = new Insumo(idInsumo, codigo, nombre, categoria,
                unidadMedida, precio);
        if (!rep.actualizarInsumo(editado)) {
            msj.mostrarError("No se pudo guardar la edicion del insumo");
            return false;
        }
        msj.mostrar("Insumo editado correctamente.\n"
                + "Codigo: " + codigo + "\n"
                + "Nombre: " + nombre);
        return true;
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private boolean iguales(String a, String b) {
        return a == null ? b == null : a.equals(b);
    }
}
