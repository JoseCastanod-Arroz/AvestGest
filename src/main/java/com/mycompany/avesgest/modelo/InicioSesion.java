/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 *
 * @author krate
 */
public class InicioSesion {
    
    private String credencial;
    private String contrasenia;
    private UsuarioRepositorio rep;
    private Mensaje msj;
    
    public InicioSesion()
    {
        rep = new UsuarioRepositorioMemoria();
        msj = new Mensaje();
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
    
    public boolean validarCredencial(){
        if(rep.validarCredencial(credencial)){
            return true;
        }
        msj.mostrar("Usuario no encontrado, Cree Usuario nuevo");
        return false;
    }
    
    public boolean validarContrasenia(){
        if(rep.validar(credencial, contrasenia)){
            msj.mostrar("Credenciales Correctas, Bienvenido");
            return true;
        }
        msj.mostrarError("Error, Contraseña no valida");
        return false;
    }
    
    
}
