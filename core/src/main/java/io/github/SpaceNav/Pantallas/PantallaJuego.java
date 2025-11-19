package io.github.SpaceNav.Pantallas;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import io.github.SpaceNav.Enemigos.*;

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
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;

    private boolean pausa = false;
    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int cantMobs;
  
    
    private Nave nave;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private ArrayList<Mobs> enemies = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private ArrayList<Portal> DiegoPortales = new ArrayList<>();
    private int SeCreoPortal = 0;
    


    private Texture texturaNave;
    private Texture texturaRocket;
    private Texture texturaBomb;
  
    private FabricaEntidadesJuego fabricaEntidades;
	private float mapWidth;
	private float mapHeight;
 



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

        // Cargar texturas de la nave y fondo
        cargarTexturasPropias();
        
        // Inicializar fábrica
        fabricaEntidades = new FabricaEntidadesJuego();

        // Efectos de sonido
        AudioManager.getInstance().cargarSonido("explosion", "../assets/explosion.ogg");

        // Crear nave con las texturas propias
        nave = new Nave(Gdx.graphics.getWidth()/2-50, 30,
                texturaNave,
                texturaRocket,
                texturaBomb
        );
        nave.setVidas(vidas);
        nave.setBombs(bombs);
        nave.setEventListener(this);

        // Crear asteroides y enemigos usando la fábrica
        asteroids = fabricaEntidades.crearAsteroides(cantAsteroides, velXAsteroides, velYAsteroides);
        enemies = fabricaEntidades.crearEnemigos(cantMobs, velXAsteroides, nave);
    }


    private void cargarTexturasPropias() {
        texturaNave = new Texture(Gdx.files.internal("../assets/MainShip3.png"));
        texturaRocket = new Texture(Gdx.files.internal("../assets/Rocket2.png"));
        texturaBomb = new Texture(Gdx.files.internal("../assets/BombLowScaled.png"));
    }





    
    public void setPausa(boolean pausa) {
    	this.pausa = pausa;
    }

    
    public void actualizarJuego(float delta) {
        nave.update(pausa, mapWidth, mapHeight );  

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
            balas.get(i).update(mapWidth,mapHeight);
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

        camera.update();

        // DIBUJAR MAPA PRIMERO
        renderer.setView(camera);
        // --- HACER QUE LA CÁMARA SIGA AL JUGADOR ---
        camera.position.set(
                nave.getX(),
                nave.getY(),
                0
        );
        camera.update();

        // --- RENDER MAPA ---
        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (!pausa) {
                game.setScreen(new PantallaPausa(game, this));
                pausa = true;
            } else {
                pausa = false;
            }
        }

        // LUEGO actualizas tu juego
        actualizarJuego(delta);

        // Finalmente dibujas los sprites
        batch.setProjectionMatrix(camera.combined);
        dibujarJuego();

        if (!pausa) {
            verificarFinDeJuego();
        }
    }



    private Portal crearPortalFinDelJuego() {
        SeCreoPortal = 1;
        return fabricaEntidades.crearPortal(velXAsteroides, velYAsteroides);
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

    @Override
    public void show() {

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        mapa = new TmxMapLoader().load("../assets/SpaceNavMapa.tmx", params);

        renderer = new OrthogonalTiledMapRenderer(mapa);

        MapProperties props = mapa.getProperties();

        int tileWidth = props.get("tilewidth", Integer.class);
        int tileHeight = props.get("tileheight", Integer.class);
        int mapWidthInTiles = props.get("width", Integer.class);
        int mapHeightInTiles = props.get("height", Integer.class);

        mapWidth = mapWidthInTiles * tileWidth;
        mapHeight = mapHeightInTiles * tileHeight;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
    }

    

    public void resize(int width, int height) {
    	camera.viewportWidth = width;
    	camera.viewportHeight = height;
    	camera.update();
    }
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
        
        if (mapa != null) mapa.dispose();
        if (renderer != null) renderer.dispose();
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