package io.github.SpaceNav.Pantallas; 

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.SpaceNav.Image;
import io.github.SpaceNav.SpaceNavigation;
import io.github.SpaceNav.Armas.WeaponSingle;
import io.github.SpaceNav.Armas.WeaponTriple;
import io.github.SpaceNav.Utilidades.BotonListener;
import io.github.SpaceNav.Utilidades.EstilosUI;

public class PantallaMenu implements Screen {
    private final SpaceNavigation game;
    private final Stage stage;
    private final Image logo;
    
    private TextButton botonJugar;
    private TextButton botonAjustes;
    private TextButton botonSalir;
    
    private float r = 0f, g = 0f, b = 0.2f;

    public PantallaMenu(SpaceNavigation game) {
        this.game = game;
        this.stage = new Stage();
        
        
        this.logo = new Image("../assets/LogoUniformUpscaled.png", 400, 400);
        
        EstilosUI.inicializar();
        Gdx.input.setInputProcessor(stage);
        crearPantalla();
    }

    private void crearPantalla() {
        this.botonJugar = new TextButton("JUGAR", EstilosUI.getEstiloBoton());
        this.botonAjustes = new TextButton("AJUSTES", EstilosUI.getEstiloBoton());
        this.botonSalir = new TextButton("Salir", EstilosUI.getEstiloBoton());
        
        this.botonJugar.setPosition(
            (Gdx.graphics.getWidth() - this.botonJugar.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - this.botonJugar.getHeight()) / 2f -50
        );

        Texture tx = new Texture(Gdx.files.internal("../assets/Rocket2.png"));
        Texture tx2 = new Texture(Gdx.files.internal("../assets/BombLowScaled.png"));
        
        botonJugar.addListener(new BotonListener(botonJugar, () -> {
            Screen ss = new PantallaJuego(game, 1, 3, 3, 0, 1, 1, 10, 1, 4f, new WeaponSingle(
                    tx,    // textura bala
                    tx2,    // textura bomb
                    0.3f
                ),6f); 
            ss.resize(1200, 800); 
            game.setScreen(ss); 
            dispose();
        }));
        
        stage.addActor(this.botonJugar);
        
        botonAjustes.setPosition(
            (Gdx.graphics.getWidth() - botonAjustes.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - botonAjustes.getHeight()) / 2f -100
        );

        botonAjustes.addListener(new BotonListener(botonAjustes, () -> {
            game.setScreen(new PantallaAjustes(this.game));
            dispose();
        }));
            
        stage.addActor(this.botonAjustes);
        
        this.botonSalir.setPosition(
            (Gdx.graphics.getWidth() - this.botonSalir.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - this.botonSalir.getHeight()) / 2f -150
        );

        botonSalir.addListener(new BotonListener(botonSalir, () -> {
            Gdx.app.exit(); 
        }));
            
        stage.addActor(this.botonSalir);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(r, g, b, 1);

        stage.act(delta);
        stage.draw();

    
        game.getBatch().begin();
        logo.render(game.getBatch());
        game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation!", 140, 400);
        game.getBatch().end();
    }

    public void setBackgroundColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        stage.dispose();
        logo.dispose();
    }
}