/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.modelo;

/**
 * Logica del caso de uso "Anular venta" (No. 02 del documento, proceso 5).
 *
 * Operacion exclusiva del administrador. Verifica que la venta este vigente,
 * valida el motivo obligatorio, y al confirmar registra la anulacion, marca la
 * venta como anulada (conservandola en el historial) y devuelve los productos
 * al inventario.
 *
 * @author krate
 */
public class AnularVenta {

    private Venta venta;
    private String motivo;
    private String administrador;

    private final VentaRepositorio rep;
    private final Mensaje msj;

    public AnularVenta() {
        this(new VentaRepositorioMemoria());
    }

    public AnularVenta(VentaRepositorio rep) {
        this.rep = rep;
        this.msj = new Mensaje();
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    /**
     * Recupera la venta seleccionada con todos sus detalles.
     *
     * @param idVenta venta a mostrar
     * @return la venta encontrada, o null si no existe
     */
    public Venta obtenerDetalle(int idVenta) {
        venta = rep.buscarVenta(idVenta);
        return venta;
    }

    /**
     * Paso 7 del flujo: verifica que la venta pueda anularse. Si ya fue
     * anulada muestra el error correspondiente y no permite continuar.
     *
     * @return true si la venta esta vigente
     */
    public boolean verificarEstadoVenta() {
        if (venta == null) {
            msj.mostrarError("Debe seleccionar una venta de la lista");
            return false;
        }
        if (!venta.estaVigente()) {
            msj.mostrarError("La venta N. " + venta.getIdVenta()
                    + " ya fue anulada.\n"
                    + "Motivo registrado: "
                    + (venta.getMotivoAnulacion() == null
                            ? "(sin motivo)" : venta.getMotivoAnulacion()));
            return false;
        }
        return true;
    }

    /**
     * Paso 8 del flujo: el motivo de la anulacion es obligatorio.
     *
     * @return true si el motivo tiene contenido
     */
    public boolean validarMotivo() {
        if (motivo == null || motivo.trim().isEmpty()) {
            msj.mostrarError("El motivo de anulacion es obligatorio");
            return false;
        }
        return true;
    }

    /**
     * Genera el texto del resumen que se muestra antes de confirmar.
     *
     * @return resumen de la anulacion
     */
    public String generarResumen() {
        StringBuilder resumen = new StringBuilder("Resumen de la anulacion\n");
        resumen.append("Venta N. ").append(venta.getIdVenta()).append("\n");
        resumen.append("Valor total: $").append(venta.getValorTotal()).append("\n");
        resumen.append("Fecha de la venta: ").append(venta.getFecha()).append("\n");
        resumen.append("Motivo: ").append(motivo.trim()).append("\n");
        resumen.append("Anulada por: ").append(administrador);
        return resumen.toString();
    }

    /**
     * Confirma la anulacion: registra el motivo, marca la venta como anulada y
     * devuelve los productos al inventario en una sola operacion. Si algo
     * falla, la venta y el stock quedan como estaban y se puede reintentar.
     *
     * @return true si la anulacion se registro correctamente
     */
    public boolean confirmar() {
        if (!verificarEstadoVenta() || !validarMotivo()) {
            return false;
        }

        if (!rep.anularVenta(venta.getIdVenta(), motivo, administrador)) {
            msj.mostrarError("No se pudo anular la venta. "
                    + "La venta y el stock quedan sin cambios. "
                    + "Puede reintentar");
            return false;
        }

        Venta anulada = rep.buscarVenta(venta.getIdVenta());
        venta = anulada;
        msj.mostrar("Venta anulada correctamente.\n"
                + "Venta N. " + venta.getIdVenta() + "\n"
                + "Los productos fueron devueltos al inventario.");
        return true;
    }
}
