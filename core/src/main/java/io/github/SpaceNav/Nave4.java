package io.github.SpaceNav;

import java.net.NoRouteToHostException;

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
    private float rotacion = 0f; // ángulo 
    float largoNave;
    float anguloRad;
    private float velX = 0f;
    private float velY = 0f;
    private float aceleracion = 0.15f;
    private float friccion = 0.99f;
    private float velocidadMax = 6f;
    
    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    	this.largoNave =  spr.getHeight()/2 * 0.9f;

    }
    public void draw(SpriteBatch batch, PantallaJuego juego){
    	long sonidoBalaId;
        float x =  spr.getX();
        float y =  spr.getY();
        if (!herido) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  rotacion += 2f;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) rotacion -= 2f;
            rotacion = (rotacion + 360) % 360;

            this.anguloRad = (rotacion - 90) * MathUtils.degreesToRadians;

            // Acelerar hacia adelante o atrás
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                velX -= Math.cos(anguloRad) * aceleracion;
                velY -= Math.sin(anguloRad) * aceleracion;
            }
          

            // Aplicar fricción
            velX *= friccion;
            velY *= friccion;

            // Limitar la velocidad máxima
            float velocidadActual = (float)Math.sqrt(velX * velX + velY * velY);
            if (velocidadActual > velocidadMax) {
                float factor = velocidadMax / velocidadActual;
                velX *= factor;
                velY *= factor;
            }

            // Mover nave según velocidad acumulada
            x += velX;
            y += velY;

            // Limitar dentro de la pantalla
            x = MathUtils.clamp(x, 0, Gdx.graphics.getWidth() - spr.getWidth());
            y = MathUtils.clamp(y, 0, Gdx.graphics.getHeight() - spr.getHeight());

            spr.setPosition(x, y);
            spr.setRotation(rotacion);
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
        	float offset = 5; 
        	float puntaX = spr.getX() + spr.getWidth()/2 - MathUtils.cos(anguloRad) * (largoNave + offset);
        	float puntaY = spr.getY() + spr.getHeight()/2 - MathUtils.sin(anguloRad) * (largoNave + offset);
          Bullet  bala = new Bullet(puntaX,puntaY,txBala, rotacion);
	      juego.agregarBala(bala);
	      sonidoBalaId = soundBala.play();
	      soundBala.setVolume(sonidoBalaId, 0.3f);
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
                b.setYSpeed(-Math.abs(b.getYSpeed())); // rebote hacia abajo
            } else {
                b.setYSpeed(Math.abs(b.getYSpeed()));  // rebote hacia arriba
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
