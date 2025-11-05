package io.github.SpaceNav.jugador;

abstract class HabilidadesDanioSelf {

	public final void applyDamage(Nave nave) {
        if (puedeRecibirDanio(nave)) {
            int cantidad = calcularDanio(nave);
            nave.decrementarVida(cantidad);
            nave.activarEfectoHerido();
            despuesDeDanio(nave);
        } else {
            ignorarDanio(nave);
        }
    }
	
	//Métodos abstracto
	public abstract boolean puedeRecibirDanio(Nave nave);
    public abstract int calcularDanio(Nave nave);
    //Métodos opcionales
    public void despuesDeDanio(Nave nave) {}
    public void ignorarDanio(Nave nave) {}
}
