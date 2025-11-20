package io.github.SpaceNav.Utilidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import io.github.SpaceNav.Armas.WeaponQuintuple;
import io.github.SpaceNav.Armas.WeaponSingle;
import io.github.SpaceNav.Armas.WeaponTriple;
import io.github.SpaceNav.jugador.Nave;

import java.util.ArrayList;

public class PerkManager {

    private static final ArrayList<Perk> perks = new ArrayList<>();

    // Registrar todos los perks aquí
    static {

        // + VIDA
        perks.add(new Perk("+Vida")  {
            @Override
            public void aplicar(Nave nave) {
                nave.setVidas(nave.getVidas() + 1);
            }
        });

        // + BOMBA
        perks.add(new Perk("+Bomba") {
            @Override
            public void aplicar(Nave nave) {
                nave.setBombs(nave.getBombs() + 1);
            }
        });

     // NUEVO: Cambiar arma
        perks.add(new Perk("+Mejora de Arma")  {
            @Override
            public void aplicar(Nave nave) {

                Texture tx = new Texture(Gdx.files.internal("../assets/Rocket2.png"));
                Texture tx2 = new Texture(Gdx.files.internal("../assets/BombLowScaled.png"));

                // Deben estar en este orden para no hacer doble mejora
                
                // Verificar si el arma actual ES una instancia de WeaponQuintuple mejora la cadencia
                if (nave.getWeapon() instanceof WeaponQuintuple) {
                	float nuevaCadencia = nave.getWeaponCadencia() - 0.04f;

                	// Cadencia mínima permitida (por ejemplo 0.05s)
                	nuevaCadencia = MathUtils.clamp(nuevaCadencia, 0.05f, 5f);

                	nave.setWeapon(
                	    new WeaponQuintuple(
                	        tx,
                	        tx2,
                	        nuevaCadencia
                	    )
                	);
                }
                
                // Verificar si el arma actual ES una instancia de WeaponSingle
                if (nave.getWeapon() instanceof WeaponTriple) {
                    nave.setWeapon(
                        new WeaponQuintuple(
                            tx,    // textura bala
                            tx2,    // textura bomb
                            nave.getWeaponCadencia()
                        )
                    );
                }

                // Verificar si el arma actual ES una instancia de WeaponSingle
                if (nave.getWeapon() instanceof WeaponSingle) {
                    nave.setWeapon(
                        new WeaponTriple(
                            tx,    // textura bala
                            tx2,    // textura bomb
                            nave.getWeaponCadencia()
                        )
                    );
                }
            }
        });


        // NUEVO: +Velocidad
        perks.add(new Perk("+Velocidad")  {
            @Override
            public void aplicar(Nave nave) {
                nave.setVelocidad(nave.getVelocidad() + 0.7f);
                nave.setMaxVelocidad(nave.getMaxVelocidad() + 0.9f);
            }
        });

        // perk se agregan asi:
        // perks.add(new Perk("Daño ++") { public void aplicar(Nave n){ n.daño+=20; } });
    }

    public static ArrayList<Perk> getPerks() {
        return perks;
    }

    public static Perk obtenerPerkAleatorio() {
        return perks.get(MathUtils.random(perks.size() - 1));
    }
}
