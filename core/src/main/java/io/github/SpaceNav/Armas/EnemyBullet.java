package io.github.SpaceNav.Armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import jugador.Nave;


public class EnemyBullet {

	private float x;
	private float y;
	private boolean destroyed = false;
	private Sprite spr;
	public float speed=400f;	
	    
	    public EnemyBullet(float x, float y, Texture tx) {
	    	this.x=x;
	    	this.y=y;
	    	spr = new Sprite(tx);
	    	spr.setSize(40, 80);
	    	spr.setOriginCenter();
	    	spr.setPosition(x, y);
	    }
	    public void update() {
	    	y -= speed * Gdx.graphics.getDeltaTime();
	    	spr.setPosition(x, y);	
	    	
	    	if (y + spr.getHeight() < -30) {
	            destroyed = true;
	        }
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	    
	    public boolean checkCollision(Nave mob) {
	        if(spr.getBoundingRectangle().overlaps(mob.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean isDestroyed() {return destroyed;}
	    
		public float getY() {
			return y;
		}
		public void setY(float y) {
			this.y = y;
		}
		public float getX() {
			return x;
		}
		public void setX(float x) {
			this.x = x;
		}
		public Rectangle getArea() {
			return getSprite().getBoundingRectangle();
		}
		public Sprite getSprite() {
			return spr;
        }
		public float getWidth() {
		    return spr.getWidth();
		}
		public float getHeight() {
		    return spr.getHeight();
		}
	
}
