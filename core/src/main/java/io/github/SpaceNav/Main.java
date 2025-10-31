package io.github.SpaceNav;

import com.badlogic.gdx.ApplicationAdapter;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
 
    private Image imagen;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.imagen = new Image("libgdx.png",140,210);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        this.imagen.render(this.batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        imagen.dispose();
    }
}
