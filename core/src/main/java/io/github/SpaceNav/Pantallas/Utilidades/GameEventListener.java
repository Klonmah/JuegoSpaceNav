package io.github.SpaceNav.Pantallas.Utilidades;



import io.github.SpaceNav.Armas.Bomb;
import io.github.SpaceNav.Armas.Bullet;
import io.github.SpaceNav.Armas.EnemyBullet;

public interface GameEventListener {
    void onBulletFired(Bullet bullet);
    void onBombFired(Bomb bomb);
    void onEnemyBulletFired(EnemyBullet bullet);
    void onScoreChanged(int points);
    void onEntityDestroyed(); // Para sonidos/efectos
}