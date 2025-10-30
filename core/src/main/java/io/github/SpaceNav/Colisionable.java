package io.github.SpaceNav;
import com.badlogic.gdx.math.Rectangle;

public interface Colisionable {
	    Rectangle getArea();
	    void onColision(); // Opcional: para manejar lo que pasa al colisionar
}

