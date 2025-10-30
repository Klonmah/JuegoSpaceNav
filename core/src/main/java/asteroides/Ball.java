package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Asteroid {

    public Ball(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
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
    public Rectangle getArea() {
        return getSprite().getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
       
        super.onColision(); 
  
        
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (!isDestruido()) {
            getSprite().draw(batch);
        }
    }
    

    public void checkCollision(Asteroid another) {
    	super.checkCollision(another); 
        }
    
}
    
    
