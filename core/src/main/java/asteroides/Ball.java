package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball extends Asteroid {

    public Ball(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        // Ball tiene 1 de vida y da 10 puntos
        super(x, y, size, xSpeed, ySpeed, tx, 1, 10);
        
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
        if (isDestroyed()) return; 

        setX(getX() + getXSpeed());
        setY(getY() + getYSpeed());

        float ancho = getSprite().getWidth();
        float alto = getSprite().getHeight();
        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        // --- Rebote horizontal ---
        if (getX() < 0) {
            setX(0);
            setXSpeed(Math.abs(getXSpeed())); // rebota hacia la derecha
        } else if (getX() + ancho > screenW) {
            setX((int) (screenW - ancho));
            setXSpeed(-Math.abs(getXSpeed())); // rebota hacia la izquierda
        }

        // --- Rebote vertical ---
        if (getY() < 0) {
            setY(0);
            setYSpeed(Math.abs(getYSpeed())); // rebota hacia arriba
        } else if (getY() + alto > screenH) {
            setY((int) (screenH - alto));
            setYSpeed(-Math.abs(getYSpeed())); // rebota hacia abajo
        }
        
        getSprite().setPosition(getX(), getY());
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
