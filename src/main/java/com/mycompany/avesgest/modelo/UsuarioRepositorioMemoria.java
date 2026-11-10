/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * "Base de datos" simulada en memoria.
 *
 * Guarda una lista de usuarios quemados para poder probar el inicio de sesion
 * sin tener una base de datos real todavia. Cuando tengas la base de datos,
 * crea otra clase que implemente UsuarioRepositorio y cambia la instancia en
 * el controlador; el resto del codigo sigue funcionando igual.
 *
 * @author krate
 */
public class UsuarioRepositorioMemoria implements UsuarioRepositorio {

    private final List<Usuario> usuarios;

    public UsuarioRepositorioMemoria() {
        usuarios = new ArrayList<>();
        // Datos de prueba (simulan los registros de la tabla usuarios)
        usuarios.add(new Usuario("admin", "1234", "Administrador"));
        usuarios.add(new Usuario("krate", "avesgest", "Krate"));
        usuarios.add(new Usuario("invitado", "guest", "Invitado"));
    }

    @Override
    public Usuario buscarPorCredencial(String credencial) {
        if (credencial == null) {
            return null;
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getCredencial().equalsIgnoreCase(credencial)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public boolean validarCredencial(String credencial) {
        return buscarPorCredencial(credencial) != null;
    }

    @Override
    public boolean validar(String credencial, String contrasenia) {
        Usuario usuario = buscarPorCredencial(credencial);
        return usuario != null
                && usuario.getContrasenia().equals(contrasenia);
    }

    @Override
    public boolean guardar(Usuario usuario) {
        if (usuario == null || usuario.getCredencial() == null) {
            return false;
        }
        // No permitir credenciales duplicadas
        if (buscarPorCredencial(usuario.getCredencial()) != null) {
            return false;
        }
        usuarios.add(usuario);
        return true;
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public boolean actualizarRol(String credencial, String rol) {
        Usuario usuario = buscarPorCredencial(credencial);
        if (usuario == null) {
            return false;
        }
        usuario.setRol(rol);
        return true;
    }
}
