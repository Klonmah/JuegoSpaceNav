package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;

public class ComportamientoPerseguirAgresivo implements ComportamientoEnemigo {
    private Nave jugador;
    private float velocidadEnemigo = 150f;
    
    public ComportamientoPerseguirAgresivo(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setXSpeed(velocidadEnemigo);
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        float direccionX = jugador.getX() - enemigo.getX();
        float direccionY = jugador.getY() - enemigo.getY();
        float magnitud = (float) Math.sqrt(direccionX * direccionX + direccionY * direccionY);
        
        if (magnitud > 0) {
            direccionX /= magnitud; 
            direccionY /= magnitud;
            
            enemigo.setX(enemigo.getX() + direccionX * velocidadEnemigo * delta);
            enemigo.setY(enemigo.getY() + direccionY * velocidadEnemigo * delta);
            
           
            enemigo.getSprite().setPosition(enemigo.getX(), enemigo.getY());
            
           
            float angle = (float) Math.toDegrees(Math.atan2(direccionY, direccionX));
            enemigo.getSprite().setRotation(angle - 90); 
        }
    }

    @Override
    public boolean estaCompletado() {
        return false; 
    }
}