package io.github.SpaceNav.Armas;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import io.github.SpaceNav.Bullet;
import io.github.SpaceNav.Nave;
import io.github.SpaceNav.PantallaJuego;


public class WeaponSingle extends Weapon {

    public WeaponSingle(Texture txBala, Sound sonidoBala, float cadencia) {
        super(txBala, sonidoBala, cadencia);
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (this.getTiempoDesdeUltimoDisparo() < this.getCadencia()) return; // aún en cooldown

        // Crear bala
        juego.agregarBala(new Bullet(puntaX, puntaY, this.getTxBala(), nave.getRotacion()));

        // Reproducir sonido
        long idSonido = this.getSonidoBala().play();
        this.getSonidoBala().setVolume(idSonido, 0.3f);

        this.setTiempoDesdeUltimoDisparo(0f);
    }
}