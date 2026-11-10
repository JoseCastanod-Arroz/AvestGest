/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Contrato de acceso a datos de usuarios.
 *
 * Define QUE operaciones se pueden hacer con los usuarios, sin decir COMO.
 * Hoy se implementa con datos en memoria (UsuarioRepositorioMemoria) y
 * manana se puede implementar con una base de datos real (por ejemplo
 * UsuarioRepositorioSQL) sin tocar el controlador.
 *
 * @author krate
 */
public interface UsuarioRepositorio {

    /**
     * Busca un usuario por su credencial (usuario, correo, etc.).
     *
     * @param credencial identificador del usuario
     * @return el usuario encontrado, o null si no existe
     */
    Usuario buscarPorCredencial(String credencial);

    /**
     * Valida que la credencial corresponda a un usuario existente.
     *
     * Util para el primer paso del inicio de sesion, donde solo se pide la
     * credencial antes de solicitar la contrasenia.
     *
     * @param credencial identificador del usuario
     * @return true si existe un usuario con esa credencial
     */
    boolean validarCredencial(String credencial);

    /**
     * Valida si la credencial y contrasenia corresponden a un usuario valido.
     *
     * @param credencial identificador del usuario
     * @param contrasenia contrasenia a verificar
     * @return true si las credenciales son correctas
     */
    boolean validar(String credencial, String contrasenia);

    /**
     * Guarda un usuario nuevo (equivale a "Actualizar base de datos" del
     * diagrama de creacion de usuarios).
     *
     * @param usuario usuario a registrar
     * @return true si se registro correctamente, false si ya existia la
     *         credencial o el usuario era invalido
     */
    boolean guardar(Usuario usuario);

    /**
     * Devuelve todos los usuarios registrados (paso "desplegar lista de
     * usuarios" del diagrama de asignacion de rol).
     *
     * @return lista de usuarios
     */
    java.util.List<Usuario> listar();

    /**
     * Actualiza el rol de un usuario (paso "actualizar rol en base de datos").
     *
     * @param credencial identificador del usuario
     * @param rol nuevo rol a asignar
     * @return true si el usuario existia y se actualizo
     */
    boolean actualizarRol(String credencial, String rol);
}
