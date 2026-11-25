/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Inventario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Formulario de configuracion de nivel minimo de stock (HU-204), exclusivo del
 * administrador. Muestra la lista de insumos y, al seleccionar uno, su unidad
 * de medida, stock actual y nivel minimo actual, con el campo para ingresar el
 * nuevo nivel minimo.
 *
 * @author krate
 */
public class PnlNivelMinimo extends javax.swing.JPanel {

    public PnlNivelMinimo() {
        initComponents();
    }

    public JComboBox<String> getCmbInsumo() {
        return cmbInsumo;
    }

    public JLabel getLblUnidad() {
        return lblUnidad;
    }

    public JLabel getLblStockActual() {
        return lblStockActual;
    }

    public JLabel getLblNivelActual() {
        return lblNivelActual;
    }

    public JTextField getTxtNuevoNivel() {
        return txtNuevoNivel;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInsumo = new javax.swing.JLabel();
        lblUnidadTit = new javax.swing.JLabel();
        lblStockTit = new javax.swing.JLabel();
        lblNivelTit = new javax.swing.JLabel();
        lblNuevo = new javax.swing.JLabel();
        lblUnidad = new javax.swing.JLabel();
        lblStockActual = new javax.swing.JLabel();
        lblNivelActual = new javax.swing.JLabel();
        cmbInsumo = new javax.swing.JComboBox<>();
        txtNuevoNivel = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        lblInsumo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblInsumo.setText("Insumo:");

        lblUnidadTit.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblUnidadTit.setText("Unidad de medida:");

        lblStockTit.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblStockTit.setText("Stock actual:");

        lblNivelTit.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblNivelTit.setText("Nivel minimo actual:");

        lblNuevo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblNuevo.setText("Nuevo nivel minimo:");

        lblUnidad.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblUnidad.setText("-");

        lblStockActual.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblStockActual.setText("-");

        lblNivelActual.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblNivelActual.setText("-");

        btnGuardar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInsumo)
                    .addComponent(lblUnidadTit)
                    .addComponent(lblStockTit)
                    .addComponent(lblNivelTit)
                    .addComponent(lblNuevo))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUnidad)
                    .addComponent(lblStockActual)
                    .addComponent(lblNivelActual)
                    .addComponent(txtNuevoNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInsumo)
                    .addComponent(cmbInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnidadTit)
                    .addComponent(lblUnidad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockTit)
                    .addComponent(lblStockActual))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivelTit)
                    .addComponent(lblNivelActual))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevo)
                    .addComponent(txtNuevoNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbInsumo;
    private javax.swing.JLabel lblInsumo;
    private javax.swing.JLabel lblNivelActual;
    private javax.swing.JLabel lblNivelTit;
    private javax.swing.JLabel lblNuevo;
    private javax.swing.JLabel lblStockActual;
    private javax.swing.JLabel lblStockTit;
    private javax.swing.JLabel lblUnidad;
    private javax.swing.JLabel lblUnidadTit;
    private javax.swing.JTextField txtNuevoNivel;
    // End of variables declaration//GEN-END:variables
}
