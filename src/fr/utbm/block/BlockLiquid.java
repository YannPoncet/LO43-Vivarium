package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

//import fr.utbm.entity.Direction;
import fr.utbm.world.Map;

public abstract class BlockLiquid extends Block{
	
	protected int state = 0;
	//protected Direction flowing;
	protected boolean isStable;
	float stateTime = 0;
	
	public BlockLiquid(float x, float y,Texture text, Map mapIn)
	{
		super(x, y, text, mapIn);
	}
	
	@Override
	public void damage(int dmg)
	{
		
	}
	
	public boolean isStable()
	{
		if((map.getBlock((int)(this.x/16), (int)((this.y/16)+1)) == null) || (map.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null) || (map.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null))
		{
			return false;
		}
		else if(map.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockId == this.blockId)
		{
			if(((BlockLiquid)map.getBlock((int)(this.x/16), (int)((this.y/16)+1))).state <= this.state)
			{
				return false;
			}
		}
		else if(map.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
		{
			if(((BlockLiquid)map.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state <= this.state)
			{
				return false;
			}
		}
		else if(map.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
		{
			if(((BlockLiquid)map.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state <= this.state)
			{
				return false;
			}
		}
		return true;
	}

}
