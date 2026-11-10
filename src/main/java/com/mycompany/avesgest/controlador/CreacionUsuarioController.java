/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.modelo.CreacionUsuario;
import com.mycompany.avesgest.vista.CreacionUsuario.FormCreacionUsuario;
import com.mycompany.avesgest.vista.CreacionUsuario.PnlCreacionUsuarioDatos;

/**
 *
 * @author krate
 */
public class CreacionUsuarioController {

    private final FormCreacionUsuario vista;
    private final PnlCreacionUsuarioDatos pnldatos;
    private CreacionUsuario creUsu;

    public CreacionUsuarioController()
    {
        creUsu = new CreacionUsuario();
        vista = new FormCreacionUsuario();
        pnldatos = new PnlCreacionUsuarioDatos();
        Iniciar();
    }

    public void Iniciar()
    {
        vista.setVisible(true);
        IngresarDatos();
        pnldatos.getBtnCrearUsuario().addActionListener(e -> CrearUsuario());
    }

    private void IngresarDatos()
    {
        vista.getPanelContenido().removeAll();
        vista.getPanelContenido().add(pnldatos);
        vista.getPanelContenido().revalidate();
        vista.getPanelContenido().repaint();
    }

    private void CrearUsuario()
    {
        String nombre = pnldatos.getTxtNombre().getText();
        String correo = pnldatos.getTxtCorreo().getText();
        String contrasenia = new String(pnldatos.getTxtContrasenia().getPassword());

        creUsu.setNombre(nombre);
        creUsu.setCorreo(correo);
        creUsu.setContrasenia(contrasenia);

        creUsu.registrar();
    }

}
