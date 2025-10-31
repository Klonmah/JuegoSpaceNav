package io.github.SpaceNav.Enemigos;

public interface Destructible {
 
    void takeDamage(int damage);
   
    boolean isDestroyed();
  
    int getScoreValue();
  
    int getHp();

    int getMaxHp();
}