package io.github.SpaceNav.jugador;

public class danioEscudado implements habilidadesDanio {
    private int ignorarProximoHit=0;

    @Override
    public void applyDamage(Nave nave) {
    	if(ignorarProximoHit!=0)
    	{
    		nave.decrementarVida(1);
    		nave.activarEfectoHerido();
    		ignorarProximoHit=0;
    	}
    	else
    	{
    		ignorarProximoHit=1;
    		
    	}
    }
}
