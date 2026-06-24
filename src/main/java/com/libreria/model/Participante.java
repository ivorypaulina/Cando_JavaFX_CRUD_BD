package com.libreria.model;

import javafx.beans.property.*;

public class Participante {

    private final IntegerProperty id            = new SimpleIntegerProperty();
    private final StringProperty  cedula        = new SimpleStringProperty();
    private final StringProperty  nombre        = new SimpleStringProperty();
    private final StringProperty  apellido      = new SimpleStringProperty();
    private final IntegerProperty edad          = new SimpleIntegerProperty();
    private final StringProperty  correo        = new SimpleStringProperty();
    private final StringProperty  estadoCivil   = new SimpleStringProperty();
    private final StringProperty  jornada       = new SimpleStringProperty();
    private final StringProperty  categoria     = new SimpleStringProperty();
    private final StringProperty  observaciones = new SimpleStringProperty();

    public Participante() {}

    public Participante(int id, String cedula, String nombre, String apellido,
                        int edad, String correo, String estadoCivil,
                        String jornada, String categoria, String observaciones) {
        setId(id);
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setCorreo(correo);
        setEstadoCivil(estadoCivil);
        setJornada(jornada);
        setCategoria(categoria);
        setObservaciones(observaciones);
    }

    // ── Getters / Setters / Properties ───────────────────────────
    public int getId()                    { return id.get(); }
    public void setId(int v)              { id.set(v); }
    public IntegerProperty idProperty()   { return id; }

    public String getCedula()             { return cedula.get(); }
    public void setCedula(String v)       { cedula.set(v); }
    public StringProperty cedulaProperty(){ return cedula; }

    public String getNombre()             { return nombre.get(); }
    public void setNombre(String v)       { nombre.set(v); }
    public StringProperty nombreProperty(){ return nombre; }

    public String getApellido()               { return apellido.get(); }
    public void setApellido(String v)         { apellido.set(v); }
    public StringProperty apellidoProperty()  { return apellido; }

    public int getEdad()                    { return edad.get(); }
    public void setEdad(int v)              { edad.set(v); }
    public IntegerProperty edadProperty()   { return edad; }

    public String getCorreo()               { return correo.get(); }
    public void setCorreo(String v)         { correo.set(v); }
    public StringProperty correoProperty()  { return correo; }

    public String getEstadoCivil()                { return estadoCivil.get(); }
    public void setEstadoCivil(String v)          { estadoCivil.set(v); }
    public StringProperty estadoCivilProperty()   { return estadoCivil; }

    public String getJornada()              { return jornada.get(); }
    public void setJornada(String v)        { jornada.set(v); }
    public StringProperty jornadaProperty() { return jornada; }

    public String getCategoria()                { return categoria.get(); }
    public void setCategoria(String v)          { categoria.set(v); }
    public StringProperty categoriaProperty()   { return categoria; }

    public String getObservaciones()                { return observaciones.get(); }
    public void setObservaciones(String v)          { observaciones.set(v); }
    public StringProperty observacionesProperty()   { return observaciones; }
}
