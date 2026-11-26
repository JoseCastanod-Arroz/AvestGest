/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.modelo.AnularVenta;
import com.mycompany.avesgest.modelo.DetalleVenta;
import com.mycompany.avesgest.modelo.HistorialVentas;
import com.mycompany.avesgest.modelo.Insumo;
import com.mycompany.avesgest.modelo.RegistroVenta;
import com.mycompany.avesgest.modelo.Stock;
import com.mycompany.avesgest.modelo.Venta;
import com.mycompany.avesgest.modelo.VentaRepositorio;
import com.mycompany.avesgest.modelo.VentaRepositorioSQL;
import com.mycompany.avesgest.vista.Ventas.FormVentas;
import com.mycompany.avesgest.vista.Ventas.PnlAnulacionVenta;
import com.mycompany.avesgest.vista.Ventas.PnlDetalleVenta;
import com.mycompany.avesgest.vista.Ventas.PnlFiltroProducto;
import com.mycompany.avesgest.vista.Ventas.PnlHistorialVentas;
import com.mycompany.avesgest.vista.Ventas.PnlMenuVentas;
import com.mycompany.avesgest.vista.Ventas.PnlRegistroVenta;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador del modulo de gestion de ventas (Modulo 3).
 *
 * Orquesta los cinco procesos del modulo (registro, historial por fecha, filtro
 * por producto, detalle de venta y anulacion) coordinando el modelo con las
 * vistas, igual que los demas controladores del proyecto. Comparte un unico
 * VentaRepositorio entre todas las operaciones para que el stock se mantenga
 * consistente mientras la ventana esta abierta.
 *
 * La anulacion de venta es exclusiva del administrador: el controlador
 * deshabilita el boton del menu cuando el rol no corresponde.
 *
 * @author krate
 */
public class VentasController {

    private final VentaRepositorio rep;
    private final String rolUsuario;
    private final String usuarioActivo;

    private final FormVentas vista;
    private final PnlMenuVentas pnlMenu;

    /**
     * Crea el controlador para un operario (acceso limitado).
     */
    public VentasController() {
        this("admin", "Administrador");
    }

    /**
     * @param usuarioActivo credencial del usuario con la sesion activa
     * @param rolUsuario    rol del usuario: "Administrador" u otro (operario)
     */
    public VentasController(String usuarioActivo, String rolUsuario) {
        this.rep = new VentaRepositorioSQL();
        this.usuarioActivo = usuarioActivo;
        this.rolUsuario = rolUsuario;
        this.vista = new FormVentas();
        this.pnlMenu = new PnlMenuVentas();
        iniciar();
    }

    private boolean esAdministrador() {
        return "Administrador".equalsIgnoreCase(rolUsuario);
    }

    private void iniciar() {
        vista.setVisible(true);
        mostrarMenu();

        pnlMenu.getBtnRegistro().addActionListener(e -> abrirRegistro());
        pnlMenu.getBtnHistorial().addActionListener(e -> abrirHistorial());
        pnlMenu.getBtnFiltro().addActionListener(e -> abrirFiltro());

        // Anular venta es exclusivo del administrador
        boolean admin = esAdministrador();
        pnlMenu.getBtnAnular().setEnabled(admin);
        pnlMenu.getBtnAnular().addActionListener(e -> abrirAnulacion());

        pnlMenu.getBtnVolver().addActionListener(e -> vista.dispose());
    }

    private void mostrarPanel(JPanel panel) {
        JPanel contenido = vista.getPanelContenido();
        contenido.removeAll();
        contenido.add(panel);
        contenido.revalidate();
        contenido.repaint();
    }

    private void mostrarMenu() {
        mostrarPanel(pnlMenu);
    }

    // ----------------------------------------------------------------
    // Proceso 1: registro de venta
    // ----------------------------------------------------------------
    private void abrirRegistro() {
        RegistroVenta modelo = new RegistroVenta(rep);
        modelo.setUsuario(usuarioActivo);
        List<Insumo> insumos = modelo.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados en el sistema");
            return;
        }

        PnlRegistroVenta pnl = new PnlRegistroVenta();
        cargarProductos(pnl.getCmbProducto(), insumos);
        refrescarCanasta(pnl, modelo);
        refrescarProductoSeleccionado(pnl, insumos);

