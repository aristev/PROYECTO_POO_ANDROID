package com.example.proyecto_poo; // Reemplaza con tu paquete real

import java.io.Serializable;
import java.util.ArrayList;

// Implementa Serializable para pasarse entre pantallas
public class Equipo implements Serializable {
    private String nombre;
    private ArrayList<Personaje> personajes;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.personajes = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void agregarPersonaje(Personaje personaje) {
        if (personajes.size() < 4) {
            personajes.add(personaje);
        }
    }

    // Retorna el siguiente personaje vivo empezando desde un índice, cíclicamente
    public Personaje getSiguienteVivo(int indiceInicial) {
        if (estaDerrotado()) return null;

        for (int i = 0; i < 4; i++) {
            int indexActual = (indiceInicial + i) % 4;
            Personaje p = personajes.get(indexActual);
            if (p.estaVivo()) {
                return p;
            }
        }
        return null;
    }

    public boolean estaDerrotado() {
        for (Personaje p : personajes) {
            if (p.estaVivo()) {
                return false;
            }
        }
        return true;
    }

    public int vidaTotal() {
        int suma = 0;
        for (Personaje p : personajes) {
            suma += p.getVida();
        }
        return suma;
    }
}