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
		//System.out.println("je suis rentré dans le test de l'update du bloc en " + (int)(this.x/16) +" ; "+ (int)(this.y/16));
		
		if(state > 7 || durability < 0) //if there is no more water on the BlockWater or his durability is done
		{
			dead = true; //we remove the block
		}
		else
		{
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockId == 4)
			{
				text = TextureManager.getTexture(4);
			}
			else
			{
				text = TextureManager.getTexture(4 + state);
			}
		}
		
		if(iter == 5)
		{
			if(state == 7)
			{
				durability --;
			}
			else
			{
				durability = DURABILITY;
			}
			
			//Priority for the water to fall down
			//void block under
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
			{
				BlockWater block = new BlockWater(x/16, (y/16)-1, 7, world);
				block.flowing = Direction.DOWN;
				world.setBlock(((int)(this.x/16)), ((int)(this.y/16))-1, block);
				this.state ++;
			}
			
			//same liquid block under
			else if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId && ((BlockLiquid)world.getBlock((int)(this.x/16), (int)(this.y/16)-1)).state != 0)
			{
				if(((BlockLiquid)world.getBlock((int)(this.x/16), (int)((this.y/16)-1))).state > 0)
				{
					((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state --;
					this.state ++;
				}
			}
			
			//When the water has fallen we can flow it on sides
			else if(this.state < 7) //if water still have enough to flow on sides
			{
				if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null) //if there is nothing on the right
				{
					BlockWater block = new BlockWater((x/16)+1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), block);
	                this.state ++;
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null) //if there is nothing on the left
				{
					BlockWater block = new BlockWater((x/16)-1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
	                this.state ++;
				}
				else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId) //if the block on the right has the same Id
				{
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if the one on the left has it too
					{
						//we compare both of them to see where the water should flow first (where there is less water)
						if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state && state > 5)
						{
							//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state)
						{
							//we compare now with the amount of water we have on the central block
							if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
							{
								//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
							}
							//if there is less on the side, here the right one, we move 1 amount of water on this side
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
							{
								state++;
								((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
							}
						}
						else //if the left side has less water than the right one
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
							{
								//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
							}
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
							{
								state++;
								((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
							}
						}
					}
					else //if there is another block than water on the left then only the right one is water
					{
						if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
						{
							//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
						{
							state++;
							((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
						}
					}
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if only the left block is water 
				{
					if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
					{
						//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
					}
					else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
					{
						state++;
						((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
					}
				}
			}
			iter = 0;
		}
		else
		{
			iter ++;
		}
		
	}

}
