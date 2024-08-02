package com.c11.colectivosfinal.logica;

public class LineaColectivos {
    int idLinea, idColectivo, idLineaColectivo;

    public LineaColectivos (int idLinea, int idColectivo){
        this.idLinea = idLinea;
        this.idColectivo = idColectivo;
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
