package io.github.SpaceNav.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.SpaceNav.SpaceNavigation;
import io.github.SpaceNav.AudioManager;

public class PantallaAjustes implements Screen {

    private Stage pantalla;
    private Slider slider;
    private Label valorLabel;
    private final SpaceNavigation game;   
    private TextButton botonVolverMenu;
    private BitmapFont font;
    private AudioManager audioManager = AudioManager.getInstance();
    
    public PantallaAjustes(SpaceNavigation game) {
    	this.game = game;
    	
    }
    
    @Override
    public void show() {
        pantalla = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(pantalla);

        // Fuente para los textos
        this.font = new BitmapFont();
        
        this.slider = new Slider(0f, 100f, 1f, false, EstilosUI.getEstiloSlider());
        this.slider.setValue(audioManager.getVolumenMaestro());
        this.slider.setPosition(400, 300);

        // Etiqueta que muestra el valor
        valorLabel = new Label("Volumen: " + (int)this.slider.getValue(), new Label.LabelStyle(font, Color.WHITE));
        // Actualizar texto cuando se mueve el slider
        this.slider.addListener(event -> {
            valorLabel.setText("Volumen: " + (int)this.slider.getValue());
            return false;
        });

        //Layout con Table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(valorLabel).padBottom(20).row();
        table.add(slider).width(300).height(20);

        pantalla.addActor(table);
        
        
        this.botonVolverMenu = new TextButton("Salir", EstilosUI.getEstiloBoton());
        
        this.botonVolverMenu.setPosition(
                (Gdx.graphics.getWidth() - this.botonVolverMenu.getWidth()) / 2f  ,
                (Gdx.graphics.getHeight() - this.botonVolverMenu.getHeight()) / 2f -150
            );

        botonVolverMenu.addListener(new BotonListener(botonVolverMenu, () -> {
                game.mostrarMenu();
            }));
            
        pantalla.addActor(this.botonVolverMenu);
            
        
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        pantalla.act(delta);
        pantalla.draw();
        
        slider.addListener(event -> {
            float nuevoVolumen = slider.getValue() / 100f;
            game.setVolume(nuevoVolumen);
            valorLabel.setText("Volumen: " + (int)slider.getValue());
            audioManager.setVolumenMaestro((int)slider.getValue());
            
            return false;
        });
    }

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
        pantalla.dispose();
        font.dispose();
    }
}