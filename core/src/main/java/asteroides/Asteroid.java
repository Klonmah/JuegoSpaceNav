package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public abstract class Asteroid {
	private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;

    public Asteroid(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2); // el size es el radio
        spr.setOriginCenter();

        int ancho = (int) spr.getWidth();
        int alto = (int) spr.getHeight();

        // Corrige si el sprite estaría fuera de pantalla //quitado ancho y alto en el segundo y tercer if por como se dibujan los sprites
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        // Guardar posición corregida
        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);

        // Velocidades
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    public void update() {
        x += getXSpeed();
        y += getYSpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (y+getYSpeed() < 0 || y+getYSpeed() > Gdx.graphics.getHeight())
        	setYSpeed(getYSpeed() * -1);
        spr.setPosition(x, y);
    }
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    public void checkCollision(Asteroid another) {
    }
    
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getYSpeed() {
		return ySpeed;
	}
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
	}
	
	public float getWidth() {
	    return spr.getWidth();
	}

	public float getHeight() {
	    return spr.getHeight();
	}
	public void setPosition(float x, float y) {
	    this.x = (int) x;
	    this.y = (int) y;
	    spr.setPosition(x, y);
	}
    public Sprite getSprite() {
    	return spr;
    }
}
