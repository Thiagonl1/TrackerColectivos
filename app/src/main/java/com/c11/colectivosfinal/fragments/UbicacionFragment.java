package com.c11.colectivosfinal.fragments;


import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.c11.colectivosfinal.BuildConfig;
import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.logica.Colectivos;
import com.c11.colectivosfinal.logica.OsmApi;
import com.c11.colectivosfinal.logica.Recorrido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UbicacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UbicacionFragment extends Fragment {

    private static final long INTERVALO_ACTUALIZACION = 5000; // 5 sec

    /* Necesario para las llamadas a ubicacion */
    private Handler handler;
    private Runnable runnable;

    /* Necesario por osm */
    private MapView map;
    private OsmApi osmApi;
    private Colectivos colectivos;
    private Marker marker1;
    private Recorrido recorridoClass;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "recorrido";
    private static final String ARG_PARAM2 = "idColectivo";
    private static final String ARG_PARAM3 = "idLinea";

    // TODO: Rename and change types of parameters
    private String recorrido;
    private String idColectivo;
    private String idLinea;

    public UbicacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recorrido Parameter 1.
     * @param idColectivo Parameter 2.
     * @param idLinea Parameter 3.
     * @return A new instance of fragment UbicacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UbicacionFragment newInstance(String recorrido, String idColectivo, String idLinea) {
        UbicacionFragment fragment = new UbicacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, recorrido);
        args.putString(ARG_PARAM2, idColectivo);
        args.putString(ARG_PARAM3, idLinea);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recorrido = getArguments().getString(ARG_PARAM1);
            idColectivo = getArguments().getString(ARG_PARAM2);
            idLinea = getArguments().getString(ARG_PARAM3);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inicializar con la app id para evitar baneo de osm
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        // Encontrar las vistas
        map = view.findViewById(R.id.mapView);
        // OSM API
        osmApi = new OsmApi(map, getContext());
        colectivos = new Colectivos(map, getContext());
        recorridoClass = new Recorrido(recorrido, idLinea, map, getContext());
        // marker necesario para trackear
        marker1 = colectivos.setUpMarker(idLinea);
        // Configuración de la base de datos remota
        osmApi.setUpMap(getContext());
        recorridoClass.insertarRecorrido();
        handler = new Handler();
        if(idColectivo!=null){
            runnable = new Runnable() {
                @Override
                public void run(){

                    buscarPersona("https://dadaproductora.com.ar/web_services/buscar_ubicacion.php?idColectivo= "+idColectivo);
                    colectivos.putMarkerInMap(marker1);
                    colectivos.updateMarker(marker1);
                    handler.postDelayed(this, INTERVALO_ACTUALIZACION);
                }
            };
        }
        return view;
    }

    public void buscarPersona(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        boolean found = false;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int personaId = jsonObject.getInt("idColectivo");
                            if (personaId == Integer.parseInt(idColectivo)) {
                                double latitud = jsonObject.getDouble("latitud");
                                double longitud = jsonObject.getDouble("longitud");

                                colectivos.setIdColectivo(personaId);
                                colectivos.setLatitud(latitud);
                                colectivos.setLongitud(longitud);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            Toast.makeText(getContext(), "Colectivo no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error procesando datos", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error de conexión BD", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        handler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}