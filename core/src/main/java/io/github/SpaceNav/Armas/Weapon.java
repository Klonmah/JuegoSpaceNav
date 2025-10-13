package io.github.SpaceNav.Armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.Nave;

public abstract class Weapon {
    private Texture txBala;
    private Sound sonidoBala;
    private float cadencia; // segundos entre disparos
    private float tiempoDesdeUltimoDisparo = 0f;
    
 // GETTERS
    public Texture getTxBala() {
        return txBala;
    }

    public Sound getSonidoBala() {
        return sonidoBala;
    }

    public float getCadencia() {
        return cadencia;
    }

    public float getTiempoDesdeUltimoDisparo() {
        return tiempoDesdeUltimoDisparo;
    }

    // SETTERS
    public void setTxBala(Texture txBala) {
        this.txBala = txBala;
    }

    public void setSonidoBala(Sound sonidoBala) {
        this.sonidoBala = sonidoBala;
    }

    public void setCadencia(float cadencia) {
        this.cadencia = cadencia;
    }

    public void setTiempoDesdeUltimoDisparo(float tiempoDesdeUltimoDisparo) {
        this.tiempoDesdeUltimoDisparo = tiempoDesdeUltimoDisparo;
    }
    
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