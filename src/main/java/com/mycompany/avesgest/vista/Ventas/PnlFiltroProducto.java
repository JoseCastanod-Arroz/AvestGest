/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Ventas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de filtro de ventas por producto (proceso 3). Despliega la lista
 * de productos del inventario y, al filtrar, muestra las ventas que incluyen
 * el producto seleccionado. Desde la tabla se puede abrir el detalle de una
 * venta.
 *
 * @author krate
 */
public class PnlFiltroProducto extends javax.swing.JPanel {

    public PnlFiltroProducto() {
        initComponents();
    }

    public JComboBox<String> getCmbProducto() {
        return cmbProducto;
    }

    public JButton getBtnFiltrar() {
        return btnFiltrar;
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

        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        btnVerDetalle = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        lblProducto.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblProducto.setText("Producto:");

        btnFiltrar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnFiltrar.setText("Filtrar ventas");

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
                .addComponent(lblProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cmbProducto, 0, 340, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
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
                    .addComponent(lblProducto)
                    .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaVentas;
    // End of variables declaration//GEN-END:variables
}
