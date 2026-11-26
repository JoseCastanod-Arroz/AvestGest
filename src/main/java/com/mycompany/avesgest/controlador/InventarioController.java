/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.avesgest.controlador;

import com.mycompany.avesgest.modelo.ConfiguracionNivelMinimo;
import com.mycompany.avesgest.modelo.ConsultaStock;
import com.mycompany.avesgest.modelo.EdicionInsumo;
import com.mycompany.avesgest.modelo.Insumo;
import com.mycompany.avesgest.modelo.InventarioRepositorio;
import com.mycompany.avesgest.modelo.InventarioRepositorioSQL;
import com.mycompany.avesgest.modelo.MotivoSalida;
import com.mycompany.avesgest.modelo.RegistroEntrada;
import com.mycompany.avesgest.modelo.RegistroSalida;
import com.mycompany.avesgest.modelo.Stock;
import com.mycompany.avesgest.vista.Inventario.FormInventario;
import com.mycompany.avesgest.vista.Inventario.PnlConsultaStock;
import com.mycompany.avesgest.vista.Inventario.PnlEdicionInsumo;
import com.mycompany.avesgest.vista.Inventario.PnlMenuInventario;
import com.mycompany.avesgest.vista.Inventario.PnlNivelMinimo;
import com.mycompany.avesgest.vista.Inventario.PnlRegistroEntrada;
import com.mycompany.avesgest.vista.Inventario.PnlRegistroSalida;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador del modulo de inventario (Modulo 2).
 *
 * Orquesta las cinco operaciones del modulo (entrada, salida, consulta, nivel
 * minimo y edicion) coordinando el modelo con las vistas, igual que los demas
 * controladores del proyecto. Comparte un unico InventarioRepositorio entre
 * todas las operaciones para que el stock se mantenga consistente mientras la
 * ventana esta abierta.
 *
 * @author krate
 */
public class InventarioController {

    private final InventarioRepositorio rep;
    private final String rolUsuario;
    private final String usuarioActivo;

    private final FormInventario vista;
    private final PnlMenuInventario pnlMenu;

    /**
     * Crea el controlador para un operario (acceso limitado).
     */
    public InventarioController() {
        this("admin", "Administrador");
    }

    /**
     * @param usuarioActivo credencial del usuario con la sesion activa
     * @param rolUsuario    rol del usuario: "Administrador" u otro (operario)
     */
    public InventarioController(String usuarioActivo, String rolUsuario) {
        this.rep = new InventarioRepositorioSQL();
        this.usuarioActivo = usuarioActivo;
        this.rolUsuario = rolUsuario;
        this.vista = new FormInventario();
        this.pnlMenu = new PnlMenuInventario();
        iniciar();
    }

    private boolean esAdministrador() {
        return "Administrador".equalsIgnoreCase(rolUsuario);
    }

    private void iniciar() {
        vista.setVisible(true);
        mostrarMenu();

        pnlMenu.getBtnEntrada().addActionListener(e -> abrirEntrada());
        pnlMenu.getBtnSalida().addActionListener(e -> abrirSalida());
        pnlMenu.getBtnConsulta().addActionListener(e -> abrirConsulta());

        // Configurar nivel minimo y editar insumo son solo del administrador
        boolean admin = esAdministrador();
        pnlMenu.getBtnNivelMinimo().setEnabled(admin);
        pnlMenu.getBtnEdicion().setEnabled(admin);
        pnlMenu.getBtnNivelMinimo().addActionListener(e -> abrirNivelMinimo());
        pnlMenu.getBtnEdicion().addActionListener(e -> abrirEdicion());
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
    // HU-201: Registro de entrada
    // ----------------------------------------------------------------
    private void abrirEntrada() {
        RegistroEntrada modelo = new RegistroEntrada(rep);
        List<Insumo> insumos = modelo.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay insumos registrados en el sistema");
            return;
        }

        PnlRegistroEntrada pnl = new PnlRegistroEntrada();
        cargarInsumos(pnl.getCmbInsumo(), insumos);
        pnl.getTxtFecha().setText(LocalDate.now().toString());
        actualizarStockLabel(pnl.getLblStockActual(), insumos, pnl.getCmbInsumo().getSelectedIndex());

        pnl.getCmbInsumo().addActionListener(e
                -> actualizarStockLabel(pnl.getLblStockActual(), insumos, pnl.getCmbInsumo().getSelectedIndex()));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());
        pnl.getBtnGuardar().addActionListener(e -> guardarEntrada(pnl, insumos, modelo));

