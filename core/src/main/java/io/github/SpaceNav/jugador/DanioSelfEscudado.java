package io.github.SpaceNav.jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DanioSelfEscudado extends HabilidadesDanioSelf {
    private boolean ignorarProximoHit=true;
    private Texture nuevaTexture = new Texture("../assets/MainShip3Shielded.png");
    private Sprite nuevoSprite = new Sprite(nuevaTexture);
    private Texture viejaTexture = new Texture("../assets/MainShip3.png");
    private Sprite viejoSprite = new Sprite(viejaTexture);
    
    public DanioSelfEscudado(Nave nave) {
    	nuevoSprite.setBounds(nave.getX(), nave.getY(), 45, 45);
        nuevoSprite.setOriginCenter();
        nave.setSprite(nuevoSprite);
    }

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
        viejoSprite.setBounds(nave.getX(), nave.getY(), 45, 45);
        viejoSprite.setOriginCenter();
        nave.setSprite(viejoSprite);
    }

    @Override
    public void ignorarDanio(Nave nave) {
        ignorarProximoHit = false; // Después de ignorar un daño, se desactiva el escudo
        nuevoSprite.setBounds(nave.getX(), nave.getY(), 45, 45);
        nuevoSprite.setOriginCenter();
        nave.setSprite(nuevoSprite);
    }
}
