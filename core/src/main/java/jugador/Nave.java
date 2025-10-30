package jugador;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


import Pantallas.PantallaJuego;
import io.github.SpaceNav.Armas.EnemyBullet;
import io.github.SpaceNav.Armas.Weapon;
import io.github.SpaceNav.Armas.WeaponQuintuple;
import io.github.SpaceNav.AudioManager;
import io.github.SpaceNav.Colisionable;


public class Nave {
	
	private boolean destruida = false;
    private int Iframes = 120;
	
    private int vidas = 20;
    private int bombs = 3;
  
    private Sprite spr;
 


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
    
    public Nave(int x, int y, Texture tx, Texture txBala, Texture txBomb) {
    	
    	AudioManager.getInstance().cargarSonido("herido", "../assets/hurt.ogg");
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    	this.largoNave =  spr.getHeight();
    	this.anchoNave= this.spr.getWidth();
    	
    	this.weapon = new WeaponQuintuple(txBala, txBomb, 0.3f); // 0.3s entre disparos
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
      
    public boolean checkCollision(Colisionable colisionable) {
        if (!herido && colisionable.getArea().overlaps(spr.getBoundingRectangle()) && Iframes == 0) {
            
            
            colisionable.onColision();
            
            
            aplicarDanio();
            
            return true;
        }
        return false;
    }
    
    private void aplicarDanio() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        Iframes = 120;
        AudioManager.getInstance().reproducirSonido("herido");
        if (vidas <= 0) {
            destruida = true;
        }
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
    
   
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}

	public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
	
	public void destruir() {
		this.destruida = true;
	}
	
	// Disparar

	
	
	public void rotar(float grados) {
	    this.rotacion = (this.rotacion + grados + 360) % 360;
	
	
	    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	    	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
	    		velX -= MathUtils.cos(anguloRad) * (aceleracion/4);
	    		velY -= MathUtils.sin(anguloRad) * (aceleracion/4);
	    	} else {
	    		velX -= MathUtils.cos(anguloRad) * aceleracion;
	    		velY -= MathUtils.sin(anguloRad) * aceleracion;
	    	}
	    }
	}
	
	
	public boolean checkCollision(EnemyBullet e) {
	    // Si la bala o el sprite de la nave no existen, no hay colisión
	    if (e == null || e.getArea() == null || spr == null) return false;

	    // Si está destruida o invulnerable, no hacer nada
	    if (herido || Iframes > 0) return false;

	    if (e.getArea().overlaps(spr.getBoundingRectangle())) {
	        // Actualizar vidas y herir
	        vidas--;
	        herido = true;
	        tiempoHerido = tiempoHeridoMax;
	        //sonidoHerido.play();
	        Iframes = 120;

	        if (vidas <= 0)
	            destruida = true;

	        return true;
	    }

	    return false;
	}

}
