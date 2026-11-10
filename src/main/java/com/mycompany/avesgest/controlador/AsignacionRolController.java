/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.modelo.AsignacionRol;
import com.mycompany.avesgest.modelo.Usuario;
import com.mycompany.avesgest.vista.AsignacionRol.FormAsignacionRol;
import com.mycompany.avesgest.vista.AsignacionRol.PnlAsignacionRol;
import javax.swing.JOptionPane;

/**
 *
 * @author krate
 */
public class AsignacionRolController {

    private final FormAsignacionRol vista;
    private final PnlAsignacionRol pnlrol;
    private AsignacionRol asigRol;

    public AsignacionRolController()
    {
        asigRol = new AsignacionRol();
        vista = new FormAsignacionRol();
        pnlrol = new PnlAsignacionRol();
        Iniciar();
    }

    public void Iniciar()
    {
        vista.setVisible(true);
        CargarDatos();
        SeleccionarUsuario();
        pnlrol.getBtnAsignarRol().addActionListener(e -> AsignarRol());
    }

    private void CargarDatos()
    {
        pnlrol.getCmbUsuario().removeAllItems();
        for (Usuario u : asigRol.listarUsuarios()) {
            pnlrol.getCmbUsuario().addItem(u.getCredencial());
        }
        pnlrol.getCmbRol().removeAllItems();
        for (String rol : asigRol.listarRoles()) {
            pnlrol.getCmbRol().addItem(rol);
        }
    }

    private void SeleccionarUsuario()
    {
        vista.getPanelContenido().removeAll();
        vista.getPanelContenido().add(pnlrol);
        vista.getPanelContenido().revalidate();
        vista.getPanelContenido().repaint();
    }

    private void AsignarRol()
    {
        String credencial = (String) pnlrol.getCmbUsuario().getSelectedItem();
        String rol = (String) pnlrol.getCmbRol().getSelectedItem();

        asigRol.setCredencial(credencial);
        asigRol.setRol(rol);

        int confirma = JOptionPane.showConfirmDialog(null,
                "¿Confirma asignar el rol \"" + rol + "\" al usuario \"" + credencial + "\"?",
                "Confirmar seleccion de rol", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            asigRol.asignarRol();
        }
    }

}
