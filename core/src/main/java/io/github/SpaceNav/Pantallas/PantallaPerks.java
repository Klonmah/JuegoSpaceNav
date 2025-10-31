package io.github.SpaceNav.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.SpaceNav.SpaceNavigation;
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar el juego de fondo
        this.juego.dibujarJuego();

        // Dibujar menú de perks
        batch.begin();
        game.getFont().getData().setScale(3f);
        game.getFont().draw(batch, "ELIGE TUS PERKS",
                Gdx.graphics.getWidth() / 2 - 150,
                Gdx.graphics.getHeight() / 2 + 100);
        game.getFont().draw(batch, "Presiona O para mas vida.",
                Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2);
        game.getFont().draw(batch, "Presiona P para mas bombas.",
                Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2 - 75);
        batch.end();

        // Control de volumen
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            game.setVolume(Math.min(1f, game.getVolume() + 0.1f));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            game.setVolume(Math.max(0f, game.getVolume() - 0.1f));
        }
        
        // PERKS
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            crearSiguienteNivel(1, 0); // +1 vida
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            crearSiguienteNivel(0, 1); // +1 bomba
        } 
    }

    private void crearSiguienteNivel(int vidasExtra, int bombsExtra) {
        Screen ss = new PantallaJuego(game, 
            ronda + 1, 
            nave.getVidas() + vidasExtra, 
            nave.getBombs() + bombsExtra, 
            score, 
            velXAsteroides + 1, 
            velYAsteroides + 1, 
            cantAsteroides + 6,  
            cantMobs + 2
        );
        ss.resize(1200, 800);
        game.setScreen(ss);
        dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}