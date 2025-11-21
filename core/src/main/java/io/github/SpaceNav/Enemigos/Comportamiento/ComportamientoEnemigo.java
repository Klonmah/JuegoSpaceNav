package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;

public interface ComportamientoEnemigo {
    void actualizar(Mobs enemigo, float delta);
    void iniciar(Mobs enemigo);
    boolean estaCompletado();
}