package io.github.SpaceNav.Armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.Nave;

public abstract class Weapon {
    private Texture txBala;
    private Texture txBomb;
    private Sound sonidoBala;
    private float cadencia; // segundos entre disparos
    private float tiempoDesdeUltimoDisparo = 0f;
    
 // GETTERS
    public Texture getTxBala() {
        return txBala;
    }

    public Texture getTxBomb() {
        return txBomb;
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
    

    public void setTxBomb(Texture txBomb) {
        this.txBomb = txBomb;
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
    
    public Weapon(Texture txBala, Texture txBomb, Sound sonidoBala, float cadencia) {
        this.txBala = txBala;
        this.txBomb = txBomb;
        this.sonidoBala = sonidoBala;
        this.cadencia = cadencia;
    }

    public void update(float delta) {
        tiempoDesdeUltimoDisparo += delta;
    }

    // Recibe la posición de la punta de la nave
    public abstract void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY);
    
    // Recibe la posición de la punta de la nave (bomb)
    public abstract void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY);
}