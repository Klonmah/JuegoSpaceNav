package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;
    
    private int volumenMaestro = 80;
    private int volumenMusica = 50;
    
    private Map<String, Music> sonidos = new HashMap<>(); 

   

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // Setters 
    public void setVolumenMaestro(int volumen) {
        this.volumenMaestro = Math.max(0, Math.min(100, volumen));
        actualizarVolumenGlobal();
    }

    public void setVolumenMusica(int volumen) {
        this.volumenMusica = Math.max(0, Math.min(100, volumen));
        actualizarVolumenGlobal();
    }

    //Getters
    public int getVolumenMaestro() {
        return volumenMaestro;
    }

    public int getVolumenMusica() {
        return volumenMusica;
    }

    //Calcular volumen final
    private float getVolumenFinal() {
        return (volumenMusica * volumenMaestro / 100.0f) / 100.0f;
    }

    // Cargar Music
    public void cargarSonido(String nombre, String rutaArchivo) {
        Music sonido = Gdx.audio.newMusic(Gdx.files.internal(rutaArchivo));
        sonido.setLooping(false); 
        sonidos.put(nombre, sonido);
    }

    //  Reproducir sonido
    public void reproducirSonido(String nombre) {
        Music sonido = sonidos.get(nombre);
        if (sonido != null) {
            sonido.setVolume(getVolumenFinal());
            sonido.play();
        }
    }

    //  Reproducir sonido en loop (para música de fondo)
    public void reproducirEnLoop(String nombre) {
        Music sonido = sonidos.get(nombre);
        if (sonido != null) {
            sonido.setLooping(true);
            sonido.setVolume(getVolumenFinal());
            sonido.play();
        }
    }


    public void pararSonido(String nombre) {
        Music sonido = sonidos.get(nombre);
        if (sonido != null) {
            sonido.stop();
        }
    }

  
    public void pausarSonido(String nombre) {
        Music sonido = sonidos.get(nombre);
        if (sonido != null) {
            sonido.pause();
        }
    }

    // ✅ Actualizar volúmenes en tiempo real
    private void actualizarVolumenGlobal() {
        float volumenFinal = getVolumenFinal();
        for (Music sonido : sonidos.values()) {
            if (sonido.isPlaying()) {
                sonido.setVolume(volumenFinal);
            }
        }
    }

  
    public void silenciarTodo() {
        for (Music sonido : sonidos.values()) {
            sonido.pause();
        }
    }
    
    public void reanudarTodo() {
        for (Music sonido : sonidos.values()) {
            if (!sonido.isPlaying()) {
                sonido.play();
            }
        }
        actualizarVolumenGlobal();
    }
    
    public void dispose() {
        for (Music sonido : sonidos.values()) {
            sonido.dispose();
        }
        sonidos.clear();
    }
}
