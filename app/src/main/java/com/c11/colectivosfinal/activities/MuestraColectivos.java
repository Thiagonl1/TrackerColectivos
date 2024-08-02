package com.c11.colectivosfinal.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.databinding.ActivityMuestraColectivosBinding;
import com.c11.colectivosfinal.fragments.HomeFragment;
import com.c11.colectivosfinal.fragments.SettingsFragment;
import com.c11.colectivosfinal.fragments.UbicacionFragment;

public class MuestraColectivos extends AppCompatActivity {

    ActivityMuestraColectivosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMuestraColectivosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.item_ajustes:
                    replaceFragment(new SettingsFragment());
                    return true;
                case R.id.item_ubicacion:
                    replaceFragment(new UbicacionFragment());
                    return true;
                default:
                    return false;
            }
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}