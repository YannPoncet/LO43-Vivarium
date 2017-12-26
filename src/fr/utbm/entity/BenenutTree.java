package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BenenutTree extends EntityVegetal{
	
	public BenenutTree(float x, float y, World worldIn) {
		
		super(x, y, 96, 144, worldIn);
		maturity = 100;
		text = TextureManager.getTexture(101);
		
	}

}
