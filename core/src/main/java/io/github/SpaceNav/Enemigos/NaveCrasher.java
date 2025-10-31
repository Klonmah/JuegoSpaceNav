package io.github.SpaceNav.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.jugador.*;
import java.util.Random;

public class NaveCrasher implements Mobs {
    
    private int x;
    private int y;
    private float velocidadX;
    private Sprite spr;
    private float cadencia;
    private float pitch;
    private boolean activa = true;
    private boolean destruida = false;
    private int vida = 3;
    private int valorPuntos = 25;

    public NaveCrasher(int x, int y, int size, int velocidadX, Texture tx) {
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
        this.velocidadX = velocidadX;
    }
    
    @Override
    public void update(float deltaTime, Nave jugador) {
        if (!activa || destruida) return;
        
        if (this.getX() < jugador.getX()) {
            x += velocidadX/4;
        }
        if (this.getX() > jugador.getX()) {
            x -= velocidadX/4;
        }
        if (this.getY() < jugador.getY()) {
            y += velocidadX/4;
        }
        if (this.getY() > jugador.getY()) {
            y -= velocidadX/4;
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
    
    @Override
    public float getXSpeed() {
        return velocidadX;
    }
    
    @Override
    public void setXSpeed(float velocidadX) {
        this.velocidadX = velocidadX;
    }
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    public boolean isDestruida() {
        return destruida;
    }

    // Implementación de Destructible
    @Override
    public void takeDamage(int damage) {
        vida -= damage;
        if (vida <= 0) {
            this.destruida = true;
        }
    }
    
    @Override
    public boolean isDestroyed() {
        return destruida;
    }
    
    @Override
    public int getScoreValue() {
        return valorPuntos;
    }
    
    @Override
    public int getHp() {
        return vida;
    }
    
    @Override
    public int getMaxHp() {
        return 3;
    }
}