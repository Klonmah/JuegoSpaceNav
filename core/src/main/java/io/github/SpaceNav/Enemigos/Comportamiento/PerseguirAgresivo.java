package io.github.SpaceNav.Enemigos.Comportamiento;

import io.github.SpaceNav.Enemigos.Mobs;
import io.github.SpaceNav.jugador.Nave;

public class PerseguirAgresivo implements ComportamientoEnemigo{
	private Nave jugador;
	private float distanciaPersecusion = 300f; //Distancia Minima Para que enemigo persiga
	private float velocidadEnemigo = 150f;
	
    public PerseguirAgresivo(Nave jugador) {
        this.jugador = jugador;
    }
    
    @Override
    public void iniciar(Mobs enemigo) {
        enemigo.setXSpeed(velocidadEnemigo); // Más rápido para persecución
    }
    
    @Override
    public void actualizar(Mobs enemigo, float delta) {
        float direccionX = jugador.getX() - enemigo.getX();
        float direccionY = jugador.getY() - enemigo.getY();
        float magnitud = (float) Math.sqrt(direccionX * direccionX + direccionY * direccionY);
        
        if (magnitud > 0) {
            direccionX /= magnitud;
            direccionY /= magnitud;
            
        }
            
            enemigo.setX(enemigo.getX()+direccionX * enemigo.getVelocidad() * delta);
            enemigo.setY(enemigo.getY()+direccionY * enemigo.getVelocidad() * delta);
        
        enemigo.getSprite().setPosition(direccionX, direccionY);
    }

	@Override
	public boolean estaCompletado() {
		// TODO Auto-generated method stub
		return false;
	}

}
