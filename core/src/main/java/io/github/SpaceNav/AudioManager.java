package io.github.SpaceNav;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class AudioManager {
    private static AudioManager instance;
    
    private int volumenMaestro = 80;
    private int volumenMusica = 50;
    

    private Map<String, List<Music>> gruposSonidos = new HashMap<>();
    private Map<String, Music> sonidosUnicos = new HashMap<>(); // Para música y sonidos únicos

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

    // Getters
    public int getVolumenMaestro() {
        return volumenMaestro;
    }

    public int getVolumenMusica() {
        return volumenMusica;
    }

    // Calcular volumen final
    private float getVolumenFinal() {
        return (volumenMusica * volumenMaestro / 100.0f) / 100.0f;
    }

    // Cargar sonido con agrupación automática
    public void cargarSonido(String nombre, String rutaArchivo) {
        cargarSonido(nombre, rutaArchivo, 3); // Por defecto 3 instancias para efectos
    }
    
    // Cargar sonido con número específico de instancias
    public void cargarSonido(String nombre, String rutaArchivo, int numInstancias) {
        if (numInstancias <= 1) {
            // Sonido único (para música de fondo, etc.)
            Music sonido = Gdx.audio.newMusic(Gdx.files.internal(rutaArchivo));
            sonido.setLooping(false);
            sonidosUnicos.put(nombre, sonido);
        } else {
            // Grupo de sonidos (para explosiones, disparos, etc.)
            List<Music> grupo = new ArrayList<>();
            for (int i = 0; i < numInstancias; i++) {
                Music sonido = Gdx.audio.newMusic(Gdx.files.internal(rutaArchivo));
                sonido.setLooping(false);
                grupo.add(sonido);
            }
            gruposSonidos.put(nombre, grupo);
        }
    }


    public void reproducirSonido(String nombre) {
        float volumenFinal = getVolumenFinal();
        
        // Primero verificar si es un sonido único
        Music sonidoUnico = sonidosUnicos.get(nombre);
        if (sonidoUnico != null) {
            sonidoUnico.setVolume(volumenFinal);
            if (!sonidoUnico.isPlaying()) {
                sonidoUnico.play();
            } else {
                // Si ya está sonando, lo reiniciamos
                sonidoUnico.stop();
                sonidoUnico.play();
            }
            return;
        }
        
        // Si no es único, buscar en los grupos
        List<Music> grupo = gruposSonidos.get(nombre);
        if (grupo != null && !grupo.isEmpty()) {
            // BUSCAR un sonido que no esté reproduciéndose
            for (Music sonido : grupo) {
                if (!sonido.isPlaying()) {
                    sonido.setVolume(volumenFinal);
                    sonido.play();
                    return;
                }
            }
            
       
            Music sonidoMasViejo = grupo.get(0);
            for (Music sonido : grupo) {
           
                if (!sonido.isPlaying()) {
                    sonido.setVolume(volumenFinal);
                    sonido.play();
                    return;
                }
            }
            
         
            sonidoMasViejo.stop();
            sonidoMasViejo.setVolume(volumenFinal);
            sonidoMasViejo.play();
        }
    }


    public void reproducirEnLoop(String nombre) {
        Music sonido = sonidosUnicos.get(nombre);
        if (sonido != null) {
            sonido.setLooping(true);
            sonido.setVolume(getVolumenFinal());
            sonido.play();
        }
    }


    public void pararSonido(String nombre) {
        // Parar sonido único
        Music sonidoUnico = sonidosUnicos.get(nombre);
        if (sonidoUnico != null) {
            sonidoUnico.stop();
        }
        
        // Parar todo el grupo
        List<Music> grupo = gruposSonidos.get(nombre);
        if (grupo != null) {
            for (Music sonido : grupo) {
                sonido.stop();
            }
        }
    }

    public void pausarSonido(String nombre) {
        Music sonido = sonidosUnicos.get(nombre);
        if (sonido != null) {
            sonido.pause();
        }
        
        List<Music> grupo = gruposSonidos.get(nombre);
        if (grupo != null) {
            for (Music sonido1 : grupo) {
                sonido1.pause();
            }
        }
    }

    // Actualizar volúmenes en tiempo real
    private void actualizarVolumenGlobal() {
        float volumenFinal = getVolumenFinal();
        
        // Actualizar sonidos únicos
        for (Music sonido : sonidosUnicos.values()) {
            if (sonido.isPlaying()) {
                sonido.setVolume(volumenFinal);
            }
        }
        
        // Actualizar grupos de sonidos
        for (List<Music> grupo : gruposSonidos.values()) {
            for (Music sonido : grupo) {
                if (sonido.isPlaying()) {
                    sonido.setVolume(volumenFinal);
                }
            }
        }
    }

    public void silenciarTodo() {
        for (Music sonido : sonidosUnicos.values()) {
            sonido.pause();
        }
        for (List<Music> grupo : gruposSonidos.values()) {
            for (Music sonido : grupo) {
                sonido.pause();
            }
        }
    }
    
    public void reanudarTodo() {
        actualizarVolumenGlobal();
      
    }
    
    public void dispose() {
        for (Music sonido : sonidosUnicos.values()) {
            sonido.dispose();
        }
        for (List<Music> grupo : gruposSonidos.values()) {
            for (Music sonido : grupo) {
                sonido.dispose();
            }
        }
        sonidosUnicos.clear();
        gruposSonidos.clear();
    }
}
