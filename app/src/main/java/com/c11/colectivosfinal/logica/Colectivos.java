package com.c11.colectivosfinal.logica;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.c11.colectivosfinal.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;



public class Colectivos{


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





    public Marker setUpMarker(String idLinea){
        Marker marker = new Marker(map, context);
        GeoPoint geoPoint = new GeoPoint(latitud, longitud);
        marker.setPosition(geoPoint);
        Bitmap bitmap = null;


        if (idLinea == null) {
            Log.e("Colectivos", "idLinea is null");
        } else if (idLinea.equals("1")) {
            // Set the blue icon
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ubicacion_azul);
        } else if (idLinea.equals("2")) {
            // Set the yellow icon
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ubicacion_amarillo);
        }

        // lo redimensiono
        Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(bitmap, 100,100, false);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmapRedimensionado);
        marker.setIcon(drawable);
        return marker;
    }

    public void putMarkerInMap(Marker marker){

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
