package io.github.SpaceNav.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.jugador.*;
import io.github.SpaceNav.Enemigos.Comportamiento.ComportamientoEnemigo;
import io.github.SpaceNav.Enemigos.Sistemas.*;
import io.github.SpaceNav.Armas.EnemyBullet;

public class NaveEnemiga implements Mobs, ShooterEnemigo {
    private SistemaMovimiento movimiento;
    private SistemaDisparos disparos;
    private SistemaEstado estado;
   
    private Sprite sprite;
    private int vida = 1;
    private int valorPuntos = 15;
    
    public NaveEnemiga(int x, int y, int size, int xSpeed, Texture texturaNave, Texture balaTexture) {
        this.sprite = new Sprite(texturaNave);
        this.sprite.setSize(size * 2, size * 2);
        this.sprite.setOriginCenter();
        
        this.movimiento = new SistemaMovimiento(x, y, xSpeed, sprite);
        this.disparos = new SistemaDisparos(4.0f, balaTexture);
        this.estado = new SistemaEstado();
        
        corregirPosicionInicial();
    }
    
    private void corregirPosicionInicial() {
        int ancho = (int) sprite.getWidth();
        int alto = (int) sprite.getHeight();
        int x = movimiento.getX();
        int y = movimiento.getY();
        
        if (x < 0) x = 0;
        if (x + ancho > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = 0;
        if (y + alto > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;
        
        movimiento.setPosition(x, y);
    }
    
    @Override
    public void update(float deltaTime, Nave jugador) {
        if (!estado.isActive()) return;
        movimiento.update(deltaTime);
    }
    
    // Implementación de Shooter
    @Override
    public EnemyBullet shoot(float delta) {
        return getBala(delta); // Reutiliza tu método existente
    }
    
    @Override
    public boolean canShoot() {
        return estado.isActive() && disparos != null;
    }
    
    // Mantener método existente para compatibilidad
    public EnemyBullet getBala(float delta) {
        if (!estado.isActive() || disparos == null) return null;
        
        float xDisparo = getX() + getWidth() / 2f;
        float yDisparo = getY();
        return disparos.update(delta, xDisparo, yDisparo);
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (estado.isActive()) {
            sprite.draw(batch);
        }
    }
    
    @Override
    public Rectangle getArea() {
        return sprite.getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
        estado.destruir();
    }
    
    @Override
    public boolean isActive() {
        return estado.isActive();
    }
    
    @Override
    public float getX() {
        return movimiento.getX();
    }
    
    @Override
    public float getY() {
        return movimiento.getY();
    }
    
    @Override
    public float getWidth() {
        return sprite.getWidth();
    }
    
    @Override
    public float getHeight() {
        return sprite.getHeight();
    }
    
    @Override
    public float getXSpeed() {
        return movimiento.getXSpeed();
    }
    
    public void setXSpeed(float xSpeed) {
    	this.movimiento.setXSpeed(xSpeed);
    }

    public void setDisparosActivos(boolean activos) {
        if (disparos != null) {
            disparos.setActivo(activos);
        }
    }
    
    public boolean isDestruida() {
        return estado.isDestruida();
    }

    // Implementación de Destructible
    @Override
    public void takeDamage(int damage) {
        vida -= damage;
        if (vida <= 0) {
            estado.destruir();
        }
    }
    
    @Override
    public boolean isDestroyed() {
        return estado.isDestruida();
    }
    
    @Override
    public int getScoreValue() {
        return valorPuntos;
    }
    
    @Override
    public int getHp() {
        return vida;
    }
    
    @Override
    public int getMaxHp() {
        return vida;
    }

	@Override
	public float getVelocidad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX(float x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setComportamiento(ComportamientoEnemigo comportamiento) {
		// TODO Auto-generated method stub
		
	}
}