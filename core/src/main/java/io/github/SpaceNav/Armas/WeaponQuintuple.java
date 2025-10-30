package io.github.SpaceNav.Armas;



import com.badlogic.gdx.graphics.Texture;

import Pantallas.PantallaJuego;
import jugador.Nave;
import io.github.SpaceNav.AudioManager;
public class WeaponQuintuple extends Weapon {
    
    public WeaponQuintuple(Texture txBala, Texture txBomb, float cadencia) {
        super(txBala, txBomb, cadencia);
        AudioManager.getInstance().cargarSonido("disparoQuintuple", "../assets/pop-sound.mp3");
    }

    @Override
    public void fire(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (!puedeDisparar()) return; 
        
        crearDisparoQuintuple(nave, juego, puntaX, puntaY);
        reproducirSonidoDisparo();
        resetearDisparo(); 
    }
    
    @Override
    public void firebomb(Nave nave, PantallaJuego juego, float puntaX, float puntaY) {
        if (!puedeDisparar()) return;
        
        crearBomba(nave, juego, puntaX, puntaY);
        reproducirSonidoDisparo();
        resetearDisparo();
    }
    
    // ✅ MÉTODOS PRIVADOS para SRP
    private boolean puedeDisparar() {
        return this.getTiempoDesdeUltimoDisparo() >= this.getCadencia();
    }
    
    private void resetearDisparo() {
        this.setTiempoDesdeUltimoDisparo(0f);
    }
    
    private void reproducirSonidoDisparo() {
        AudioManager.getInstance().reproducirSonido("disparoQuintuple");
    }
    
    private void crearDisparoQuintuple(Nave nave, PantallaJuego juego, float x, float y) {
        juego.agregarBala(new Bullet(x, y, this.getTxBala(), nave.getRotacion() - 20));
        juego.agregarBala(new Bullet(x, y, this.getTxBala(), nave.getRotacion() - 10));
        juego.agregarBala(new Bullet(x, y, this.getTxBala(), nave.getRotacion()));
        juego.agregarBala(new Bullet(x, y, this.getTxBala(), nave.getRotacion() + 10));
        juego.agregarBala(new Bullet(x, y, this.getTxBala(), nave.getRotacion() + 20));
    }
    
    private void crearBomba(Nave nave, PantallaJuego juego, float x, float y) {
        juego.agregarBomb(new Bomb(x, y, this.getTxBomb(), nave.getRotacion()));
    }
}