package com.jdcasas.appmochilero;
import android.content.Context;

public class Lugares {
    private String id;
    private String descripcion;
    private String imagen1;
    private String imagen2;
    private String creadoPor;//id del usuario
    Context context;
    DialogKachuelo dialogo;

    public Lugares(String descripcion, String imagen1, String imagen2, String creadoPor) {
        this.descripcion = descripcion;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.creadoPor = creadoPor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen2) {
        this.imagen2 = imagen2;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }
}
