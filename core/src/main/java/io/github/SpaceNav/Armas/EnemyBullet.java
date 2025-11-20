package io.github.SpaceNav.Armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import io.github.SpaceNav.Colisionable;
import io.github.SpaceNav.jugador.Nave;
import io.github.SpaceNav.Utilidades.MapaManager;

public class EnemyBullet implements Colisionable {
    private float x;
    private float y;
    private boolean destroyed = false;
    private Sprite spr;
    public float speed = 400f;
    
    // Variables para apuntar al jugador
    private float direccionX = 0f;
    private float direccionY = -1f;
    private Nave jugador;
    
    public EnemyBullet(float x, float y, Texture tx, Nave jugador) {
        this.x = x;
        this.y = y;
        this.jugador = jugador;
        spr = new Sprite(tx);
        spr.setSize(40, 80);
        spr.setOriginCenter();
        spr.setPosition(x, y);
        
        calcularDireccionAlJugador();
    }
    
    public void update() {
        // Movimiento hacia el jugador
        x += direccionX * speed * Gdx.graphics.getDeltaTime();
        y += direccionY * speed * Gdx.graphics.getDeltaTime();
        
        spr.setPosition(x, y);
        
        // Rotar el sprite para que apunte hacia el jugador
        float angulo = (float) Math.atan2(direccionY, direccionX) * MathUtils.radiansToDegrees;
        spr.setRotation(angulo - 90);
        

        int mapWidth = MapaManager.getInstance().getMapWidth();
        int mapHeight = MapaManager.getInstance().getMapHeight();
        
        // Destruir si sale del MAPA (no de la pantalla)
        if (y + spr.getHeight() < -30 || y > mapHeight + 30 ||
            x + spr.getWidth() < -30 || x > mapWidth + 30) {
            destroyed = true;
        }
    }
    
    private void calcularDireccionAlJugador() {
        float dx = jugador.getX() - x;
        float dy = jugador.getY() - y;
        
        // Normalizar la dirección
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);
        if (distancia > 0) {
            this.direccionX = dx / distancia;
            this.direccionY = dy / distancia;
        }
    }
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
    
    public boolean checkCollision(Colisionable colisionable) {
        boolean colisiona = !destroyed && spr.getBoundingRectangle().overlaps(colisionable.getArea());
        
        if (colisiona) {
            colisionable.onColision();
            this.destroyed = true;
        }
        
        return colisiona;
    }
    
    @Override
    public void onColision() {
        this.destroyed = true;
    }
    
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
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