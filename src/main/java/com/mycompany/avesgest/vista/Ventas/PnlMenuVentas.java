/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Ventas;

import javax.swing.JButton;

/**
 * Menu del modulo de gestion de ventas (menu_ventas del modelo de interfaz).
 * Muestra una opcion por cada operacion del modulo: registro, historial por
 * fecha, filtro por producto y anulacion. El controlador decide cuales
 * habilitar segun el rol: la anulacion es exclusiva del administrador.
 *
 * @author krate
 */
public class PnlMenuVentas extends javax.swing.JPanel {

    public PnlMenuVentas() {
        initComponents();
    }

    public JButton getBtnRegistro() {
        return btnRegistro;
    }

    public JButton getBtnHistorial() {
        return btnHistorial;
    }

    public JButton getBtnFiltro() {
        return btnFiltro;
    }

    public JButton getBtnAnular() {
        return btnAnular;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRegistro = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();
        btnFiltro = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        btnRegistro.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnRegistro.setText("Registrar venta");

        btnHistorial.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnHistorial.setText("Consultar historial de ventas por fecha");

        btnFiltro.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnFiltro.setText("Filtrar ventas por producto");

        btnAnular.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnAnular.setText("Anular venta");

        btnVolver.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnVolver.setText("Volver al menu principal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistro, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(btnHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(220, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnFiltro;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnRegistro;
    private javax.swing.JButton btnVolver;
    // End of variables declaration//GEN-END:variables
}
