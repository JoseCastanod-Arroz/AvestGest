/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.vista.InicioSesion.FormInicioSesion;
import com.mycompany.avesgest.vista.InicioSesion.PnlInicioSesionContrasenia;
import com.mycompany.avesgest.vista.InicioSesion.PnlInicioSesionCredencial;

/**
 *
 * @author krate
 */
public class InicioSesionController {
    
    private final FormInicioSesion vista;
    private final PnlInicioSesionCredencial pnlcredencial;
    private final PnlInicioSesionContrasenia pnlcontrasenia;

    public InicioSesionController()
    {
        vista = new FormInicioSesion();
        pnlcredencial = new PnlInicioSesionCredencial();
        pnlcontrasenia = new PnlInicioSesionContrasenia();
        Iniciar();
    }

    public void Iniciar()
    {
        vista.setVisible(true);
        IngresarCredencial();
        pnlcredencial.getBtnSiguienteCredencial().addActionListener(e -> IngresarContrasenia());
    }

    private void IngresarCredencial(){
        vista.getPanelContenido().removeAll();
        vista.getPanelContenido().add(pnlcredencial);
        vista.getPanelContenido().revalidate();
        vista.getPanelContenido().repaint();
    }

    private void IngresarContrasenia()
    {
        vista.getPanelContenido().removeAll();
        vista.getPanelContenido().add(pnlcontrasenia);
        vista.getPanelContenido().revalidate();
        vista.getPanelContenido().repaint();
        
    }

    
}
