package io.github.SpaceNav.Armas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.Nave;

public class WeaponQuintuple extends Weapon {

    public WeaponQuintuple(Texture txBala, Sound sonidoBala, float cadencia) {
        super(txBala, sonidoBala, cadencia);
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
        long idSonido = this.getSonidoBala().play();
        this.getSonidoBala().setVolume(idSonido, 0.3f);

        this.setTiempoDesdeUltimoDisparo(0f);
    }
}