package Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import jugador.Nave;

import Enemigos.Sistemas.SistemaMovimiento;
import Enemigos.Sistemas.SistemaDisparos;
import Enemigos.Sistemas.SistemaEstado;
import io.github.SpaceNav.Armas.EnemyBullet;

public class NaveEnemiga implements Mobs {
    
    private SistemaMovimiento movimiento;
    private SistemaDisparos disparos;
    private SistemaEstado estado;
    private Sprite sprite;
    
    public NaveEnemiga(int x, int y, int size, int xSpeed, Texture texturaNave, Texture balaTexture) {
        // Inicializar sprite
        this.sprite = new Sprite(texturaNave);
        this.sprite.setSize(size * 2, size * 2);
        this.sprite.setOriginCenter();
        
        //  Inicializar sistemas mediante composición
        this.movimiento = new SistemaMovimiento(x, y, xSpeed, sprite);
        this.disparos = new SistemaDisparos(4.0f, balaTexture);
        this.estado = new SistemaEstado();
        
        // Corrección de posición inicial
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
        
        // Actualizar movimiento
        movimiento.update(deltaTime);
        
        
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
    
    // ✅ Getters de la interfaz Mobs
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
}