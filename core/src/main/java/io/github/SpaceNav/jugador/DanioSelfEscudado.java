package io.github.SpaceNav.jugador;

public class DanioSelfEscudado extends HabilidadesDanioSelf {
    private boolean ignorarProximoHit=true;

	@Override
	public boolean puedeRecibirDanio(Nave nave) {
		return !ignorarProximoHit;
	}

	@Override
	public int calcularDanio(Nave nave) {
		return 1;
	}

	@Override
    public void despuesDeDanio(Nave nave) {
        ignorarProximoHit = true; // Después de recibir daño, se reactiva el escudo
    }

    @Override
    public void ignorarDanio(Nave nave) {
        ignorarProximoHit = false; // Después de ignorar un daño, se desactiva el escudo
    }
}
