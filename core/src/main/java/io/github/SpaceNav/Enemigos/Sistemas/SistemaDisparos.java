package io.github.SpaceNav.Enemigos.Sistemas;

import com.badlogic.gdx.graphics.Texture;
import java.util.Random;
import io.github.SpaceNav.Armas.EnemyBullet;
import io.github.SpaceNav.Utilidades.AudioManager;
import io.github.SpaceNav.jugador.Nave;

public class SistemaDisparos {
    private float cadencia;
    private float tiempoDesdeUltimoDisparo = 0f;
    private Texture balaTexture;
    private boolean activo = true;
    private Nave jugador;
    
    public SistemaDisparos(float cadenciaBase, Texture balaTexture,Nave jugador) {
        Random r = new Random();
        this.cadencia = cadenciaBase + r.nextFloat();
        this.balaTexture = balaTexture;
        AudioManager.getInstance().cargarSonido("laserEnemigo", "../assets/laserSound.mp3");
        this.jugador = jugador;
    }
    
    //  Retorna la bala si debe disparar, null si no
    public EnemyBullet update(float delta, float x, float y) {
        if (!activo) return null;
        
        tiempoDesdeUltimoDisparo += delta;
        if (tiempoDesdeUltimoDisparo >= cadencia) {
            tiempoDesdeUltimoDisparo = 0;
            
            AudioManager.getInstance().reproducirSonido("laserEnemigo");
            return new EnemyBullet(x, y, balaTexture,jugador);
        }
        
        return null;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}