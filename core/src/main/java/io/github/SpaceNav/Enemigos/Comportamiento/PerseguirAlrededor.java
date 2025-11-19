package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;


import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;

public class PerseguirAlrededor implements ComportamientoEnemigo {
    
    private Nave jugador;
    private float distanciaPersecusion = 10000f; // Distancia Minima Para que enemigo persiga
    private float velocidadEnemigo = 150f;
    private float distanciaMax = 300f; // Distancia a la que el enemigo tratara de posicionarse del jugador
    private float anguloActual = 0f;
    private float velocidadAngular = 90f; // Grados por segundo para el movimiento circular
    private boolean iniciado = false;
    
    public PerseguirAlrededor(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setXSpeed(velocidadEnemigo);
        // Calcular ángulo inicial basado en la posición relativa al jugador
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
            // Si estamos demasiado lejos, acercarse primero
            if (distanciaAlJugador > distanciaMax * 1.2f) {
                // Acercarse al jugador
                dx /= distanciaAlJugador;
                dy /= distanciaAlJugador;
                enemigo.setX(enemigo.getX() + dx * velocidadEnemigo * delta);
                enemigo.setY(enemigo.getY() + dy * velocidadEnemigo * delta);
            } 
            // Si estamos demasiado cerca, alejarse
            else if (distanciaAlJugador < distanciaMax * 0.8f) {
                // Alejarse del jugador
                dx /= distanciaAlJugador;
                dy /= distanciaAlJugador;
                enemigo.setX(enemigo.getX() - dx * velocidadEnemigo * delta);
                enemigo.setY(enemigo.getY() - dy * velocidadEnemigo * delta);
            }
            // Si estamos a la distancia correcta, orbitar
            else {
                // Calcular el ángulo actual relativo al jugador
                float anguloActual = (float) Math.toDegrees(Math.atan2(dy, dx));
                
                // Incrementar el ángulo para el movimiento orbital
                anguloActual += velocidadAngular * delta;
                
                // Calcular nueva posición orbital
                float nuevoAnguloRad = (float) Math.toRadians(anguloActual);
                float targetX = jugador.getX() - (float) Math.cos(nuevoAnguloRad) * distanciaMax;
                float targetY = jugador.getY() - (float) Math.sin(nuevoAnguloRad) * distanciaMax;
                
                // Mover directamente a la posición orbital
                enemigo.setX(targetX);
                enemigo.setY(targetY);
            }
        }
    }

    @Override
    public boolean estaCompletado() {
        // Este comportamiento es continuo, nunca se completa por sí solo
        return false;
    }

    // Métodos opcionales para configurar parámetros
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