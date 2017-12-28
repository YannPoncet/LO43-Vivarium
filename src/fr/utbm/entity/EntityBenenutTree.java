package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityBenenutTree extends EntityVegetal{
	
	public EntityBenenutTree(float x, float y, World worldIn) {
		
		super(x, y, 96, 144, worldIn);
		maturity = 6;
		text = TextureManager.getTexture(201 + maturity);
		
	}

}
