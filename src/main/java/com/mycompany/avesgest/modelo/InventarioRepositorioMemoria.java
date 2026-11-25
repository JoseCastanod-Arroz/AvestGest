/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * "Base de datos" simulada en memoria para el modulo de inventario.
 *
 * Guarda insumos, su stock y los movimientos en listas quemadas, igual que
 * UsuarioRepositorioMemoria lo hace con los usuarios. Mantiene la consistencia
 * de stock (suma de entradas menos salidas) actualizando el stock cada vez que
 * se registra un movimiento. Cuando exista la base de datos real, basta con
 * crear otra clase que implemente InventarioRepositorio.
 *
 * Nota: se usa un unico ArrayList compartido por instancia. Como cada clase de
 * logica crea su propio repositorio (igual que en el resto del proyecto), los
 * datos de prueba se recargan por cada operacion abierta; el comportamiento es
 * suficiente para validar los flujos del modulo.
 *
 * @author krate
 */
public class InventarioRepositorioMemoria implements InventarioRepositorio {

    private final List<Insumo> insumos;
    private final List<Stock> stocks;
    private final List<MovimientoInventario> movimientos;
    private int secuenciaMovimiento;

    public InventarioRepositorioMemoria() {
        insumos = new ArrayList<>();
        stocks = new ArrayList<>();
        movimientos = new ArrayList<>();
        secuenciaMovimiento = 0;
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        // Insumos quemados (simulan registros de la tabla INSUMO)
        insumos.add(new Insumo(1, "CAF001", "Cafe de origen", "Bebidas", "kg", 35000));
        insumos.add(new Insumo(2, "LEC001", "Leche entera", "Bebidas", "litro", 4500));
        insumos.add(new Insumo(3, "AZU001", "Azucar", "Abarrotes", "kg", 3800));
        insumos.add(new Insumo(4, "VAS001", "Vaso desechable", "Empaques", "unidad", 150));

        // Stock inicial con nivel minimo configurado
        stocks.add(new Stock(1, 20, 5));
        stocks.add(new Stock(2, 8, 10));   // nace en stock bajo
        stocks.add(new Stock(3, 0, 3));    // nace agotado
        stocks.add(new Stock(4, 500, 100));
    }

    @Override
    public List<Insumo> listarInsumos() {
        return new ArrayList<>(insumos);
    }

    @Override
    public Insumo buscarInsumo(int idInsumo) {
        for (Insumo i : insumos) {
            if (i.getIdInsumo() == idInsumo) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<Insumo> buscarCoincidencias(String identificador) {
        List<Insumo> resultado = new ArrayList<>();
        if (identificador == null) {
            return resultado;
        }
        String buscado = identificador.trim().toLowerCase();
        if (buscado.isEmpty()) {
            return new ArrayList<>(insumos);
        }
        for (Insumo i : insumos) {
            if (i.getCodigo().toLowerCase().contains(buscado)
                    || i.getNombre().toLowerCase().contains(buscado)) {
                resultado.add(i);
            }
        }
        return resultado;
    }

    @Override
    public boolean existeDuplicado(String codigo, String nombre, int idInsumoExcluir) {
        for (Insumo i : insumos) {
            if (i.getIdInsumo() == idInsumoExcluir) {
                continue;
            }
            if (i.getCodigo().equalsIgnoreCase(codigo)
                    || i.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean actualizarInsumo(Insumo insumo) {
        if (insumo == null) {
            return false;
        }
        Insumo existente = buscarInsumo(insumo.getIdInsumo());
        if (existente == null) {
            return false;
        }
        // La edicion solo cambia la informacion fija, nunca el stock
        existente.setCodigo(insumo.getCodigo());
        existente.setNombre(insumo.getNombre());
        existente.setCategoria(insumo.getCategoria());
        existente.setUnidadMedida(insumo.getUnidadMedida());
        existente.setPrecio(insumo.getPrecio());
        return true;
    }

    @Override
    public Stock obtenerStock(int idInsumo) {
        for (Stock s : stocks) {
            if (s.getIdInsumo() == idInsumo) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Stock> listarStock() {
        return new ArrayList<>(stocks);
    }

    @Override
    public boolean actualizarNivelMinimo(int idInsumo, int nivelMinimo) {
        Stock s = obtenerStock(idInsumo);
        if (s == null) {
            return false;
        }
        s.setNivelMinimo(nivelMinimo);
        return true;
    }

    @Override
    public boolean registrarEntrada(MovimientoInventario movimiento) {
        if (movimiento == null) {
            return false;
        }
        Stock s = obtenerStock(movimiento.getIdInsumo());
        if (s == null) {
            return false;
        }
        movimiento.setIdMovimiento(++secuenciaMovimiento);
        movimientos.add(movimiento);
        s.sumar(movimiento.getCantidad());
        return true;
    }

    @Override
    public boolean registrarSalida(MovimientoInventario movimiento) {
        if (movimiento == null) {
            return false;
        }
        Stock s = obtenerStock(movimiento.getIdInsumo());
        if (s == null) {
            return false;
        }
        // Nunca dejar el stock negativo
        if (!s.haySuficiente(movimiento.getCantidad())) {
            return false;
        }
        movimiento.setIdMovimiento(++secuenciaMovimiento);
        movimientos.add(movimiento);
        s.restar(movimiento.getCantidad());
        return true;
    }

    @Override
    public List<MovimientoInventario> listarMovimientos(int idInsumo) {
        List<MovimientoInventario> resultado = new ArrayList<>();
        for (MovimientoInventario m : movimientos) {
            if (m.getIdInsumo() == idInsumo) {
                resultado.add(m);
            }
        }
        return resultado;
    }
}
