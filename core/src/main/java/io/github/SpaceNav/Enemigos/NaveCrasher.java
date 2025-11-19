package io.github.SpaceNav.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.SpaceNav.Enemigos.Comportamiento.ComportamientoEnemigo;
import io.github.SpaceNav.jugador.*;
import java.util.Random;


public class NaveCrasher implements Mobs {
    
    private float x;
    private float y;
    private float velocidad;
    private Sprite spr;
    private float cadencia;
    private float pitch;
    private boolean activa = true;
    private boolean destruida = false;
    private int vida = 3;
    private int valorPuntos = 25;
    private ComportamientoEnemigo comportamiento;

    public NaveCrasher(int x, int y, int size, int velocidad, Texture tx) {
        Random r = new Random();
        this.cadencia = 4.0f + r.nextFloat();
    
        
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2);
        spr.setOriginCenter();

        float ancho = spr.getWidth();
        float alto = spr.getHeight();

        // Corrección de posición - coordenadas LibGDX (0,0 abajo-izquierda)
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - (int)ancho;
        if (y < 0) y = (int)alto;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - (int)alto;

        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);
        this.velocidad = velocidad; // Cambiado de velocidadX a velocidad
        
    }
    
    @Override
    public void update(float deltaTime, Nave jugador) {
        if (!activa || destruida) return;
        
        // Usar el comportamiento si está asignado
        if (comportamiento != null) {
            comportamiento.actualizar(this, deltaTime);
        } else {
            // Comportamiento por defecto (backup)
            float direccionX = jugador.getX() - this.x;
            float direccionY = jugador.getY() - this.y;
            float magnitud = (float) Math.sqrt(direccionX * direccionX + direccionY * direccionY);
            
            if (magnitud > 0) {
                direccionX /= magnitud;
                direccionY /= magnitud;
                
                x += direccionX * velocidad * deltaTime;
                y += direccionY * velocidad * deltaTime;
            }
        }
        
        // Mantener dentro de los límites de la pantalla
        float ancho = spr.getWidth();
        float alto = spr.getHeight();
        
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - ancho) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - alto) y = Gdx.graphics.getHeight() - alto;
        
        spr.setPosition(x, y);
    }
    
    public void setComportamiento(ComportamientoEnemigo comportamiento) {
        this.comportamiento = comportamiento;
        if (this.comportamiento != null) {
            this.comportamiento.iniciar(this);
        }
    }
    
    
    public void setVelocidad(float velocidad) {
    	this.velocidad = velocidad;
    }
    public float getVelocidad() {
    	return this.velocidad;
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
    public void setX(float x) {
    	this.x =x;
    }
    public void setY(float y) {
    	this.y =y;
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
        this.x = x;
        this.y = y;
        spr.setPosition(x, y);
    }
    

    
    @Override
    public void setXSpeed(float velocidad) {
        this.velocidad = velocidad;
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
    public Sprite getSprite() {
    	return this.spr;
    }
    public void setSprite(Sprite spr) {
    	this.spr = spr;
    }


}