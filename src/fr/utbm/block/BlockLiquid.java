package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.entity.Direction;
import fr.utbm.world.World;

public abstract class BlockLiquid extends Block{
	
	protected static final int DURABILITY = 100;
	protected int state = 0;
	protected Direction flowing;
	protected int iter = 0;
	protected int durability = DURABILITY; //number of ticks a liquid cube of state == 7 can hold
	
	public BlockLiquid(float x, float y,Texture text, World w)
	{
		super(x, y, text, w);
	}
	
	@Override
	public void damage(int dmg)
	{
		
	}
	
	public boolean isStable()
	{
		if((world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) == null) || (world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null) || (world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null))
		{
			return false;
		}
		else if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockId == this.blockId)
		{
			if(((BlockLiquid)world.getBlock((int)(this.x/16), (int)((this.y/16)+1))).state <= this.state)
			{
				return false;
			}
		}
		else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
		{
			if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state <= this.state)
			{
				return false;
			}
		}
		else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
		{
			if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state <= this.state)
			{
				return false;
			}
		}
		return true;
	}

}
