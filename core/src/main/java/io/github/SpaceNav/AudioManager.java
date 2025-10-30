package io.github.SpaceNav;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static AudioManager instance;
    
    private int volumenMaestro;    
    private int volumenMusica;      
    private int volumenEfectos;    

    private AudioManager() {
        this.volumenMaestro = 50;  
        this.volumenMusica = 50;
        this.volumenEfectos = 50;
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    
    public void setVolumenMaestro(int volumen) {
        this.volumenMaestro = Math.max(0, Math.min(100, volumen));
        actualizarVolumenGlobal();
    }

    public void setVolumenMusica(int volumen) {
        this.volumenMusica = Math.max(0, Math.min(100, volumen));
        actualizarVolumenGlobal();
    }

    public void setVolumenEfectos(int volumen) {
        this.volumenEfectos = Math.max(0, Math.min(100, volumen));
        actualizarVolumenGlobal();
    }

    public int getVolumenMaestro() {
        return volumenMaestro;
    }

    public int getVolumenMusica() {
        return volumenMusica;
    }

    public int getVolumenEfectos() {
        return volumenEfectos;
    }

    // ✅ Métodos de conversión a float para LibGDX
    private float aFloat(int porcentaje) {
        return porcentaje / 100.0f;
    }

    // ✅ Calcular volúmenes finales (con maestro aplicado)
    public float getVolumenMusicaFinal() {
        return aFloat(volumenMusica * volumenMaestro / 100);
    }

    public float getVolumenEfectosFinal() {
        return aFloat(volumenEfectos * volumenMaestro / 100);
    }
    
    // ✅ Obtener volúmenes finales como int (0-100)
    public int getVolumenMusicaFinalInt() {
        return volumenMusica * volumenMaestro / 100;
    }

    public int getVolumenEfectosFinalInt() {
        return volumenEfectos * volumenMaestro / 100;
    }

    // ✅ Métodos de audio
    public void aplicarVolumen(Music musica) {
        if (musica != null) {
            musica.setVolume(getVolumenMusicaFinal());
        }
    }

    public long reproducirEfecto(Sound efecto) {
        if (efecto != null) {
            return efecto.play(getVolumenEfectosFinal());
        }
        return -1;
    }

    // ✅ Métodos útiles adicionales
    private void actualizarVolumenGlobal() {
        
    }
    
    
    //Para el Futuro
    public void pausarMusica(Music musica) {
        if (musica != null) {
            musica.pause();
        }
    }
    
    public void reanudarMusica(Music musica) {
        if (musica != null) {
            musica.play();
            musica.setVolume(getVolumenMusicaFinal());
        }
    }
}
