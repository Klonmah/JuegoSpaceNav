package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;

public class ComportamientoSecuencial implements ComportamientoEnemigo {
    
    private Nave jugador;
    
    
    private ComportamientoEnemigo estrategiaLejana;  
    private ComportamientoEnemigo estrategiaCercana; 
    private ComportamientoEnemigo estrategiaHuir;    
    
    private ComportamientoEnemigo comportamientoActual;

    public ComportamientoSecuencial(Nave jugador, 
                                    ComportamientoEnemigo estrategiaLejana, 
                                    ComportamientoEnemigo estrategiaCercana, 
                                    ComportamientoEnemigo estrategiaHuir) {
        this.jugador = jugador;
        this.estrategiaLejana = estrategiaLejana;
        this.estrategiaCercana = estrategiaCercana;
        this.estrategiaHuir = estrategiaHuir;
        
        this.comportamientoActual = estrategiaLejana;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        comportamientoActual.iniciar(enemigo);
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        ComportamientoEnemigo nuevaEstrategia = determinarEstrategia(enemigo);

       
        if (nuevaEstrategia != comportamientoActual) {
            comportamientoActual = nuevaEstrategia;
            comportamientoActual.iniciar(enemigo);
        }

     
        comportamientoActual.actualizar(enemigo, delta);
    }

 
    private ComportamientoEnemigo determinarEstrategia(Mobs enemigo) {
 
        if (enemigo.getHp() < 2) {
            return estrategiaHuir;
        }
        float distancia = calcularDistancia(enemigo, jugador);
        if (distancia < 400f) { 
            return estrategiaCercana;
        }
        return estrategiaLejana;
    }
    @Override
    public boolean estaCompletado() {
        return false; 
    }
    
    private float calcularDistancia(Mobs enemigo, Nave jugador) {
        float dx = jugador.getX() - enemigo.getX();
        float dy = jugador.getY() - enemigo.getY();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}