/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Inventario;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de consulta de stock actual (HU-203). Es solo de lectura: tiene el
 * campo para ingresar el identificador (codigo o nombre), los botones para
 * buscar, filtrar por stock bajo y ver detalle, y una tabla con los insumos y
 * su estado.
 *
 * @author krate
 */
public class PnlConsultaStock extends javax.swing.JPanel {

    public PnlConsultaStock() {
        initComponents();
    }

    public JTextField getTxtIdentificador() {
        return txtIdentificador;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnTodos() {
        return btnTodos;
    }

    public JButton getBtnStockBajo() {
        return btnStockBajo;
    }

    public JButton getBtnDetalle() {
        return btnDetalle;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public JTable getTablaStock() {
        return tablaStock;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaStock.getModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIdentificador = new javax.swing.JLabel();
        txtIdentificador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        btnStockBajo = new javax.swing.JButton();
        btnDetalle = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        scrollTabla = new javax.swing.JScrollPane();
        tablaStock = new javax.swing.JTable();

        lblIdentificador.setFont(new java.awt.Font("sansserif", 0, 16)); // NOI18N
        lblIdentificador.setText("Codigo o nombre:");

        btnBuscar.setText("Buscar");
        btnTodos.setText("Ver todos");
        btnStockBajo.setText("Solo stock bajo");
        btnDetalle.setText("Ver detalle");
        btnVolver.setText("Volver");

        tablaStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Codigo", "Nombre", "Disponible", "Unidad", "Nivel minimo", "Estado"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollTabla.setViewportView(tablaStock);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIdentificador)
                        .addGap(10, 10, 10)
                        .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBuscar)
                        .addGap(10, 10, 10)
                        .addComponent(btnTodos)
                        .addGap(10, 10, 10)
                        .addComponent(btnStockBajo))
                    .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDetalle)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdentificador)
                    .addComponent(txtIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnTodos)
                    .addComponent(btnStockBajo))
                .addGap(18, 18, 18)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetalle)
                    .addComponent(btnVolver))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDetalle;
    private javax.swing.JButton btnStockBajo;
    private javax.swing.JButton btnTodos;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel lblIdentificador;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaStock;
    private javax.swing.JTextField txtIdentificador;
    // End of variables declaration//GEN-END:variables
}
