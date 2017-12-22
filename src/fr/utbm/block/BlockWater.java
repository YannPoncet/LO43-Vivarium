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
			//System.out.println("J'ai kill le bloc en " + this.x/16 +" ; "+ this.y/16);
			dead = true;
		}
		else
		{
			if((flowing == Direction.DOWN && state == 7 && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null) || (world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockId == 4))
			{
				text = TextureManager.getTexture(12);
			}
			else
			{
				text = TextureManager.getTexture(4 + state);
			}
		}
		
		if(iter == 40)
		{
			//void block under
			//System.out.println("je suis rentré dans le test de l'update du bloc en " + (int)(this.x/16) +" ; "+ (int)(this.y/16));
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
			{
				//System.out.println("je descends pour la premiere fois");
				BlockWater block = new BlockWater(x/16, (y/16)-1, 7, world);
				block.flowing = Direction.DOWN;
				world.setBlock(((int)(this.x/16)), ((int)(this.y/16))-1, block);
				this.state ++;
				//System.out.println("Son état est : " + ((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state);
			}
			//same liquid block under
			else if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId)
			{
				//System.out.println("je prolonge la descente");
				if(((BlockLiquid)world.getBlock((int)(this.x/16), (int)((this.y/16)-1))).state > 0)
				{
					//System.out.println("J'actualise l'autre bloc");
					//flowing = Direction.DOWN;
					((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state --;
					this.state ++;
					//System.out.println("Son état est : " + ((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state);
				}
			}
			else if(this.state < 7)
			{
				if(flowing != Direction.RIGHT)
				{
					if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null)
					{
						BlockWater block = new BlockWater((x/16)+1, y/16, 7, world);
						block.flowing = Direction.RIGHT;
		                world.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), block);
		                this.state ++;
					}
					else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
			        {
			            if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > this.state)
			            {
			            	this.flowing = Direction.RIGHT;
			            	((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).flowing = Direction.RIGHT;
			                ((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
			                this.state ++;
			            }
			        }
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null)
					{
						BlockWater block = new BlockWater((x/16)-1, y/16, 7, world);
						block.flowing = Direction.LEFT;
		                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
		                this.state ++;
					}
					else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
			        {
						System.out.println(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state + " comparé à " + state);
			            if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > this.state)
			            {
			            	this.flowing = Direction.LEFT;
			            	((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).flowing = Direction.LEFT;
			                ((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
			                this.state ++;
			            }
			        }
				}
				else if(flowing != Direction.LEFT)
				{
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null)
					{
						BlockWater block = new BlockWater((x/16)-1, y/16, 7, world);
						block.flowing = Direction.LEFT;
		                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
		                this.state ++;
					}
					else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
			        {
						System.out.println(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state + " comparé à " + state);
			            if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > this.state)
			            {
			            	this.flowing = Direction.LEFT;
			            	((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).flowing = Direction.LEFT;
			                ((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
			                this.state ++;
			            }
			        }
					if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null)
					{
						BlockWater block = new BlockWater((x/16)+1, y/16, 7, world);
						block.flowing = Direction.RIGHT;
		                world.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), block);
		                this.state ++;
					}
					else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
			        {
			            if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > this.state)
			            {
			            	this.flowing = Direction.RIGHT;
			            	((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).flowing = Direction.RIGHT;
			                ((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
			                this.state ++;
			            }
			        }
				}
				
	            /*if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
	            {
	                if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state < this.state)
	                {
	                    flowing = Direction.LEFT;
	                    ((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state += 1;
	                    world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16))).update();
	                }
	            }*/
			}
            
			//System.out.println("Mon état est : " + state);
			iter = 0;
		}
		else
		{
			iter ++;
		}
		
	}

}
