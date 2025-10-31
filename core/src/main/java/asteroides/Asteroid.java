package asteroides;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import Enemigos.Destructible;
import io.github.SpaceNav.Colisionable;

public abstract class Asteroid implements Colisionable, Destructible {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;
    private Sound sonidoBala;
    private float cadencia;
    private float tiempoDesdeUltimoDisparo = 0f;
    private boolean destruido = false;
    private int vida;
    private int vidaMaxima;
    private int valorPuntos;

    public Asteroid(int x, int y, int size, int xSpeed, int ySpeed, Texture tx, int vida, int valorPuntos) {
        this.vida = vida;
        this.vidaMaxima = vida;
        this.valorPuntos = valorPuntos;
        
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2);
        spr.setOriginCenter();

        int ancho = (int) spr.getWidth();
        int alto = (int) spr.getHeight();

        // Corrección de posición
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = Gdx.graphics.getHeight() - 20;
        if (y > 0 && y < Gdx.graphics.getHeight() - 40) y = Gdx.graphics.getHeight() - 20;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    // Implementación de Destructible
    @Override
    public void takeDamage(int damage) {
        vida -= damage;
        if (vida <= 0) {
            this.destruido = true;
        }
    }
    
    @Override
    public boolean isDestroyed() {
        return destruido;
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
        return vidaMaxima;
    }
    
    public void update() {
        if (destruido) return;

        // Actualiza posición
        x += xSpeed;
        y += ySpeed;

        float ancho = spr.getWidth();
        float alto = spr.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // --- Rebote horizontal ---
        if (x < 0) {
            x = 0;
            xSpeed = Math.abs(xSpeed); // rebota hacia la derecha
        } else if (x + ancho > screenWidth) {
            x = (int) (screenWidth - ancho);
            xSpeed = -Math.abs(xSpeed); // rebota hacia la izquierda
        }

        // --- Rebote vertical ---
        if (y < 0) {
            y = 0;
            ySpeed = Math.abs(ySpeed); // rebota hacia arriba
        } else if (y + alto > screenHeight) {
            y = (int) (screenHeight - alto);
            ySpeed = -Math.abs(ySpeed); // rebota hacia abajo
        }

        spr.setPosition(x, y);
    }

    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
    
    @Override
    public void onColision() {
        this.destruido = true;
    }
    
    public boolean isDestruido() {
        return destruido;
    }
    
    public void draw(SpriteBatch batch) {
        if (!destruido) {
            spr.draw(batch);
        }
    }
    
    public void checkCollision(Colisionable otro) {
        if (!(otro instanceof Asteroid)) return; // Solo colisiona con otros asteroides
        
        Asteroid another = (Asteroid) otro;
        
        if (this.destruido || another.isDestruido()) return;
        
        // Calcular los centros de ambos
        float cx1 = getX() + getSprite().getWidth() / 2f;
        float cy1 = getY() + getSprite().getHeight() / 2f;
        float cx2 = another.getX() + another.getSprite().getWidth() / 2f;
        float cy2 = another.getY() + another.getSprite().getHeight() / 2f;

        // Diferencia de posición
        float dx = cx2 - cx1;
        float dy = cy2 - cy1;
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);

        float radio1 = getSprite().getWidth() / 2f;
        float radio2 = another.getSprite().getWidth() / 2f;

        // ¿Se tocan o superponen?
        if (distancia < radio1 + radio2) {
            
            // Normalizar el vector de colisión
            float nx = dx / distancia;
            float ny = dy / distancia;

            // Separarlas un poco para evitar vibración
            float overlap = (radio1 + radio2 - distancia) / 2f;
            setX((int) (getX() - (nx * overlap)));
            setY((int) (getY() - (ny * overlap)));
            another.setY((int) (another.getY() + (ny * overlap)));
            another.setX((int) (another.getX() + (nx * overlap)));
            getSprite().setPosition(getX(), getY());
            another.getSprite().setPosition(another.getX(), another.getY());

            // --- Rebote realista ---
            // Velocidades antes del impacto
            float vx1 = getXSpeed();
            float vy1 = getYSpeed();
            float vx2 = another.getXSpeed();
            float vy2 = another.getYSpeed();

            // Proyección de las velocidades sobre el eje de colisión
            float p1 = vx1 * nx + vy1 * ny;
            float p2 = vx2 * nx + vy2 * ny;

            // Supongamos masas iguales 
            float m1 = 1f;
            float m2 = 1f;

            // Fórmulas de colisión elástica 
            float p1Final = ((m1 - m2) * p1 + 2 * m2 * p2) / (m1 + m2);
            float p2Final = ((m2 - m1) * p2 + 2 * m1 * p1) / (m1 + m2);

            
            setXSpeed((int) (getXSpeed() + ((p1Final - p1) * nx + 2)));
            setYSpeed((int) (getYSpeed() + ((p1Final - p1) * ny + 2)));
            another.setXSpeed((int) (another.getXSpeed() + (p2Final - p2) * nx + 2));
            another.setYSpeed((int) (another.getYSpeed() + (p2Final - p2) * ny + 2));
        }
    }
    
    public int getXSpeed() {
        return xSpeed;
    }
    
    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    public int getYSpeed() {
        return ySpeed;
    }
    
    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public float getWidth() {
        return spr.getWidth();
    }

    public float getHeight() {
        return spr.getHeight();
    }
    
    public void setPosition(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
        spr.setPosition(x, y);
    }
    
    public Sprite getSprite() {
        return spr;
    }
}