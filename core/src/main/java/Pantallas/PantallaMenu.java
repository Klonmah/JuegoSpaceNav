package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.SpaceNav.SpaceNavigation;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture logo;
	private float volumeGive;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);


	    game.getBatch().begin();
	    game.getBatch().draw(logo, 400, 400);

		game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 400);
		game.getFont().draw(game.getBatch(), "Presiona Space para comenzar, U y J para cambiar volumen...", 100, 300);
	
		game.getBatch().end();
		volumeGive = 0.5f;

		if ( Gdx.input.isKeyJustPressed(Input.Keys.U )) {
			volumeGive += 0.1f;
			if (volumeGive > 1f)
			{
				volumeGive = 1f;
			}
		    game.setVolume(volumeGive);
		}
		else if ( Gdx.input.isKeyJustPressed(Input.Keys.J )) {
			volumeGive -= 0.1f;
			if (volumeGive < 0f)
			{
				volumeGive = 0f;
			}
		    game.setVolume(volumeGive);
		}  else if ( Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,10,volumeGive);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
        logo = new Texture(Gdx.files.internal("../assets/LogoUniformUpscaled.png"));
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        logo.dispose();
		
	}
   
}