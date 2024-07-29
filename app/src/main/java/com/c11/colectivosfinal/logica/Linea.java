package com.c11.colectivosfinal.logica;


public class Linea {
    private int idLinea;
    private String descripcion;

    public Linea(int idLinea, String descripcion) {
        this.idLinea = idLinea;
        this.descripcion = descripcion;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



}
