package Pantallas;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import Enemigos.NaveEnemiga;
import asteroides.Asteroid;
import Enemigos.Mobs;
import Enemigos.NaveCrasher;
import asteroides.Ball;
import asteroides.BallStrong;
import io.github.SpaceNav.Armas.Bomb;
import io.github.SpaceNav.Armas.Bullet;
import io.github.SpaceNav.Armas.EnemyBullet;
import jugador.Nave;
import io.github.SpaceNav.*;

public class PantallaJuego implements Screen , GameEventListener{

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

    private Nave nave;
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    private ArrayList<Mobs> enemies = new ArrayList<>();

    private ArrayList<Bullet> balas = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int bombs, int score,
                         int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantMobs) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;
    
        this.cantMobs=cantMobs;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        // Efectos de sonido
        AudioManager.getInstance().cargarSonido("explosion", "../assets/explosion.ogg");
        
       

        // Cargar nave
        nave = new Nave(Gdx.graphics.getWidth()/2-50, 30,
                new Texture(Gdx.files.internal("../assets/MainShip3.png")),
                new Texture(Gdx.files.internal("../assets/Rocket2.png")),
                new Texture(Gdx.files.internal("../assets/BombLowScaled.png"))
                );
        nave.setVidas(vidas);
        nave.setBombs(bombs);
        
      
        nave.setEventListener(this);
	     // Crear asteroides
	     Random r = new Random();
	     for (int i = 0; i < cantAsteroides; i++) {
	         int size = 20 + r.nextInt(10);
	         

	         // Centro de la pantalla
	         int centerX = Gdx.graphics.getWidth() / 2;
	         int centerY = Gdx.graphics.getHeight() / 2;

	         // Dispersarción aleatoria alrededor del centro
	         int dispersion = 200;
	         int x = centerX + r.nextInt(dispersion * 2) - dispersion;
	         int y = centerY + r.nextInt(dispersion * 2) - dispersion;
	         
	         Ball bb = new Ball(
	             x, y,
	             size,
	             velXAsteroides + r.nextInt(4),
	             velYAsteroides + r.nextInt(4),
	             new Texture(Gdx.files.internal("../assets/aGreyMedium4.png"))
	         );
	
	         asteroids.add(bb);

	     }

        // Asteroides fuertes
        for (int i = 0; i < cantAsteroides / 4; i++) {
            int size = 20 + r.nextInt(10);
      
      
            

	         // Centro de la pantalla
	         int centerX = Gdx.graphics.getWidth() / 2;
	         int centerY = Gdx.graphics.getHeight() / 2;

	         // Dispersación aleatoria alrededor del centro
	         int dispersion = 300; 
	         int x = centerX + r.nextInt(dispersion * 2) - dispersion;
	         int y = centerY + r.nextInt(dispersion * 2) - dispersion;

	     
	         BallStrong bb = new BallStrong(x, y, size,
	             velXAsteroides + r.nextInt(4),
	             velYAsteroides + r.nextInt(4),
	             new Texture(Gdx.files.internal("../assets/aGreyMedium4Red.png")));

            asteroids.add(bb);

        }
        
        //Naves Normales
        for(int i=0;i<cantMobs;i++) {
        	int size= 25;
        	int ancho=50;
        	int x=r.nextInt(Gdx.graphics.getWidth() - ancho);
        	int y = 420;
        	
        	NaveEnemiga nn= new NaveEnemiga(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("../assets/EnemyShip1.png")),
                    new Texture(Gdx.files.internal("../assets/EnemyBullet.png")));
        	
        	enemies.add(nn);
 
        }
        

        //Naves Crashers (sigen al jugador wuuuu)
        for(int i=0;i<cantMobs;i++) {
        	int size= 25;
        	int ancho=50;
        	int x=r.nextInt(Gdx.graphics.getWidth() - ancho);
        	int y = 800;
        	
        	NaveCrasher nn= new NaveCrasher(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("../assets/EnemyShip2.png")));
        	
        	enemies.add(nn);

        }
    }
    
    
    public void setPausa(boolean pausa) {
    	this.pausa = pausa;
    }

    // testeando nuevo volumen
  
    
    public void actualizarJuego(float delta) {
        // ✅ LLAMAR AL NUEVO MÉTODO SIN PantallaJuego
        nave.update(pausa);  // Ya no pasa 'this'

        // Si está en pausa, no actualizar nada más
        if (pausa) return;
        
        if (!nave.estaHerido()) {
            // Actualizar proyectiles
            updateProjectiles(delta);
            
            // Disparos enemigos
            updateEnemyShots(delta);
            
            // Sistema de colisiones
            SystemaColision.checkBulletAsteroidCollisions(balas, asteroids, this);
            SystemaColision.checkBulletEnemyCollisions(balas, enemies, this);
            SystemaColision.checkBombCollisions(bombs, asteroids, enemies, this);
            SystemaColision.checkAsteroidCollisions(asteroids);
            SystemaColision.checkPlayerCollisions(nave, asteroids, enemies, enemyBullets);
            
            // Movimiento
            updateMovements(delta);
            
            // Limpiar proyectiles destruidos
            cleanDestroyedProjectiles();
        }
    }

    private void updateProjectiles(float delta) {
        for (Bullet b : balas) b.update();
        for (Bomb b : bombs) b.update(delta);
        for (EnemyBullet e : enemyBullets) e.update();
    }

    private void updateEnemyShots(float delta) {
        for (int j = 0; j < enemies.size(); j++) {
            Mobs mob = enemies.get(j);
            mob.update(delta, nave);
            
            if (mob instanceof NaveEnemiga) {
                NaveEnemiga naveEnemiga = (NaveEnemiga) mob;
                EnemyBullet bala = naveEnemiga.getBala(delta);
                if (bala != null) {
                    // ✅ USAR EL NUEVO MÉTODO CON EVENTOS
                    onEnemyBulletFired(bala);
                }
            }
        }
    }

    private void updateMovements(float delta) {
        for (Asteroid ball : asteroids) ball.update();
        for (Mobs mob : enemies) mob.update(delta, nave);
    }

    private void cleanDestroyedProjectiles() {
        // Limpiar balas destruidas (iterar hacia atrás para evitar problemas de índice)
        for (int i = balas.size() - 1; i >= 0; i--) {
            if (balas.get(i).isDestroyed()) {
                balas.remove(i);
            }
        }
        
        // Limpiar bombas destruidas  
        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).isDestroyed()) {
                bombs.remove(i);
            }
        }
        
        // Limpiar balas enemigas destruidas
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {
            EnemyBullet e = enemyBullets.get(i);
            if (e != null && e.isDestroyed()) {
                enemyBullets.remove(i);
            }
        }
    }
    

    public void dibujarJuego() {
        batch.begin();
        dibujaEncabezado();
        for (Bullet b : balas) b.draw(batch);
        for (EnemyBullet e : enemyBullets) e.draw(batch);
        for (Bomb b : bombs) b.draw(batch);
        nave.draw(batch);
        for (Asteroid b : asteroids) b.draw(batch);
        for (Mobs b : enemies) b.draw(batch);
        batch.end();
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
           
            if (pausa == false) {
                game.setScreen(new PantallaPausa(game, this));
                this.pausa = true;
            }else {
            	this.pausa = false;
            }
        }

        // actualizar juego
        actualizarJuego(delta);

        // dibujar siempre, incluso si está en pausa
        dibujarJuego();

        // --- nivel completado o game over ---
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        if (asteroids.isEmpty()) {
            game.setScreen(new PantallaPerks(game, this, nave, ronda, score, velXAsteroides, velYAsteroides, cantAsteroides, cantMobs));
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
    @Override public void dispose() {  }

    public void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, "Bombas: " + nave.getBombs(), 10, 60);
        game.getFont().draw(batch, "Vidas: " + nave.getVidas() + " Ronda: " + ronda, 10, 30);
        game.getFont().draw(batch, "Score:" + score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(),
                Gdx.graphics.getWidth() / 2 - 100, 30);
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