package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.render.Renderable;

public class Block extends Renderable{
	
	public Block(float x, float y,Texture text) {
		super(x*16, y*16, text);
	}
	

}
