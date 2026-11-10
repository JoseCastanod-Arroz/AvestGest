/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Representa un usuario tal como estaria almacenado en la base de datos.
 *
 * @author krate
 */
public class Usuario {

    private String id;
    private String credencial;
    private String contrasenia;
    private String nombre;
    private String apellido;
    private String correo;

    public Usuario() {
    }

    public Usuario(String credencial, String contrasenia, String nombre) {
        this.credencial = credencial;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
    }

    public Usuario(String id, String credencial, String contrasenia,
            String nombre, String apellido, String correo) {
        this.id = id;
        this.credencial = credencial;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
