package fr.utbm.entity;

import fr.utbm.world.World;

public class EntityAnimal extends Entity{

	protected int directionX = 0;
	
	
	public EntityAnimal(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
	}

}
