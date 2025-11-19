package io.github.SpaceNav.asteroides;


import com.badlogic.gdx.graphics.Texture;

public class Ball extends Asteroid {
    public Ball(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        // Ball tiene 1 de vida y da 10 puntos
        super(x, y, size, xSpeed, ySpeed, tx, 1, 10);
    }
    
    @Override
    public void onColision() {
        takeDamage(1);
    }
}