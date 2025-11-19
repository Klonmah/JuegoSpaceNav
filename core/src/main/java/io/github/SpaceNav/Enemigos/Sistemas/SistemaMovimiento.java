package io.github.SpaceNav.Enemigos.Sistemas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SistemaMovimiento {
    private int x, y;
    float xSpeed;
    private Sprite sprite;
    
    public SistemaMovimiento(int x, int y, int xSpeed, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.sprite = sprite;
        sprite.setPosition(x, y);
    }
    
    public void update(float deltaTime) {
        x += xSpeed;
        
        // Rebote en bordes
        if (x + xSpeed < 0 || x + xSpeed + sprite.getWidth() > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }
        
        sprite.setPosition(x, y);
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public float getXSpeed() { return xSpeed; }
    public void setXSpeed(float xSpeed) {
    	this.xSpeed = xSpeed;
    }
    public void setX(float x) {
    	this.x = (int) x;
    }
    public void setY(float y) {
    	this.y = (int) y;
    }
    public void setPosition(int x, int y) { 
        this.x = x; 
        this.y = y; 
        sprite.setPosition(x, y);
    }
}