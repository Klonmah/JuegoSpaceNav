package io.github.SpaceNav.Armas;



import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.AudioManager;
import jugador.Nave;

public abstract class Weapon {
    private Texture txBala;
    private Texture txBomb;
  
    private float cadencia; // segundos entre disparos
    private float tiempoDesdeUltimoDisparo = 0f;
    private AudioManager audioManager = AudioManager.getInstance();
    
 // GETTERS
    public AudioManager getAudioManager() {
    	return this.audioManager;
    }
    public Texture getTxBala() {
        return txBala;
    }

    public Texture getTxBomb() {
        return txBomb;
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

    public void setCadencia(float cadencia) {
        this.cadencia = cadencia;
    }

    public void setTiempoDesdeUltimoDisparo(float tiempoDesdeUltimoDisparo) {
        this.tiempoDesdeUltimoDisparo = tiempoDesdeUltimoDisparo;
    }
    
    public Weapon(Texture txBala, Texture txBomb, float cadencia) {
        this.txBala = txBala;
        this.txBomb = txBomb;
  
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