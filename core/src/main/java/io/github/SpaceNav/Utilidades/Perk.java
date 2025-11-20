package io.github.SpaceNav.Utilidades;
import io.github.SpaceNav.jugador.Nave;

public abstract class Perk {

    private String nombre;

    public Perk(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Se ejecuta cuando el jugador elige el perk
    public abstract void aplicar(Nave nave);
}
