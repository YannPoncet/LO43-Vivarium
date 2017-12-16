package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

public abstract class BlockLiquid extends Block{
	
	protected int state;
	
	public BlockLiquid(float x, float y,Texture text)
	{
		super(x, y, text);
		
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
