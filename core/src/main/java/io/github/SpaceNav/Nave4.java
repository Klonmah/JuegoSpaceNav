package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;



public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    float velocidad = 4f;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
        float x =  spr.getX();
        float y =  spr.getY();
        if (!herido) {
	        // que se mueva con teclado
        	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  x -= velocidad;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += velocidad;
            if (Gdx.input.isKeyPressed(Input.Keys.UP))    y += velocidad;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  y -= velocidad;
        	
	     /*   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.setRotation(--rotacion);
	        
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;    
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));
	        	     
	        }*/
	        
	        // que se mantenga dentro de los bordes de la ventana
            x = MathUtils.clamp(x, 0, Gdx.graphics.getWidth() - spr.getWidth());
            y = MathUtils.clamp(y, 0, Gdx.graphics.getHeight() - spr.getHeight());
	        
	        spr.setPosition(x, y);
         
 		    spr.draw(batch);
        } else {
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		   spr.draw(batch); 
 		  spr.setX(x);
 		   tiempoHerido--;
 		   if (tiempoHerido<=0) herido = false;
 		 }
        // disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {         
          Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
	      juego.agregarBala(bala);
	      soundBala.play();
        }
       
    }
      
    public boolean checkCollision(Ball2 b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            // Rebote simple: invertir la dirección de la bola según dónde chocó
            if (b.getX() + b.getWidth()/2 < spr.getX() + spr.getWidth()/2) {
                b.setXSpeed(-Math.abs(b.getXSpeed())); // rebote a la izquierda
            } else {
                b.setXSpeed(Math.abs(b.getXSpeed()));  // rebote a la derecha
            }

            if (b.getY() + b.getHeight()/2 < spr.getY() + spr.getHeight()/2) {
                b.setySpeed(-Math.abs(b.getySpeed())); // rebote hacia abajo
            } else {
                b.setySpeed(Math.abs(b.getySpeed()));  // rebote hacia arriba
            }

            // Actualizar vidas y herir
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();

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
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
}
