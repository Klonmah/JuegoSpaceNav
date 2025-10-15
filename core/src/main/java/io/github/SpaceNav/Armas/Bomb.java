package io.github.SpaceNav.Armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import asteroides.Asteroid;

public class Bomb {

    private float x;
    private float y;
    private float rotacion;        
    private float scale = 1f;      
    private float growthSpeed = 0.02f; 
    private float rotationSpeed = 2f;  
    private float lifeTime = 4f;   
    private boolean destroyed = false;

    private Sprite spr;

    public Bomb(float x, float y, Texture tx, float rotacionInicial) {
        this.x = x;
        this.y = y;
        this.rotacion = rotacionInicial;

        spr = new Sprite(tx);
        spr.setOriginCenter();
        spr.setRotation(rotacion);
        spr.setPosition(x, y);
    }

    public void update(float delta) {
        this.x +=  0.2f;
        this.y +=  0.2f;
        if (destroyed) return;

        lifeTime -= delta;
        if (lifeTime <= 0) {
            this.destroyed = true;
            return;
        }

        rotacion += rotationSpeed;
        spr.setRotation(rotacion);

        scale += growthSpeed;
        spr.setScale(scale);

        // Mantener centrado al crecer
        spr.setPosition(x - spr.getWidth() / 2f * (scale - 1), y - spr.getHeight() / 2f * (scale - 1));
    }

    public void draw(SpriteBatch batch) {
        if (!destroyed)
            spr.draw(batch);
    }

    public boolean checkCollision(Asteroid asteroid) {
        return !destroyed && spr.getBoundingRectangle().overlaps(asteroid.getArea());
    }

    public boolean isDestroyed() {return destroyed;}
}

