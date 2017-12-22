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
		/*if(world.getBlock(23, 313) != null)
		{
			System.out.println("JE SUIS UN BLOC");
			System.out.println(world.getBlock(23, 313).blockId);
		}*/
		
		if(state > 7 || durability < 0)
		{
			//System.out.println("J'ai kill le bloc en " + this.x/16 +" ; "+ this.y/16);
			dead = true;
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
		
		if(iter == 10)
		{
			if(state == 7)
			{
				durability --;
			}
			else
			{
				durability = DURABILITY;
			}
			//void block under
			System.out.println("je suis rentré dans le test de l'update du bloc en " + (int)(this.x/16) +" ; "+ (int)(this.y/16));
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
			else if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)).blockId == this.blockId && ((BlockLiquid)world.getBlock((int)(this.x/16), (int)(this.y/16)-1)).state != 0)
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
				if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) == null)
				{
					BlockWater block = new BlockWater((x/16)+1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)+1)), ((int)(this.y/16)), block);
	                this.state ++;
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) == null)
				{
					BlockWater block = new BlockWater((x/16)-1, y/16, 7, world);
	                world.setBlock(((int)((this.x/16)-1)), ((int)(this.y/16)), block);
	                this.state ++;
				}
				else if(world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockId == this.blockId)
				{
					if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
					{
						System.out.println(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state + " comparé à " + ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state);
						System.out.println("j'ai un state de : " + state);
						if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state && state > 5)
						{
							System.out.println("je ne fais rien car c'est égal");
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > ((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state)
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
							{
								
							}
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
							{
								System.out.println("j'augmente l'état à droite car mieux que à gauche");
								state++;
								((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
							}
						}
						else
						{
							if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
							{
								
							}
							else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
							{
								System.out.println("j'augmente l'état à gauche car mieux que à droite");
								state++;
								((BlockLiquid)world.getBlock(((int)((this.x/16)-1)), ((int)(this.y/16)))).state --;
							}
						}
					}
					else
					{
						if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state == state)
						{
							
						}
						else if(((BlockLiquid)world.getBlock((int)((this.x/16)+1), (int)(this.y/16))).state > state)
						{
							System.out.println("j'augmente l'état à droite vu que je peux aller que là");
							state++;
							((BlockLiquid)world.getBlock(((int)((this.x/16)+1)), ((int)(this.y/16)))).state --;
						}
					}
				}
				else if(world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockId == this.blockId)
				{
					if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state == state)
					{
						
					}
					else if(((BlockLiquid)world.getBlock((int)((this.x/16)-1), (int)(this.y/16))).state > state)
					{
						System.out.println("j'augmente l'état à gauche car je ne peux que aller là");
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
