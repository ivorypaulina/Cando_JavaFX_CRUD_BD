package com.libreria.controller;

import com.libreria.dao.ParticipanteDAO;
import com.libreria.model.Participante;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * PASOS 5–9 – Controlador de la ventana CRUD de Participantes.
 *
 * PASO 5 · Diseño de controles (inyectados con @FXML)
 * PASO 6 · onGuardar() → INSERT con validaciones + try-catch
 * PASO 7 · cargarTabla() → SELECT con try-catch
 * PASO 8 · selección en tabla → formulario → UPDATE con try-catch
 * PASO 9 · onEliminar() → Alert CONFIRMATION → DELETE con try-catch
 */
public class ParticipantesController implements Initializable {

    // ── PASO 5 · Controles del formulario ─────────────────────────
    @FXML private Label           lblIdGenerado;
    @FXML private TextField       txtCedula;
    @FXML private TextField       txtNombre;
    @FXML private TextField       txtApellido;
    @FXML private TextField       txtEdad;
    @FXML private TextField       txtCorreo;
    @FXML private ComboBox<String> cmbEstadoCivil;
    @FXML private ToggleGroup     tgJornada;
    @FXML private RadioButton     rbMatutina;
    @FXML private RadioButton     rbVespertina;
    @FXML private RadioButton     rbNocturna;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private TextArea        txtObservaciones;

    // ── Búsqueda ──────────────────────────────────────────────────
    @FXML private TextField       txtBuscar;

    // ── Tabla ─────────────────────────────────────────────────────
    @FXML private TableView<Participante>            tablaParticipantes;
    @FXML private TableColumn<Participante, Integer> colId;
    @FXML private TableColumn<Participante, String>  colCedula;
    @FXML private TableColumn<Participante, String>  colNombre;
    @FXML private TableColumn<Participante, String>  colApellido;
    @FXML private TableColumn<Participante, Integer> colEdad;
    @FXML private TableColumn<Participante, String>  colCorreo;
    @FXML private TableColumn<Participante, String>  colEstadoCivil;
    @FXML private TableColumn<Participante, String>  colJornada;
    @FXML private TableColumn<Participante, String>  colCategoria;

    /** ID del registro seleccionado; -1 = modo "nuevo". */
    private int idSeleccionado = -1;

    // ── PASO 5 · Inicialización ───────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ComboBox opciones
        cmbEstadoCivil.getItems().addAll("Soltero", "Casado", "Divorciado", "Viudo");
        cmbCategoria.getItems().addAll("Principiante", "Intermedio", "Avanzado", "Profesional");

        // Enlazar columnas a propiedades del modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colEstadoCivil.setCellValueFactory(new PropertyValueFactory<>("estadoCivil"));
        colJornada.setCellValueFactory(new PropertyValueFactory<>("jornada"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        // PASO 8 · Listener: clic en fila → carga formulario
        tablaParticipantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, nuevo) -> {
                    if (nuevo != null) cargarEnFormulario(nuevo);
                });

