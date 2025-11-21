package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;
import io.github.SpaceNav.Utilidades.MapaManager; // Importar el Singleton

public class ComportamientoHuir implements ComportamientoEnemigo {
    
    private Nave jugador;
    private float velocidadHuir = 150f;
    private boolean iniciado = false;
    
    public ComportamientoHuir(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setVelocidad(velocidadHuir);
        iniciado = true;
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        if (!iniciado) iniciar(enemigo);
        
        float dx = jugador.getX() - enemigo.getX();
        float dy = jugador.getY() - enemigo.getY();
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);
        
        if (distancia > 0) {
  
            float dirX = dx / distancia;
            float dirY = dy / distancia;
            
  
            float nuevoX = enemigo.getX() - dirX * velocidadHuir * delta;
            float nuevoY = enemigo.getY() - dirY * velocidadHuir * delta;

        
            MapaManager mapa = MapaManager.getInstance();
            
          
            nuevoX = mapa.arreglarX(nuevoX, enemigo.getWidth());
            
       
            nuevoY = mapa.arreglarY(nuevoY, enemigo.getHeight());
            
       
            enemigo.setX(nuevoX);
            enemigo.setY(nuevoY);
            
    
            enemigo.getSprite().setPosition(nuevoX, nuevoY);
            
       
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
            enemigo.getSprite().setRotation(angle + 180 - 90);
        }
    }

    @Override
    public boolean estaCompletado() {
        return false; 
    }
}