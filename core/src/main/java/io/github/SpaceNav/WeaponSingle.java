package io.github.SpaceNav;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class WeaponSingle extends Weapon {

    public WeaponSingle(Texture txBala, Sound sonidoBala, float cadencia) {
        super(txBala, sonidoBala, cadencia);
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (tiempoDesdeUltimoDisparo < cadencia) return; // aún en cooldown

        // Crear bala
        juego.agregarBala(new Bullet(puntaX, puntaY, txBala, nave.getRotacion()));

        // Reproducir sonido
        long idSonido = sonidoBala.play();
        sonidoBala.setVolume(idSonido, 0.3f);

        tiempoDesdeUltimoDisparo = 0f;
    }
}