package io.github.SpaceNav.Enemigos;

import io.github.SpaceNav.Armas.EnemyBullet;

// Interfaz para enemigos que disparen
//Por ahora solo hay 1 pero se implementaran más en el futuro
public interface ShooterEnemigo {

    EnemyBullet shoot(float delta);
    
  
    boolean canShoot();
}