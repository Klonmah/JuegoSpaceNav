package io.github.SpaceNav.Utilidades;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BotonListener extends ClickListener {
    private Runnable onClick;   // acción que se ejecuta al hacer clic
    private TextButton boton;   // referencia al botón que escucha

    public BotonListener(TextButton boton, Runnable onClick) {
        this.boton = boton;
        this.onClick = onClick;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        boton.setColor(1, 0.8f, 0.2f, 1); // cambia color al pasar el mouse
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        boton.setColor(1, 1, 1, 1); // vuelve al color normal
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (onClick != null) onClick.run(); // ejecuta la acción pasada
    }
}