package fr.utbm.entity;

import fr.utbm.world.World;

public abstract class EntityVegetal extends Entity{
	
	protected int maturity;
	
	public EntityVegetal(float x, float y, int w, int h, World worldIn) {
		
		super(x, y, w, h, worldIn);
		
	}

}