        pnl.getCmbProducto().addActionListener(e -> refrescarProductoSeleccionado(pnl, insumos));
        pnl.getBtnAgregar().addActionListener(e -> agregarProducto(pnl, insumos, modelo));
        pnl.getBtnQuitar().addActionListener(e -> quitarProducto(pnl, modelo));
        pnl.getBtnFinalizar().addActionListener(e -> finalizarVenta(pnl, modelo));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());

        mostrarPanel(pnl);
    }

    /**
     * Muestra el precio y el stock del producto elegido (pasos 6 del flujo).
     */
    private void refrescarProductoSeleccionado(PnlRegistroVenta pnl, List<Insumo> insumos) {
        int idx = pnl.getCmbProducto().getSelectedIndex();
        if (idx < 0 || idx >= insumos.size()) {
            pnl.getLblPrecioValor().setText("-");
            pnl.getLblStockValor().setText("-");
            return;
        }
        Insumo insumo = insumos.get(idx);
        Stock stock = rep.obtenerStock(insumo.getIdInsumo());
        pnl.getLblPrecioValor().setText("$" + insumo.getPrecio());
        pnl.getLblStockValor().setText(stock == null ? "-"
                : stock.getCantidadDisponible() + " " + insumo.getUnidadMedida());
    }

    private void agregarProducto(PnlRegistroVenta pnl, List<Insumo> insumos, RegistroVenta modelo) {
        int idx = pnl.getCmbProducto().getSelectedIndex();
        if (idx < 0 || idx >= insumos.size()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
            return;
        }
        Integer cantidad = parseEntero(pnl.getTxtCantidad().getText(), "cantidad");
        if (cantidad == null) {
            return;
        }
        if (modelo.agregarProducto(insumos.get(idx), cantidad)) {
            pnl.getTxtCantidad().setText("");
            refrescarCanasta(pnl, modelo);
        }
    }

    private void quitarProducto(PnlRegistroVenta pnl, RegistroVenta modelo) {
        int fila = pnl.getTablaVenta().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la venta para quitarlo");
            return;
        }
        if (modelo.quitarProducto(fila)) {
            refrescarCanasta(pnl, modelo);
        }
    }

    private void refrescarCanasta(PnlRegistroVenta pnl, RegistroVenta modelo) {
        DefaultTableModel tabla = pnl.getModeloTabla();
        tabla.setRowCount(0);
        for (DetalleVenta d : modelo.getCanasta()) {
            tabla.addRow(new Object[]{
                d.getCodigoProducto(),
                d.getNombreProducto(),
                d.getPrecioUnitario(),
                d.getCantidad(),
                d.getSubtotal()
            });
        }
        pnl.getLblTotalValor().setText("$" + modelo.calcularTotal());
    }

    private void finalizarVenta(PnlRegistroVenta pnl, RegistroVenta modelo) {
        if (!modelo.validarDatos()) {
            return;
        }
        // Si no se confirma, la venta queda en curso y se pueden corregir los
        // datos sin tocar el stock (caso excepcional 3 del documento)
        if (!confirmar(modelo.generarResumen())) {
            return;
        }
        if (modelo.confirmar()) {
            mostrarMenu();
        }
    }

    // ----------------------------------------------------------------
    // Proceso 2: consulta del historial de ventas por fecha
    // ----------------------------------------------------------------
    private void abrirHistorial() {
        HistorialVentas modelo = new HistorialVentas(rep);
        PnlHistorialVentas pnl = new PnlHistorialVentas();

        pnl.getTxtDesde().setText(LocalDate.now().withDayOfMonth(1).toString());
        pnl.getTxtHasta().setText(LocalDate.now().toString());
        llenarTablaVentas(pnl.getModeloTabla(), modelo.listarTodas());

        pnl.getBtnConsultar().addActionListener(e -> consultarPorFecha(pnl, modelo));
        pnl.getBtnTodos().addActionListener(e -> {
            pnl.getTxtDesde().setText("");
            pnl.getTxtHasta().setText("");
            llenarTablaVentas(pnl.getModeloTabla(), modelo.listarTodas());
        });
        pnl.getBtnVerDetalle().addActionListener(e
                -> abrirDetalleDesdeTabla(pnl.getTablaVentas(), modelo, this::mostrarMenu));
        pnl.getBtnVolver().addActionListener(e -> mostrarMenu());

        mostrarPanel(pnl);
    }

    private void consultarPorFecha(PnlHistorialVentas pnl, HistorialVentas modelo) {
        LocalDate desde = parseFecha(pnl.getTxtDesde().getText(), "fecha inicial");
        if (desde == null) {
            return;
        }
        LocalDate hasta = parseFecha(pnl.getTxtHasta().getText(), "fecha final");
        if (hasta == null) {
            return;
        }
        if (!modelo.validarRangoFechas(desde, hasta)) {
            return;
        }

        List<Venta> ventas = modelo.consultarPorFecha(desde, hasta);
        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas en el rango de fechas consultado");
        }
        llenarTablaVentas(pnl.getModeloTabla(), ventas);
    }

    // ----------------------------------------------------------------
    // Proceso 3: filtro de ventas por producto
    // ----------------------------------------------------------------
    private void abrirFiltro() {
        HistorialVentas modelo = new HistorialVentas(rep);
        PnlFiltroProducto pnl = new PnlFiltroProducto();

        List<Insumo> insumos = rep.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados en el sistema");
            return;
        }
        cargarProductos(pnl.getCmbProducto(), insumos);
        llenarTablaVentas(pnl.getModeloTabla(), modelo.listarTodas());

        pnl.getBtnFiltrar().addActionListener(e -> filtrarPorProducto(pnl, insumos, modelo));
        pnl.getBtnTodos().addActionListener(e
                -> llenarTablaVentas(pnl.getModeloTabla(), modelo.listarTodas()));
        pnl.getBtnVerDetalle().addActionListener(e
                -> abrirDetalleDesdeTabla(pnl.getTablaVentas(), modelo, this::mostrarMenu));
        pnl.getBtnVolver().addActionListener(e -> mostrarMenu());

        mostrarPanel(pnl);
    }

    private void filtrarPorProducto(PnlFiltroProducto pnl, List<Insumo> insumos,
            HistorialVentas modelo) {
        int idx = pnl.getCmbProducto().getSelectedIndex();
        if (idx < 0 || idx >= insumos.size()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
            return;
        }

        List<Venta> ventas = modelo.consultarPorProducto(insumos.get(idx).getIdInsumo());
        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas para ese producto");
        }
        llenarTablaVentas(pnl.getModeloTabla(), ventas);
    }

    // ----------------------------------------------------------------
    // Proceso 4: detalle de venta especifica
    // ----------------------------------------------------------------
    private void abrirDetalleDesdeTabla(JTable tabla, HistorialVentas modelo, Runnable alVolver) {
        Integer idVenta = idVentaDe(tabla);
        if (idVenta == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una venta de la lista");
            return;
        }
        mostrarDetalle(idVenta, modelo, alVolver);
    }

    private void mostrarDetalle(int idVenta, HistorialVentas modelo, Runnable alVolver) {
        Venta venta = modelo.consultarDetalle(idVenta);
        if (venta == null) {
            JOptionPane.showMessageDialog(null, "No se encontro la venta seleccionada");
            return;
        }

        PnlDetalleVenta pnl = new PnlDetalleVenta();
        pnl.getLblTitulo().setText("Detalle de la venta N. " + venta.getIdVenta());
        pnl.getLblFechaValor().setText(String.valueOf(venta.getFecha()));
        pnl.getLblUsuarioValor().setText(venta.getUsuario() == null
                ? "(sin usuario)" : venta.getUsuario());
        pnl.getLblEstadoValor().setText(venta.getEstado().getDescripcion());
        pnl.getLblTotalValor().setText("$" + venta.getValorTotal());

        boolean anulada = !venta.estaVigente();
        pnl.getLblMotivo().setVisible(anulada);
        pnl.getScrollMotivo().setVisible(anulada);
        pnl.getAreaMotivo().setText(anulada
                ? String.valueOf(venta.getMotivoAnulacion()) : "");

        DefaultTableModel tabla = pnl.getModeloTabla();
        tabla.setRowCount(0);
        for (DetalleVenta d : venta.getDetalles()) {
            tabla.addRow(new Object[]{
                d.getCodigoProducto(),
                d.getNombreProducto(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getSubtotal()
            });
        }

        pnl.getBtnVolver().addActionListener(e -> alVolver.run());
        mostrarPanel(pnl);
    }

    // ----------------------------------------------------------------
    // Proceso 5: anulacion de venta (solo administrador)
    // ----------------------------------------------------------------
    private void abrirAnulacion() {
        if (!esAdministrador()) {
            JOptionPane.showMessageDialog(null,
                    "La anulacion de ventas es exclusiva del administrador");
            return;
        }

        AnularVenta modelo = new AnularVenta(rep);
        modelo.setAdministrador(usuarioActivo);

        PnlAnulacionVenta pnl = new PnlAnulacionVenta();
        llenarTablaVentas(pnl.getModeloTabla(), rep.listarVentas());
        limpiarDetalleAnulacion(pnl);

        // Al elegir una venta de la lista se muestra su detalle (pasos 5 y 6)
        pnl.getTablaVentas().getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                mostrarDetalleAnulacion(pnl, modelo);
            }
        });
        pnl.getBtnAnular().addActionListener(e -> anularVenta(pnl, modelo));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());

        mostrarPanel(pnl);
    }

    private void mostrarDetalleAnulacion(PnlAnulacionVenta pnl, AnularVenta modelo) {
        Integer idVenta = idVentaDe(pnl.getTablaVentas());
        if (idVenta == null) {
            limpiarDetalleAnulacion(pnl);
            return;
        }

        Venta venta = modelo.obtenerDetalle(idVenta);
        if (venta == null) {
            limpiarDetalleAnulacion(pnl);
            return;
        }

        pnl.getLblFechaValor().setText(String.valueOf(venta.getFecha()));
        pnl.getLblEstadoValor().setText(venta.getEstado().getDescripcion());
        pnl.getLblTotalValor().setText("$" + venta.getValorTotal());

        DefaultTableModel tabla = pnl.getModeloDetalle();
        tabla.setRowCount(0);
        for (DetalleVenta d : venta.getDetalles()) {
            tabla.addRow(new Object[]{
                d.getCodigoProducto(),
                d.getNombreProducto(),
                d.getCantidad(),
                d.getSubtotal()
            });
        }
    }

    private void limpiarDetalleAnulacion(PnlAnulacionVenta pnl) {
        pnl.getLblFechaValor().setText("-");
        pnl.getLblEstadoValor().setText("-");
        pnl.getLblTotalValor().setText("-");
        pnl.getModeloDetalle().setRowCount(0);
    }

    private void anularVenta(PnlAnulacionVenta pnl, AnularVenta modelo) {
        Integer idVenta = idVentaDe(pnl.getTablaVentas());
        if (idVenta == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una venta de la lista");
            return;
        }

        // Pasos 6 y 7: mostrar el detalle y verificar que no este anulada
        Venta venta = modelo.obtenerDetalle(idVenta);
        if (!modelo.verificarEstadoVenta()) {
            return;
        }

        // Paso 8: motivo obligatorio
        modelo.setMotivo(pnl.getTxtMotivo().getText());
        if (!modelo.validarMotivo()) {
            return;
        }

        // Pasos 9 a 11: resumen y confirmacion
        if (!confirmar(modelo.generarResumen())) {
            return;
        }

        // Pasos 12 a 14: registrar, devolver stock y avisar
        if (modelo.confirmar()) {
            pnl.getTxtMotivo().setText("");
            llenarTablaVentas(pnl.getModeloTabla(), rep.listarVentas());
            limpiarDetalleAnulacion(pnl);
        }
    }

    // ----------------------------------------------------------------
    // Utilidades comunes
    // ----------------------------------------------------------------
    private void cargarProductos(JComboBox<String> combo, List<Insumo> insumos) {
        combo.removeAllItems();
        for (Insumo i : insumos) {
            combo.addItem(i.getCodigo() + " - " + i.getNombre());
        }
    }

    private void llenarTablaVentas(DefaultTableModel tabla, List<Venta> ventas) {
        tabla.setRowCount(0);
        for (Venta v : ventas) {
            tabla.addRow(new Object[]{
                v.getIdVenta(),
                String.valueOf(v.getFecha()),
                v.getUsuario() == null ? "-" : v.getUsuario(),
                v.getValorTotal(),
                v.getEstado().getDescripcion()
            });
        }
    }

    /**
     * Devuelve el id de la venta marcada en la tabla, o null si no hay
     * seleccion. No muestra mensajes: sirve tambien para el evento de
     * seleccion, donde desmarcar la fila no es un error.
     */
    private Integer idVentaDe(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return null;
        }
        try {
            return Integer.valueOf(String.valueOf(tabla.getModel().getValueAt(fila, 0)));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private boolean confirmar(String resumen) {
        int opcion = JOptionPane.showConfirmDialog(null,
                resumen + "\n\n¿Confirma la operacion?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    private Integer parseEntero(String texto, String campo) {
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "El campo \"" + campo + "\" debe ser un numero entero valido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private LocalDate parseFecha(String texto, String campo) {
        try {
            return LocalDate.parse(texto.trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null,
                    "El campo \"" + campo + "\" debe tener el formato AAAA-MM-DD",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
