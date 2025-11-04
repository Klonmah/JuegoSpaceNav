package io.github.SpaceNav.jugador;

public class danioNormal implements habilidadesDanio{

	@Override
	public void applyDamage(Nave nave) {
		nave.decrementarVida(1);
		nave.activarEfectoHerido();
	}
	
	
}
