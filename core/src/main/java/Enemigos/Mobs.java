package Enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Mobs {
	
	public void update();
	public void checkCollision(Mobs another);
	public Sprite getSprite();
	public int getX();
	public int getY();
	public void setY(int i);
	public void setX(int i);
	public int getXSpeed();
	public Rectangle getArea();
	public float getWidth();
	public void setXSpeed(int i);
	public void draw(SpriteBatch batch);
	
}
