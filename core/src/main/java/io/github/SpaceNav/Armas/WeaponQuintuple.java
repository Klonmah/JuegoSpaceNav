package io.github.SpaceNav.Armas;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import jugador.Nave;
import io.github.SpaceNav.AudioManager;
public class WeaponQuintuple extends Weapon {

    public WeaponQuintuple(Texture txBala, Texture txBomb, Sound sonidoBala, float cadencia) {
        super(txBala, txBomb, sonidoBala, cadencia);
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (this.getTiempoDesdeUltimoDisparo() < this.getCadencia()) return; // aún en cooldown

        // Disparo triple (central + dos laterales)
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() - 20));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() - 10));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion()));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() + 10));
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion() + 20));
        
        // Sonido
        AudioManager.getInstance().cargarSonido("disparoQuintuple","../assets/pop-sound.mp3");
        AudioManager.getInstance().reproducirSonido("disparoQuintuple");

        this.setTiempoDesdeUltimoDisparo(0f);
    }
    
    public void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (this.getTiempoDesdeUltimoDisparo() < this.getCadencia()) return; // aún en cooldown

        // Crear bala
        juego.agregarBomb(new Bomb(puntaX, puntaY, this.getTxBomb(), nave.getRotacion()));

        // Reproducir sonido
        AudioManager.getInstance().cargarSonido("disparoQuintuple","../assets/pop-sound.mp3");
        AudioManager.getInstance().reproducirSonido("disparoQuintuple");
        

        this.setTiempoDesdeUltimoDisparo(0f);
    }
}