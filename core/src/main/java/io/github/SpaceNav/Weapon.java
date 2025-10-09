package io.github.SpaceNav;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public abstract class Weapon {
    protected Texture txBala;
    protected Sound sonidoBala;
    protected float cadencia; // segundos entre disparos
    protected float tiempoDesdeUltimoDisparo = 0f;

    public Weapon(Texture txBala, Sound sonidoBala, float cadencia) {
        this.txBala = txBala;
        this.sonidoBala = sonidoBala;
        this.cadencia = cadencia;
    }

    public void update(float delta) {
        tiempoDesdeUltimoDisparo += delta;
    }

    // Recibe la posición de la punta de la nave
    public abstract void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY);
}