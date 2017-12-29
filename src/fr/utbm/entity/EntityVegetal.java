package fr.utbm.entity;

import fr.utbm.world.World;

public abstract class EntityVegetal extends Entity{
	
	protected int maturity;
	protected int iter = 0; //used to avoid too fast growing of the vegetal in game
	protected int entityHealth;
	protected int maxHealth;
	
	public EntityVegetal(float x, float y, int w, int h, World worldIn) {
		
		super(x, y, w, h, worldIn);
		
	}
	
	@Override
	public abstract void update();
	
	public abstract void voidUnder();

}
