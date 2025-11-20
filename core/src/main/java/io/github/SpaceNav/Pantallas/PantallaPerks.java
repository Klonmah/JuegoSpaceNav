package io.github.SpaceNav.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.SpaceNav.SpaceNavigation;
import io.github.SpaceNav.Armas.Weapon;
import io.github.SpaceNav.Utilidades.Perk;
import io.github.SpaceNav.Utilidades.PerkManager;
import io.github.SpaceNav.jugador.*;

public class PantallaPerks implements Screen {

    private SpaceNavigation game;
    private PantallaJuego juego;
    private SpriteBatch batch;
    private Nave nave;
    private int ronda;
    private int score;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int cantMobs;
    private Perk perk1;
    private Perk perk2;
    private Perk perk3;

    
    private com.badlogic.gdx.graphics.OrthographicCamera camera;
	private Weapon weapon;

    public PantallaPerks(SpaceNavigation game, PantallaJuego juego, Nave nave, int ronda, int score, 
                        int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantMobs) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.nave = nave;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;
        this.cantMobs = cantMobs;
        perk1 = PerkManager.obtenerPerkAleatorio();
        perk2 = PerkManager.obtenerPerkAleatorio();
        perk3 = PerkManager.obtenerPerkAleatorio();

        
        //Se ajusta la pantalla para la camara
        this.camera = new com.badlogic.gdx.graphics.OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar el juego de fondo
        this.juego.dibujarJuego();

        //CONFIGURAR BATCH CON LA CÁMARA DE ESTA PANTALLA
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        game.getFont().getData().setScale(3f);
        game.getFont().draw(batch, "ELIGE TUS PERKS",
                Gdx.graphics.getWidth() / 2 - 150,
                Gdx.graphics.getHeight() / 2 + 100);
        game.getFont().draw(batch, "1) " + perk1.getNombre(),  Gdx.graphics.getWidth()/2 - 400, Gdx.graphics.getHeight()/2);
        game.getFont().draw(batch, "2) " + perk2.getNombre(),  Gdx.graphics.getWidth()/2 - 400, Gdx.graphics.getHeight()/2 - 75);
        game.getFont().draw(batch, "3) " + perk3.getNombre(),  Gdx.graphics.getWidth()/2 - 400, Gdx.graphics.getHeight()/2 - 150);

        batch.end();

        // Control de volumen
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            game.setVolume(Math.min(1f, game.getVolume() + 0.1f));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            game.setVolume(Math.max(0f, game.getVolume() - 0.1f));
        }
        
        // PERKS
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            perk1.aplicar(nave);
            crearSiguienteNivel();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            perk2.aplicar(nave);
            crearSiguienteNivel();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            perk3.aplicar(nave);
            crearSiguienteNivel();
        }

    }

    private void crearSiguienteNivel() {
        Screen ss = new PantallaJuego(game, 
            ronda + 1, 
            nave.getVidas() + 1, 
            nave.getBombs(), 
            score, 
            velXAsteroides, 
            velYAsteroides, 
            cantAsteroides + 2,  
            cantMobs + 1,
            nave.getVelocidad(),
            nave.getWeapon(),
            nave.getMaxVelocidad()
        );
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();
    }

    @Override public void show() {}
    @Override 
    public void resize(int width, int height) {
        //Se actualiza la camara para redimensionar
        camera.setToOrtho(false, width, height);
        camera.update();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}