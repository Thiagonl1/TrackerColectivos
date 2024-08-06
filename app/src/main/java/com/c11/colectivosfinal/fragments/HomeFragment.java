package com.c11.colectivosfinal.fragments;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.logica.LineaColectivos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<LineaColectivos> lineaColectivos = new ArrayList<>();
        Button button_colectivo1 = view.findViewById(R.id.btn_colectivoInter);
        Button button_colectivo2 = view.findViewById(R.id.btn_colectivoUrb);


        button_colectivo1.setOnClickListener(v -> {
            queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idLinea=1", lineaColectivos);
        });
        button_colectivo2.setOnClickListener(v -> queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idLinea=2", lineaColectivos));

    }


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
                            Toast.makeText(getContext(),
                                    "ID Colectivo: " + lineaColectivos.get(0).getIdColectivo(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "No se encontraron colectivos.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // Captura de errores JSON
                        Toast.makeText(getContext(),
                                "Error procesando los datos.",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        // Captura de otros errores
                        Toast.makeText(getContext(),
                                "No se pudo acceder a la base de datos.",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Manejo de errores de red
                    Toast.makeText(getContext(),
                            "Error de conexión BD.",
                            Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

}