        mostrarPanel(pnl);
    }

    private void guardarEntrada(PnlRegistroEntrada pnl, List<Insumo> insumos, RegistroEntrada modelo) {
        Integer cantidad = parseEntero(pnl.getTxtCantidad().getText(), "cantidad");
        if (cantidad == null) {
            return;
        }
        LocalDate fecha = parseFecha(pnl.getTxtFecha().getText());
        if (fecha == null) {
            return;
        }
        int idInsumo = insumos.get(pnl.getCmbInsumo().getSelectedIndex()).getIdInsumo();

        modelo.setIdInsumo(idInsumo);
        modelo.setCantidad(cantidad);
        modelo.setFecha(fecha);
        modelo.setProveedor(pnl.getTxtProveedor().getText());
        modelo.setComentario(pnl.getTxtComentario().getText());
        modelo.setUsuario(usuarioActivo);

        if (!modelo.validarDatos()) {
            return;
        }
        if (!confirmar(modelo.generarResumen())) {
            return; // registro no confirmado: no se modifica el stock
        }
        if (modelo.confirmar()) {
            mostrarMenu();
        }
    }

    // ----------------------------------------------------------------
    // HU-202: Registro de salida
    // ----------------------------------------------------------------
    private void abrirSalida() {
        RegistroSalida modelo = new RegistroSalida(rep);
        List<Insumo> insumos = modelo.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay insumos registrados en el sistema");
            return;
        }

        PnlRegistroSalida pnl = new PnlRegistroSalida();
        cargarInsumos(pnl.getCmbInsumo(), insumos);
        for (MotivoSalida m : modelo.listarMotivos()) {
            pnl.getCmbMotivo().addItem(m.getNombre());
        }
        pnl.getTxtFecha().setText(LocalDate.now().toString());
        actualizarStockLabel(pnl.getLblStockActual(), insumos, pnl.getCmbInsumo().getSelectedIndex());

        pnl.getCmbInsumo().addActionListener(e
                -> actualizarStockLabel(pnl.getLblStockActual(), insumos, pnl.getCmbInsumo().getSelectedIndex()));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());
        pnl.getBtnGuardar().addActionListener(e -> guardarSalida(pnl, insumos, modelo));

        mostrarPanel(pnl);
    }

    private void guardarSalida(PnlRegistroSalida pnl, List<Insumo> insumos, RegistroSalida modelo) {
        Integer cantidad = parseEntero(pnl.getTxtCantidad().getText(), "cantidad");
        if (cantidad == null) {
            return;
        }
        LocalDate fecha = parseFecha(pnl.getTxtFecha().getText());
        if (fecha == null) {
            return;
        }
        int idInsumo = insumos.get(pnl.getCmbInsumo().getSelectedIndex()).getIdInsumo();
        MotivoSalida motivo = modelo.listarMotivos()[pnl.getCmbMotivo().getSelectedIndex()];

        modelo.setIdInsumo(idInsumo);
        modelo.setCantidad(cantidad);
        modelo.setFecha(fecha);
        modelo.setMotivo(motivo);
        modelo.setComentario(pnl.getTxtComentario().getText());
        modelo.setUsuario(usuarioActivo);

        if (!modelo.validarDatos() || !modelo.validarExistencias()) {
            return;
        }
        if (!confirmar(modelo.generarResumen())) {
            return;
        }
        if (modelo.confirmar()) {
            mostrarMenu();
        }
    }

    // ----------------------------------------------------------------
    // HU-203: Consulta de stock actual
    // ----------------------------------------------------------------
    private void abrirConsulta() {
        ConsultaStock modelo = new ConsultaStock(rep);
        PnlConsultaStock pnl = new PnlConsultaStock();

        if (!modelo.hayInsumos()) {
            JOptionPane.showMessageDialog(null, "No hay insumos registrados en el sistema");
            return;
        }
        llenarTabla(pnl, modelo.listar());

        pnl.getBtnTodos().addActionListener(e -> {
            pnl.getTxtIdentificador().setText("");
            llenarTabla(pnl, modelo.listar());
        });
        pnl.getBtnBuscar().addActionListener(e -> {
            List<ConsultaStock.DetalleStock> res = modelo.buscar(pnl.getTxtIdentificador().getText());
            if (res.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron insumos para esa busqueda");
            }
            llenarTabla(pnl, res);
        });
        pnl.getBtnStockBajo().addActionListener(e -> {
            List<ConsultaStock.DetalleStock> res = modelo.filtrarStockBajo();
            if (res.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay insumos en stock bajo");
            }
            llenarTabla(pnl, res);
        });
        pnl.getBtnDetalle().addActionListener(e -> mostrarDetalleSeleccionado(pnl));
        pnl.getBtnVolver().addActionListener(e -> mostrarMenu());

        mostrarPanel(pnl);
    }

    private void llenarTabla(PnlConsultaStock pnl, List<ConsultaStock.DetalleStock> detalles) {
        DefaultTableModel modelo = pnl.getModeloTabla();
        modelo.setRowCount(0);
        for (ConsultaStock.DetalleStock d : detalles) {
            modelo.addRow(new Object[]{
                d.getInsumo().getCodigo(),
                d.getInsumo().getNombre(),
                d.getStock().getCantidadDisponible(),
                d.getInsumo().getUnidadMedida(),
                d.getStock().getNivelMinimo(),
                d.getEstado().getDescripcion()
            });
        }
    }

    private void mostrarDetalleSeleccionado(PnlConsultaStock pnl) {
        int fila = pnl.getTablaStock().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un insumo de la lista");
            return;
        }
        DefaultTableModel m = pnl.getModeloTabla();
        String detalle = "Detalle del insumo\n"
                + "Codigo: " + m.getValueAt(fila, 0) + "\n"
                + "Nombre: " + m.getValueAt(fila, 1) + "\n"
                + "Cantidad disponible: " + m.getValueAt(fila, 2) + "\n"
                + "Unidad de medida: " + m.getValueAt(fila, 3) + "\n"
                + "Nivel minimo: " + m.getValueAt(fila, 4) + "\n"
                + "Estado: " + m.getValueAt(fila, 5);
        JOptionPane.showMessageDialog(null, detalle);
    }

    // ----------------------------------------------------------------
    // HU-204: Configuracion de nivel minimo
    // ----------------------------------------------------------------
    private void abrirNivelMinimo() {
        ConfiguracionNivelMinimo modelo = new ConfiguracionNivelMinimo(rep);
        List<Insumo> insumos = modelo.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay insumos registrados en el sistema");
            return;
        }

        PnlNivelMinimo pnl = new PnlNivelMinimo();
        cargarInsumos(pnl.getCmbInsumo(), insumos);
        refrescarNivelMinimo(pnl, insumos, modelo);

        pnl.getCmbInsumo().addActionListener(e -> refrescarNivelMinimo(pnl, insumos, modelo));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());
        pnl.getBtnGuardar().addActionListener(e -> guardarNivelMinimo(pnl, insumos, modelo));

        mostrarPanel(pnl);
    }

    private void refrescarNivelMinimo(PnlNivelMinimo pnl, List<Insumo> insumos, ConfiguracionNivelMinimo modelo) {
        int idx = pnl.getCmbInsumo().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        Insumo insumo = insumos.get(idx);
        Stock stock = modelo.obtenerStock(insumo.getIdInsumo());
        pnl.getLblUnidad().setText(insumo.getUnidadMedida());
        pnl.getLblStockActual().setText(stock == null ? "-" : String.valueOf(stock.getCantidadDisponible()));
        pnl.getLblNivelActual().setText(stock == null ? "Sin nivel" : String.valueOf(stock.getNivelMinimo()));
    }

    private void guardarNivelMinimo(PnlNivelMinimo pnl, List<Insumo> insumos, ConfiguracionNivelMinimo modelo) {
        Integer nivel = parseEntero(pnl.getTxtNuevoNivel().getText(), "nivel minimo");
        if (nivel == null) {
            return;
        }
        int idInsumo = insumos.get(pnl.getCmbInsumo().getSelectedIndex()).getIdInsumo();

        modelo.setIdInsumo(idInsumo);
        modelo.setNuevoNivel(nivel);
        modelo.setUsuario(usuarioActivo);

        if (!modelo.validar()) {
            return;
        }
        if (!confirmar(modelo.generarResumen())) {
            return;
        }
        if (modelo.confirmar()) {
            refrescarNivelMinimo(pnl, insumos, modelo);
        }
    }

    // ----------------------------------------------------------------
    // HU-205: Edicion de informacion de insumo
    // ----------------------------------------------------------------
    private void abrirEdicion() {
        EdicionInsumo modelo = new EdicionInsumo(rep);
        List<Insumo> insumos = modelo.listarInsumos();
        if (insumos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay insumos registrados en el sistema");
            return;
        }

        PnlEdicionInsumo pnl = new PnlEdicionInsumo();
        cargarInsumos(pnl.getCmbInsumo(), insumos);
        cargarDatosInsumo(pnl, insumos, modelo);

        pnl.getCmbInsumo().addActionListener(e -> cargarDatosInsumo(pnl, insumos, modelo));
        pnl.getBtnCancelar().addActionListener(e -> mostrarMenu());
        pnl.getBtnGuardar().addActionListener(e -> guardarEdicion(pnl, insumos, modelo));

        mostrarPanel(pnl);
    }

    private void cargarDatosInsumo(PnlEdicionInsumo pnl, List<Insumo> insumos, EdicionInsumo modelo) {
        int idx = pnl.getCmbInsumo().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        Insumo insumo = insumos.get(idx);
        pnl.getTxtCodigo().setText(insumo.getCodigo());
        pnl.getTxtNombre().setText(insumo.getNombre());
        pnl.getTxtCategoria().setText(insumo.getCategoria());
        pnl.getTxtUnidad().setText(insumo.getUnidadMedida());
        pnl.getTxtPrecio().setText(String.valueOf(insumo.getPrecio()));
        Stock stock = modelo.obtenerStock(insumo.getIdInsumo());
        pnl.getLblStockRef().setText(stock == null ? "-" : String.valueOf(stock.getCantidadDisponible()));
    }

    private void guardarEdicion(PnlEdicionInsumo pnl, List<Insumo> insumos, EdicionInsumo modelo) {
        Double precio = parseDecimal(pnl.getTxtPrecio().getText());
        if (precio == null) {
            return;
        }
        int idInsumo = insumos.get(pnl.getCmbInsumo().getSelectedIndex()).getIdInsumo();

        modelo.setIdInsumo(idInsumo);
        modelo.setCodigo(pnl.getTxtCodigo().getText().trim());
        modelo.setNombre(pnl.getTxtNombre().getText().trim());
        modelo.setCategoria(pnl.getTxtCategoria().getText().trim());
        modelo.setUnidadMedida(pnl.getTxtUnidad().getText().trim());
        modelo.setPrecio(precio);
        modelo.setUsuario(usuarioActivo);

        if (!modelo.validar()) {
            return;
        }
        if (!confirmar(modelo.generarResumen())) {
            return;
        }
        if (modelo.confirmar()) {
            mostrarMenu();
        }
    }

    // ----------------------------------------------------------------
    // Utilidades comunes
    // ----------------------------------------------------------------
    private void cargarInsumos(JComboBox<String> combo, List<Insumo> insumos) {
        combo.removeAllItems();
        for (Insumo i : insumos) {
            combo.addItem(i.getCodigo() + " - " + i.getNombre());
        }
    }

    private void actualizarStockLabel(javax.swing.JLabel label, List<Insumo> insumos, int idx) {
        if (idx < 0) {
            label.setText("-");
            return;
        }
        Stock stock = rep.obtenerStock(insumos.get(idx).getIdInsumo());
        label.setText(stock == null ? "-"
                : stock.getCantidadDisponible() + " " + insumos.get(idx).getUnidadMedida());
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

    private Double parseDecimal(String texto) {
        try {
            return Double.valueOf(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "El precio debe ser un numero valido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private LocalDate parseFecha(String texto) {
        try {
            return LocalDate.parse(texto.trim());
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null,
                    "La fecha debe tener el formato AAAA-MM-DD",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
