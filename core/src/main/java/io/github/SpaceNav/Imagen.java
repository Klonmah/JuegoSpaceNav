package io.github.SpaceNav;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Imagen {
	private Texture tex;
    private float x, y;

    public Imagen(String ruta, float x, float y) {
        tex = new Texture(ruta);
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, x, y);
    }
    
    public void dispose() {
    	this.tex.dispose();
    }
}

