package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.world.World;

public abstract class BlockLiquid extends Block{
	
	protected static final int STABILITY = 4; //number of rounds without changes of state a block is considered stable
	protected int state = 0; //equals to 0 when its a full block of liquid, and goes up to 7 the less filled he is
	protected int iter = 0; //used to avoid too fast update of the liquid on the map
	protected int durability; //number of ticks a liquid cube of state == 7 can hold
	protected int isStable = STABILITY;
	protected BlockLiquid rightSuccessor = null;
	
	public BlockLiquid(float x, float y,Texture text, World w)
	{
		super(x, y, text, w);
		isSolid = false;
	}
	
	@Override
	public void damage(int dmg)
	{
		
	}
}
