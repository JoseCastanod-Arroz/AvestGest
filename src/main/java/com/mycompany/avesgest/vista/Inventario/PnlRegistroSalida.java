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
 * Formulario de registro de salida de insumos (HU-202). Contiene la lista de
 * insumos, el stock actual, los campos cantidad y fecha, la lista de motivos
 * (consumo, vencimiento, perdida o daño) y el comentario, mas los botones
 * Guardar y Cancelar.
 *
 * @author krate
 */
public class PnlRegistroSalida extends javax.swing.JPanel {

    public PnlRegistroSalida() {
        initComponents();
    }

    public JComboBox<String> getCmbInsumo() {
        return cmbInsumo;
    }

    public JComboBox<String> getCmbMotivo() {
        return cmbMotivo;
    }

    public JLabel getLblStockActual() {
        return lblStockActual;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtComentario() {
        return txtComentario;
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
        lblCantidad = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblMotivo = new javax.swing.JLabel();
        lblComentario = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        lblStockActual = new javax.swing.JLabel();
        cmbInsumo = new javax.swing.JComboBox<>();
        cmbMotivo = new javax.swing.JComboBox<>();
        txtCantidad = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtComentario = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        lblInsumo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblInsumo.setText("Insumo:");

        lblCantidad.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblCantidad.setText("Cantidad:");

        lblFecha.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblFecha.setText("Fecha (AAAA-MM-DD):");

        lblMotivo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblMotivo.setText("Motivo:");

        lblComentario.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblComentario.setText("Comentario (opcional):");

        lblStock.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblStock.setText("Stock actual:");

        lblStockActual.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblStockActual.setText("-");

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
                    .addComponent(lblStock)
                    .addComponent(lblCantidad)
                    .addComponent(lblFecha)
                    .addComponent(lblMotivo)
                    .addComponent(lblComentario))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockActual)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComentario, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInsumo)
                    .addComponent(cmbInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStock)
                    .addComponent(lblStockActual))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMotivo)
                    .addComponent(cmbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComentario)
                    .addComponent(txtComentario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbInsumo;
    private javax.swing.JComboBox<String> cmbMotivo;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblComentario;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInsumo;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblStockActual;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtComentario;
    private javax.swing.JTextField txtFecha;
    // End of variables declaration//GEN-END:variables
}
