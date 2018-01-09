package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalTree extends EntityVegetal {
	
	public final String name = "Tree";

	public EntityVegetalTree(float x, float y, int bigOrSmall, World worldIn) {
		//0 for smallForest, 1 for bigForest, 2 for smallSnow, 3 for bigSnow
		super(x, y, 64, 203, worldIn);
		if(bigOrSmall == 2 || bigOrSmall == 3){
			width = 96;
		}
		entityHealth = 100;
		maxHealth = 100;
		this.text = TextureManager.getTexture(211+bigOrSmall);
	}
	
	public void update()
	{
		suffocating();
		voidUnder();
	}
	public float getTrunkPos(){
		return this.x + (this.width/2);
	}
	
	public void voidUnder()
	{
		if(width == 64)
		{
			if(world.getBlock((int)(x/16), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+1), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+2), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+3), (int)((y/16)-1)) == null)
			{
				dead = true;
			}
		}
		else
		{
			if(world.getBlock((int)((x/16)+1), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+2), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+3), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+4), (int)((y/16)-1)) == null)
			{
				dead = true;
			}
		}
	}
}
