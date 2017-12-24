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
		
		if(state > 7 || durability < 0) //if there is no more water on the BlockWater or his durability is done
		{
			dead = true; //we remove the block
		}
		else
		{
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockId == this.blockId)
			{
				text = TextureManager.getTexture(this.blockId);
			}
			else
			{
				text = TextureManager.getTexture(this.blockId + state);
			}
		}
		
		if(iter == 5) //The Water update will operate one time out of <the number inside the if> ticks
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
				isStable = STABILITY;
			}
			
			//same liquid block under
			else if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId && ((BlockLiquid)world.getBlock((int)(this.x/16), (int)(this.y/16)-1)).state != 0)
			{
				if(((BlockLiquid)world.getBlock((int)(this.x/16), (int)((this.y/16)-1))).state > 0)
				{
					((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).state --;
					this.state ++;
					isStable = STABILITY;
				}
				else
				{
					isStable --;
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
	                isStable = STABILITY;
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null) //if there is nothing on the left
				{
					BlockWater block = new BlockWater((x/16)-1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
	                this.state ++;
	                isStable = STABILITY;
				}
				else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId) //if the block on the right has the same Id
				{
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if the one on the left has it too
					{
						//we compare both of them to see where the water should flow first (where there is less water)
						if((((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state) && (state == ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state-1))
						{
							isStable --;
							//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state)
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state && ((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state+1)
							{
								isStable --;
								//nothing to avoid "pulsars" : water blocs that cant be stable and pulse on the screen
							}
							//we compare now with the amount of water we have on the central block
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
							{
								isStable --;
								//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
							}
							//if there is less on the side, here the right one, we move 1 amount of water on this side
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
							{
								state++;
								isStable = STABILITY;
								((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
							}
							else
							{
								isStable --;
							}
						}
						//if the left side has less water than the right one
						else
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state && ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state+1)
							{
								isStable --;
								//nothing to avoid "pulsars" : water blocs that cant be stable and pulse on the screen
							}
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
							{
								isStable --;
								//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
							}
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
							{
								state++;
								isStable = STABILITY;
								((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
							}
							else
							{
								isStable --;
							}
						}
					}
					else //if there is another block than water on the left then only the right one is water
					{
						if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
						{
							isStable --;
							//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
						{
							state++;
							isStable = STABILITY;
							((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
						}
						else
						{
							isStable --;
						}
					}
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if only the left block is water 
				{
					if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
					{
						isStable --;
						//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
					}
					else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
					{
						state++;
						isStable = STABILITY;
						((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
					}
					else
					{
						isStable --;
					}
				}
				else
				{
					isStable --;
				}
			}
			else
			{
				isStable --;
			}
			
			if(isStable < 0)
			{
				isStable = 0;
			}
			
			if(isStable == 0)
			{
				if(rightSuccessor != null) //we check if his successor is still stable
				{
					if(rightSuccessor.isStable != 0)
					{
						rightSuccessor = null;
					}
				}
				
				//we actualize his successor if he doesn't have one
				if(rightSuccessor == null && world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) != null && world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId && ((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).isStable == 0)
				{
					rightSuccessor = ((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16)));
				}
				
				if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null || world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId != this.blockId)
				{
					if(rightSuccessor != null)
					{
						int[][] minMax = new int[2][2];
						BlockWater temp = this;
						minMax[0][0] = temp.state;
						minMax[0][1] = 1;
						minMax[1][0] = temp.state;
						minMax[1][1] = 1;
						
						
						while(temp.rightSuccessor != null)
						{
							temp = ((BlockWater)(temp.rightSuccessor));
							
							if(temp.state == minMax[0][0])
							{
								minMax[0][1]++;
							}
							else if(temp.state > minMax[0][0])
							{
								minMax[0][0] = temp.state;
								minMax[0][1] = 1;
							}
							
							if(temp.state == minMax[1][0])
							{
								minMax[1][1]++;
							}
							else if(temp.state < minMax[1][0])
							{
								minMax[1][0] = temp.state;
								minMax[1][1] = 1;
							}
						}
						
						temp = this;
						
						if(minMax[0][0] - minMax[1][0] == 1)
						{
							//nothing
						}
						else if(minMax[0][0] - minMax[1][0] > 1)
						{
							int modified = 0;
							while(temp.rightSuccessor != null)
							{
								if(temp.state == minMax[0][0] && minMax[1][1] > 0)
								{
									temp.state --;
									modified ++;
									minMax[1][1]--;
								}
								temp = ((BlockWater)(temp.rightSuccessor));
							}
							if(temp.state == minMax[0][0] && minMax[1][1] > 0)
							{
								temp.state --;
								modified ++;
								minMax[1][1]--;
							}
							
							temp = this;
							
							while(temp.rightSuccessor != null)
							{
								if(temp.state == minMax[1][0] && modified != 0)
								{
									temp.state ++;
									modified --;
								}
								temp = ((BlockWater)(temp.rightSuccessor));
							}
							if(temp.state == minMax[1][0] && modified != 0)
							{
								temp.state ++;
								modified --;
							}
						}
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
