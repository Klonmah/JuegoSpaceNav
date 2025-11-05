package io.github.SpaceNav.jugador;

public class DanioSelfNormal extends HabilidadesDanioSelf{

	@Override
	public boolean puedeRecibirDanio(Nave nave) {
		return true;
	}

	@Override
	public int calcularDanio(Nave nave) {
		return 1;
	}
}
