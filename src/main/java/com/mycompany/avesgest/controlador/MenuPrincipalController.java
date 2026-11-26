/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.vista.Inicio.FormMenuPrincipal;
import com.mycompany.avesgest.vista.Inicio.PnlMenuPrincipal;
import javax.swing.JPanel;

/**
 * Controlador de la ventana inicial del sistema (ventana_inicio del modelo de
 * interfaz).
 *
 * Presenta el menu de modulos y, segun la opcion que elija el usuario, abre la
 * ventana del modulo de inventario o la del modulo de gestion de ventas. La
 * credencial y el rol de la sesion se pasan a cada modulo para que cada
 * controlador aplique sus propias restricciones.
 *
 * @author krate
 */
public class MenuPrincipalController {

    private final String usuarioActivo;
    private final String rolUsuario;

    private final FormMenuPrincipal vista;
    private final PnlMenuPrincipal pnlMenu;

    /**
     * @param usuarioActivo credencial de la sesion activa
     * @param rolUsuario    rol de la sesion: "Administrador" u otro
     */
    public MenuPrincipalController(String usuarioActivo, String rolUsuario) {
        this.usuarioActivo = usuarioActivo;
        this.rolUsuario = rolUsuario;
        this.vista = new FormMenuPrincipal();
        this.pnlMenu = new PnlMenuPrincipal();
        iniciar();
    }

    public MenuPrincipalController() {
        this("admin", "Administrador");
    }

    private void iniciar() {
        vista.setVisible(true);
        mostrarPanel(pnlMenu);

        pnlMenu.getBtnInventario().addActionListener(e -> abrirInventario());
        pnlMenu.getBtnVentas().addActionListener(e -> abrirVentas());
    }

    private void mostrarPanel(JPanel panel) {
        JPanel contenido = vista.getPanelContenido();
        contenido.removeAll();
        contenido.add(panel);
        contenido.revalidate();
        contenido.repaint();
    }

    /**
     * Abre la gestion de inventario en su propia ventana.
     */
    private void abrirInventario() {
        new InventarioController(usuarioActivo, rolUsuario);
    }

    /**
     * Abre la gestion de ventas en su propia ventana.
     */
    private void abrirVentas() {
        new VentasController(usuarioActivo, rolUsuario);
    }
}
