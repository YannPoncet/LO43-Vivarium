package fr.utbm.tools;

import fr.utbm.block.Block;
import fr.utbm.block.BlockAsh;
import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockDirtSurface;
import fr.utbm.block.BlockLava;
import fr.utbm.block.BlockObsidian;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockSandSurface;
import fr.utbm.block.BlockSnowSurface;
import fr.utbm.block.BlockStone;
import fr.utbm.block.BlockWater;
import fr.utbm.block.BlockWood;
import fr.utbm.entity.Entity;
import fr.utbm.world.World;

public class ObjectGetter {
	
	public static Block getBlock(int id, float x, float y, World w){
		switch(id){
		case 0:
			return new BlockWater(x,y,0,w);
		case 1:
			return new BlockDirt(x,y,w);
		case 2:
			return new BlockDirtSurface(x,y,w);
		case 3:
			return new BlockSnowSurface(x,y,w);
		case 4:
			return new BlockSand(x,y,w);
		case 5:
			return new BlockSandSurface(x,y,w);
		case 6:
			return new BlockAsh(x,y,w);
		case 7:
			return new BlockStone(x,y,w);
		case 8:
			return new BlockObsidian(x,y,w);
		case 9:
			return new BlockWood(x,y,w);
		case 10:
			return new BlockLava(x,y,0,w);
		default:
			return null;
			
		}
	}
	/*public static Entity getEntity(int id, float x, float y, World w){
		switch(id){
		case 0:
			return new BlockWater(x,y,0,w);
		case 1:
			return new BlockDirt(x,y,w);
		case 2:
			return new BlockDirtSurface(x,y,w);
		case 3:
			return new BlockSnowSurface(x,y,w);
		case 4:
			return new BlockSand(x,y,w);
		case 5:
			return new BlockSandSurface(x,y,w);
		case 6:
			return new BlockAsh(x,y,w);
		case 7:
			return new BlockStone(x,y,w);
		case 8:
			return new BlockObsidian(x,y,w);
		case 9:
			return new BlockWood(x,y,w);
		case 10:
			return new BlockLava(x,y,0,w);
		default:
			return null;
			
		}
	}*/

}
