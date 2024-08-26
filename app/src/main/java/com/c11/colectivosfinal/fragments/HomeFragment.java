package com.c11.colectivosfinal.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private final List<LineaColectivos> lineasColectivoBundle = new ArrayList<>();
    private  final List<String> lineasColectivo = new ArrayList<>(Arrays.asList("Interurbano", "Urbano"));
    private final List<String> recorridoColectivoUrb = new ArrayList<>(Arrays.asList("San Clemente: Terminal - Puerto", "San Clemente: Puerto - Terminal"));
    private final List<String> recorridoColectivoIntUrb = new ArrayList<>(Arrays.asList("San Clemente - Mar de Ajo", "Mar de Ajo - San Clemente"));


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }

        ArrayAdapter<String> adapterLineas = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lineasColectivo);
        ListView listView = view.findViewById(R.id.lista);
        listView.setAdapter(adapterLineas);

        listView.setOnItemClickListener((adapterView, view1, position, id) -> {

            switch(position){
                case 0:
                    if(Objects.requireNonNull(adapterLineas.getItem(0)).equals("Interurbano")){
                        adapterLineas.clear();
                        adapterLineas.addAll(recorridoColectivoIntUrb);
                    }else{
                        // Mandar el idColectivo correspondiente y cambiar de fragment
                        if(Objects.requireNonNull(adapterLineas.getItem(0)).equals("San Clemente: Terminal - Puerto")){
                            queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idColectivo=2", lineasColectivoBundle);

                        }else{
                            // significa que es SC - MDA
                            queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idColectivo=3", lineasColectivoBundle);
                        }
                    }
                    break;
                case 1:
                    if(Objects.requireNonNull(adapterLineas.getItem(1)).equals("Urbano")){
                        adapterLineas.clear();
                        adapterLineas.addAll(recorridoColectivoUrb);
                    }else{
                        if(Objects.requireNonNull(adapterLineas.getItem(1)).equals("San Clemente: Puerto - Terminal")){
                            queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idColectivo=1", lineasColectivoBundle);

                        }else{
                            // significa que es el interurbano MDA - SAN CLEMENTE
                            queryLineaColectivo("https://dadaproductora.com.ar/web_services/buscar_idLinea.php?idColectivo=4", lineasColectivoBundle);
                        }
                        // Mandar el idColectivo correspondiente y cambiar de fragment
                    }
            }
            adapterLineas.notifyDataSetChanged();
        });

    }

    public Bundle creaBundle (String idColectivo, String recorrido, String idLinea){
        Bundle datos = new Bundle();
        datos.putString("idColectivo", idColectivo);
        datos.putString("recorrido", recorrido);
        datos.putString("idLinea", idLinea);

        return datos;
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

                            String idLinea = jsonObject.getString("idLinea");
                            int idColectivo = jsonObject.getInt("idColectivo");
                            String recorrido = jsonObject.getString("recorrido");

                            lineaColectivos.add(new LineaColectivos(idLinea, idColectivo, recorrido));
                        }

                        if (!lineaColectivos.isEmpty()) {
                            Toast.makeText(getContext(),
                                    "ID Linea: " + lineaColectivos.get(0).getIdLinea(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "No se encontraron colectivos.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Bundle bundle = creaBundle(String.valueOf(lineaColectivos.get(0).getIdColectivo()),  lineaColectivos.get(0).getRecorrido(), lineaColectivos.get(0).getIdLinea());
                        switchFragment(bundle);

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

        @SuppressLint("UseRequireInsteadOfGet") RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(jsonArrayRequest);
    }

    public void switchFragment(Bundle bundle){
        @SuppressLint("UseRequireInsteadOfGet") FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        String tag = UbicacionFragment.class.getSimpleName();
        UbicacionFragment fragmentCambio;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String recorrido = bundle.getString("recorrido");
        String idColectivo = bundle.getString("idColectivo");
        String idLinea = bundle.getString("idLinea");

        fragmentCambio = UbicacionFragment.newInstance(recorrido, idColectivo, idLinea);
        fragmentTransaction.replace(R.id.frame_layout, fragmentCambio, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_layout, fragmentCambio, tag);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }


}