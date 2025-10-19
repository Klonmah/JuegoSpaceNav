package Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class NaveEnemiga implements Mobs{
	
	private int x;
    private int y;
    private int xSpeed;
    private Sprite spr;

    public NaveEnemiga(int x, int y, int size, int xSpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setSize(size * 2, size * 2); // el size es el radio
        spr.setOriginCenter();

        int ancho = (int) spr.getWidth();
        int alto = (int) spr.getHeight();

        // Corrige si el sprite estaría fuera de pantalla //quitado ancho y alto en el segundo y tercer if por como se dibujan los sprites
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = Gdx.graphics.getHeight()-20;
        if (y>0 && y<Gdx.graphics.getHeight()-40) y=Gdx.graphics.getHeight()-20;
        if (y > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        // Guardar posición corregida
        this.x = x;
        this.y = y;
        spr.setPosition(this.x, this.y);

        // Velocidad
        this.xSpeed = xSpeed;
    }
    
    public void update() {
        x += getXSpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        
        spr.setPosition(x, y);
    }
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    public void checkCollision(Mobs another) {
    	// Calcular los centros de ambos
        float cx1 = x + spr.getWidth() / 2f;
        float cy1 = y + spr.getHeight() / 2f;
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
            setX((int) (x-(nx * overlap)));
            setY((int) (y-(ny * overlap)));
            another.setY((int) (another.getY()+(ny*overlap)));
            another.setX((int) (another.getX()+(nx*overlap)));
            spr.setPosition(x, y);
            another.getSprite().setPosition(another.getX(), another.getY());

            // --- Rebote realista ---
            // Velocidades antes del impacto
            float vx1 = xSpeed;
            float vx2 = another.getXSpeed();
        }
    }
    
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
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
