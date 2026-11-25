/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.avesgest;

import com.mycompany.avesgest.controlador.InventarioController;

/**
 *
 * @author krate
 */
public class AvesGest {

    public static void main(String[] args) {
        // Modulo 2 - Gestion de Inventario.
        // Se abre con un administrador para habilitar todas las operaciones
        // (configurar nivel minimo y editar insumo son exclusivas de ese rol).
        InventarioController ctr = new InventarioController("admin", "Administrador");
    }
}