        // PASO 7 · Cargar datos iniciales
        cargarTabla();
    }

    // ── PASO 7 · LEER ─────────────────────────────────────────────
    /** Ejecuta SELECT y enlaza la lista al TableView con filtro de búsqueda. */
    private void cargarTabla() {
        try {
            FilteredList<Participante> listaFiltrada =
                    new FilteredList<>(ParticipanteDAO.leer(), p -> true);

            // Filtro en tiempo real por nombre, apellido o cédula
            txtBuscar.textProperty().addListener((obs, oldVal, newVal) ->
                listaFiltrada.setPredicate(p -> {
                    if (newVal == null || newVal.isBlank()) return true;
                    String lower = newVal.toLowerCase();
                    return p.getNombre().toLowerCase().contains(lower)
                        || p.getApellido().toLowerCase().contains(lower)
                        || p.getCedula().toLowerCase().contains(lower);
                })
            );

            tablaParticipantes.setItems(listaFiltrada);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error al cargar datos",
                    "No se pudieron obtener los registros de la base de datos.\n" + e.getMessage());
        }
    }

    // ── PASO 6 / 8 · GUARDAR (INSERT o UPDATE) ───────────────────
    @FXML
    private void onGuardar() {
        // ── Validaciones (PASO 6) ─────────────────────────────────
        try {
            String cedula    = txtCedula.getText().trim();
            String nombre    = txtNombre.getText().trim();
            String apellido  = txtApellido.getText().trim();
            String edadStr   = txtEdad.getText().trim();
            String correo    = txtCorreo.getText().trim();
            String estadoCivil = cmbEstadoCivil.getValue();
            String categoria   = cmbCategoria.getValue();

            // Campos vacíos
            if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                    || edadStr.isEmpty() || correo.isEmpty()
                    || estadoCivil == null || categoria == null
                    || tgJornada.getSelectedToggle() == null) {
                throw new IllegalArgumentException("Todos los campos obligatorios deben completarse.");
            }

            // Cédula solo números
            if (!cedula.matches("\\d+")) {
                throw new IllegalArgumentException("La cédula debe contener solo números.");
            }

            // Edad numérica y mayor a 5
            int edad;
            try {
                edad = Integer.parseInt(edadStr);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("La edad debe ser un número entero.");
            }
            if (edad <= 5) {
                throw new IllegalArgumentException("La edad debe ser mayor a 5 años.");
            }

            // Correo con @
            if (!correo.contains("@") || !correo.contains(".")) {
                throw new IllegalArgumentException("El correo no tiene un formato válido.");
            }

            String jornada = ((RadioButton) tgJornada.getSelectedToggle()).getText();

            // ── Validaciones en BD (duplicados) ──────────────────
            if (ParticipanteDAO.cedulaExiste(cedula, idSeleccionado)) {
                throw new IllegalArgumentException("La cédula ya está registrada en el sistema.");
            }
            if (ParticipanteDAO.correoExiste(correo, idSeleccionado)) {
                throw new IllegalArgumentException("El correo ya está registrado en el sistema.");
            }

            // ── Construir objeto ──────────────────────────────────
            Participante p = new Participante(
                    idSeleccionado == -1 ? 0 : idSeleccionado,
                    cedula, nombre, apellido, edad, correo,
                    estadoCivil, jornada, categoria,
                    txtObservaciones.getText().trim()
            );

            // ── INSERT o UPDATE ───────────────────────────────────
            if (idSeleccionado == -1) {
                // PASO 6 · Crear
                ParticipanteDAO.crear(p);
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Registro exitoso", "Participante registrado correctamente.");
            } else {
                // PASO 8 · Actualizar
                ParticipanteDAO.actualizar(p);
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Actualización exitosa", "Participante actualizado correctamente.");
            }

            onNuevo();
            cargarTabla();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos inválidos", e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos",
                    "Ocurrió un error al guardar:\n" + e.getMessage());
        }
    }

    // ── PASO 9 · ELIMINAR ─────────────────────────────────────────
    @FXML
    private void onEliminar() {
        Participante seleccionado = tablaParticipantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Sin selección", "Seleccione un participante en la tabla para eliminar.");
            return;
        }

        // Alert de CONFIRMACIÓN (PASO 9)
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Está seguro de eliminar este participante?");
        confirm.setContentText(
                "Nombre: " + seleccionado.getNombre() + " " + seleccionado.getApellido() +
                "\nCédula: " + seleccionado.getCedula() +
                "\n\nEsta acción no se puede deshacer.");

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    ParticipanteDAO.eliminar(seleccionado.getId());
                    mostrarAlerta(Alert.AlertType.INFORMATION,
                            "Eliminado", "El participante fue eliminado correctamente.");
                    onNuevo();
                    cargarTabla();
                } catch (SQLException e) {
                    mostrarAlerta(Alert.AlertType.ERROR,
                            "Error al eliminar",
                            "No se pudo eliminar el registro:\n" + e.getMessage());
                }
            }
        });
    }

    // ── Limpiar formulario / modo Nuevo ───────────────────────────
    @FXML
    private void onNuevo() {
        idSeleccionado = -1;
        lblIdGenerado.setText("(Auto)");
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEdad.clear();
        txtCorreo.clear();
        cmbEstadoCivil.getSelectionModel().clearSelection();
        if (tgJornada.getSelectedToggle() != null)
            tgJornada.getSelectedToggle().setSelected(false);
        cmbCategoria.getSelectionModel().clearSelection();
        txtObservaciones.clear();
        tablaParticipantes.getSelectionModel().clearSelection();
    }

    // ── PASO 8 · Cargar fila seleccionada en el formulario ────────
    private void cargarEnFormulario(Participante p) {
        idSeleccionado = p.getId();
        lblIdGenerado.setText(String.valueOf(p.getId()));
        txtCedula.setText(p.getCedula());
        txtNombre.setText(p.getNombre());
        txtApellido.setText(p.getApellido());
        txtEdad.setText(String.valueOf(p.getEdad()));
        txtCorreo.setText(p.getCorreo());
        cmbEstadoCivil.setValue(p.getEstadoCivil());
        for (Toggle t : tgJornada.getToggles()) {
            if (((RadioButton) t).getText().equals(p.getJornada())) {
                t.setSelected(true);
                break;
            }
        }
        cmbCategoria.setValue(p.getCategoria());
        txtObservaciones.setText(p.getObservaciones());
    }

    // ── Salir ─────────────────────────────────────────────────────
    @FXML
    private void onSalir() {
        ((Stage) txtCedula.getScene().getWindow()).close();
    }

    // ── Helper Alert ──────────────────────────────────────────────
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
