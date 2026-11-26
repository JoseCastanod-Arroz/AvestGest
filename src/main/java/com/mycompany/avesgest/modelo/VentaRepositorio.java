/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Contrato de acceso a datos del modulo de gestion de ventas.
 *
 * Define QUE operaciones se pueden hacer con ventas, su detalle y su
 * anulacion, sin decir COMO. Hoy se implementa contra PostgreSQL
 * (VentaRepositorioSQL) o con datos en memoria (VentaRepositorioMemoria) sin
 * tocar los controladores.
 *
 * Los productos de una venta son los insumos del modulo de inventario, por lo
 * que el contrato tambien expone la lectura de insumos y stock que el modulo
 * de ventas necesita (mostrar precio y existencias al agregar un producto).
 *
 * @author krate
 */
public interface VentaRepositorio {

    /**
     * Devuelve todos los insumos (productos) disponibles para vender.
     *
     * @return lista de insumos
     */
    List<Insumo> listarInsumos();

    /**
     * Devuelve el stock de un insumo.
     *
     * @param idInsumo identificador del insumo
     * @return el stock, o null si no existe
     */
    Stock obtenerStock(int idInsumo);

    /**
     * Guarda la venta con todos sus detalles y descuenta el stock de cada
     * producto dentro de una sola transaccion. Si no alcanza el stock de
     * algun producto, no se guarda nada.
     *
     * @param venta venta con la lista de detalles cargada
     * @return true si la venta se registro correctamente
     */
    boolean guardarVenta(Venta venta);

    /**
     * Devuelve todas las ventas del historial, sin el detalle de productos.
     *
     * @return lista de ventas ordenadas de mas reciente a mas antigua
     */
    List<Venta> listarVentas();

    /**
     * Devuelve las ventas registradas dentro de un rango de fechas
     * (ambos extremos incluidos).
     *
     * @param desde fecha inicial del rango
     * @param hasta fecha final del rango
     * @return lista de ventas del rango
     */
    List<Venta> listarVentasPorFecha(LocalDate desde, LocalDate hasta);

    /**
     * Devuelve las ventas que incluyen un producto determinado.
     *
     * @param idInsumo identificador del producto vendido
     * @return lista de ventas que contienen ese producto
     */
    List<Venta> listarVentasPorInsumo(int idInsumo);

    /**
     * Busca una venta por su identificador y carga ademas sus detalles y su
     * motivo de anulacion si la tiene.
     *
     * @param idVenta identificador de la venta
     * @return la venta encontrada, o null si no existe
     */
    Venta buscarVenta(int idVenta);

    /**
     * Devuelve los productos que componen una venta.
     *
     * @param idVenta identificador de la venta
     * @return lista de lineas de detalle
     */
    List<DetalleVenta> listarDetalles(int idVenta);

    /**
     * Anula una venta: la marca como ANULADA, registra el motivo con el
     * administrador responsable y devuelve los productos al inventario, todo
     * dentro de una sola transaccion. Si la venta ya estaba anulada, no se
     * modifica nada.
     *
     * @param idVenta identificador de la venta a anular
     * @param motivo motivo de la anulacion (obligatorio)
     * @param credencialAdministrador credencial de quien anula
     * @return true si la anulacion se registro correctamente
     */
    boolean anularVenta(int idVenta, String motivo, String credencialAdministrador);
}
