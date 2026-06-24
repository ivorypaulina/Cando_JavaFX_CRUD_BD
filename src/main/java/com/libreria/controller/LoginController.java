package com.libreria.controller;

import com.libreria.db.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * PASO 4 – Controlador de la ventana Login.
 */
public class LoginController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField txtPassword;

    private static final String USUARIO_OK  = "admin";
    private static final String PASSWORD_OK = "1234";

    @FXML
    private void onIngresar() {
        // ── Validación de campos vacíos ───────────────────────────
        try {
            String usuario  = txtUsuario.getText().trim();
            String password = txtPassword.getText().trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Por favor complete todos los campos.");
            }

            if (!usuario.equals(USUARIO_OK) || !password.equals(PASSWORD_OK)) {
                throw new SecurityException("Usuario o contraseña incorrectos.");
            }

            // ── Verificar conexión a BD antes de abrir la app ─────
            try {
                Conexion.getInstancia(); // intenta conectar
            } catch (SQLException e) {
                mostrarAlerta(Alert.AlertType.ERROR,
                        "Error de conexión",
                        "No se pudo conectar a la base de datos.\n\n" +
                        "Verifique que MySQL esté activo y que las credenciales\n" +
                        "en Conexion.java sean correctas.\n\n" +
                        "Detalle: " + e.getMessage());
                return;
            }

            // ── Abrir ventana CRUD ────────────────────────────────
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/libreria/participantes.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(
                    getClass().getResource("/com/libreria/styles.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Librería Musical – Gestión de Participantes");
            stage.setScene(scene);
            stage.setMinWidth(1150);
            stage.setMinHeight(720);
            stage.show();

            // Cerrar login
            ((Stage) txtUsuario.getScene().getWindow()).close();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", e.getMessage());
        } catch (SecurityException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", e.getMessage());
            txtPassword.clear();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado", e.getMessage());
        }
    }

    @FXML
    private void onSalir() {
        ((Stage) txtUsuario.getScene().getWindow()).close();
    }

    // ── Helper ────────────────────────────────────────────────────
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
