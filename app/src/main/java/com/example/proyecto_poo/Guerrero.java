package com.example.proyecto_poo;

public class Guerrero extends Personaje {

    public Guerrero(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }

    @Override
    public String usarEstrategia(Equipo miEquipo, int datoExtra) {
        // La estrategia del Guerrero duplica su ataque permanentemente
        this.ataque = this.ataque * 2;
        return getNombre() + " activa BURST FURY. ¡Su ataque se duplica permanentemente a " + this.ataque + "!";
    }
}