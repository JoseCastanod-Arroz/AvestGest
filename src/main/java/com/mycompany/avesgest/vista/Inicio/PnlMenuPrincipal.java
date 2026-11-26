/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Inicio;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Menu inicial del sistema: presenta los modulos disponibles y traslada al
 * usuario a la ventana correspondiente segun la opcion que haya seleccionado.
 *
 * @author krate
 */
public class PnlMenuPrincipal extends javax.swing.JPanel {

    public PnlMenuPrincipal() {
        initComponents();
    }

    public JButton getBtnInventario() {
        return btnInventario;
    }

    public JButton getBtnVentas() {
        return btnVentas;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnInventario = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("sansserif", 0, 22)); // NOI18N
        lblTitulo.setText("Seleccione un modulo");

        btnInventario.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnInventario.setText("Gestion de Inventario");

        btnVentas.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        btnVentas.setText("Gestion de Ventas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(lblTitulo)
                .addGap(35, 35, 35)
                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnVentas;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
