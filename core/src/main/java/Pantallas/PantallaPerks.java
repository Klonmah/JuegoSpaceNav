package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.SpaceNav.Nave;
import io.github.SpaceNav.SpaceNavigation;

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
    private float volumeMenu;
    
    public PantallaPerks(SpaceNavigation game, PantallaJuego juego, Nave nave, int ronda, int score, int velXAsteroides, int velYAsteroides, int cantAsteroides, float volumeMenu) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.nave = nave;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;
        this.volumeMenu = volumeMenu;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        this.juego.dibujarJuego();

        // dibujar menú de pausa encima
        batch.begin();
        game.getFont().getData().setScale(3f);
        game.getFont().draw(batch, "ELIGE TUS PERKS",
                Gdx.graphics.getWidth() / 2 - 150,
                Gdx.graphics.getHeight() / 2 + 100);
        game.getFont().draw(batch, "Presiona O para mas vida.",
                Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2 );
        game.getFont().draw(batch, "Presiona P para mas bombas.",
                Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2 -75);
        game.getFont().draw(batch, "Presiona U y J para cambiar volumen...\nEscape para ignorar",
                Gdx.graphics.getWidth() / 2 - 400,
                Gdx.graphics.getHeight() / 2 -200);
        batch.end();

		if ( Gdx.input.isKeyJustPressed(Input.Keys.U )) {
		    game.setVolume(game.getVolume() + 0.1f);
			if (game.getVolume() > 1f)
			{
				game.setVolume(1f);
			}
		}
		else if ( Gdx.input.isKeyJustPressed(Input.Keys.J )) {
			if (game.getVolume() > 0.1f)
			{
			    game.setVolume(game.getVolume() - 0.1f);
			}else{game.setVolume(0f);}
		}
		
        // input para reanudar
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.O)) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas()+ 1, nave.getBombs(), score,
                    velXAsteroides + 1, velYAsteroides + 1, cantAsteroides + 6, volumeMenu);
            ss.resize(1200, 800);
            game.setScreen(ss);
			dispose();
        }

        // input para reanudar
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), nave.getBombs()+ 1, score,
                    velXAsteroides + 1, velYAsteroides + 1, cantAsteroides + 6, volumeMenu);
            ss.resize(1200, 800);
            game.setScreen(ss);
			dispose();
        }

        // input para reanudar
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), nave.getBombs(), score,
                    velXAsteroides + 1, velYAsteroides + 1, cantAsteroides + 6, volumeMenu);
            ss.resize(1200, 800);
            game.setScreen(ss);
			dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}