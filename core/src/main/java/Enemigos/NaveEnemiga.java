package Enemigos;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import Pantallas.PantallaJuego;
import io.github.SpaceNav.Armas.EnemyBullet;
import io.github.SpaceNav.Imagen;
import io.github.SpaceNav.AudioManager;

public class NaveEnemiga implements Mobs {
    
    private int x;
    private int y;
    private int xSpeed;
    private Sprite spr;
    private float cadencia;
    private float tiempoDesdeUltimoDisparo = 0f;
    private Texture balaTexture;
    private Sound disparoSound;
    private float pitch;
    private boolean activa = true;
    private boolean destruida = false;
    private AudioManager audioManager = AudioManager.getInstance();

    public NaveEnemiga(int x, int y, int size, int xSpeed, Texture tx) {
        Random r = new Random();
        this.cadencia = 4.0f + r.nextFloat();
        pitch = 0.7f + (float)Math.random() * (1.3f - 0.7f);
        
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2);
        spr.setOriginCenter();
        
        balaTexture = new Texture(Gdx.files.internal("../assets/EnemyBullet.png"));
        disparoSound = Gdx.audio.newSound(Gdx.files.internal("../assets/laserSound.mp3"));
        
        

        int ancho = (int) spr.getWidth();
        int alto = (int) spr.getHeight();

        // Corrección de posición
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = Gdx.graphics.getHeight() - 20;
        if (y > 0 && y < Gdx.graphics.getHeight() - 40) y = Gdx.graphics.getHeight() - 20;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);
        this.xSpeed = xSpeed;
    }
    
    @Override
    public void update(float deltaTime) {
        if (!activa || destruida) return;
        
        x += xSpeed;

        if (x + xSpeed < 0 || x + xSpeed + spr.getWidth() > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }
        
        spr.setPosition(x, y);
    }
    
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
        // ✅ Implementa el método de Colisionable
        System.out.println("NaveEnemiga impactada!");
        this.destruida = true;
        // Aquí puedes añadir efectos de explosión, sonido, etc.
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (activa && !destruida) {
            spr.draw(batch);
        }
    }
    
    @Override
    public boolean isActive() {
        return activa && !destruida;
    }
    
    // ✅ Método específico para el disparo (no en la interfaz)
    public void updateDisparo(float delta, PantallaJuego juego) {
        if (!isActive()) return;
        
        tiempoDesdeUltimoDisparo += delta;

        if (tiempoDesdeUltimoDisparo >= cadencia) {
            tiempoDesdeUltimoDisparo = 0;
            EnemyBullet b = new EnemyBullet(
                    getX() + getWidth() / 2f,
                    getY(),
                    balaTexture
            );
            juego.agregarBalaEnemiga(b);
            audioManager.reproducirEfecto(disparoSound);
            
        }
    }
    
    // ✅ Getters mejorados
    @Override
    public float getX() {
        return this.x;
    }
    
    @Override
    public float getY() {
        return this.y;
    }
    
    @Override
    public float getWidth() {
        return spr.getWidth();
    }

    @Override
    public float getHeight() {
        return spr.getHeight();
    }
    
    public void setPosition(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
        spr.setPosition(x, y);
    }
    
    public int getXSpeed() {
        return xSpeed;
    }
    
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    public boolean isDestruida() {
        return destruida;
    }
}