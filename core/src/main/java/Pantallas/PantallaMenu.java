package Pantallas; 

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.SpaceNav.AudioManager;

import io.github.SpaceNav.SpaceNavigation;

public class PantallaMenu implements Screen {
    private final SpaceNavigation game;
    private final Stage stage;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    private Texture logo;
    private TextButton botonJugar;
    private TextButton botonAjustes;
    private TextButton botonSalir;
    
    private float volumeGive = 0.5f;
    private float r = 0f, g = 0f, b = 0.2f; // color de fondo por defecto

    public PantallaMenu(SpaceNavigation game) {
        this.game = game;
        this.stage = new Stage();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1200, 800);
        this.font = new BitmapFont();
        EstilosUI.inicializar();
        
        Gdx.input.setInputProcessor(stage);
        crearPantalla();
    }

    private void crearPantalla() {
        
       
     
        
        // Color del texto normal y presionado
        font.getData().setScale(2f);
        
        
        this.botonJugar = new TextButton("JUGAR", EstilosUI.getEstiloBoton());
        this.botonAjustes = new TextButton("AJUSTES", EstilosUI.getEstiloBoton());
        this.botonSalir = new TextButton("Salir", EstilosUI.getEstiloBoton());
        
        
        /*Boton Jugar*/
        this.botonJugar.setPosition(
            (Gdx.graphics.getWidth() - this.botonJugar.getWidth()) / 2f ,
            (Gdx.graphics.getHeight() - this.botonJugar.getHeight()) / 2f -50
        );

        botonJugar.addListener(new BotonListener(botonJugar, () -> {
            Screen ss = new PantallaJuego(game,1,3,3,0,1,1,10,volumeGive,1); 
            ss.resize(1200, 800); 
            game.setScreen(ss); 
            dispose();
            
        }));
        
        stage.addActor(this.botonJugar);
        
        botonAjustes.setPosition(
                (Gdx.graphics.getWidth() - botonAjustes.getWidth()) / 2f  ,
                (Gdx.graphics.getHeight() - botonAjustes.getHeight()) / 2f -100
            );

        botonAjustes.addListener(new BotonListener(botonJugar, () -> {
        			game.setScreen(new PantallaAjustes(this.game));
                	
                	
                	dispose();
            }));
            
        stage.addActor(this.botonAjustes);
        
        this.botonSalir.setPosition(
                (Gdx.graphics.getWidth() - this.botonSalir.getWidth()) / 2f  ,
                (Gdx.graphics.getHeight() - this.botonSalir.getHeight()) / 2f -150
            );

        botonSalir.addListener(new BotonListener(botonJugar, () -> {
                	Gdx.app.exit(); 
            }));
            
        stage.addActor(this.botonSalir);
            
        
    }

    @Override
    public void show() {
        logo = new Texture(Gdx.files.internal("../assets/LogoUniformUpscaled.png"));
    }

    @Override
    public void render(float delta) {
        // 💡 Aquí puedes cambiar el color del fondo fácilmente:
        ScreenUtils.clear(r, g, b, 1);

        stage.act(delta);
        stage.draw();

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(logo, 400, 400);
        game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation!", 140, 400);
        game.getBatch().end();

       
    }

    // 💡 Método para cambiar el color de fondo desde esta clase
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
        font.dispose();
        logo.dispose();
    }
}