package io.github.SpaceNav.Armas;

import com.badlogic.gdx.graphics.Texture;

import io.github.SpaceNav.Pantallas.*;
import io.github.SpaceNav.AudioManager;
import io.github.SpaceNav.jugador.*;

public class WeaponTriple extends Weapon {
    
    public WeaponTriple(Texture txBala, Texture txBomb, float cadencia) {
        super(txBala, txBomb, cadencia);
        AudioManager.getInstance().cargarSonido("disparoTriple", "../assets/pop-sound.mp3");
    }

    @Override
    public void executeFire(Nave nave, float puntaX, float puntaY) {
        GameEventListener listener = getEventListener();
        if (listener != null) {
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() - 15));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion()));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() + 15));
        }
        AudioManager.getInstance().reproducirSonido("disparoTriple");
    }
    
    @Override
    public void executeFireBomb(Nave nave, float puntaX, float puntaY) {
        getEventListener().onBombFired(new Bomb(puntaX, puntaY, getTxBomb(), nave.getRotacion()));
        AudioManager.getInstance().reproducirSonido("disparoTriple");
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        super.fire(nave, puntaX, puntaY);
    }
    
    @Override
    public void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        super.firebomb(nave, puntaX, puntaY);
    }
}