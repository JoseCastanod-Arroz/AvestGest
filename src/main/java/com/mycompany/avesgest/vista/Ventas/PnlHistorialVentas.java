/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Ventas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de consulta del historial de ventas por fecha (proceso 2). Solo
 * de lectura: tiene los campos para indicar el rango de fechas, los botones
 * para consultar o ver todas las ventas, una tabla con el resultado y el acceso
 * al detalle de una venta seleccionada.
 *
 * @author krate
 */
public class PnlHistorialVentas extends javax.swing.JPanel {

    public PnlHistorialVentas() {
        initComponents();
    }

    public JTextField getTxtDesde() {
        return txtDesde;
    }

    public JTextField getTxtHasta() {
        return txtHasta;
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public JButton getBtnTodos() {
        return btnTodos;
    }

    public JButton getBtnVerDetalle() {
        return btnVerDetalle;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public JTable getTablaVentas() {
        return tablaVentas;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaVentas.getModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDesde = new javax.swing.JLabel();
        txtDesde = new javax.swing.JTextField();
        lblHasta = new javax.swing.JLabel();
        txtHasta = new javax.swing.JTextField();
        btnConsultar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        btnVerDetalle = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        lblDesde.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblDesde.setText("Desde (AAAA-MM-DD):");

        lblHasta.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblHasta.setText("Hasta (AAAA-MM-DD):");

        btnConsultar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnConsultar.setText("Consultar");

        btnTodos.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnTodos.setText("Ver todas");

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Venta", "Fecha", "Usuario", "Valor total", "Estado"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollTabla.setViewportView(tablaVentas);

        btnVerDetalle.setText("Ver detalle de la venta");

        btnVolver.setText("Volver");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollTabla)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnVerDetalle)
                .addGap(18, 18, 18)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDesde)
                    .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHasta)
                    .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtDesde;
    private javax.swing.JTextField txtHasta;
    // End of variables declaration//GEN-END:variables
}
