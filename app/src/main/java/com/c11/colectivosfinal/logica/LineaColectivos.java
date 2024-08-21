package com.c11.colectivosfinal.logica;

public class LineaColectivos {
    int idColectivo, idLineaColectivo;
    String idLinea;
    String recorrido;

    public LineaColectivos (String idLinea, int idColectivo, String recorrido){
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

    public String getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(String idLinea) {
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
