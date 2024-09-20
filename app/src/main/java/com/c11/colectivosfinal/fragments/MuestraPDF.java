package com.c11.colectivosfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import com.c11.colectivosfinal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MuestraPDF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuestraPDF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "nombrePDF";

    // TODO: Rename and change types of parameters
    private String nombrePDF;

    public MuestraPDF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nombrePDF Parameter 1.
     * @return A new instance of fragment EleccionLinea.
     */
    // TODO: Rename and change types and number of parameters
    public static MuestraPDF newInstance(String nombrePDF) {
        MuestraPDF fragment = new MuestraPDF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, nombrePDF);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombrePDF = getArguments().getString(ARG_PARAM1);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muestrapdf, container, false);

        PDFView pdfView = view.findViewById(R.id.pdfView);
        pdfView.fromAsset(nombrePDF).load();

        return view;
    }
}