package asteroides;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BallStrong extends Asteroid {
    private int hits = 0;

    public BallStrong(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        // BallStrong tiene 3 de vida y da 30 puntos
        super(x, y, size, xSpeed, ySpeed, tx, 3, 30);
    }
    

    public void getHit() {
        takeDamage(1);
    }
    
    @Override
    public void update() {
        super.update(); // Usa el update de Asteroid
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
}