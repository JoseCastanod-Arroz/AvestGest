/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.avesgest;

import com.mycompany.avesgest.controlador.MenuPrincipalController;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada del sistema.
 *
 * Abre la ventana inicial, desde la que el usuario entra a la gestion de
 * inventario o a la gestion de ventas.
 *
 * @author krate
 */
public class AvesGest {

    public static void main(String[] args) {
        // La interfaz se construye en el event dispatch thread de Swing.
        // La sesion de prueba arranca como administrador para habilitar todas
        // las operaciones de cada modulo (anular venta, nivel minimo y edicion
        // de insumo son exclusivas de ese rol).
        SwingUtilities.invokeLater(
                () -> new MenuPrincipalController("admin", "Administrador"));
    }
}
