package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Ball2 extends Asteroid {


    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	super(x, y, size, xSpeed, ySpeed, tx);
    	
        int ancho = (int) getSprite().getWidth();
        int alto = (int) getSprite().getHeight();

        // Corrige si el sprite estaría fuera de pantalla
        if (x < 0) x = 0;
        if (x + ancho > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - ancho;
        if (y < 0) y = 0;
        if (y + alto > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - alto;

        // Guardar posición corregida
        setX(x);
        setY(y);
        getSprite().setPosition(getX(), getY());
    }
    
    public void update() {
        setX(getX()+getXSpeed());
        setY(getY()+getYSpeed());

        if (getX()+getXSpeed() < 0 || getX()+getXSpeed()+getSprite().getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (getY()+getYSpeed() < 0 || getY()+getYSpeed() > Gdx.graphics.getHeight())
        	setYSpeed(getYSpeed() * -1);
        getSprite().setPosition(getX(), getY());
    }
    
    public Rectangle getArea() {
    	return getSprite().getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	getSprite().draw(batch);
    }
    
    public void checkCollision(Asteroid another) {
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
            setX((int) (getX()-(nx * overlap)));
            setY((int) (getY()-(ny * overlap)));
            another.setY((int) (another.getY()+(ny*overlap)));
            another.setX((int) (another.getX()+(nx*overlap)));
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

            // Supongamos masas iguales (si quieres, puedes hacer que dependa del tamaño)
            float m1 = 1f;
            float m2 = 1f;

            // Fórmulas de colisión elástica 1D (solo sobre el eje de impacto)
            float p1Final = ((m1 - m2) * p1 + 2 * m2 * p2) / (m1 + m2);
            float p2Final = ((m2 - m1) * p2 + 2 * m1 * p1) / (m1 + m2);

            // Cambiar solo la componente normal, mantener tangencial igual
            setXSpeed((int) (getXSpeed()+((p1Final - p1) * nx+2)));
            setYSpeed((int) (getYSpeed()+((p1Final - p1) * ny+2)));
            another.setXSpeed((int) (another.getXSpeed()+(p2Final - p2) * nx+2));
            another.setYSpeed((int) (another.getYSpeed()+(p2Final - p2) * ny+2));
        }
    }
	
	public float getWidth() {
	    return getSprite().getWidth();
	}

	public float getHeight() {
	    return getSprite().getHeight();
	}
	public void setPosition(float x, float y) {
	    setX((int) x);
	    setY((int) y);
	    getSprite().setPosition(x, y);
	}
    
}
