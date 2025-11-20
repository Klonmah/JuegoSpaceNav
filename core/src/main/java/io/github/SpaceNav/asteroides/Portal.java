package io.github.SpaceNav.asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Portal extends Ball {

    public Portal(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        // Ball tiene 1 de vida y da 10 puntos
        super(x, y, size, xSpeed, ySpeed, tx);
        
        int ancho = (int) getSprite().getWidth();
        int alto = (int) getSprite().getHeight();
        int screenW = Gdx.graphics.getWidth();
        int screenH = Gdx.graphics.getHeight();

        // Ajuste inicial para no aparecer fuera de pantalla
        if (x < 0) x = 0;
        if (x > screenW - ancho) x = screenW - ancho;
        if (y < 0) y = 0;
        if (y > screenH - alto) y = screenH - alto;

        setX(x);
        setY(y);
        getSprite().setPosition(getX(), getY());
    }
    
    @Override
    public void update() {
    }
    
    @Override
    public Rectangle getArea() {
        return getSprite().getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
        takeDamage(1);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!isDestroyed()) {
            getSprite().draw(batch);
        }
    }
    
    public void checkCollision(Asteroid another) {
        super.checkCollision(another);
    }
} 
