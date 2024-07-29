package com.c11.colectivosfinal.logica;

import android.content.Context;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.api.IMapController;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class Colectivos{
    private Marker marker;
    public int idColectivo;
    public boolean enFuncion;
    public double latitud, longitud;
    private Context context;

    // OSM API
    private MapView map;


    public Colectivos(MapView mapView, Context context) {
        this.map = mapView;
        this.context = context;
    }

    public int getIdColectivo() {
        return idColectivo;
    }

    public void setIdColectivo(int idColectivo) {
        this.idColectivo = idColectivo;
    }

    public boolean isEnFuncion() {
        return enFuncion;
    }

    public void setEnFuncion(boolean enFuncion) {
        this.enFuncion = enFuncion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Marker setUpMarker(){
        Marker marker = new Marker(map, context);
        GeoPoint geoPoint = new GeoPoint(latitud, longitud);
        marker.setPosition(geoPoint);
        marker.setTitle("Persona a");
        marker.setSnippet("Mda - Sc");
        return marker;
    }

    public void putMarkerInMap(Marker marker){
        /*if (!map.getOverlays().contains(polyline)) {
            map.getOverlays().add(polyline);
        }*/
        map.getOverlays().add(marker);
        map.invalidate();
    }

    public void updateMarker(Marker marker) {
        GeoPoint newPoint = new GeoPoint(latitud, longitud);
        //geoPoints.add(newPoint);
        //polyline.setPoints(geoPoints);

        // Borrar el marker para volverlo a poner, esto nos hace tener el polyline abajo del marker y no al reves
        map.getOverlays().remove(marker);

        /*if (!map.getOverlays().contains(polyline)) {
            map.getOverlays().add(polyline);
        }*/

        marker.setPosition(newPoint);

        map.getOverlays().add(marker);

        map.invalidate();
    }


}
