package io.github.SpaceNav.asteroides;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.Utilidades.MapaManager; // 1. Importamos el Manager

public class Portal extends Ball {

    public Portal(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {

        super(x, y, size, xSpeed, ySpeed, tx);
        

        MapaManager mapa = MapaManager.getInstance();

  
        int nuevoX = (int)mapa.arreglarX(x, getSprite().getWidth());
        int nuevoY = (int)mapa.arreglarY(y, getSprite().getHeight());


        this.setX(nuevoX);
        this.setX(nuevoY);
        getSprite().setPosition(nuevoX, nuevoY);
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