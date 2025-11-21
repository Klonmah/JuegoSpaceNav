package io.github.SpaceNav.Enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.SpaceNav.Colisionable;
import io.github.SpaceNav.Enemigos.Comportamiento.ComportamientoEnemigo;
import io.github.SpaceNav.jugador.*;

public interface Mobs extends Colisionable, Destructible  {
    void update(float deltaTime, Nave jugador);
    void draw(SpriteBatch batch);
    boolean isActive();
    float getX();
    void setX(float x);
    void setY(float y);
    float getY();
    Sprite getSprite();
    void setSprite(Sprite spr);
    float getWidth();
    float getHeight();
    void setXSpeed(float xSpeed);
    void setVelocidad(float velocidad);
    float getVelocidad();
    public void setComportamiento(ComportamientoEnemigo comportamiento);
}