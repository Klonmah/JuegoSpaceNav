package Enemigos;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import jugador.Nave;


import io.github.SpaceNav.AudioManager;

public class NaveCrasher implements Mobs {
    
    private int x;
    private int y;
    private float xSpeed;
    private Sprite spr;
    private float cadencia;
    private float pitch;
    private boolean activa = true;
    private boolean destruida = false;
    private AudioManager audioManager = AudioManager.getInstance();

    public NaveCrasher(int x, int y, int size, int xSpeed, Texture tx) {
        Random r = new Random();
        this.cadencia = 4.0f + r.nextFloat();
        pitch = 0.7f + (float)Math.random() * (1.3f - 0.7f);
        
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2);
        spr.setOriginCenter();
        
        

        
        

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
    public void update(float deltaTime, Nave Jugador) {
        if (!activa || destruida) return;
        
        if ( this.getX() < Jugador.getX())
        {
            x += xSpeed/4;
        }
        if ( this.getX() > Jugador.getX())
        {
            x -= xSpeed/4;
        }
        if ( this.getY() < Jugador.getY())
        {
            y += xSpeed/4;
        }
        if ( this.getY() > Jugador.getY())
        {
            y -= xSpeed/4;
        }
        
        spr.setPosition(x, y);
    }
    
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
       
        this.destruida = true;

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
    
    public float getXSpeed() {
        return xSpeed;
    }
    
    
    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    public boolean isDestruida() {
        return destruida;
    }
}