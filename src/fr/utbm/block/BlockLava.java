package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockLava extends BlockLiquid{
	
	protected static final int DURABILITY = 300;
	
	public BlockLava(float x, float y, int state, World w)
	{
		super(x, y, TextureManager.getTexture(16 + state), w);
		this.blockId = 16;
		this.maxHealth = 1;
		this.blockHealth = 1;
		this.blockType = BlockType.LAVA;
		this.isGravitySensitive = true;
		this.state = state;
		durability = DURABILITY;
	}
	
	@Override
	public void update()
	{
		
		if(state > 7 || durability < 0) //if there is no more lava on the BlockLava or his durability is done
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
		
		if(state == 7)
		{
			durability --;
		}
		else
		{
			durability = DURABILITY;
		}
		
		if(iter == 20) //The Lava update will operate one time out of <the number inside the if> ticks
		{
			//Check if there is water around
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockType == BlockType.WATER)
			{
				dead = true;
				world.setBlock((int)(x/16), (int)(y/16), new BlockObsidian(x/16, y/16, world));
			}
			if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) != null && world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockType == BlockType.WATER && state != 7)
			{
				dead = true;
				world.setBlock((int)(x/16), (int)(y/16), new BlockObsidian(x/16, y/16, world));
			}
			if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) != null && world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockType == BlockType.WATER && state != 7)
			{
				dead = true;
				world.setBlock((int)(x/16), (int)(y/16), new BlockObsidian(x/16, y/16, world));
			}
			
			//Priority for the Lava to fall down before checking sides
			//void block under
			if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
			{
				BlockLava block = new BlockLava(x/16, (y/16)-1, 7, world);
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
					((BlockLiquid)world.getBlock(((int)(this.x/16)), ((int)((this.y/16)-1)))).durability = DURABILITY;
					this.state ++;
					isStable = STABILITY;
				}
				else
				{
					isStable --;
				}
			}
			
			//When the lava has fallen we can flow it on sides
			else if(this.state < 7) //if lava still has enough to flow on sides
			{
				if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null) //if there is nothing on the right
				{
					BlockLava block = new BlockLava((x/16)+1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), block);
	                this.state ++;
	                isStable = STABILITY;
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null) //if there is nothing on the left
				{
					BlockLava block = new BlockLava((x/16)-1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
	                this.state ++;
	                isStable = STABILITY;
				}
				else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId) //if the block on the right has the same Id
				{
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if the one on the left has it too
					{
						//we compare both of them to see where the lava should flow first (where there is less lava)
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
								//nothing to avoid "pulsars" : lava blocs that cant be stable and pulse on the screen
							}
							//we compare now with the amount of lava we have on the central block
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
							{
								isStable --;
								//nothing : we had to add it because when we ask smth < smthElse and they are equal, it still choose one of them
							}
							//if there is less on the side, here the right one, we move 1 amount of lava on this side
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
						//if the left side has less lava than the right one
						else
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state && ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state+1)
							{
								isStable --;
								//nothing to avoid "pulsars" : lava blocs that cant be stable and pulse on the screen
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
					else //if there is another block than lava on the left then only the right one is lava
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
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId) //if only the left block is lava 
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
						BlockLava temp = this;
						minMax[0][0] = temp.state;
						minMax[0][1] = 1;
						minMax[1][0] = temp.state;
						minMax[1][1] = 1;
						
						
						while(temp.rightSuccessor != null)
						{
							temp = ((BlockLava)(temp.rightSuccessor));
							
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
								temp = ((BlockLava)(temp.rightSuccessor));
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
								temp = ((BlockLava)(temp.rightSuccessor));
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
