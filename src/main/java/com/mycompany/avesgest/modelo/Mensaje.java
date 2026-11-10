/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import javax.swing.JOptionPane;

/**
 *
 * @author krate
 */
public class Mensaje {
    
    public void mostrar(String mensaje){
        JOptionPane.showConfirmDialog(this, mensaje);
    }

    public void mostrarError(String mensaje){
        JOptionPane.showConfirmDialog(this, mensaje);
    }
}
