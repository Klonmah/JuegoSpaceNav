package Enemigos;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.SpaceNav.Colisionable;
import jugador.Nave;

public interface Mobs extends Colisionable {
    void update(float deltaTime, Nave jugador);
    void draw(SpriteBatch batch);
    boolean isActive();
    float getX();
    float getY();
    float getWidth();
    float getHeight();
    float getXSpeed();
    void setXSpeed(float xSpeed);
}