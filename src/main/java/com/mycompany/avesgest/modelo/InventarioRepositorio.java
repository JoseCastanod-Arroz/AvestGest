/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.List;

/**
 * Contrato de acceso a datos del modulo de inventario.
 *
 * Define QUE operaciones se pueden hacer con insumos, stock y movimientos, sin
 * decir COMO. Hoy se implementa con datos en memoria
 * (InventarioRepositorioMemoria) y manana se puede implementar contra una base
 * de datos real (por ejemplo InventarioRepositorioSQL) sin tocar los
 * controladores.
 *
 * @author krate
 */
public interface InventarioRepositorio {

    /**
     * Devuelve todos los insumos registrados.
     *
     * @return lista de insumos
     */
    List<Insumo> listarInsumos();

    /**
     * Busca un insumo por su identificador interno.
     *
     * @param idInsumo identificador del insumo
     * @return el insumo encontrado, o null si no existe
     */
    Insumo buscarInsumo(int idInsumo);

    /**
     * Busca insumos cuyo codigo o nombre coincida (total o parcialmente) con el
     * identificador dado. Util para la consulta de stock y la edicion.
     *
     * @param identificador codigo o nombre a buscar
     * @return lista de insumos que coinciden (vacia si no hay)
     */
    List<Insumo> buscarCoincidencias(String identificador);

    /**
     * Indica si existe otro insumo (distinto del dado) con el mismo codigo o
     * nombre. Sirve para impedir duplicados en la edicion.
     *
     * @param codigo codigo a verificar
     * @param nombre nombre a verificar
     * @param idInsumoExcluir id del insumo que se esta editando (se ignora en
     *                        la comparacion)
     * @return true si hay un duplicado
     */
    boolean existeDuplicado(String codigo, String nombre, int idInsumoExcluir);

    /**
     * Actualiza la informacion fija de un insumo (nombre, codigo, categoria,
     * unidad de medida, precio). No toca el stock ni los movimientos.
     *
     * @param insumo insumo con los datos nuevos
     * @return true si el insumo existia y se actualizo
     */
    boolean actualizarInsumo(Insumo insumo);

    /**
     * Devuelve el stock de un insumo.
     *
     * @param idInsumo identificador del insumo
     * @return el stock, o null si no existe
     */
    Stock obtenerStock(int idInsumo);

    /**
     * Devuelve el stock de todos los insumos registrados.
     *
     * @return lista de stock
     */
    List<Stock> listarStock();

    /**
     * Configura el nivel minimo de un insumo.
     *
     * @param idInsumo identificador del insumo
     * @param nivelMinimo nuevo nivel minimo
     * @return true si el insumo existia y se actualizo
     */
    boolean actualizarNivelMinimo(int idInsumo, int nivelMinimo);

    /**
     * Guarda un movimiento de entrada y suma la cantidad al stock.
     *
     * @param movimiento movimiento de entrada
     * @return true si se registro correctamente
     */
    boolean registrarEntrada(MovimientoInventario movimiento);

    /**
     * Guarda un movimiento de salida y resta la cantidad al stock. No permite
     * dejar el stock negativo.
     *
     * @param movimiento movimiento de salida
     * @return true si se registro correctamente
     */
    boolean registrarSalida(MovimientoInventario movimiento);

    /**
     * Devuelve el historial de movimientos de un insumo.
     *
     * @param idInsumo identificador del insumo
     * @return lista de movimientos
     */
    List<MovimientoInventario> listarMovimientos(int idInsumo);
}
