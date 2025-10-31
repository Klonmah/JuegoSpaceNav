package io.github.SpaceNav.Enemigos.Sistemas;

public class SistemaEstado {
    private boolean activa = true;
    private boolean destruida = false;
    
    public boolean isActive() {
        return activa && !destruida;
    }
    
    public boolean isDestruida() {
        return destruida;
    }
    
    public void destruir() {
        this.destruida = true;
    }
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}