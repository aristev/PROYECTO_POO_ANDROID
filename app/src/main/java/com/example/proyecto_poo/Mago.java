package com.example.proyecto_poo;

import java.util.ArrayList;

public class Mago extends Personaje {

    public Mago(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }

    @Override
    public String usarEstrategia(Equipo miEquipo, int datoExtra) {
        ArrayList<Personaje> aliados = miEquipo.getPersonajes();
        ArrayList<Personaje> aliadosHeridos = new ArrayList<>();

        // Buscar aliados vivos que no tengan la vida al máximo
        for (Personaje p : aliados) {
            if (p.estaVivo() && p.getVida() < p.getVidaMaxima()) {
                aliadosHeridos.add(p);
            }
        }

        if (aliadosHeridos.isEmpty()) {
            return getNombre() + " intenta usar HEAL, pero nadie necesita curación.";
        }

        // Selecciona un aliado aleatorio de los heridos
        int indice = (int) (Math.random() * aliadosHeridos.size());
        Personaje aliadoElegido = aliadosHeridos.get(indice);

        // Calcula el 25% de la vida MÁXIMA del mago
        int curacion = (int) (getVidaMaxima() * 0.25);
        int vidaAnterior = aliadoElegido.getVida();

        // Aplica curación sin sobrepasar el máximo
        int nuevaVida = Math.min(aliadoElegido.getVidaMaxima(), vidaAnterior + curacion);
        aliadoElegido.setVida(nuevaVida);

        int curacionReal = nuevaVida - vidaAnterior;

        return getNombre() + " usa HEAL sobre " + aliadoElegido.getNombre() + " curando " + curacionReal + " PV.";
    }
}