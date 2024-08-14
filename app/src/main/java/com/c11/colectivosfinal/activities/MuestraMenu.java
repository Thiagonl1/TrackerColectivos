package com.c11.colectivosfinal.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.c11.colectivosfinal.R;
import com.c11.colectivosfinal.databinding.ActivityMuestraMenuBinding;
import com.c11.colectivosfinal.fragments.HomeFragment;
import com.c11.colectivosfinal.fragments.SettingsFragment;
import com.c11.colectivosfinal.fragments.UbicacionFragment;

public class MuestraMenu extends AppCompatActivity {

    private ActivityMuestraMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMuestraMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            binding.bottomNavigationView.setSelectedItemId(R.id.item_home);
        }

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
        fragmentManager.executePendingTransactions();
        invalidateMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_prot, menu);  // Inflate the main menu
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        if (currentFragment instanceof HomeFragment) {
            // Modify menu for HomeFragment
            menu.findItem(R.id.item_home).setVisible(true);
        } else if (currentFragment instanceof SettingsFragment) {
            // Modify menu for SettingsFragment
            menu.findItem(R.id.item_ajustes).setVisible(true);
        } else if (currentFragment instanceof UbicacionFragment) {
            // Modify menu for UbicacionFragment
            menu.findItem(R.id.item_ubicacion).setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        switch (item.getItemId()) {
            case R.id.item_home:
                // Handle specific action
                return true;
            case R.id.item_ajustes:
                // Handle specific action
                return true;
            case R.id.item_ubicacion:
                // Handle specific action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}