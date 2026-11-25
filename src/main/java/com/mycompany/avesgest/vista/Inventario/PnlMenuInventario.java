/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Inventario;

import javax.swing.JButton;

/**
 * Menu del modulo de inventario. Muestra una opcion por cada operacion del
 * modulo. El controlador decide cuales habilitar segun el rol: entrada, salida
 * y consulta para operario y administrador; nivel minimo y edicion solo para el
 * administrador.
 *
 * @author krate
 */
public class PnlMenuInventario extends javax.swing.JPanel {

    public PnlMenuInventario() {
        initComponents();
    }

    public JButton getBtnEntrada() {
        return btnEntrada;
    }

    public JButton getBtnSalida() {
        return btnSalida;
    }

    public JButton getBtnConsulta() {
        return btnConsulta;
    }

    public JButton getBtnNivelMinimo() {
        return btnNivelMinimo;
    }

    public JButton getBtnEdicion() {
        return btnEdicion;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEntrada = new javax.swing.JButton();
        btnSalida = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnNivelMinimo = new javax.swing.JButton();
        btnEdicion = new javax.swing.JButton();

        btnEntrada.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnEntrada.setText("Registrar entrada de insumos");

        btnSalida.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnSalida.setText("Registrar salida de insumos");

        btnConsulta.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnConsulta.setText("Consultar stock actual");

        btnNivelMinimo.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnNivelMinimo.setText("Configurar nivel minimo de stock");

        btnEdicion.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnEdicion.setText("Editar informacion de insumo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(btnSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNivelMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(220, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNivelMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdicion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnEdicion;
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnNivelMinimo;
    private javax.swing.JButton btnSalida;
    // End of variables declaration//GEN-END:variables
}
