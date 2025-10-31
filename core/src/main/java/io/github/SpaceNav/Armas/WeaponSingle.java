package io.github.SpaceNav.Armas;

import com.badlogic.gdx.graphics.Texture;
import Pantallas.PantallaJuego;
import io.github.SpaceNav.AudioManager;
import jugador.Nave;

public class WeaponSingle extends Weapon {
    
    public WeaponSingle(Texture txBala, Texture txBomb, float cadencia) {
        super(txBala, txBomb, cadencia);
        AudioManager.getInstance().cargarSonido("disparoSingle", "../assets/pop-sound.mp3");
    }

    @Override
    public void executeFire(Nave nave, float puntaX, float puntaY) {
        getEventListener().onBulletFired(new Bullet(puntaX, puntaY, getTxBala(), nave.getRotacion()));
        AudioManager.getInstance().reproducirSonido("disparoSingle");
    }
    
    @Override
    public void executeFireBomb(Nave nave, float puntaX, float puntaY) {
        getEventListener().onBombFired(new Bomb(puntaX, puntaY, getTxBomb(), nave.getRotacion()));
        AudioManager.getInstance().reproducirSonido("disparoSingle");
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