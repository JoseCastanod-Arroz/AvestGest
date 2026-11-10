/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.List;

/**
 *
 * @author krate
 */
public class AsignacionRol {

    private String credencial;
    private String rol;

    private final UsuarioRepositorio rep;
    private final Mensaje msj;

    private static final String[] ROLES = {"Administrador", "Empleado", "Invitado"};

    public AsignacionRol() {
        rep = new UsuarioRepositorioMemoria();
        msj = new Mensaje();
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Usuario> listarUsuarios() {
        return rep.listar();
    }

    public String[] listarRoles() {
        return ROLES.clone();
    }

    public boolean verificarUsuario() {
        Usuario usuario = rep.buscarPorCredencial(credencial);
        if (usuario == null) {
            msj.mostrarError("El usuario seleccionado no existe");
            return false;
        }
        msj.mostrar("Usuario: " + usuario.getNombre() + "\n"
                + "Correo: " + usuario.getCorreo() + "\n"
                + "Rol actual: " + (usuario.getRol() == null ? "Sin rol" : usuario.getRol()));
        return true;
    }

    public boolean validarRol() {
        if (rol == null || rol.trim().isEmpty()) {
            msj.mostrarError("Debe seleccionar un rol");
            return false;
        }
        for (String r : ROLES) {
            if (r.equalsIgnoreCase(rol)) {
                return true;
            }
        }
        msj.mostrarError("El rol seleccionado no es valido");
        return false;
    }

    public boolean asignarRol() {
        if (!validarRol()) {
            return false;
        }
        if (rep.actualizarRol(credencial, rol)) {
            msj.mostrar("Rol asignado correctamente al usuario " + credencial
                    + ". Nuevo rol: " + rol);
            return true;
        }
        msj.mostrarError("No se pudo asignar el rol: el usuario no existe");
        return false;
    }
}
