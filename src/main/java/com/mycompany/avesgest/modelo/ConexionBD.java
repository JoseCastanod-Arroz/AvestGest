/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la conexion a la base de datos PostgreSQL.
 *
 * Aqui estan los datos de conexion en un solo lugar para poder cambiarlos sin
 * tocar el resto del codigo. Si manana cambia el host, el usuario o la clave,
 * solo se edita esta clase.
 *
 * @author krate
 */
public class ConexionBD {

    // Datos de conexion. Ajustar segun el entorno.
    private static final String HOST = "localhost";
    private static final String PUERTO = "5432";
    private static final String BASE_DATOS = "avesgest";
    private static final String USUARIO = "avesgest";
    private static final String CLAVE = "avesgest";

    private static final String URL =
            "jdbc:postgresql://" + HOST + ":" + PUERTO + "/" + BASE_DATOS;

    private ConexionBD() {
    }

    /**
     * Abre una nueva conexion a la base de datos.
     *
     * @return conexion lista para usar
     * @throws SQLException si no se puede conectar
     */
    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
