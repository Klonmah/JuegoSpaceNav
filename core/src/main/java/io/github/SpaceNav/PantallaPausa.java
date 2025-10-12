package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaPausa implements Screen {

    private SpaceNavigation game;
    private PantallaJuego juego;
    private SpriteBatch batch;

    public PantallaPausa(SpaceNavigation game, PantallaJuego juego) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        this.juego.dibujarJuego();

        // dibujar menú de pausa encima
        batch.begin();
        game.getFont().getData().setScale(3f);
        game.getFont().draw(batch, "PAUSA",
                Gdx.graphics.getWidth() / 2 - 100,
                Gdx.graphics.getHeight() / 2);
        batch.end();

        // input para reanudar
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(juego);
            juego.setPausa(false); // restablece la pausa
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}