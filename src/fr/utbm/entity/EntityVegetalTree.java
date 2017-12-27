package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalTree extends EntityVegetal {

	public EntityVegetalTree(float x, float y, int bigOrSmall, World worldIn) {
		//0 for small and 1 for big
		super(x, y, 150, 203, worldIn);
		this.text = TextureManager.getTexture(200+bigOrSmall);
	}
	
	/*
	@Override
	public void render(SpriteBatch sp) {
		
		
	}
	*/
}
