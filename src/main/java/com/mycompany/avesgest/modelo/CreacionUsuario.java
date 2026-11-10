/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 *
 * @author krate
 */
public class CreacionUsuario {

    private String nombre;
    private String correo;
    private String contrasenia;
    private String id;

    private final UsuarioRepositorio rep;
    private final Mensaje msj;

    public CreacionUsuario() {
        rep = new UsuarioRepositorioMemoria();
        msj = new Mensaje();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getId() {
        return id;
    }

    public boolean validarFormato() {
        if (estaVacio(nombre) || estaVacio(correo) || estaVacio(contrasenia)) {
            msj.mostrarError("Formato incorrecto: hay espacios sin llenar");
            return false;
        }
        return true;
    }

    private void generarId() {
        id = "U" + System.currentTimeMillis();
    }

    public boolean registrar() {
        if (!validarFormato()) {
            return false;
        }
        generarId();
        Usuario usuario = new Usuario(id, correo, contrasenia,
                nombre, null, correo);
        if (rep.guardar(usuario)) {
            mostrarDatos();
            return true;
        }
        msj.mostrarError("No se pudo crear el usuario: el correo ya esta registrado");
        return false;
    }

    public void mostrarDatos() {
        msj.mostrar("Usuario creado correctamente.\n"
                + "ID: " + id + "\n"
                + "Nombre: " + nombre + "\n"
                + "Correo: " + correo);
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
