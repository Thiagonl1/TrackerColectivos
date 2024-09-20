package com.c11.colectivosfinal.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.logica.LineaColectivos;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  final List<String> lineasColectivo = new ArrayList<>(Arrays.asList("Interurbano", "Urbano"));
    private final List<String> recorridoColectivoUrb = new ArrayList<>(Arrays.asList("San Clemente: Terminal - Puerto", "San Clemente: Puerto - Terminal"));
    private final List<String> recorridoColectivoIntUrb = new ArrayList<>(Arrays.asList("San Clemente - Mar de Ajo", "Mar de Ajo - San Clemente"));


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


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
                            Bundle bundle = creaBundle("SanClementepdf.pdf");
                            switchFragment(bundle);

                        }else{
                            // significa que es SC - MDA
                            Bundle bundle = creaBundle("semanaMDASC.pdf");
                            switchFragment(bundle);


                        }
                    }
                    break;
                case 1:
                    if(Objects.requireNonNull(adapterLineas.getItem(1)).equals("Urbano")){
                        adapterLineas.clear();
                        adapterLineas.addAll(recorridoColectivoUrb);
                    }else{
                        if(Objects.requireNonNull(adapterLineas.getItem(1)).equals("San Clemente: Puerto - Terminal")){
                            Bundle bundle = creaBundle("SanClementepdf.pdf");
                            switchFragment(bundle);

                        }else{
                            // significa que es el interurbano MDA - SAN CLEMENTE
                            Bundle bundle = creaBundle("semanaMDASC.pdf");
                            switchFragment(bundle);

                        }
                        // Mandar el idColectivo correspondiente y cambiar de fragment
                    }
            }
            adapterLineas.notifyDataSetChanged();
        });

        // Initialize the button using the inflated view
        return view;
    }


    public void switchFragment(Bundle bundle){
        @SuppressLint("UseRequireInsteadOfGet") FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        String tag = MuestraPDF.class.getSimpleName();
        MuestraPDF fragmentCambio;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String nombrePDF = bundle.getString("nombrePDF");

        fragmentCambio = MuestraPDF.newInstance(nombrePDF);
        fragmentTransaction.replace(R.id.frame_layouta, fragmentCambio, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public Bundle creaBundle (String nombrePDF){
        Bundle datos = new Bundle();
        datos.putString("nombrePDF", nombrePDF);
        return datos;
    }

}