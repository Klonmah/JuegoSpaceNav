package io.github.SpaceNav.Armas;

import com.badlogic.gdx.graphics.Texture;
import io.github.SpaceNav.Pantallas.*;
import io.github.SpaceNav.jugador.*;
import io.github.SpaceNav.AudioManager;

public class WeaponQuintuple extends Weapon {
    
    public WeaponQuintuple(Texture txBala, Texture txBomb, float cadencia) {
        super(txBala, txBomb, cadencia);
        AudioManager.getInstance().cargarSonido("disparoQuintuple", "../assets/pop-sound.mp3");
    }

    @Override
    public void executeFire(Nave nave, float puntaX, float puntaY) {
        GameEventListener listener = getEventListener();
        if (listener != null) {
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() - 20));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() - 10));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion()));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() + 10));
            listener.onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion() + 20));
        }
        AudioManager.getInstance().reproducirSonido("disparoQuintuple");
    }
    
    @Override
    public void executeFireBomb(Nave nave, float puntaX, float puntaY) {
        getEventListener().onBombFired(new Bomb(puntaX, puntaY, getTxBomb(), nave.getRotacion()));
        AudioManager.getInstance().reproducirSonido("disparoQuintuple");
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