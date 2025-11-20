package io.github.SpaceNav.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import io.github.SpaceNav.SpaceNavigation;
import io.github.SpaceNav.Utilidades.BotonListener;
import io.github.SpaceNav.Utilidades.EstilosUI;

public class PantallaPausa implements Screen {

    private SpaceNavigation game;
    private PantallaJuego juego;
    private SpriteBatch batch;
    private Stage pantalla;
    private TextButton botonMenuPrincipal;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;

    public PantallaPausa(SpaceNavigation game,
        PantallaJuego juego,
        OrthographicCamera camera,
        OrthogonalTiledMapRenderer renderer,
        SpriteBatch batch) {

		this.game = game;
		this.juego = juego;
		this.camera = camera;
		this.renderer = renderer;
		this.batch = batch;
		
		this.pantalla = new Stage();
		
		crearBotonMenuPrincipal();
	}


    private void crearBotonMenuPrincipal() {
        this.botonMenuPrincipal = new TextButton("Menú Principal", EstilosUI.getEstiloBoton());
        
        this.botonMenuPrincipal.setPosition(
            (Gdx.graphics.getWidth() - this.botonMenuPrincipal.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - this.botonMenuPrincipal.getHeight()) / 2f - 100
        );

        botonMenuPrincipal.addListener(new BotonListener(botonMenuPrincipal, () -> {
       
            game.mostrarMenu();
            juego.setPausa(false); // Restablece la pausa
            dispose();
        }));
        
        pantalla.addActor(this.botonMenuPrincipal);
        Gdx.input.setInputProcessor(pantalla);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Dibujar mapa
        camera.update();
        renderer.setView(camera);
        renderer.render();

        // Dibujar nave, enemigos, HUD, todo
        juego.dibujarJuego();


        // Dibujar menú de pausa encima
        // Usar la cámara del juego
        batch.setProjectionMatrix(juego.getCamera().combined);

        batch.begin();
        game.getFont().getData().setScale(3f);

        // Texto centrado en cámara
        game.getFont().draw(batch, "PAUSA",
                juego.getCamera().position.x - 80,
                juego.getCamera().position.y + 40);
        batch.end();

        // Dibujar botones
        pantalla.act(delta);
        pantalla.draw();

        // Input para reanudar (ESC)
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(juego);
            juego.setPausa(false);
        }
        
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.D)) {
            game.setScreen(new PantallaAjustes(this.game));
            juego.setPausa(false);
        }
        batch.setProjectionMatrix(juego.getCamera().combined);

    }

    @Override 
    public void show() {}

    @Override 
    public void resize(int width, int height) {
        pantalla.getViewport().update(width, height, true);
    }

    @Override 
    public void pause() {}

    @Override 
    public void resume() {}

    @Override 
    public void hide() {}

    @Override 
    public void dispose() {
        if (pantalla != null) {
            pantalla.dispose();
        }
    }
}