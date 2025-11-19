package io.github.SpaceNav.Armas;

import com.badlogic.gdx.graphics.Texture;
import io.github.SpaceNav.Pantallas.*;
import io.github.SpaceNav.Pantallas.Utilidades.GameEventListener;
import io.github.SpaceNav.AudioManager;
import io.github.SpaceNav.jugador.*;

public abstract class Weapon {
    private Texture txBala;
    private Texture txBomb;
    private float cadencia;
    private float tiempoDesdeUltimoDisparo = 0f;
    private AudioManager audioManager = AudioManager.getInstance();
    private GameEventListener eventListener;

    public void setEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    // GETTERS
    public Texture getTxBala() { return txBala; }
    public Texture getTxBomb() { return txBomb; }
    public float getCadencia() { return cadencia; }
    public float getTiempoDesdeUltimoDisparo() { return tiempoDesdeUltimoDisparo; }
    public GameEventListener getEventListener() { return eventListener; } 

    // SETTERS
    public void setTiempoDesdeUltimoDisparo(float tiempo) { this.tiempoDesdeUltimoDisparo = tiempo; }

    public Weapon(Texture txBala, Texture txBomb, float cadencia) {
        this.txBala = txBala;
        this.txBomb = txBomb;
        this.cadencia = cadencia;
    }

    public void update(float delta) {
        tiempoDesdeUltimoDisparo += delta;
    }

    public final void fire(Nave nave, float puntaX, float puntaY) {
        if (tiempoDesdeUltimoDisparo >= cadencia && eventListener != null) {
            tiempoDesdeUltimoDisparo = 0f;
            executeFire(nave, puntaX, puntaY); 
        }
    }
    
    public final void firebomb(Nave nave, float puntaX, float puntaY) {
        if (eventListener != null) {
            executeFireBomb(nave, puntaX, puntaY); 
        }
    }

    //Métodos abstractos
    public abstract void executeFire(Nave nave, float puntaX, float puntaY);
    public abstract void executeFireBomb(Nave nave, float puntaX, float puntaY);


    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        this.fire(nave, puntaX, puntaY);
    }
    
    public void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        this.firebomb(nave, puntaX, puntaY);
    }
}