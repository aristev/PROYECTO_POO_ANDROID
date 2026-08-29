package com.example.proyecto_poo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnEmpezarBatalla;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicia la música de fondo
        SoundManager.iniciarMusica(this);

        btnEmpezarBatalla = findViewById(R.id.btnEmpezarBatalla);
        btnSalir = findViewById(R.id.btnSalir);

        if (btnEmpezarBatalla != null) {
            btnEmpezarBatalla.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SeleccionEquiposActivity.class);
                startActivity(intent);
            });
        }

        if (btnSalir != null) {
            btnSalir.setOnClickListener(v -> mostrarDialogoSalir());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundManager.iniciarMusica(this);
    }

    private void mostrarDialogoSalir() {
        new AlertDialog.Builder(this)
                .setTitle("Salir del Juego")
                .setMessage("¿Estás seguro de que deseas salir?")
                .setPositiveButton("SÍ, SALIR", (dialog, which) -> {
                    SoundManager.detenerMusica(); // Apaga la música por completo
                    finishAffinity();
                })
                .setNegativeButton("CANCELAR", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onBackPressed() {
        mostrarDialogoSalir();
    }
}