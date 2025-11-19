package io.github.SpaceNav.Pantallas;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.github.SpaceNav.asteroides.*;
import io.github.SpaceNav.Enemigos.*;
import io.github.SpaceNav.Enemigos.Comportamiento.PerseguirAgresivo;
import io.github.SpaceNav.Enemigos.Comportamiento.PerseguirAlrededor;
import io.github.SpaceNav.jugador.Nave;
import io.github.SpaceNav.asteroides.Portal;

public class FabricaEntidadesJuego {
    
    // Configuraciones
    private int tamanioAsteroide = 20;
    private int variacionTamanioAsteroide = 10;
    private int tamanioEnemigo = 25;
    private int spawnEnemigoX = 50;
    private int dispersionAsteroideNormal = 200;
    private int dispersionAsteroideStrong = 300;
    private int variacionVelocidad = 4;
    
    // Texturas
    private Texture texturaAsteroide;
    private Texture texturaAsteroideStrong;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigoCrusher;
    private Texture texturaBalaEnemiga;
    private Texture texturaPortal;
    
    public FabricaEntidadesJuego() {
        cargarTexturas();
    }
    
    private void cargarTexturas() {
        texturaAsteroide = new Texture(Gdx.files.internal("../assets/aGreyMedium4.png"));
        texturaAsteroideStrong = new Texture(Gdx.files.internal("../assets/aGreyMedium4Red.png"));
        texturaEnemigo1 = new Texture(Gdx.files.internal("../assets/EnemyShip1.png"));
        texturaEnemigoCrusher = new Texture(Gdx.files.internal("../assets/EnemyShip2.png"));
        texturaBalaEnemiga = new Texture(Gdx.files.internal("../assets/EnemyBullet.png"));
        texturaPortal = new Texture(Gdx.files.internal("../assets/PortalTriste.png"));
    }
    
    // Método para crear todos los asteroides de una vez
    public ArrayList<Asteroid> crearAsteroides(int cantAsteroides, int velXAsteroides, int velYAsteroides) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        Random r = new Random();
        
        // Asteroides normales
        for (int i = 0; i < cantAsteroides; i++) {
            Asteroid asteroid = crearAsteroideNormal(velXAsteroides, velYAsteroides, r);
            asteroids.add(asteroid);
        }

        // Asteroides fuertes
        for (int i = 0; i < cantAsteroides / 4; i++) {
            Asteroid asteroid = crearAsteroideFuerte(velXAsteroides, velYAsteroides, r);
            asteroids.add(asteroid);
        }
        
        return asteroids;
    }
    
    // Método para crear todos los enemigos de una vez
    public ArrayList<Mobs> crearEnemigos(int cantMobs, int velXAsteroides, Nave naveJugador) {
        ArrayList<Mobs> enemies = new ArrayList<>();
        Random r = new Random();
        
        // Naves Normales
        for(int i = 0; i < cantMobs; i++) {
            Mobs enemigo = crearNaveNormal(velXAsteroides, naveJugador, r);
            enemies.add(enemigo);
        }
        
        // Naves Crashers
        for(int i = 0; i < cantMobs; i++) {
            Mobs enemigo = crearNaveCrasher(velXAsteroides, naveJugador, r);
            enemies.add(enemigo);
        }
        
        return enemies;
    }
    
    // Métodos privados para creación individual
    private Asteroid crearAsteroideNormal(int velXAsteroides, int velYAsteroides, Random r) {
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideNormal, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new Ball(
            posicion[0], posicion[1], size, velX, velY, texturaAsteroide
        );
    }
    
    private Asteroid crearAsteroideFuerte(int velXAsteroides, int velYAsteroides, Random r) {
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideStrong, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new BallStrong(
            posicion[0], posicion[1], size, velX, velY, texturaAsteroideStrong
        );
    }
    
    private Mobs crearNaveNormal(int velXAsteroides, Nave naveJugador, Random r) {
        int size = tamanioEnemigo;
        int x = r.nextInt(Gdx.graphics.getWidth() - spawnEnemigoX);
        int vel = velXAsteroides + r.nextInt(variacionVelocidad);
        
        NaveEnemiga enemigo = new NaveEnemiga(
            x, 620, size, vel, texturaEnemigo1, texturaBalaEnemiga
        );
        PerseguirAlrededor comportamiento = new PerseguirAlrededor(naveJugador);
        enemigo.setComportamiento(comportamiento);
        return enemigo;
    }
    
    private Mobs crearNaveCrasher(int velXAsteroides, Nave naveJugador, Random r) {
        int size = tamanioEnemigo;
        int x = r.nextInt(Gdx.graphics.getWidth() - spawnEnemigoX);
        int vel = velXAsteroides + r.nextInt(variacionVelocidad);
        
        Mobs enemigo = new NaveCrasher(x, 800, size, vel, texturaEnemigoCrusher);
        enemigo.setComportamiento(new PerseguirAgresivo(naveJugador));
        return enemigo;
    }
    
    // Método para crear portal
    public Portal crearPortal(int velXAsteroides, int velYAsteroides) {
        Random r = new Random();
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideNormal, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new Portal(
            posicion[0], posicion[1], size, velX, velY, texturaPortal
        );
    }
    
    // Método auxiliar para calcular posición
    private int[] calcularPosicionCentrada(int dispersion, Random r) {
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;
        
        int x = centerX + r.nextInt(dispersion * 2) - dispersion;
        int y = centerY + r.nextInt(dispersion * 2) - dispersion;
        
        return new int[]{x, y};
    }
    
    // Método para liberar recursos
    public void dispose() {
        if (texturaAsteroide != null) texturaAsteroide.dispose();
        if (texturaAsteroideStrong != null) texturaAsteroideStrong.dispose();
        if (texturaEnemigo1 != null) texturaEnemigo1.dispose();
        if (texturaEnemigoCrusher != null) texturaEnemigoCrusher.dispose();
        if (texturaBalaEnemiga != null) texturaBalaEnemiga.dispose();
        if (texturaPortal != null) texturaPortal.dispose();
    }
}