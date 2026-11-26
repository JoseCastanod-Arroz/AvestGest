/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.avesgest.vista.Ventas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de anulacion de venta (proceso 5, exclusiva del administrador).
 * Despliega la lista de ventas registradas, muestra el detalle de la venta
 * seleccionada para verificar su estado, solicita el motivo obligatorio y
 * permite confirmar o cancelar la anulacion.
 *
 * @author krate
 */
public class PnlAnulacionVenta extends javax.swing.JPanel {

    public PnlAnulacionVenta() {
        initComponents();
    }

    public JTable getTablaVentas() {
        return tablaVentas;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaVentas.getModel();
    }

    public JTable getTablaDetalle() {
        return tablaDetalle;
    }

    public DefaultTableModel getModeloDetalle() {
        return (DefaultTableModel) tablaDetalle.getModel();
    }

    public JLabel getLblFechaValor() {
        return lblFechaValor;
    }

    public JLabel getLblEstadoValor() {
        return lblEstadoValor;
    }

    public JLabel getLblTotalValor() {
        return lblTotalValor;
    }

    public JTextArea getTxtMotivo() {
        return txtMotivo;
    }

    public JButton getBtnAnular() {
        return btnAnular;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblVentas = new javax.swing.JLabel();
        scrollVentas = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        lblFecha = new javax.swing.JLabel();
        lblFechaValor = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblEstadoValor = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotalValor = new javax.swing.JLabel();
        lblDetalle = new javax.swing.JLabel();
        scrollDetalle = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        lblMotivo = new javax.swing.JLabel();
        scrollMotivo = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        btnAnular = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        lblVentas.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblVentas.setText("Ventas registradas:");

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Venta", "Fecha", "Valor total", "Estado"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollVentas.setViewportView(tablaVentas);

        lblFecha.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblFecha.setText("Fecha:");

        lblFechaValor.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblFechaValor.setText("-");

        lblEstado.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblEstado.setText("Estado:");

        lblEstadoValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblEstadoValor.setText("-");

        lblTotal.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblTotal.setText("Valor total:");

        lblTotalValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTotalValor.setText("-");

        lblDetalle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblDetalle.setText("Detalle:");

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Codigo", "Producto", "Cantidad", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollDetalle.setViewportView(tablaDetalle);

        lblMotivo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblMotivo.setText("Motivo de anulacion (obligatorio):");

        txtMotivo.setColumns(20);
        txtMotivo.setRows(2);
        scrollMotivo.setViewportView(txtMotivo);

        btnAnular.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnAnular.setText("Anular venta");

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblVentas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollVentas)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblFechaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblEstadoValor, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblTotalValor, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblDetalle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollDetalle)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblMotivo)
                .addGap(15, 15, 15)
                .addComponent(scrollMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblVentas)
                .addGap(10, 10, 10)
                .addComponent(scrollVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(lblFechaValor)
                    .addComponent(lblEstado)
                    .addComponent(lblEstadoValor)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalValor))
                .addGap(15, 15, 15)
                .addComponent(lblDetalle)
                .addGap(8, 8, 8)
                .addComponent(scrollDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMotivo)
                    .addComponent(scrollMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoValor;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFechaValor;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValor;
    private javax.swing.JLabel lblVentas;
    private javax.swing.JScrollPane scrollDetalle;
    private javax.swing.JScrollPane scrollMotivo;
    private javax.swing.JScrollPane scrollVentas;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextArea txtMotivo;
    // End of variables declaration//GEN-END:variables
}
