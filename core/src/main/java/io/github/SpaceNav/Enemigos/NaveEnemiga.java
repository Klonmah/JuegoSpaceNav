package io.github.SpaceNav.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.jugador.Nave;
import io.github.SpaceNav.Enemigos.Comportamiento.ComportamientoEnemigo;
import io.github.SpaceNav.Enemigos.Sistemas.*;
import io.github.SpaceNav.Armas.EnemyBullet;

public class NaveEnemiga implements Mobs, ShooterEnemigo {
    private SistemaMovimiento movimiento;
    private SistemaDisparos disparos;
    private SistemaEstado estado;
    private ComportamientoEnemigo comportamiento;
   
    private Sprite sprite;
    private int vida = 1;
    private int valorPuntos = 15;
    private float velocidad = 150f;
    
    // Posiciones directas para el movimiento como NaveCrasher
    private float x;
    private float y;
    
    public NaveEnemiga(int x, int y, int size, int xSpeed, Texture texturaNave, Texture balaTexture,Nave jugador) {
        this.sprite = new Sprite(texturaNave);
        this.sprite.setSize(size * 2, size * 2);
        this.sprite.setOriginCenter();
        
        // Inicializar posiciones directas
        this.x = x;
        this.y = y;
        this.velocidad = xSpeed;
        
        this.movimiento = new SistemaMovimiento(x, y, xSpeed, sprite);
        this.disparos = new SistemaDisparos(4.0f, balaTexture,jugador);
        this.estado = new SistemaEstado();
        
        corregirPosicionInicial();
        // Posicionar sprite inicialmente
        this.sprite.setPosition(this.x, this.y);
    }
    
    private void corregirPosicionInicial() {
        float ancho = sprite.getWidth();
        float alto = sprite.getHeight();
        
        if (x < 0) x = 0;
        if (x + ancho > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = 0;
        if (y + alto > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;
        
   
        if (movimiento != null) {
            movimiento.setPosition((int)x, (int)y);
        }
    }
    
    @Override
    public void update(float deltaTime, Nave jugador) {
        if (!estado.isActive()) return;
        
        // Actualizar comportamiento si está asignado
        if (comportamiento != null) {
            comportamiento.actualizar(this, deltaTime);
        } else {
            // Comportamiento por defecto - perseguir como NaveCrasher
            float direccionX = jugador.getX() - this.x;
            float direccionY = jugador.getY() - this.y;
            
            float magnitud = (float) Math.sqrt(direccionX * direccionX + direccionY * direccionY);
            
            if (magnitud > 0) {
                direccionX /= magnitud;
                direccionY /= magnitud;
                
                x += direccionX * velocidad * deltaTime;
                y += direccionY * velocidad * deltaTime;
            }
            
            // Mantener dentro de límites
            float ancho = sprite.getWidth();
            float alto = sprite.getHeight();
            
            if (x < 0) x = 0;
            if (x > Gdx.graphics.getWidth() - ancho) x = Gdx.graphics.getWidth() - ancho;
            if (y < 0) y = 0;
            if (y > Gdx.graphics.getHeight() - alto) y = Gdx.graphics.getHeight() - alto;
        }
        
    
        apuntarAlJugador(jugador);
        
        // CRUCIAL: Actualizar posición del sprite y sistemas
        sprite.setPosition(x, y);
        if (movimiento != null) {
            movimiento.setPosition((int)x, (int)y);
        }
    }

 
    private void apuntarAlJugador(Nave jugador) {
        float dx = jugador.getX() - (x + sprite.getWidth() / 2);
        float dy = jugador.getY() - (y + sprite.getHeight() / 2);
        float angulo = (float) Math.atan2(dy, dx) * com.badlogic.gdx.math.MathUtils.radiansToDegrees;
        sprite.setRotation(angulo + 90); // 
    }
    
    // Implementación de Shooter
    @Override
    public EnemyBullet shoot(float delta) {
        return getBala(delta);
    }
    
    @Override
    public boolean canShoot() {
        return estado.isActive() && disparos != null;
    }
    
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
    
    // Getters y Setters corregidos para usar posiciones directas
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public float getWidth() {
        return sprite.getWidth();
    }
    
    @Override
    public float getHeight() {
        return sprite.getHeight();
    }
    
 
    public float getXSpeed() {
        return velocidad;
    }
    
    public void setXSpeed(float xSpeed) {
        this.velocidad = xSpeed;
        if (movimiento != null) {
            movimiento.setXSpeed((int)xSpeed);
        }
    }

    @Override
    public void setX(float x) {
        this.x = x;
        if (movimiento != null) {
            movimiento.setX((int)x);
        }
        sprite.setX(x);
    }

    @Override
    public void setY(float y) {
        this.y = y;
        if (movimiento != null) {
            movimiento.setY((int)y);
        }
        sprite.setY(y);
    }
    
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
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
        return velocidad;
    }
    
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void setComportamiento(ComportamientoEnemigo comportamiento) {
        this.comportamiento = comportamiento;
        if (comportamiento != null) {
            comportamiento.iniciar(this);
        }
    }
    
    public boolean isComportamientoCompletado() {
        return comportamiento != null && comportamiento.estaCompletado();
    }
    
    public void clearComportamiento() {
        this.comportamiento = null;
    }
    
    public ComportamientoEnemigo getComportamiento() {
        return comportamiento;
    }

	@Override
	public Sprite getSprite() {
		
		return this.sprite;
	}

	@Override
	public void setSprite(Sprite spr) {
		this.sprite = spr;
		
	}
}