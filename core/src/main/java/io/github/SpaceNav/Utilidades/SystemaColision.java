package io.github.SpaceNav.Utilidades;


import java.util.List;
import io.github.SpaceNav.Enemigos.*;
import io.github.SpaceNav.asteroides.*;
import io.github.SpaceNav.Armas.Bomb;
import io.github.SpaceNav.Armas.Bullet;
import io.github.SpaceNav.Armas.EnemyBullet;
import io.github.SpaceNav.jugador.*;
import io.github.SpaceNav.Enemigos.Destructible;
import io.github.SpaceNav.Pantallas.PantallaJuego;

public class SystemaColision {
    
    public static void revisarColisionBalaAsteroide(List<Bullet> balas, List<Asteroid> asteroids, PantallaJuego pantalla) {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroide = asteroids.get(j);
                if (b.checkCollision(asteroide)) {
                    AudioManager.getInstance().reproducirSonido("explosion");
                    manejarColisionDestructible(asteroide, asteroids, j, pantalla);
                    j--;
                }
            }
        }
    }
    
    public static void revisarColisionBalaEnemigo(List<Bullet> balas, List<Mobs> enemies, PantallaJuego pantalla) {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Mobs mob = enemies.get(j);
                if (b.checkCollision(mob)) {
                    AudioManager.getInstance().reproducirSonido("explosion");
                    manejarColisionDestructible(mob, enemies, j, pantalla);
                    j--;
                }
            }
        }
    }
    
    public static void revisarColisionesDeBombas(List<Bomb> bombs, List<Asteroid> asteroids, List<Mobs> enemies, PantallaJuego pantalla) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            
            // Colisiones con asteroides
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid asteroide = asteroids.get(j);
                if (b.checkCollision(asteroide)) {
                    AudioManager.getInstance().reproducirSonido("explosion");
                    manejarColisionDestructible(asteroide, asteroids, j, pantalla);
                    j--;
                }
            }
            
            // Colisiones con enemigos
            for (int j = 0; j < enemies.size(); j++) {
                Mobs mob = enemies.get(j);
                if (b.checkCollision(mob)) {
                    AudioManager.getInstance().reproducirSonido("explosion");
                    manejarColisionDestructible(mob, enemies, j, pantalla);
                    j--;
                }
            }
        }
    }
    
    public static void revisarColisionesDeAsteroides(List<Asteroid> asteroids) {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid ball1 = asteroids.get(i);
            for (int j = i + 1; j < asteroids.size(); j++) {
                Asteroid ball2 = asteroids.get(j);
                ball1.checkCollision(ball2);
            }
        }
    }

    
    public static void revisarColisionesDeJugador(Nave nave, List<Asteroid> asteroids, List<Mobs> enemies, List<EnemyBullet> enemyBullets, List<Portal> DiegoPortales) {
        // Colisión con asteroides
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid b = asteroids.get(i);
            if (nave.checkCollision(b)) {
                asteroids.remove(i);
                i--;
            }
        }
        
        // Colisión el Portal
        for (int i = 0; i < DiegoPortales.size(); i++) {
            Portal b = DiegoPortales.get(i);
            if (nave.checkCollision(b)) {
            	nave.setEnPortal(1);
            }
        }
        
        // Colisión con enemigos
        for (int i = 0; i < enemies.size(); i++) {
            Mobs b = enemies.get(i);
            if (nave.checkCollision(b)) {
                enemies.remove(i);
                i--;
            }
        }
        
        // Colisión con balas enemigas
        for (int i = 0; i < enemyBullets.size(); i++) {
            EnemyBullet e = enemyBullets.get(i);
            if (e != null && nave.checkCollision(e)) {
                enemyBullets.remove(i);
                i--;
            }
        }
    }
    
    private static void manejarColisionDestructible(Destructible target, List<?> list, int index, PantallaJuego pantalla) {
        target.takeDamage(1);
        if (target.isDestroyed()) {
            list.remove(index);
            pantalla.addScore(target.getScoreValue());
        }
    }

}