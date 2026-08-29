package com.example.proyecto_poo;
public class Mistico extends Personaje {

    public Mistico(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
    }

    @Override
    public String usarEstrategia(Equipo miEquipo, int numeroPredecido) {
        // La estrategia del Místico usa el dato extra (el número predecido por el usuario en el diálogo)
        int dado = (int) (Math.random() * 6) + 1; // Dado del 1 al 6

        if (dado == numeroPredecido) {
            // Acierta: Suma el ataque de todo su equipo vivo al suyo
            int ataqueExtra = 0;
            for (Personaje p : miEquipo.getPersonajes()) {
                if (p.estaVivo() && p != this) {
                    ataqueExtra += p.getAtaque();
                }
            }
            this.ataque += ataqueExtra;
            return getNombre() + " predijo correctamente el " + dado + ". ¡Suma el ataque aliado! Nuevo ataque: " + this.ataque + ".";
        } else {
            // Falla: No pasa nada
            return getNombre() + " predijo " + numeroPredecido + " pero salió " + dado + ". La conexión mística falló.";
        }
    }
}