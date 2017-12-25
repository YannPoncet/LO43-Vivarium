package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.entity.Direction;
import fr.utbm.world.World;

public abstract class BlockLiquid extends Block{
	
	protected static final int DURABILITY = 300; //number of ticks a liquid cube of state == 7 can hold
	protected static final int STABILITY = 4; //number of rounds without changes of state a block is considered stable
	protected int state = 0;
	protected Direction flowing;
	protected int iter = 0;
	protected int durability = DURABILITY;
	protected int isStable = STABILITY;
	protected BlockLiquid rightSuccessor = null;
	
	public BlockLiquid(float x, float y,Texture text, World w)
	{
		super(x, y, text, w);
	}
	
	@Override
	public void damage(int dmg)
	{
		
	}
}
