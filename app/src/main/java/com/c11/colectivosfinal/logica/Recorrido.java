package com.c11.colectivosfinal.logica;

import static android.app.PendingIntent.getActivity;




import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.graphics.drawable.DrawableContainerCompat;

import com.c11.colectivosfinal.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Recorrido {
    private String recorrido, idLinea;
    private MapView map;
    private Context context;


    public Recorrido(String recorrido, String idLinea, MapView map, Context context) {
        this.recorrido = recorrido;
        this.idLinea = idLinea;
        this.map = map;
        this.context = context;
    }

    public void insertarRecorrido(){
        InputStream inputStream = null;
        try{

            int resourceId = context.getResources().getIdentifier(recorrido, "raw", context.getPackageName());
            if(resourceId == 0 ){
                throw new Resources.NotFoundException("No se encontraron recorridos para "+recorrido);
            }
            inputStream = context.getResources().openRawResource(resourceId);

            String json = new Scanner(inputStream).useDelimiter("\\A").next();
            JSONObject geoJson = new JSONObject(json);
            parseGeoJson(geoJson);
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void parseFeature(JSONObject feature) throws Exception {
        JSONObject geometry = feature.getJSONObject("geometry");
        String type = geometry.getString("type");

        switch (type) {
            case "Point":
                addPoint(geometry.getJSONArray("coordinates"));
                break;
            case "LineString":
                addLineString(geometry.getJSONArray("coordinates"));
                break;
            case "Polygon":
                addPolygon(geometry.getJSONArray("coordinates"));

                break;
        }
    }

    public void addLineString(JSONArray coordinates) throws Exception {
        List<GeoPoint> points = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coord = coordinates.getJSONArray(i);
            points.add(new GeoPoint(coord.getDouble(1), coord.getDouble(0)));
        }
        Polyline line = new Polyline();
        line.setPoints(points);

        line.setColor(Color.parseColor("#3283F7"));
        line.getOutlinePaint().setStrokeCap(Paint.Cap.ROUND);


        map.getOverlays().add(line);
    }


    public void addPolygon(JSONArray coordinates) throws Exception {
        List<GeoPoint> points = new ArrayList<>();
        JSONArray ring = coordinates.getJSONArray(0);
        for (int i = 0; i < ring.length(); i++) {
            JSONArray coord = ring.getJSONArray(i);
            points.add(new GeoPoint(coord.getDouble(1), coord.getDouble(0)));
        }
        Polygon polygon = new Polygon();
        polygon.setStrokeColor(Color.RED);

        polygon.setPoints(points);

        map.getOverlays().add(polygon);
    }


    public void addPoint(JSONArray coordinates) throws Exception {
        double lon = coordinates.getDouble(0);
        double lat = coordinates.getDouble(1);
        GeoPoint point = new GeoPoint(lat, lon);
        List<OverlayItem> items = new ArrayList<>();
        OverlayItem overlayItem = new OverlayItem("Parada", "Tuki", point);

        Bitmap bitmap = null;

        if(idLinea.equals("1")){
            // creo el icono del azul
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parada_azul);
        }else if(idLinea.equals("2")) {
            // creo el icono del amarillo
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.parada_amarillo);
        }
        // lo redimensiono
        Bitmap bitmapRedimensionado = Bitmap.createScaledBitmap(bitmap, 40,40, false);
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmapRedimensionado);

        overlayItem.setMarker(drawable);

        items.add(overlayItem);
        ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(context, items, null);
        overlay.setFocusItemsOnTap(true);

        map.getOverlays().add(overlay);
    }

    public void parseGeoJson(JSONObject geoJson) throws Exception{
        String type = geoJson.getString("type");
        if("FeatureCollection".equals(type)){
            JSONArray features = geoJson.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                parseFeature(features.getJSONObject(i));
            }
        }
    }


}
