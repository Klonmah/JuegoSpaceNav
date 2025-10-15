package Pantallas;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import asteroides.Asteroid;
import asteroides.Ball;
import asteroides.BallStrong;
import io.github.SpaceNav.Nave;
import io.github.SpaceNav.SpaceNavigation;
import io.github.SpaceNav.Armas.Bomb;
import io.github.SpaceNav.Armas.Bullet;
import io.github.SpaceNav.*;

public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private boolean pausa = false;
    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private float volumeMenu;

    private Nave nave;
    private ArrayList<Asteroid> asteroids1 = new ArrayList<>();
    private ArrayList<Asteroid> asteroids2 = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    // bomb
    private ArrayList<Bomb> bombs = new ArrayList<>();

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
                         int velXAsteroides, int velYAsteroides, int cantAsteroides, float volumen) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;
        this.volumeMenu = volumen;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        // Efectos de sonido
        
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("../assets/explosion.ogg"));
        explosionSound.setVolume(1, volumeMenu);

        // Cargar nave
        nave = new Nave(Gdx.graphics.getWidth()/2-50, 30,
                new Texture(Gdx.files.internal("../assets/MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("../assets/hurt.ogg")),
                new Texture(Gdx.files.internal("../assets/Rocket2.png")),
                new Texture(Gdx.files.internal("../assets/BombLowScaled.png")),
                Gdx.audio.newSound(Gdx.files.internal("../assets/pop-sound.mp3")));
        nave.setVidas(vidas);

        // Crear asteroides
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            int size = 20 + r.nextInt(10);
            int ancho = size * 2;
            int alto = size * 2;
            int x = r.nextInt(Gdx.graphics.getWidth() - ancho);
            int y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50 - alto);

            Ball bb = new Ball(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    velYAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("../assets/aGreyMedium4.png")));

            asteroids1.add(bb);
            asteroids2.add(bb);
        }

        // Asteroides fuertes
        for (int i = 0; i < cantAsteroides / 4; i++) {
            int size = 20 + r.nextInt(10);
            int ancho = size * 2;
            int alto = size * 2;
            int x = r.nextInt(Gdx.graphics.getWidth() - ancho);
            int y = 50 + r.nextInt(Gdx.graphics.getHeight() - 50 - alto);

            BallStrong bb = new BallStrong(x, y, size,
                    velXAsteroides + r.nextInt(4),
                    velYAsteroides + r.nextInt(4),
                    new Texture(Gdx.files.internal("../assets/aGreyMedium4Red.png")));

            asteroids1.add(bb);
            asteroids2.add(bb);
        }
    }
    
    
    public void setPausa(boolean pausa) {
    	this.pausa = pausa;
    }

    // testeando nuevo volumen
    public float getVolume() {
    	return this.volumeMenu;
    }
    public void setVolume(int v) {
    	this.volumeMenu = v;
    }
    
    public void actualizarJuego(float delta) {
        // --- actualizar nave con pausa ---
        nave.update(pausa, this);

        // Si está en pausa, no actualizar nada más
        if (pausa) return;
        
        
        
        if (!nave.estaHerido()) {
            // Colisiones balas - asteroides
            for (int i = 0; i < balas.size(); i++) {
                Bullet b = balas.get(i);
                b.update();
                for (int j = 0; j < asteroids1.size(); j++) {
                    Asteroid asteroide = asteroids1.get(j);
                    if (b.checkCollision(asteroide)) {
                        long explosionId = explosionSound.play();
                        explosionSound.setVolume(explosionId, 0.1f);

                        if (asteroide instanceof Ball) {
                            asteroids1.remove(j);
                            asteroids2.remove(j);
                            j--;
                        } else if (asteroide instanceof BallStrong) {
                            BallStrong ball = (BallStrong) asteroide;
                            ball.getHit();
                            if (ball.getHp() <= 0) {
                                asteroids1.remove(j);
                                asteroids2.remove(j);
                                j--;
                            }
                        }
                        score += 10;
                    }
                }
                if (b.isDestroyed()) {
                    balas.remove(i);
                    i--;
                }
            }
            // Colisiones bombs - asteroides
            for (int i = 0; i < bombs.size(); i++) {
                Bomb b = bombs.get(i);
                b.update(delta);
                for (int j = 0; j < asteroids1.size(); j++) {
                    Asteroid asteroide = asteroids1.get(j);
                    if (b.checkCollision(asteroide)) {
                        long explosionId = explosionSound.play();
                        explosionSound.setVolume(explosionId, 0.1f);

                        if (asteroide instanceof Ball) {
                            asteroids1.remove(j);
                            asteroids2.remove(j);
                            j--;
                        } else if (asteroide instanceof BallStrong) {
                            BallStrong ball = (BallStrong) asteroide;
                            ball.getHit();
                            if (ball.getHp() <= 0) {
                                asteroids1.remove(j);
                                asteroids2.remove(j);
                                j--;
                            }
                        }
                        score += 5;
                    }
                }
                if (b.isDestroyed()) {
                	bombs.remove(i);
                    i--;
                }
            }

            // Movimiento asteroides
            for (Asteroid ball : asteroids1) ball.update();

            // Colisiones asteroides entre sí
            for (int i = 0; i < asteroids1.size(); i++) {
                Asteroid ball1 = asteroids1.get(i);
                for (int j = 0; j < asteroids2.size(); j++) {
                    Asteroid ball2 = asteroids2.get(j);
                    if (i < j) ball1.checkCollision(ball2);
                }
            }

            // Colisión asteroides - nave
            for (int i = 0; i < asteroids1.size(); i++) {
                Asteroid b = asteroids1.get(i);
                if (nave.checkCollision(b)) {
                    asteroids1.remove(i);
                    asteroids2.remove(i);
                    i--;
                }
            }
        }
    }
    
    // --- dibujar el juego ---
    public void dibujarJuego() {
        batch.begin();
        dibujaEncabezado();
        for (Bullet b : balas) b.draw(batch);
        for (Bomb b : bombs) b.draw(batch);
        nave.draw(batch);
        for (Asteroid b : asteroids1) b.draw(batch);
        batch.end();
    }

    @Override
    public void render(float delta) {
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
            Screen ss = new PantallaGameOver(game, volumeMenu);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

        if (asteroids1.isEmpty()) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
                    velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 6, volumeMenu);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    // bomb
    public boolean agregarBomb(Bomb bb) {
        return bombs.add(bb);
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { this.explosionSound.dispose(); }

    public void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, "Bombas: " + nave.getBombs(), 10, 60);
        game.getFont().draw(batch, "Vidas: " + nave.getVidas() + " Ronda: " + ronda, 10, 30);
        game.getFont().draw(batch, "Score:" + score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(),
                Gdx.graphics.getWidth() / 2 - 100, 30);
    }
}