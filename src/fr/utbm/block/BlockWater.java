package fr.utbm.block;

//import fr.utbm.entity.Direction;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.Map;

public class BlockWater extends BlockLiquid{
	
	public BlockWater(float x, float y, int state, Map mapIn)
	{
		super(x, y, TextureManager.getTexture(4 + state), mapIn);
		this.blockId = 4;
		this.maxHealth = 1;
		this.blockHealth = 1;
		this.blockType = BlockType.LIQUID;
		this.isGravitySensitive = true;
		this.state = state;
		//flowing = Direction.DOWN;
	}
	
	@Override
	public void update()
	{
		/*try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isStable = true;
		text = TextureManager.getTexture(4 + state);
		//void block around
		System.out.println("je suis rentré dans le test de l'update du bloc en " + this.x/16 +" ; "+ this.y/16);
		if(map.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
		{
			System.out.println("je descends pour la premiere fois");
			//flowing = Direction.DOWN;
			BlockWater block = new BlockWater(x/16, (y/16)-1, 7, map);
			map.setBlock(((int)(this.x/16)), ((int)(this.y/16))-1, block);
			this.state ++;
		}
		else
		{
			if(map.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null)
			{
				flowing = Direction.RIGHT;
				map.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), new BlockWater(this.x+1, this.y, 7, map));
				isStable = false;
			}
			if(map.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null)
			{
				flowing = Direction.LEFT;
				map.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), new BlockWater(this.x-1, this.y, 7, map));
				isStable = false;
			}
		}
		
		//same liquid block around
		else if(map.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId)
		{
			System.out.println("je prolonge la descente");
			if(((BlockLiquid)map.getBlock((int)(this.x/16), (int)((this.y/16)-1))).state > 0)
			{
				System.out.println("J'actualise l'autre bloc");
				//flowing = Direction.DOWN;
				((BlockLiquid)map.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state --;
				this.state ++;
			}
		}
		
		if(state > 7)
		{
			System.out.println("J'ai kill le bloc en " + this.x/16 +" ; "+ this.y/16);
			dead = true;
			map.setBlock(((int)(this.x/16)), ((int)(this.y/16)), null);
			isStable = true;
		}
		else
		{
			text = TextureManager.getTexture(4 + state);
			isStable = false;
		}
		
		System.out.println("Mon état est : " + state);
		/*else
		{
			if(map.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
			{
				if(((BlockLiquid)map.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state < this.state)
				{
					flowing = Direction.RIGHT;
					((BlockLiquid)map.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state += 1;
					map.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16))).update();
					isStable = false;
				}
			}
			if(map.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
			{
				if(((BlockLiquid)map.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state < this.state)
				{
					flowing = Direction.LEFT;
					((BlockLiquid)map.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state += 1;
					map.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16))).update();
					isStable = false;
				}
			}
		}*/
	}

}
