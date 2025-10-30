package io.github.SpaceNav.Armas;

import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.AudioManager;
import jugador.Nave;

public class WeaponTriple extends Weapon {
    
    public WeaponTriple(Texture txBala, Texture txBomb, float cadencia) {
        super(txBala, txBomb, cadencia);
     
        AudioManager.getInstance().cargarSonido("disparoTriple", "../assets/pop-sound.mp3");
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (this.getTiempoDesdeUltimoDisparo() < this.getCadencia()) return;

        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() - 10));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion()));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() + 10));
  

        AudioManager.getInstance().reproducirSonido("disparoTriple");

        this.setTiempoDesdeUltimoDisparo(0f);
    }
    
    @Override
    public void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (this.getTiempoDesdeUltimoDisparo() < this.getCadencia()) return;

 
        juego.agregarBomb(new Bomb(puntaX, puntaY, this.getTxBomb(), nave.getRotacion()));

    
        AudioManager.getInstance().reproducirSonido("disparoTriple");

        this.setTiempoDesdeUltimoDisparo(0f);
    }
}