package io.github.SpaceNav;

import java.net.NoRouteToHostException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import Pantallas.PantallaJuego;
import asteroides.Asteroid;
import io.github.SpaceNav.Armas.Weapon;
import io.github.SpaceNav.Armas.WeaponQuintuple;



public class Nave {
	
	private boolean destruida = false;
    private int Iframes = 120;
	private float vidaMax = 20;
    private int vidas = 20;
    private int bombs = 3;
    private float velocidad = 4f;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private Texture txBomb;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    private float rotacion = 0f; // ángulo 
    private float largoNave;
    private float anchoNave;
    private float anguloRad;
    private float velX = 0f;
    private float velY = 0f;
    private float velocityfixed = 4f;
    private float aceleracion = 0.15f;
    private float friccion = 0.99f; // 0.99f
    private float velocidadMax = 6f;
    private Weapon weapon; // arma actual



    public void setWeapon(Weapon w) {
        this.weapon = w;
    }
    
    public float getRotacion() {
    	return this.rotacion;
    }
    
    public float getLargoNave() {
    	return this.largoNave;
    }
    public float getAnchoNave() {
    	return this.anchoNave;
    }
    
    public Nave(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Texture txBomb, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	this.txBomb = txBomb;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    	this.largoNave =  spr.getHeight();
    	this.anchoNave= this.spr.getWidth();
    	
    	this.weapon = new WeaponQuintuple(txBala, txBomb, soundBala, 0.3f); // 0.3s entre disparos
    }
 // Nuevo método update
    public void update(boolean pausa, PantallaJuego juego) {
        if (pausa) return;
        if (Iframes > 0)
        {
        	Iframes--;
        }
        if (Iframes <= 0)
        {
        	Iframes = 0;
        }
        
        if(herido) {
        	spr.setX(spr.getX() + MathUtils.random(-2,2));
            spr.setY(spr.getY() + MathUtils.random(-2,2));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }else {
        	
        	
        	// New Hard movement system (deprecated)
        	//if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        	//	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        	//	{
        	//		velY = velocityfixed/2;
        	//	} else { velY = velocityfixed; } 
        	//} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	//	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        	//	{
        	//		velY = -velocityfixed/2;
        	//	} else { velY = -velocityfixed; } 
        	//} else {
        	//	velY = 0;
        	//}

        	//if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        	//	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        	//	{
        	//		velX = velocityfixed/2;
        	//	} else { velX = velocityfixed; } 
        	//    
        	//} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        	//	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        	//	{
        	//		velX = -velocityfixed/2;
        	//	} else { velX = -velocityfixed; } 
        	//} else {
        	//	velX = 0;
        	//}
        	
        	
        	//ROTACIÓN (restaurada)
        	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  rotacion += 2f;
        	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= 2f;
        	rotacion = (rotacion + 360) % 360;

        	anguloRad = (rotacion - 90) * MathUtils.degreesToRadians;

        	//ACELERACIÓN
        	if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        		
            	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            	{velX -= MathUtils.cos(anguloRad) * (aceleracion/4);}
            	else { velX -= MathUtils.cos(anguloRad) * aceleracion; } 
            	
	        	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	        	{velY -= MathUtils.sin(anguloRad) * (aceleracion/4);}
	        	else { velY -= MathUtils.sin(anguloRad) * aceleracion; } 
        		
        	}
        	

        	// FRICCIÓN
        	velX *= friccion;
        	velY *= friccion;

        	// Limitar velocidad
        	float velocidadActual = (float)Math.sqrt(velX*velX + velY*velY);
        	if (velocidadActual > velocidadMax) {
        		float factor = velocidadMax / velocidadActual;
        		velX *= factor;
        		velY *= factor;
        	}

        	// Mover nave
        	float x = MathUtils.clamp(spr.getX() + velX, 0, Gdx.graphics.getWidth() - spr.getWidth());
        	float y = MathUtils.clamp(spr.getY() + velY, 0, Gdx.graphics.getHeight() - spr.getHeight());
        	spr.setPosition(x, y);
        	spr.setRotation(rotacion);

        	// Disparo spammer old : isKeyJustPressed
        	if (weapon != null) {
        		weapon.update(Gdx.graphics.getDeltaTime());
        		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
        			weapon.fire(this, juego, spr.getX()+17, spr.getY()+40);
        		}
        	}
        	if (weapon != null) {
        		weapon.update(Gdx.graphics.getDeltaTime());
        		if ( bombs > 0)
        		{
            		if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            		    bombs--;
            			weapon.firebomb(this, juego, spr.getX()+17, spr.getY()+40);
            		}
        		}
        	}
        }
    }
    // Nuevo draw simplificado
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
      
    public boolean checkCollision(Asteroid b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle()) && Iframes == 0) {
            // Rebote simple: invertir la dirección de la bola según dónde chocó
            if (b.getX() + b.getWidth()/2 < spr.getX() + spr.getWidth()/2) {
                b.setXSpeed(-Math.abs(b.getXSpeed())); // rebote a la izquierda
            } else {
                b.setXSpeed(Math.abs(b.getXSpeed()));  // rebote a la derecha
            }

            if (b.getY() + b.getHeight()/2 < spr.getY() + spr.getHeight()/2) {
                b.setYSpeed(-Math.abs(b.getYSpeed())); // rebote hacia abajo
            } else {
                b.setYSpeed(Math.abs(b.getYSpeed()));  // rebote hacia arriba
            }

            // Actualizar vidas y herir
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            Iframes = 120;
            if (vidas <= 0)
                destruida = true;

            return true;
        }
        return false;
    }
    
    
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }

    public int getVidas() {return vidas;}
    
    public int getBombs() {return bombs;}
    
    public void setBombs(int b) {bombs = b;}
    
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
