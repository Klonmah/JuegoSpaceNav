package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;


public class Bullet {

	private float x;
	private float y;
	private int xSpeed = 7;
	private int ySpeed = 7;
	private boolean destroyed = false;
	private Sprite spr;
	private float rotacion;
	private float anguloRad; // angulo de rotacion en radianes
	
	    
	    public Bullet(float x, float y, Texture tx, float rotacion) {
	    	this.x = x;
	    	this.y = y;
	    	spr = new Sprite(tx);
	    	spr.setPosition(x, y);
	    	spr.setOriginCenter();
	    	this.rotacion = rotacion;
	    	this.anguloRad= (rotacion - 90) * MathUtils.degreesToRadians;
	     
	    }
	    public void update() {
	    	x -= Math.cos(anguloRad) * xSpeed;
    	    y -= Math.sin(anguloRad) * ySpeed;
	        spr.setPosition(x, y);
	        spr.setRotation(rotacion);
	        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
	            destroyed = true;
	        }
	        if (spr.getY() < -10 || spr.getY()-spr.getHeight() > Gdx.graphics.getHeight()) {
	        	destroyed = true;
	        }
	        
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	    
	    public boolean checkCollision(Asteroid asteroid) {
	        if(spr.getBoundingRectangle().overlaps(asteroid.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean isDestroyed() {return destroyed;}
	
}
