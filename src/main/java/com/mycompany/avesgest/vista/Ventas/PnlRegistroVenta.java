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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario de registro de venta (proceso 1). Permite seleccionar un producto
 * y ver su precio y stock, ingresar la cantidad, agregarlo a la venta y repetir
 * el proceso con otros productos. Muestra el total acumulado y los botones para
 * corregir una linea, finalizar la venta o cancelar el registro.
 *
 * @author krate
 */
public class PnlRegistroVenta extends javax.swing.JPanel {

    public PnlRegistroVenta() {
        initComponents();
    }

    public JComboBox<String> getCmbProducto() {
        return cmbProducto;
    }

    public JLabel getLblPrecioValor() {
        return lblPrecioValor;
    }

    public JLabel getLblStockValor() {
        return lblStockValor;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JLabel getLblTotalValor() {
        return lblTotalValor;
    }

    public JTable getTablaVenta() {
        return tablaVenta;
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) tablaVenta.getModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProducto = new javax.swing.JLabel();
        cmbProducto = new javax.swing.JComboBox<>();
        lblPrecio = new javax.swing.JLabel();
        lblPrecioValor = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        lblStockValor = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        lblTotalValor = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        lblProducto.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblProducto.setText("Producto:");

        lblPrecio.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblPrecio.setText("Precio unitario:");

        lblPrecioValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblPrecioValor.setText("-");

        lblStock.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblStock.setText("Stock disponible:");

        lblStockValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblStockValor.setText("-");

        lblCantidad.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblCantidad.setText("Cantidad:");

        btnAgregar.setText("Agregar");

        lblTotal.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lblTotal.setText("Total de la venta:");

        lblTotalValor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblTotalValor.setText("$0");

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Codigo", "Producto", "Precio unitario", "Cantidad", "Subtotal"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollTabla.setViewportView(tablaVenta);

        btnQuitar.setText("Quitar seleccionado");

        btnFinalizar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnFinalizar.setText("Finalizar venta");

        btnCancelar.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cmbProducto, 0, 340, Short.MAX_VALUE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblPrecioValor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblStock, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblStockValor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnAgregar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblTotalValor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(scrollTabla)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnQuitar)
                .addGap(20, 20, 20)
                .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProducto)
                    .addComponent(cmbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecio)
                    .addComponent(lblPrecioValor))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStock)
                    .addComponent(lblStockValor))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalValor))
                .addGap(18, 18, 18)
                .addComponent(scrollTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cmbProducto;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPrecioValor;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblStockValor;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalValor;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
