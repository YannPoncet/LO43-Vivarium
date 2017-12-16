package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.world.Map;

public abstract class BlockLiquid extends Block{
	
	protected int state;
	
	public BlockLiquid(float x, float y,Texture text, Map mapIn)
	{
		super(x, y, text, mapIn);
		
	}
	
	@Override
	public void damage(int dmg)
	{
		
	}
	
	@Override
	public void update()
	{
		
	}

}
