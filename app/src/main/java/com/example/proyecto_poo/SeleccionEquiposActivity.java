package com.example.proyecto_poo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionEquiposActivity extends AppCompatActivity {

    private EditText etNombreJugador;

    // Equipo A
    private Spinner spA1, spA2, spA3, spA4;
    private EditText etA1, etA2, etA3, etA4;
    private ImageView ivIconoA1, ivIconoA2, ivIconoA3, ivIconoA4;
    private Button btnConfirmarA;

    // Equipo B
    private Spinner spB1, spB2, spB3, spB4;
    private EditText etB1, etB2, etB3, etB4;
    private ImageView ivIconoB1, ivIconoB2, ivIconoB3, ivIconoB4;
    private Button btnConfirmarB;

    private Button btnIniciarCombate;

    private Equipo equipoA;
    private Equipo equipoB;

    private boolean equipoAConfirmado = false;
    private boolean equipoBConfirmado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_equipos);

        etNombreJugador = findViewById(R.id.etNombreJugador);

        // ================= EQUIPO A =================

        spA1 = findViewById(R.id.spA1);
        spA2 = findViewById(R.id.spA2);
        spA3 = findViewById(R.id.spA3);
        spA4 = findViewById(R.id.spA4);

        etA1 = findViewById(R.id.etA1);
        etA2 = findViewById(R.id.etA2);
        etA3 = findViewById(R.id.etA3);
        etA4 = findViewById(R.id.etA4);

        ivIconoA1 = findViewById(R.id.ivIconoA1);
        ivIconoA2 = findViewById(R.id.ivIconoA2);
        ivIconoA3 = findViewById(R.id.ivIconoA3);
        ivIconoA4 = findViewById(R.id.ivIconoA4);

        btnConfirmarA = findViewById(R.id.btnConfirmarA);

        // ================= EQUIPO B =================

        spB1 = findViewById(R.id.spB1);
        spB2 = findViewById(R.id.spB2);
        spB3 = findViewById(R.id.spB3);
        spB4 = findViewById(R.id.spB4);

        etB1 = findViewById(R.id.etB1);
        etB2 = findViewById(R.id.etB2);
        etB3 = findViewById(R.id.etB3);
        etB4 = findViewById(R.id.etB4);

        ivIconoB1 = findViewById(R.id.ivIconoB1);
        ivIconoB2 = findViewById(R.id.ivIconoB2);
        ivIconoB3 = findViewById(R.id.ivIconoB3);
        ivIconoB4 = findViewById(R.id.ivIconoB4);

        btnConfirmarB = findViewById(R.id.btnConfirmarB);

        // Botón iniciar combate
        btnIniciarCombate = findViewById(R.id.btnIniciarCombate);

        // Configurar los Spinner y sus imágenes
        configurarSpinners();

        // ================= CONFIRMAR EQUIPO A =================

        btnConfirmarA.setOnClickListener(v -> {

            Equipo creado = validarYCrearEquipo(
                    spA1, spA2, spA3, spA4,
                    etA1, etA2, etA3, etA4,
                    "Equipo A"
            );

            if (creado != null) {

                equipoA = creado;
                equipoAConfirmado = true;

                btnConfirmarA.setText("EQUIPO A CONFIRMADO ✓");
                btnConfirmarA.setEnabled(false);
                btnConfirmarA.setAlpha(0.6f);

                Toast.makeText(
                        this,
                        "¡Equipo A registrado con éxito!",
                        Toast.LENGTH_SHORT
                ).show();

                verificarAmbosConfirmados();
            }
        });

        // ================= CONFIRMAR EQUIPO B =================

        btnConfirmarB.setOnClickListener(v -> {

            Equipo creado = validarYCrearEquipo(
                    spB1, spB2, spB3, spB4,
                    etB1, etB2, etB3, etB4,
                    "Equipo B"
            );

            if (creado != null) {

                equipoB = creado;
                equipoBConfirmado = true;

                btnConfirmarB.setText("EQUIPO B CONFIRMADO ✓");
                btnConfirmarB.setEnabled(false);
                btnConfirmarB.setAlpha(0.6f);

                Toast.makeText(
                        this,
                        "¡Equipo B registrado con éxito!",
                        Toast.LENGTH_SHORT
                ).show();

                verificarAmbosConfirmados();
            }
        });

        // ================= INICIAR COMBATE =================

        if (btnIniciarCombate != null) {

            btnIniciarCombate.setOnClickListener(v -> {

                Intent intent = new Intent(
                        SeleccionEquiposActivity.this,
                        activity_combate.class
                );

                intent.putExtra("OBJ_EQUIPO_A", equipoA);
                intent.putExtra("OBJ_EQUIPO_B", equipoB);

                String jugador = etNombreJugador.getText().toString().trim();

                if (jugador.isEmpty()) {
                    jugador = "PLAYER 1";
                }

                intent.putExtra("NOMBRE_JUGADOR", jugador);

                startActivity(intent);
            });
        }
    }

    // =========================================================
    // CONFIGURACIÓN DE LOS SPINNER
    // =========================================================

    private void configurarSpinners() {

        // "Mistico" sin tilde por compatibilidad con la fuente retro
        String[] opcionesRoles = {
                "Guerrero",
                "Mago",
                "Mistico"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item_custom,
                opcionesRoles
        );

        adapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item_custom
        );

        // Equipo A = ROJO
        configurarSpinnerConIcono(
                spA1,
                ivIconoA1,
                adapter,
                true
        );

        configurarSpinnerConIcono(
                spA2,
                ivIconoA2,
                adapter,
                true
        );

        configurarSpinnerConIcono(
                spA3,
                ivIconoA3,
                adapter,
                true
        );

        configurarSpinnerConIcono(
                spA4,
                ivIconoA4,
                adapter,
                true
        );

        // Equipo B = AZUL
        configurarSpinnerConIcono(
                spB1,
                ivIconoB1,
                adapter,
                false
        );

        configurarSpinnerConIcono(
                spB2,
                ivIconoB2,
                adapter,
                false
        );

        configurarSpinnerConIcono(
                spB3,
                ivIconoB3,
                adapter,
                false
        );

        configurarSpinnerConIcono(
                spB4,
                ivIconoB4,
                adapter,
                false
        );
    }

    // =========================================================
    // CONECTAR UN SPINNER CON SU IMAGEVIEW
    // =========================================================

    private void configurarSpinnerConIcono(
            Spinner spinner,
            ImageView icono,
            ArrayAdapter<String> adapter,
            boolean equipoRojo) {

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        String rol = parent
                                .getItemAtPosition(position)
                                .toString();

                        actualizarIcono(
                                icono,
                                rol,
                                equipoRojo
                        );
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {

                        // No se realiza ninguna acción
                    }
                }
        );
    }

    // =========================================================
    // CAMBIAR LA IMAGEN DEPENDIENDO DEL PERSONAJE Y EQUIPO
    // =========================================================

    private void actualizarIcono(
            ImageView icono,
            String rol,
            boolean equipoRojo) {

        // ================= EQUIPO A / ROJO =================

        if (equipoRojo) {

            switch (rol) {

                case "Guerrero":
                    icono.setImageResource(
                            R.drawable.ic_guerrero_rojo
                    );
                    break;

                case "Mago":
                    icono.setImageResource(
                            R.drawable.ic_mago_rojo
                    );
                    break;

                case "Mistico":
                    icono.setImageResource(
                            R.drawable.ic_mistico_rojo
                    );
                    break;
            }

        }

        // ================= EQUIPO B / AZUL =================

        else {

            switch (rol) {

                case "Guerrero":
                    icono.setImageResource(
                            R.drawable.ic_guerrero_azul
                    );
                    break;

                case "Mago":
                    icono.setImageResource(
                            R.drawable.ic_mago_azul
                    );
                    break;

                case "Mistico":
                    icono.setImageResource(
                            R.drawable.ic_mistico_azul
                    );
                    break;
            }
        }
    }

    // =========================================================
    // VALIDAR Y CREAR EQUIPO
    // =========================================================

    private Equipo validarYCrearEquipo(
            Spinner s1,
            Spinner s2,
            Spinner s3,
            Spinner s4,
            EditText e1,
            EditText e2,
            EditText e3,
            EditText e4,
            String nombreEquipo) {

        String n1 = e1.getText().toString().trim();
        String n2 = e2.getText().toString().trim();
        String n3 = e3.getText().toString().trim();
        String n4 = e4.getText().toString().trim();

        if (n1.isEmpty()
                || n2.isEmpty()
                || n3.isEmpty()
                || n4.isEmpty()) {

            Toast.makeText(
                    this,
                    "Debes ingresar los nombres de los 4 personajes de "
                            + nombreEquipo,
                    Toast.LENGTH_SHORT
            ).show();

            return null;
        }

        Spinner[] spinners = {
                s1,
                s2,
                s3,
                s4
        };

        String[] nombres = {
                n1,
                n2,
                n3,
                n4
        };

        int countGuerreros = 0;
        int countMagos = 0;
        int countMisticos = 0;

        // Contar cuántos personajes hay de cada tipo
        for (Spinner sp : spinners) {

            String rol = sp
                    .getSelectedItem()
                    .toString();

            if (rol.equalsIgnoreCase("Guerrero")) {

                countGuerreros++;

            } else if (rol.equalsIgnoreCase("Mago")) {

                countMagos++;

            } else if (
                    rol.equalsIgnoreCase("Mistico")
                            || rol.equalsIgnoreCase("Místico")) {

                countMisticos++;
            }
        }

        // Cada equipo necesita:
        // 2 Guerreros
        // 1 Mago
        // 1 Mistico

        if (countGuerreros != 2
                || countMagos != 1
                || countMisticos != 1) {

            Toast.makeText(
                    this,
                    "En " + nombreEquipo
                            + " requieres: 2 Guerreros, 1 Mistico y 1 Mago.",
                    Toast.LENGTH_LONG
            ).show();

            return null;
        }

        Equipo eq = new Equipo(nombreEquipo);

        // Crear los personajes
        for (int i = 0; i < 4; i++) {

            String rol = spinners[i]
                    .getSelectedItem()
                    .toString();

            String nom = nombres[i];

            Personaje p;

            if (rol.equalsIgnoreCase("Guerrero")) {

                p = new Guerrero(
                        nom,
                        120,
                        25,
                        10
                );

            } else if (rol.equalsIgnoreCase("Mago")) {

                p = new Mago(
                        nom,
                        80,
                        35,
                        5
                );

            } else {

                p = new Mistico(
                        nom,
                        90,
                        20,
                        8
                );
            }

            eq.agregarPersonaje(p);
        }

        return eq;
    }

    // =========================================================
    // VERIFICAR SI AMBOS EQUIPOS YA FUERON CONFIRMADOS
    // =========================================================

    private void verificarAmbosConfirmados() {

        if (equipoAConfirmado && equipoBConfirmado) {

            if (btnIniciarCombate != null) {

                btnIniciarCombate.setVisibility(
                        View.VISIBLE
                );
            }

            Toast.makeText(
                    this,
                    "¡Equipos listos! Presiona '¡A LA BATALLA!'",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}