package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalTree extends EntityVegetal {

	public EntityVegetalTree(float x, float y, int bigOrSmall, World worldIn) {
		//0 for smallForest, 1 for bigForest, 2 for smallSnow, 3 for bigSnow
		super(x, y, 80, 203, worldIn);
		entityHealth = 100;
		maxHealth = 100;
		this.text = TextureManager.getTexture(211+bigOrSmall);
	}
	
	public void update()
	{
		voidUnder();
	}
	
	public void voidUnder()
	{
		
	}
}
