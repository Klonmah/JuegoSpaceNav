package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;

public class PerseguirAgresivo implements ComportamientoEnemigo{
	private Nave jugador;
	private float distanciaPersecusion = 300f; //Distancia Minima Para que enemigo persiga
	private float velocidadEnemigo = 100f;
	
    public PerseguirAgresivo(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setXSpeed(velocidadEnemigo); // Más rápido para persecución
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        float dx = jugador.getX() - enemigo.getX();
        float dy = jugador.getY() - enemigo.getY();
        float distancia = (float) Math.sqrt(dx * dx + dy * dy);
        
        if (distancia < distanciaPersecusion && distancia > 20f) {
            // Normalizar dirección y mover
            dx /= distancia;
            dy /= distancia;
            
            enemigo.setX(enemigo.getX()+dx * enemigo.getVelocidad() * delta);
            enemigo.setX(enemigo.getY()+dy * enemigo.getVelocidad() * delta);
        }
    }

	@Override
	public boolean estaCompletado() {
		// TODO Auto-generated method stub
		return false;
	}

}
