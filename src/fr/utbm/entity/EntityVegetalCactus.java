package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalCactus extends EntityVegetal {

	private String name = "Cactus";
	
	public EntityVegetalCactus(float x, float y, int bigOrSmall, World worldIn) {
		//0 for smallCactus, 1 for bigCactus
		super(x, y, 32, 62+bigOrSmall*23, worldIn);
		entityHealth = 100;
		maxHealth = 100;
		this.text = TextureManager.getTexture(216+bigOrSmall);
	}
	
	public void update()
	{
		voidUnder();
	}
	
	public void voidUnder()
	{
		if(world.getBlock((int)(x/16), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+1), (int)((y/16)-1)) == null)
		{
			dead = true;
		}
	}
}
