package io.github.SpaceNav;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static float volumenMusica = 50f;
    private static float volumenEfectos = 50f;

    public static void setVolumenMusica(float v) {
        volumenMusica = v;
    }

    public static void setVolumenEfectos(float v) {
        volumenEfectos = v;
    }

    public static float getVolumenMusica() {
        return volumenMusica;
    }

    public static float getVolumenEfectos() {
        return volumenEfectos;
    }

    public static void aplicarVolumen(Music musica) {
        musica.setVolume(volumenMusica);
    }

    public static void reproducirEfecto(Sound efecto) {
        efecto.play(volumenEfectos);
    }
}