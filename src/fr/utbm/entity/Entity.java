package fr.utbm.entity;

import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;

public class Entity extends Renderable{

	public Entity(float x, float y) {
		super(x,y,TextureManager.getTexture(1));
	}
	public void update(){
		
	}
	public void move(){
		
	}

}
