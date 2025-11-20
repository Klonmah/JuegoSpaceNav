package io.github.SpaceNav;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.SpaceNav.Pantallas.PantallaMenu;
import io.github.SpaceNav.Utilidades.AudioManager;
import io.github.SpaceNav.Pantallas.PantallaAjustes;


public class SpaceNavigation extends Game {
	private SpriteBatch batch;
	private BitmapFont font;
	private int highScore;	
	private Music gameMusic;
	private float volumeGive = 0.5f;

	public void create() {
		
		AudioManager.getInstance().cargarSonido("MusicaFondo", "../assets/song-loop.wav",1);
		AudioManager.getInstance().reproducirEnLoop("MusicaFondo");
	
		highScore = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(); // usa Arial font x defecto
		font.getData().setScale(2f);
		Screen ss = new PantallaMenu(this);
		this.setScreen(ss);
	}

	public void mostrarMenu() {
		this.setScreen(new PantallaMenu(this));
	}
	
	public void mostrarAjustes() {
		this.setScreen(new PantallaAjustes(this));
	}
	
	//cambiar volumen
	public void setVolume(float v) {
    volumeGive = v;
    if (gameMusic != null) {
        gameMusic.setVolume(volumeGive);
    	}
    }
    
    public float getVolume() {
        return volumeGive;
    }

	public void render() {
		super.render(); 
	}

	public void dispose() {
		this.batch.dispose();
		this.font.dispose();
		AudioManager.getInstance().dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	
	

}