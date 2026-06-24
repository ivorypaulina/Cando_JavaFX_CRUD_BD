package com.libreria.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PASO 3 – Conexión a MySQL con patrón Singleton.
 */
public class Conexion {

    // ── Datos de conexión ─────────────────────────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/libreria_musical"
                                         + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "123456";

    // ── Única instancia (Singleton) ───────────────────────────────
    private static Conexion instancia;
    private Connection connection;

    private Conexion() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Conexion getInstancia() throws SQLException {
        if (instancia == null || instancia.connection.isClosed()) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() {
        return connection;
    }
}
