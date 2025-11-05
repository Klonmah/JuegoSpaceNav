package io.github.SpaceNav.jugador;

public class DanioSelfInvencible extends HabilidadesDanioSelf {

	@Override
	public boolean puedeRecibirDanio(Nave nave) {
		return false;
	}

	@Override
	public int calcularDanio(Nave nave) {
		return 0;
	}
    
}
