package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.SpaceNav.Colisionable;

public class BallStrong extends Asteroid {
    private int hp = 2;

    public BallStrong(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        
        int ancho = (int) getSprite().getWidth();
        int alto = (int) getSprite().getHeight();

        // Corrección de posición
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = Gdx.graphics.getHeight() - 20;
        if (y > 0 && y < Gdx.graphics.getHeight() - 40) y = Gdx.graphics.getHeight() - 20;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        setX(x);
        setY(y);
        getSprite().setPosition(getX(), getY());
    }
    
    @Override
    public void update() {
        if (isDestruido()) return;
        
        setX(getX() + getXSpeed());
        setY(getY() + getYSpeed());

        if (getX() + getXSpeed() < 0 || getX() + getXSpeed() + getSprite().getWidth() > Gdx.graphics.getWidth())
            setXSpeed(getXSpeed() * -1);
        if (getY() + getYSpeed() < 0 || getY() + getYSpeed() > Gdx.graphics.getHeight())
            setYSpeed(getYSpeed() * -1);
        getSprite().setPosition(getX(), getY());
    }
    
    @Override
    public void onColision() {
        getHit();
    }
    
   
    @Override
    public void checkCollision(Colisionable another) {
        super.checkCollision(another); 
    }
    
    public int getHp() {
        return hp;
    }
    
    public void getHit() {
        hp--;
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!isDestruido()) {
            getSprite().draw(batch);
        }
    }
    
  
}