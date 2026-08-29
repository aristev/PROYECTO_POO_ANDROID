package com.example.proyecto_poo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_combate extends AppCompatActivity {

    private Equipo equipoA;
    private Equipo equipoB;

    private int rondaActual = 1;
    private final int MAX_RONDAS = 15;
    private boolean turnoEquipoA = true;
    private int indicePersonajeA = 0;
    private int indicePersonajeB = 0;

    // Vistas principales
    private TextView tvRondaActual, tvTurnoInfo, tvLog, tvUsuarioLogueado;
    private ScrollView svLog;
    private Button btnAtacar, btnEstrategia;

    // Vistas Equipo A
    private TextView[] tvHpA = new TextView[4];
    private TextView[] tvNameA = new TextView[4];
    private ProgressBar[] pbA = new ProgressBar[4];
    private ImageView[] ivSpriteA = new ImageView[4]; // Sprites Equipo A

    // Vistas Equipo B
    private TextView[] tvHpB = new TextView[4];
    private TextView[] tvNameB = new TextView[4];
    private ProgressBar[] pbB = new ProgressBar[4];
    private ImageView[] ivSpriteB = new ImageView[4]; // Sprites Equipo B

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combate);

        // Recibir los datos enviados por Intent
        equipoA = (Equipo) getIntent().getSerializableExtra("OBJ_EQUIPO_A");
        equipoB = (Equipo) getIntent().getSerializableExtra("OBJ_EQUIPO_B");
        String usuario = getIntent().getStringExtra("NOMBRE_JUGADOR");

        // Equipos de respaldo
        if (equipoA == null || equipoB == null) {
            equipoA = new Equipo("Equipo A");
            equipoA.agregarPersonaje(new Guerrero("G1_A", 120, 25, 10));
            equipoA.agregarPersonaje(new Guerrero("G2_A", 120, 25, 10));
            equipoA.agregarPersonaje(new Mago("M_A", 80, 35, 5));
            equipoA.agregarPersonaje(new Mistico("Mis_A", 90, 20, 8));

            equipoB = new Equipo("Equipo B");
            equipoB.agregarPersonaje(new Guerrero("G1_B", 120, 25, 10));
            equipoB.agregarPersonaje(new Guerrero("G2_B", 120, 25, 10));
            equipoB.agregarPersonaje(new Mago("M_B", 80, 35, 5));
            equipoB.agregarPersonaje(new Mistico("Mis_B", 90, 20, 8));
        }

        vincularVistas();

        if (tvUsuarioLogueado != null && usuario != null) {
            tvUsuarioLogueado.setText("JUGADOR: " + usuario.toUpperCase());
        }

        inicializarBarrasYDatos();
        actualizarInterfaz();

        agregarLog(">>> ¡LA BATALLA HA COMENZADO!");
        agregarLog(">>> " + equipoA.getNombre() + " VS " + equipoB.getNombre());

        btnAtacar.setOnClickListener(v -> ejecutarAtaque());
        btnEstrategia.setOnClickListener(v -> ejecutarEstrategia());
    }

    private void vincularVistas() {
        tvRondaActual = findViewById(R.id.tvRondaActual);
        tvTurnoInfo = findViewById(R.id.tvTurnoInfo);
        tvLog = findViewById(R.id.tvLog);
        svLog = findViewById(R.id.svLog);
        tvUsuarioLogueado = findViewById(R.id.tvUsuarioLogueado);

        btnAtacar = findViewById(R.id.btnAtacar);
        btnEstrategia = findViewById(R.id.btnEstrategia);

        // Equipo A
        tvHpA[0] = findViewById(R.id.tvHpA0);
        tvHpA[1] = findViewById(R.id.tvHpA1);
        tvHpA[2] = findViewById(R.id.tvHpA2);
        tvHpA[3] = findViewById(R.id.tvHpA3);

        tvNameA[0] = findViewById(R.id.tvNameA0);
        tvNameA[1] = findViewById(R.id.tvNameA1);
        tvNameA[2] = findViewById(R.id.tvNameA2);
        tvNameA[3] = findViewById(R.id.tvNameA3);

        pbA[0] = findViewById(R.id.pbA0);
        pbA[1] = findViewById(R.id.pbA1);
        pbA[2] = findViewById(R.id.pbA2);
        pbA[3] = findViewById(R.id.pbA3);

        ivSpriteA[0] = findViewById(R.id.ivSpriteA0);
        ivSpriteA[1] = findViewById(R.id.ivSpriteA1);
        ivSpriteA[2] = findViewById(R.id.ivSpriteA2);
        ivSpriteA[3] = findViewById(R.id.ivSpriteA3);

        // Equipo B
        tvHpB[0] = findViewById(R.id.tvHpB0);
        tvHpB[1] = findViewById(R.id.tvHpB1);
        tvHpB[2] = findViewById(R.id.tvHpB2);
        tvHpB[3] = findViewById(R.id.tvHpB3);

        tvNameB[0] = findViewById(R.id.tvNameB0);
        tvNameB[1] = findViewById(R.id.tvNameB1);
        tvNameB[2] = findViewById(R.id.tvNameB2);
        tvNameB[3] = findViewById(R.id.tvNameB3);

        pbB[0] = findViewById(R.id.pbB0);
        pbB[1] = findViewById(R.id.pbB1);
        pbB[2] = findViewById(R.id.pbB2);
        pbB[3] = findViewById(R.id.pbB3);

        ivSpriteB[0] = findViewById(R.id.ivSpriteB0);
        ivSpriteB[1] = findViewById(R.id.ivSpriteB1);
        ivSpriteB[2] = findViewById(R.id.ivSpriteB2);
        ivSpriteB[3] = findViewById(R.id.ivSpriteB3);
    }

    private void inicializarBarrasYDatos() {
        for (int i = 0; i < 4; i++) {
            // Inicializar Equipo A
            if (i < equipoA.getPersonajes().size()) {
                Personaje pA = equipoA.getPersonajes().get(i);
                if (tvNameA[i] != null) tvNameA[i].setText(pA.getNombre());
                if (pbA[i] != null) {
                    pbA[i].setMax(pA.getVidaMaxima());
                    pbA[i].setProgress(pA.getVida());
                }
                if (ivSpriteA[i] != null) {
                    asignarIconoPersonaje(ivSpriteA[i], pA, true);
                }
            }

            // Inicializar Equipo B
            if (i < equipoB.getPersonajes().size()) {
                Personaje pB = equipoB.getPersonajes().get(i);
                if (tvNameB[i] != null) tvNameB[i].setText(pB.getNombre());
                if (pbB[i] != null) {
                    pbB[i].setMax(pB.getVidaMaxima());
                    pbB[i].setProgress(pB.getVida());
                }
                if (ivSpriteB[i] != null) {
                    asignarIconoPersonaje(ivSpriteB[i], pB, false);
                }
            }
        }
    }

    // =========================================================
    // NUEVO MÉTODO PARA ASIGNAR IMÁGENES SEGÚN CLASE Y EQUIPO
    // =========================================================
    private void asignarIconoPersonaje(ImageView iv, Personaje p, boolean esEquipoA) {
        if (iv == null || p == null) return;

        if (esEquipoA) {
            // Imágenes Rojas para Equipo A
            if (p instanceof Guerrero) {
                iv.setImageResource(R.drawable.ic_guerrero_rojo);
            } else if (p instanceof Mago) {
                iv.setImageResource(R.drawable.ic_mago_rojo);
            } else if (p instanceof Mistico) {
                iv.setImageResource(R.drawable.ic_mistico_rojo);
            }
        } else {
            // Imágenes Azules para Equipo B
            if (p instanceof Guerrero) {
                iv.setImageResource(R.drawable.ic_guerrero_azul);
            } else if (p instanceof Mago) {
                iv.setImageResource(R.drawable.ic_mago_azul);
            } else if (p instanceof Mistico) {
                iv.setImageResource(R.drawable.ic_mistico_azul);
            }
        }
    }

    private Personaje getAtacanteActual() {
        return turnoEquipoA ? equipoA.getSiguienteVivo(indicePersonajeA) : equipoB.getSiguienteVivo(indicePersonajeB);
    }

    private Personaje getDefensorObjetivo() {
        return turnoEquipoA ? equipoB.getSiguienteVivo(0) : equipoA.getSiguienteVivo(0);
    }

    private void ejecutarAtaque() {
        Personaje atacante = getAtacanteActual();
        Personaje defensor = getDefensorObjetivo();

        if (atacante == null || defensor == null) {
            verificarFinDelJuego();
            return;
        }

        int danioCausado = atacante.realizarAtaque(defensor);
        agregarLog("⚔ " + atacante.getNombre() + " (" + atacante.getClass().getSimpleName() + ") infligió " + danioCausado + " de daño a " + defensor.getNombre() + ".");

        if (!defensor.estaVivo()) {
            agregarLog("☠ ¡" + defensor.getNombre() + " ha caído en combate!");
        }

        pasarTurno();
    }

    private void ejecutarEstrategia() {
        Personaje atacante = getAtacanteActual();
        if (atacante == null) return;

        Equipo miEquipo = turnoEquipoA ? equipoA : equipoB;

        if (atacante instanceof Mistico) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Predicción Mística (1 - 6)");
            builder.setMessage("Ingresa el número que crees que saldrá en el dado:");

            final EditText input = new EditText(this);
            input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Lanzar", (dialog, which) -> {
                String val = input.getText().toString().trim();
                int pred = val.isEmpty() ? 1 : Integer.parseInt(val);
                if (pred < 1 || pred > 6) pred = 1;

                String resultado = atacante.usarEstrategia(miEquipo, pred);
                agregarLog("🔮 " + resultado);
                pasarTurno();
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
            builder.show();
        } else {
            String resultado = atacante.usarEstrategia(miEquipo, 0);
            agregarLog("⚡ " + resultado);
            pasarTurno();
        }
    }

    private void pasarTurno() {
        actualizarInterfaz();

        if (verificarFinDelJuego()) {
            return;
        }

        if (turnoEquipoA) {
            indicePersonajeA = (indicePersonajeA + 1) % 4;
            turnoEquipoA = false;
        } else {
            indicePersonajeB = (indicePersonajeB + 1) % 4;
            turnoEquipoA = true;
            rondaActual++;
        }

        if (rondaActual > MAX_RONDAS) {
            declararGanadorPorPuntos();
            return;
        }

        actualizarInterfaz();
    }

    private boolean verificarFinDelJuego() {
        if (equipoA.estaDerrotado()) {
            finalizarBatalla("¡VICTORIA TOTAL DEL " + equipoB.getNombre().toUpperCase() + "!");
            return true;
        } else if (equipoB.estaDerrotado()) {
            finalizarBatalla("¡VICTORIA TOTAL DEL " + equipoA.getNombre().toUpperCase() + "!");
            return true;
        }
        return false;
    }

    private void declararGanadorPorPuntos() {
        int hpA = equipoA.vidaTotal();
        int hpB = equipoB.vidaTotal();

        if (hpA > hpB) {
            finalizarBatalla("¡Fin de 15 rondas! Gana " + equipoA.getNombre() + " (" + hpA + " PV vs " + hpB + " PV).");
        } else if (hpB > hpA) {
            finalizarBatalla("¡Fin de 15 rondas! Gana " + equipoB.getNombre() + " (" + hpB + " PV vs " + hpA + " PV).");
        } else {
            finalizarBatalla("¡Fin de 15 rondas! Empate a " + hpA + " PV.");
        }
    }

    private void finalizarBatalla(String mensaje) {
        agregarLog("\n==================================");
        agregarLog(">>> " + mensaje);
        agregarLog("==================================");

        btnAtacar.setEnabled(false);
        btnEstrategia.setEnabled(false);
        btnAtacar.setAlpha(0.5f);
        btnEstrategia.setAlpha(0.5f);

        // Diálogo emergente de Fin de Partida
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡FIN DEL COMBATE!");
        builder.setMessage(mensaje + "\n\n¿Deseas volver al Menú Principal?");
        builder.setCancelable(false);

        builder.setPositiveButton("VOLVER AL MENÚ", (dialog, which) -> {
            Intent intent = new Intent(activity_combate.this, MainActivity.class);
            // Limpia la pila de actividades para reiniciar el juego limpiamente
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        builder.show();
    }

    private void actualizarInterfaz() {
        if (tvRondaActual != null) {
            tvRondaActual.setText("RONDA " + Math.min(rondaActual, MAX_RONDAS) + " / " + MAX_RONDAS);
        }

        Personaje atacante = getAtacanteActual();
        if (tvTurnoInfo != null && atacante != null) {
            String bando = turnoEquipoA ? "EQUIPO A" : "EQUIPO B";
            tvTurnoInfo.setText("TURNO: " + atacante.getNombre().toUpperCase() + " (" + bando + ")");
        }

        // Actualizar Equipo A
        for (int i = 0; i < 4; i++) {
            if (i < equipoA.getPersonajes().size()) {
                Personaje p = equipoA.getPersonajes().get(i);
                if (tvHpA[i] != null) tvHpA[i].setText(p.getVida() + " HP");
                if (pbA[i] != null) {
                    pbA[i].setProgress(p.getVida());
                    actualizarColorBarra(pbA[i], p.getVida(), p.getVidaMaxima(), true);
                }
            }
        }

        // Actualizar Equipo B
        for (int i = 0; i < 4; i++) {
            if (i < equipoB.getPersonajes().size()) {
                Personaje p = equipoB.getPersonajes().get(i);
                if (tvHpB[i] != null) tvHpB[i].setText(p.getVida() + " HP");
                if (pbB[i] != null) {
                    pbB[i].setProgress(p.getVida());
                    actualizarColorBarra(pbB[i], p.getVida(), p.getVidaMaxima(), false);
                }
            }
        }
    }

    // Regla de color: Verde/Azul (>50%), Naranja (<=50%), Rojo (<=20%)
    private void actualizarColorBarra(ProgressBar pb, int vida, int vidaMax, boolean esEquipoA) {
        if (pb == null || vidaMax <= 0) return;

        double porcentaje = ((double) vida / vidaMax) * 100.0;
        int color;

        if (porcentaje <= 20.0) {
            color = Color.parseColor("#FF2222"); // Rojo
        } else if (porcentaje <= 50.0) {
            color = Color.parseColor("#FF9800"); // Naranja
        } else {
            color = esEquipoA ? Color.parseColor("#4CAF50") : Color.parseColor("#5599FF"); // Verde para A / Azul para B
        }

        pb.setProgressTintList(ColorStateList.valueOf(color));
    }

    private void agregarLog(String texto) {
        if (tvLog != null) {
            tvLog.append(texto + "\n");
            if (svLog != null) {
                svLog.post(() -> svLog.fullScroll(ScrollView.FOCUS_DOWN));
            }
        }
    }
}