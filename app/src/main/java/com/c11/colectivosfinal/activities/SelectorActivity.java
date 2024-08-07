package com.c11.colectivosfinal.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.logica.LineaColectivos;

import java.util.ArrayList;
import java.util.List;

public class SelectorActivity extends AppCompatActivity {



    List<LineaColectivos> lineaColectivos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_activity);
        Button button_colectivo1 = findViewById(R.id.btn_colectivo1);
        Button button_colectivo2 = findViewById(R.id.btn_colectivo2);
        button_colectivo1 = findViewById(R.id.btn_colectivo1);
        button_colectivo2 = findViewById(R.id.btn_colectivo2);

        /* Buscamos todos los  colectivos interurbanos */
        button_colectivo1.setOnClickListener(view -> queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idLinea=1", lineaColectivos));

    }




    /* La idea de este void es agarrar los colectivos de N linea mediante la tabla Linea-Colectivo */
    public void queryLineaColectivo(String URL, List<LineaColectivos> lineaColectivos) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    try {
                        lineaColectivos.clear(); // Limpiar la lista antes de añadir nuevos elementos
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            int idLinea = jsonObject.getInt("idLinea");
                            int idColectivo = jsonObject.getInt("idColectivo");

                            lineaColectivos.add(new LineaColectivos(idLinea, idColectivo));
                        }

                        // Mostrar Toast después de que la lista haya sido actualizada
                        if (!lineaColectivos.isEmpty()) {
                            Toast.makeText(SelectorActivity.this,
                                    "ID Colectivo: " + lineaColectivos.get(0).getIdColectivo(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SelectorActivity.this,
                                    "No se encontraron colectivos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // Captura de errores JSON
                        Toast.makeText(SelectorActivity.this,
                                "Error procesando los datos.",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        // Captura de otros errores
                        Toast.makeText(SelectorActivity.this,
                                "No se pudo acceder a la base de datos.",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Manejo de errores de red
                    Toast.makeText(SelectorActivity.this,
                            "Error de conexión BD.",
                            Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(SelectorActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

}