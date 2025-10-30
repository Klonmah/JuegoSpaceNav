package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BallStrong extends Asteroid {
    private int hp = 2;

    public BallStrong(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
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
        if (isDestruido()) return;
        

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
    public void onColision() {
        getHit();
    }
    
   
    @Override
    public void checkCollision(Asteroid another) {
        if (this.isDestruido() || another.isDestruido()) return;
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