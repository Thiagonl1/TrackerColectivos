package com.c11.colectivosfinal.logica;

public class LineaColectivos {
    int idLinea, idColectivo, idLineaColectivo;
    String recorrido;

    public LineaColectivos (int idLinea, int idColectivo, String recorrido){
        this.idLinea = idLinea;
        this.idColectivo = idColectivo;
        this.recorrido = recorrido;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public int getIdColectivo() {
        return idColectivo;
    }

    public void setIdColectivo(int idColectivo) {
        this.idColectivo = idColectivo;
    }

    public int getIdLineaColectivo() {
        return idLineaColectivo;
    }

    public void setIdLineaColectivo(int idLineaColectivo) {
        this.idLineaColectivo = idLineaColectivo;
    }
}
