package com.c11.colectivosfinal.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.c11.colectivosfinal.BuildConfig;
import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.logica.Colectivos;
import com.c11.colectivosfinal.logica.OsmApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DesplegarMapa extends AppCompatActivity {

    /* Necesario por osm */
    private MapView map;
    private OsmApi osmApi;
    private Colectivos colectivos;
    private Marker marker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplegar_mapa);

        // Inicializar con la app id para evitar baneo de osm
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        // Encontrar el mapView en el layout
        map = findViewById(R.id.mapView);

        /* Boton de la ui */
        Button btn_actualizar = findViewById(R.id.btn_actualizarMapa);

        // OSM API
        osmApi = new OsmApi(map, this);
        colectivos = new Colectivos(map, this);

        // marker necesario para trackear

        marker1 = colectivos.setUpMarker();
        // Configuración de la base de datos remota
        osmApi.setUpMap(this);
    }

    public void boton(View v){
        buscarPersona("https://dadaproductora.com.ar/web_services/buscar_ubicacion.php?idColectivo=1");
        colectivos.putMarkerInMap(marker1);
        colectivos.updateMarker(marker1);
    }


    private void ejecutarServicio(String URL, OsmApi osmApi) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(DesplegarMapa.this, "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(DesplegarMapa.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idColectivo", String.valueOf(colectivos.getIdColectivo()));
                parametros.put("latitud", String.valueOf(colectivos.getLatitud()));
                parametros.put("longitud", String.valueOf(colectivos.getLongitud()));
                return parametros;
            }
        };
        // Añadir la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                            if (personaId == 1) {
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
                            Toast.makeText(getApplicationContext(), "Colectivo no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error procesando datos", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error desconocido", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error de conexión BD", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}