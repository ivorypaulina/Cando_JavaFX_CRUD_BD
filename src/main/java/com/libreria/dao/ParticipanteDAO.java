package com.libreria.dao;

import com.libreria.db.Conexion;
import com.libreria.model.Participante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ParticipanteDAO {

    // CREAR ────────────────────────────────────────────
    public static boolean crear(Participante p) throws SQLException {
        String sql = "INSERT INTO participantes "
                   + "(cedula, nombre, apellido, edad, correo, estado_civil, jornada, categoria, observaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getCedula());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setInt   (4, p.getEdad());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getEstadoCivil());
            ps.setString(7, p.getJornada());
            ps.setString(8, p.getCategoria());
            ps.setString(9, p.getObservaciones());

            return ps.executeUpdate() > 0;
        }
    }

    // ── LEER ─────────────────────────────────────────────

    public static ObservableList<Participante> leer() throws SQLException {
        ObservableList<Participante> lista = FXCollections.observableArrayList();

        String sql = "SELECT id, cedula, nombre, apellido, edad, correo, "
                   + "estado_civil, jornada, categoria, observaciones "
                   + "FROM participantes ORDER BY id";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Participante(
                        rs.getInt   ("id"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt   ("edad"),
                        rs.getString("correo"),
                        rs.getString("estado_civil"),
                        rs.getString("jornada"),
                        rs.getString("categoria"),
                        rs.getString("observaciones")
                ));
            }
        }
        return lista;
    }

    // ACTUALIZAR ───────────────────────────────────────
    public static boolean actualizar(Participante p) throws SQLException {
        String sql = "UPDATE participantes SET "
                   + "cedula=?, nombre=?, apellido=?, edad=?, correo=?, "
                   + "estado_civil=?, jornada=?, categoria=?, observaciones=? "
                   + "WHERE id=?";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,  p.getCedula());
            ps.setString(2,  p.getNombre());
            ps.setString(3,  p.getApellido());
            ps.setInt   (4,  p.getEdad());
            ps.setString(5,  p.getCorreo());
            ps.setString(6,  p.getEstadoCivil());
            ps.setString(7,  p.getJornada());
            ps.setString(8,  p.getCategoria());
            ps.setString(9,  p.getObservaciones());
            ps.setInt   (10, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // ── ELIMINAR ─────────────────────────────────────────
    public static boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM participantes WHERE id = ?";

        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── Validación de duplicados ──────────────────────────────────
    public static boolean cedulaExiste(String cedula, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM participantes WHERE cedula = ? AND id != ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setInt(2, idExcluir);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public static boolean correoExiste(String correo, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM participantes WHERE correo = ? AND id != ?";
        try (Connection con = Conexion.getInstancia().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setInt(2, idExcluir);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
