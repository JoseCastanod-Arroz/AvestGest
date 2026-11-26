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
 * Detalle de una venta especifica (proceso 4). Muestra la cabecera de la venta
 * (fecha, usuario, valor total y estado), el motivo de anulacion cuando la
 * venta esta anulada, y la tabla con los productos vendidos. Es de solo
 * lectura: solo permite volver a la lista desde la que se abrio.
 *
 * @author krate
 */
public class PnlDetalleVenta extends javax.swing.JPanel {

    public PnlDetalleVenta() {
        initComponents();
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public JLabel getLblFechaValor() {
        return lblFechaValor;
    }

    public JLabel getLblUsuarioValor() {
        return lblUsuarioValor;
    }

    public JLabel getLblEstadoValor() {
        return lblEstadoValor;
    }

    public JLabel getLblTotalValor() {
        return lblTotalValor;
    }

    public JLabel getLblMotivo() {
        return lblMotivo;
    }

    public JTextArea getAreaMotivo() {
        return areaMotivo;
    }

    public JScrollPane getScrollMotivo() {
        return scrollMotivo;
    }

    public JTable getTablaDetalle() {
        return tablaDetalle;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaDetalle.getModel();
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblFechaValor = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblEstadoValor = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblUsuarioValor = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotalValor = new javax.swing.JLabel();
        lblMotivo = new javax.swing.JLabel();
        scrollMotivo = new javax.swing.JScrollPane();
        areaMotivo = new javax.swing.JTextArea();
        scrollDetalle = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        lblTitulo.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblTitulo.setText("Detalle de la venta");

        lblFecha.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblFecha.setText("Fecha:");

        lblFechaValor.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblFechaValor.setText("-");

        lblEstado.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblEstado.setText("Estado:");

        lblEstadoValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblEstadoValor.setText("-");

        lblUsuario.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblUsuario.setText("Registrado por:");

        lblUsuarioValor.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblUsuarioValor.setText("-");

        lblTotal.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblTotal.setText("Valor total:");

        lblTotalValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTotalValor.setText("-");

        lblMotivo.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblMotivo.setText("Motivo de anulacion:");

        areaMotivo.setColumns(20);
        areaMotivo.setRows(2);
        areaMotivo.setEditable(false);
        scrollMotivo.setViewportView(areaMotivo);

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Codigo", "Producto", "Cantidad", "Precio unitario", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollDetalle.setViewportView(tablaDetalle);

        btnVolver.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnVolver.setText("Volver");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblFechaValor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblEstadoValor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblUsuarioValor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblTotalValor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(scrollMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollDetalle)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(lblFechaValor)
                    .addComponent(lblEstado)
                    .addComponent(lblEstadoValor))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(lblUsuarioValor)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalValor))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMotivo)
                    .addComponent(scrollMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scrollDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaMotivo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoValor;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFechaValor;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValor;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuarioValor;
    private javax.swing.JScrollPane scrollDetalle;
    private javax.swing.JScrollPane scrollMotivo;
    private javax.swing.JTable tablaDetalle;
    // End of variables declaration//GEN-END:variables
}
