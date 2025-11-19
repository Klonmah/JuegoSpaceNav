package io.github.SpaceNav.Utilidades;

public class MapaManager {
    private static MapaManager instance;
    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;
    
    private MapaManager() {

    }
    
    public static MapaManager getInstance() {
        if (instance == null) {
            instance = new MapaManager();
        }
        return instance;
    }
    
    public void setMapDimensions(int width, int height, int tileWidth, int tileHeight) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }
    
    // Getters
    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    
    // Métodos útiles
    public boolean estaDentroDelMapa(float x, float y, float width, float height) {
        return x >= 0 && y >= 0 && 
               x + width <= mapWidth && 
               y + height <= mapHeight;
    }
    //Se Asegura que X e Y esten dentro del mapa
    public float arreglarX(float x, float width) {
        return Math.max(0, Math.min(mapWidth - width, x));
    }
    
    public float arreglarY(float y, float height) {
        return Math.max(0, Math.min(mapHeight - height, y));
    }
}