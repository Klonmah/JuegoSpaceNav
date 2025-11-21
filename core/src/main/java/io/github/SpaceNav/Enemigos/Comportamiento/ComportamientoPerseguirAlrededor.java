package io.github.SpaceNav.Enemigos.Comportamiento;

import com.badlogic.gdx.math.MathUtils;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;




public class ComportamientoPerseguirAlrededor implements ComportamientoEnemigo {
    
    private Nave jugador;
    private float distanciaPersecusion = 10000f; 
    private float velocidadEnemigo = 150f;
    private float distanciaMax = 300f; 
    private float anguloActual = 0f;
    private float velocidadAngular = 90f; 
    private boolean iniciado = false;
    
    public ComportamientoPerseguirAlrededor(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setXSpeed(velocidadEnemigo);
        float dx = enemigo.getX() - jugador.getX();
        float dy = enemigo.getY() - jugador.getY();
        anguloActual = (float) Math.toDegrees(Math.atan2(dy, dx));
        iniciado = true;
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        float dx = jugador.getX() - enemigo.getX();
        float dy = jugador.getY() - enemigo.getY();
        float distanciaAlJugador = (float) Math.sqrt(dx * dx + dy * dy);
        
    
        if (distanciaAlJugador < distanciaPersecusion) {
            if (distanciaAlJugador > distanciaMax * 1.2f) {
                // Acercarse
                dx /= distanciaAlJugador;
                dy /= distanciaAlJugador;
                enemigo.setX(enemigo.getX() + dx * velocidadEnemigo * delta);
                enemigo.setY(enemigo.getY() + dy * velocidadEnemigo * delta);
            } 
            else if (distanciaAlJugador < distanciaMax * 0.8f) {
           
                dx /= distanciaAlJugador;
                dy /= distanciaAlJugador;
                enemigo.setX(enemigo.getX() - dx * velocidadEnemigo * delta);
                enemigo.setY(enemigo.getY() - dy * velocidadEnemigo * delta);
            }
            else {
                // Orbitar
                anguloActual += velocidadAngular * delta;
                float nuevoAnguloRad = (float) Math.toRadians(anguloActual);
                float targetX = jugador.getX() + (float) Math.cos(nuevoAnguloRad) * distanciaMax; 
                float targetY = jugador.getY() + (float) Math.sin(nuevoAnguloRad) * distanciaMax;
                
                enemigo.setX(targetX);
                enemigo.setY(targetY);
            }
        }


        

        enemigo.getSprite().setPosition(enemigo.getX(), enemigo.getY());

        
        float deltaX = jugador.getX() - enemigo.getX();
        float deltaY = jugador.getY() - enemigo.getY();
        
    
        float angle = MathUtils.atan2(deltaY, deltaX) * MathUtils.radiansToDegrees;

     
        enemigo.getSprite().setRotation(angle + 90); 
    }

    @Override
    public boolean estaCompletado() {
        return false;
    }
    public void setDistanciaPersecusion(float distancia) {
        this.distanciaPersecusion = distancia;
    }
    
    public void setVelocidadEnemigo(float velocidad) {
        this.velocidadEnemigo = velocidad;
    }
    
    public void setDistanciaMax(float distancia) {
        this.distanciaMax = distancia;
    }
    
    public void setVelocidadAngular(float velocidad) {
        this.velocidadAngular = velocidad;
    }
}