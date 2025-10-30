package io.github.SpaceNav.Armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.Colisionable;

public class EnemyBullet implements Colisionable {
    private float x;
    private float y;
    private boolean destroyed = false;
    private Sprite spr;
    public float speed = 400f;	
    
    public EnemyBullet(float x, float y, Texture tx) {
        this.x = x;
        this.y = y;
        spr = new Sprite(tx);
        spr.setSize(40, 80);
        spr.setOriginCenter();
        spr.setPosition(x, y);
    }
    
    public void update() {
        y -= speed * Gdx.graphics.getDeltaTime();
        spr.setPosition(x, y);	
        
        if (y + spr.getHeight() < -30) {
            destroyed = true;
        }
    }
    
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
    // ✅ MÉTODO CORREGIDO: Usa Colisionable en lugar de Nave
    public boolean checkCollision(Colisionable colisionable) {
        boolean colisiona = !destroyed && spr.getBoundingRectangle().overlaps(colisionable.getArea());
        
        if (colisiona) {
            colisionable.onColision();
            this.destroyed = true;
        }
        
        return colisiona;
    }
    
    // ✅ IMPLEMENTACIÓN de Colisionable
    @Override
    public void onColision() {
        this.destroyed = true; // La bala enemiga puede ser destruida
    }
    
    // ✅ getArea() ya existe - perfecto
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public Sprite getSprite() {
        return spr;
    }
    
    public float getWidth() {
        return spr.getWidth();
    }
    
    public float getHeight() {
        return spr.getHeight();
    }
}
