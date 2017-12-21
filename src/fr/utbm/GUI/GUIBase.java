package fr.utbm.GUI;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.render.Renderable;

public abstract class GUIBase extends Renderable{

	public GUIBase(float x, float y, Texture text) {
		super(x, y, text);
	}

}
