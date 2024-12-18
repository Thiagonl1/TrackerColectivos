package com.c11.colectivosfinal.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.c11.colectivosfinal.R;

import android.content.Intent;
import android.content.pm.PackageManager;


import android.Manifest;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.c11.colectivosfinal.BuildConfig;


import org.osmdroid.config.Configuration;


public class MainActivity extends AppCompatActivity {

    /*ImageView lacosta = findViewById(R.id.imageView3);
    ImageView color = findViewById(R.id.imageView2);*/

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gradle (module : app) buildFeatures{ } tenes que poner buildConfig = true..
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MuestraMenu.class);
            startActivity(intent);
            finish();
        }, 3000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos no otorgados, no se puede desplegar el mapa.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}