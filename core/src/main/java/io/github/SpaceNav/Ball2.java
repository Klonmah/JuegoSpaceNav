package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Ball2 {
	private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private Sprite spr;

    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	spr = new Sprite(tx);
    	this.x = x; 
 	
        //validar que borde de esfera no quede fuera
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        //validar que borde de esfera no quede fuera
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
        spr.setPosition(x, y);
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
    }
    public void update() {
        x += getXSpeed();
        y += getySpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (y+getySpeed() < 0 || y+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
        	setySpeed(getySpeed() * -1);
        spr.setPosition(x, y);
    }
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }
    
    public void checkCollision(Ball2 b2) {
        // Calcular los centros de ambos
        float cx1 = this.x + spr.getWidth() / 2f;
        float cy1 = this.y + spr.getHeight() / 2f;
        float cx2 = b2.x + b2.spr.getWidth() / 2f;
        float cy2 = b2.y + b2.spr.getHeight() / 2f;

        // Diferencia de posición
        float dx = cx2 - cx1;
        float dy = cy2 - cy1;
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);

        float radio1 = spr.getWidth() / 2f;
        float radio2 = b2.spr.getWidth() / 2f;

        // ¿Se tocan o superponen?
        if (distancia < radio1 + radio2) {
            // Normalizar el vector de colisión
            float nx = dx / distancia;
            float ny = dy / distancia;

            // Separarlas un poco para evitar vibración
            float overlap = (radio1 + radio2 - distancia) / 2f;
            this.x -= nx * overlap;
            this.y -= ny * overlap;
            b2.x += nx * overlap;
            b2.y += ny * overlap;
            spr.setPosition(this.x, this.y);
            b2.spr.setPosition(b2.x, b2.y);

            // --- Rebote realista ---
            // Velocidades antes del impacto
            float vx1 = this.xSpeed;
            float vy1 = this.ySpeed;
            float vx2 = b2.xSpeed;
            float vy2 = b2.ySpeed;

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
            this.xSpeed += (p1Final - p1) * nx+3;
            this.ySpeed += (p1Final - p1) * ny+3;
            b2.xSpeed += (p2Final - p2) * nx+3;
            b2.ySpeed += (p2Final - p2) * ny+3;
        }
    }
	public int getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getySpeed() {
		return ySpeed;
	}
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public float getWidth() {
	    return spr.getWidth();
	}

	public float getHeight() {
	    return spr.getHeight();
	}
    
}
