package fr.utbm.entity;

import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class Entity extends Renderable{

	protected World world;
	protected int width;
	protected int height;
	
	public Entity(float x, float y, int w, int h, World worldIn) {
		super(x*16,y*16,TextureManager.getTexture(0));
		this.world = worldIn;
		this.width = w;
		this.height = h;
	}
	public void update(){
	}

}
