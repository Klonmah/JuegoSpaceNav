package io.github.SpaceNav.Pantallas.Utilidades;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EstilosUI {

    private static BitmapFont fuente;
    private static TextButton.TextButtonStyle estiloBoton;
    private static Slider.SliderStyle estiloSlider;

    public static void inicializar() {
        // --- Fuente general ---
        fuente = new BitmapFont();
        fuente.getData().setScale(2f);

        // --- Estilo de botón ---
        estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuente;
        estiloBoton.fontColor = Color.WHITE;
        estiloBoton.overFontColor = Color.CYAN;
        estiloBoton.downFontColor = Color.BLUE;

        // --- Estilo de slider ---
        Pixmap pmFondo = new Pixmap(200, 8, Pixmap.Format.RGBA8888);
        pmFondo.setColor(Color.DARK_GRAY);
        pmFondo.fillRectangle(0, 0, 200, 8);
        Texture fondo = new Texture(pmFondo);
        pmFondo.dispose();

        // Knob (el círculo que se mueve)
        Pixmap pmKnob = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pmKnob.setColor(Color.CYAN);
        pmKnob.fillCircle(10, 10, 10);
        Texture knob = new Texture(pmKnob);
        pmKnob.dispose();

        // Crear estilo del slider
        estiloSlider = new Slider.SliderStyle();
        estiloSlider.background = new TextureRegionDrawable(new TextureRegion(fondo));
        estiloSlider.knob = new TextureRegionDrawable(new TextureRegion(knob));

        // Agrega color al "relleno" cuando el knob pasa
        estiloSlider.knobBefore = new TextureRegionDrawable(new TextureRegion(fondo));
    }

    // Obtener estilo de botón
    public static TextButton.TextButtonStyle getEstiloBoton() {
        return estiloBoton;
    }

    // Obtener estilo de slider
    public static Slider.SliderStyle getEstiloSlider() {
        return estiloSlider;
    }

    // Liberar recursos
    public static void dispose() {
        if (fuente != null) fuente.dispose();
    }
}