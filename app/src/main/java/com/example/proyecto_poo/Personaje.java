package com.example.proyecto_poo;

import java.io.Serializable;

// Clase padre, ahora implementa Serializable para poder pasarse entre pantallas
public abstract class Personaje implements Serializable {
    private String nombre;
    protected int vidaMaxima; // Añadimos esto para las barras de progreso
    protected int vida;
    protected int ataque;
    protected int defensa;

    // Constructor actualizado para guardar la vida máxima
    public Personaje(String nombre, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.vidaMaxima = vida; // La vida inicial es la máxima
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    // Retorna el daño neto realizado para escribirlo en el Log de la interfaz
    public int recibirAtaque(int dano) {
        int danoReal = dano - this.defensa;
        if (danoReal < 0) {
            danoReal = 0;
        }

        this.vida -= danoReal;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return danoReal; // Retornamos el daño para la interfaz
    }

    // Método para atacar, modificado para la interfaz
    public int realizarAtaque(Personaje contrario) {
        // En Android, la estrategia se activa con un botón, no automáticamente aquí.
        // Este método ahora solo ejecuta el golpe y retorna el daño.
        return contrario.recibirAtaque(this.ataque);
    }

    // Estrategia propia de cada personaje
    public abstract String usarEstrategia(Equipo miEquipo, int datoExtra);

    // Getters y Setters actualizados
    public String getNombre() {
        return nombre;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    } // Nuevo getter

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }
}