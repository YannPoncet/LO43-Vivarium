package fr.utbm.block;

import fr.utbm.entity.Direction;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockWater extends BlockLiquid{
	
	public BlockWater(float x, float y, int state, World w)
	{
		super(x, y, TextureManager.getTexture(4 + state), w);
		this.blockId = 4;
		this.maxHealth = 1;
		this.blockHealth = 1;
		this.blockType = BlockType.LIQUID;
		this.isGravitySensitive = true;
		this.state = state;
		flowing = Direction.DOWN;
	}
	
	@Override
	public void update()
	{
		if(state > 7)
		{
			System.out.println("J'ai kill le bloc en " + this.x/16 +" ; "+ this.y/16);
			dead = true;
		}
		else
		{
			if(flowing == Direction.DOWN && state == 7)
			{
				text = TextureManager.getTexture(12);
			}
			else
			{
				text = TextureManager.getTexture(4 + state);
			}
		}
		
		if(iter == 10)
		{
			//void block around
			System.out.println("je suis rentré dans le test de l'update du bloc en " + (int)(this.x/16) +" ; "+ (int)(this.y/16));
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
			{
				System.out.println("je descends pour la premiere fois");
				BlockWater block = new BlockWater(x/16, (y/16)-1, 7, world);
				block.flowing = Direction.DOWN;
				world.setBlock(((int)(this.x/16)), ((int)(this.y/16))-1, block);
				this.state ++;
				System.out.println("Son état est : " + ((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state);
			}
			
			
			//same liquid block around
			else if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId)
			{
				System.out.println("je prolonge la descente");
				if(((BlockLiquid)world.getBlock((int)(this.x/16), (int)((this.y/16)-1))).state > 0)
				{
					System.out.println("J'actualise l'autre bloc");
					//flowing = Direction.DOWN;
					((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state --;
					this.state ++;
					System.out.println("Son état est : " + ((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state);
				}
			}
			
			
			
			System.out.println("Mon état est : " + state);
			iter = 0;
		}
		else
		{
			iter ++;
		}
		
	}

}
