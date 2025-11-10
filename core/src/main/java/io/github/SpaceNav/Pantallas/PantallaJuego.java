package io.github.SpaceNav.Pantallas;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import io.github.SpaceNav.Enemigos.*;
import io.github.SpaceNav.Enemigos.Comportamiento.PerseguirAgresivo;
import io.github.SpaceNav.asteroides.*;
import io.github.SpaceNav.Armas.Bomb;
import io.github.SpaceNav.Armas.Bullet;
import io.github.SpaceNav.Armas.EnemyBullet;
import io.github.SpaceNav.jugador.*;
import io.github.SpaceNav.*;


public class PantallaJuego implements Screen, GameEventListener {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private boolean pausa = false;
    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int cantMobs;

    private int tamanioAsteroide = 20;
    private int variacionTamanioAsteroide = 10;
    private int tamanioEnemigo = 25;
    private int spawnEnemigoX = 50;
    //private int spawnEnemigoY = 50;
    private int dispersionAsteroideNormal = 200;
    private int dispersionAsteroideStrong = 300;
    private int variacionVelocidad = 4;
    
    private Nave nave;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Mobs> enemies = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private ArrayList<Portal> DiegoPortales = new ArrayList<>();
    private int SeCreoPortal = 0;
    

    private Texture texturaAsteroide;
    private Texture texturaAsteroideStrong;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigoCrusher;
    private Texture texturaBalaEnemiga;
    private Texture texturaNave;
    private Texture texturaRocket;
    private Texture texturaBomb;
    private Texture texturaPortal;
    private boolean texturasCargadas = false;
    private Texture texturaFondo;
    
 
    private void cargarTexturas() {
        if (!texturasCargadas) {
        	texturaFondo = new Texture(Gdx.files.internal("../assets/fondo.jpg"));
            texturaAsteroide = new Texture(Gdx.files.internal("../assets/aGreyMedium4.png"));
            texturaAsteroideStrong = new Texture(Gdx.files.internal("../assets/aGreyMedium4Red.png"));
            texturaEnemigo1 = new Texture(Gdx.files.internal("../assets/EnemyShip1.png"));
            texturaEnemigoCrusher = new Texture(Gdx.files.internal("../assets/EnemyShip2.png"));
            texturaBalaEnemiga = new Texture(Gdx.files.internal("../assets/EnemyBullet.png"));
            texturaNave = new Texture(Gdx.files.internal("../assets/MainShip3.png"));
        	texturaRocket = new Texture(Gdx.files.internal("../assets/Rocket2.png"));
        	texturaBomb = new Texture(Gdx.files.internal("../assets/BombLowScaled.png"));
        	texturaPortal = new Texture(Gdx.files.internal("../assets/PortalTriste.png"));
            texturasCargadas = true;
        }
    }

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int bombs, int score,
            	int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantMobs) {
    	this.game = game;
    	this.ronda = ronda;
    	this.score = score;
    	this.velXAsteroides = velXAsteroides;
    	this.velYAsteroides = velYAsteroides;
    	this.cantAsteroides = cantAsteroides;
    	this.cantMobs = cantMobs;

    	batch = game.getBatch();
    	camera = new OrthographicCamera();
    	camera.setToOrtho(false, 800, 640);

    	cargarTexturas();

    	// Efectos de sonido
    	AudioManager.getInstance().cargarSonido("explosion", "../assets/explosion.ogg");

    	
    	

    	nave = new Nave(Gdx.graphics.getWidth()/2-50, 30,
    			texturaNave,
    			texturaRocket,
    			texturaBomb
    			);
    	nave.setVidas(vidas);
    	nave.setBombs(bombs);
    	nave.setEventListener(this);

    	crearAsteroides();
    	crearEnemigos();
    }
    private void crearAsteroides() {
        Random r = new Random();
        
        // Asteroides normales
        for (int i = 0; i < cantAsteroides; i++) {
            Asteroid asteroid = crearAsteroideNormal(r);
            asteroids.add(asteroid);
        }

        // Asteroides fuertes
        for (int i = 0; i < cantAsteroides / 4; i++) {
            Asteroid asteroid = crearAsteroideFuerte(r);
            asteroids.add(asteroid);
        }
    }
    

    private Asteroid crearAsteroideNormal(Random r) {
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideNormal, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new Ball(
            posicion[0], posicion[1], size, velX, velY, texturaAsteroide
        );
    }
    
   
    private Asteroid crearAsteroideFuerte(Random r) {
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideStrong, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new BallStrong(
            posicion[0], posicion[1], size, velX, velY, texturaAsteroideStrong
        );
    }
    

    private int[] calcularPosicionCentrada(int dispersion, Random r) {
        int centerX = Gdx.graphics.getWidth() / 2;
        int centerY = Gdx.graphics.getHeight() / 2;
        
        int x = centerX + r.nextInt(dispersion * 2) - dispersion;
        int y = centerY + r.nextInt(dispersion * 2) - dispersion;
        
        return new int[]{x, y};
    }
    

    private void crearEnemigos() {
        Random r = new Random();
        
        // Naves Normales
        for(int i = 0; i < cantMobs; i++) {
            Mobs enemigo = crearNaveNormal(r);
            enemies.add(enemigo);
        }
        
        // Naves Crashers
        for(int i = 0; i < cantMobs; i++) {
            Mobs enemigo = crearNaveCrasher(r);
            enemies.add(enemigo);
            enemigo.setComportamiento(new PerseguirAgresivo(nave));
        }
    }
    

    private Mobs crearNaveNormal(Random r) {
        int size = tamanioEnemigo;
        int x = r.nextInt(Gdx.graphics.getWidth() - spawnEnemigoX);
        int vel = velXAsteroides + r.nextInt(variacionVelocidad);
        
        return new NaveEnemiga(
            x, 620, size, vel, texturaEnemigo1, texturaBalaEnemiga
        );
    }
    

    private Mobs crearNaveCrasher(Random r) {
        int size = tamanioEnemigo;
        int x = r.nextInt(Gdx.graphics.getWidth() - spawnEnemigoX);
        int vel = velXAsteroides + r.nextInt(variacionVelocidad);
        
        return new NaveCrasher(
            x, 800, size, vel, texturaEnemigoCrusher
        );
    }

    
    public void setPausa(boolean pausa) {
    	this.pausa = pausa;
    }

    
    public void actualizarJuego(float delta) {
        nave.update(pausa);  

        if (pausa) return;
        
        if (!nave.estaHerido()) {
            actualizarProyectiles(delta);
            actualizarBalasEnemigas(delta);
            
  
            SystemaColision.revisarColisionBalaAsteroide(balas, asteroids, this);
            SystemaColision.revisarColisionBalaEnemigo(balas, enemies, this);
            SystemaColision.revisarColisionesDeBombas(bombs, asteroids, enemies, this);
            SystemaColision.revisarColisionesDeAsteroides(asteroids);
            SystemaColision.revisarColisionesDeJugador(nave, asteroids, enemies, enemyBullets, DiegoPortales);
            
            actualizarMovimientos(delta);
            limpiarProyectilesDestruidos();
        }
    }

    private void actualizarProyectiles(float delta) {

        for (int i = 0; i < balas.size(); i++) {
            balas.get(i).update();
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update(delta);
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update();
        }
    }

    private void actualizarBalasEnemigas(float delta) {
      
        for (int j = 0; j < enemies.size(); j++) {
            Mobs mob = enemies.get(j);
            mob.update(delta, nave);
            
            if (mob instanceof NaveEnemiga) {
                NaveEnemiga naveEnemiga = (NaveEnemiga) mob;
                EnemyBullet bala = naveEnemiga.getBala(delta);
                if (bala != null) {
                    onEnemyBulletFired(bala);
                }
            }
        }
    }

    private void actualizarMovimientos(float delta) {
       
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update(delta, nave);
        }
    }


    private void limpiarProyectilesDestruidos() {
        //el remove if funciona para java 11, es para eliminar una clase si pasa lo que esta dentro del parentesis
        balas.removeIf(Bullet::isDestroyed);
        bombs.removeIf(Bomb::isDestroyed);
        enemyBullets.removeIf(bala -> bala != null && bala.isDestroyed());
        
       
        asteroids.removeIf(asteroide -> asteroide.isDestroyed());
        enemies.removeIf(enemigo -> enemigo.isDestroyed());
    }


    public void dibujarJuego() {
        batch.begin();
        
        batch.draw(texturaFondo, 0, 0, Gdx.graphics.getWidth()*1.5f, Gdx.graphics.getHeight()*1.5f);
        dibujaEncabezado();
        
        
        
        // Dibujar todos los proyectiles primero 
        for (int i = 0; i < balas.size(); i++) {
            balas.get(i).draw(batch);
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).draw(batch);
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).draw(batch);
        }
        
        //Dibujar la nave 
        nave.draw(batch);
        
        //Dibujar asteroides 
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(batch);
        }
        
        // Dibujar enemigos 
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(batch);
        }

        // Dibujar enemigos 
        for (int i = 0; i < DiegoPortales.size(); i++) {
        	DiegoPortales.get(i).draw(batch);
        }
        
        batch.end();
    }

    public void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);
        

        game.getFont().draw(batch, "Bombas: " + nave.getBombs(), 10, 60);
        game.getFont().draw(batch, "Vidas: " + nave.getVidas() + " Ronda: " + ronda, 10, 30);
        game.getFont().draw(batch, "Score:" + score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(),
                Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

  
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (!pausa) {
                game.setScreen(new PantallaPausa(game, this));
                pausa = true;
            } else {
                pausa = false;
            }
        }

        actualizarJuego(delta);
        dibujarJuego();


        if (!pausa) {
            verificarFinDeJuego();
        }
    }


    private Portal crearPortalFinDelJuego() {
    	SeCreoPortal = 1;
        Random r = new Random();
        int size = tamanioAsteroide + r.nextInt(variacionTamanioAsteroide);
        int[] posicion = calcularPosicionCentrada(dispersionAsteroideNormal, r);
        
        int velX = velXAsteroides + r.nextInt(variacionVelocidad);
        int velY = velYAsteroides + r.nextInt(variacionVelocidad);
        
        return new Portal(
            posicion[0], posicion[1], size, velX, velY, texturaPortal
        );
    }
    
    private void verificarFinDeJuego() {
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen gameOverScreen = new PantallaGameOver(game);
            gameOverScreen.resize(1200, 800);
            game.setScreen(gameOverScreen);
            dispose();
        } else if (asteroids.isEmpty() && SeCreoPortal == 0) {
        	DiegoPortales.add(crearPortalFinDelJuego());
        } else if (Nave.getEnPortal() == 1){
            game.setScreen(new PantallaPerks(game, this, nave, ronda, score, 
                velXAsteroides, velYAsteroides, cantAsteroides, cantMobs));
            dispose();
        }
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }
    
    public boolean agregarBalaEnemiga(EnemyBullet bb) {
    	System.out.println("Enemy Bullet added: " + enemyBullets.size());
        return enemyBullets.add(bb);
    }

    // bomb
    public boolean agregarBomb(Bomb bb) {
        return bombs.add(bb);
    }
    public void addScore(int points) {
        this.score += points;
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override 
    public void dispose() { 
        // Limpiar colecciones
        asteroids.clear();
        enemies.clear();
        balas.clear();
        bombs.clear();
        enemyBullets.clear();
        DiegoPortales.clear();
        
        // LIBERAR texturas
        if (texturaNave != null) {
            texturaNave.dispose();
            texturaNave = null;
        }
        if (texturaRocket != null) {
            texturaRocket.dispose();
            texturaRocket = null;
        }
        if (texturaBomb != null) {
            texturaBomb.dispose();
            texturaBomb = null;
        }
    }


    @Override
    public void onBulletFired(Bullet bullet) {
        balas.add(bullet);
    }
    
    @Override
    public void onBombFired(Bomb bomb) {
        bombs.add(bomb);
    }
    
    @Override
    public void onEnemyBulletFired(EnemyBullet bullet) {
        enemyBullets.add(bullet);
    }
    
    @Override
    public void onScoreChanged(int points) {
        score += points;
    }
    
    @Override
    public void onEntityDestroyed() {
        AudioManager.getInstance().reproducirSonido("explosion");
    }
}