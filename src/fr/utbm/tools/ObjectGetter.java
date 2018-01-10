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
import fr.utbm.entity.EntityAnimalBeaver;
import fr.utbm.entity.EntityAnimalBenenut;
import fr.utbm.entity.EntityAnimalCuteFlower;
import fr.utbm.entity.EntityAnimalDigger;
import fr.utbm.entity.EntityAnimalDwarfKing;
import fr.utbm.entity.EntityAnimalDwarfMiner;
import fr.utbm.entity.EntityAnimalDwarfWarrior;
import fr.utbm.entity.EntityAnimalHellDog;
import fr.utbm.entity.EntityAnimalHellFish;
import fr.utbm.entity.EntityAnimalMrStabby;
import fr.utbm.entity.EntityAnimalPrettyBird;
import fr.utbm.entity.EntityAnimalRedFish;
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
	
	
	
	
	
	
	public static int getEntityV(Entity e){
		int a = 0;
		if(e instanceof EntityAnimalBeaver){
			a = 1050;
		}else if(e instanceof EntityAnimalDigger){
			a = 1051;
		}else if(e instanceof EntityAnimalRedFish){
			a = 1052;
		}else if(e instanceof EntityAnimalCuteFlower){
			a = 1053;
		}else if(e instanceof EntityAnimalDwarfKing){
			a = 1054;
		}else if(e instanceof EntityAnimalHellFish){
			a = 1055;
		}else if(e instanceof EntityAnimalDwarfMiner){
			a = 1056;
		}else if(e instanceof EntityAnimalPrettyBird){
			a = 1057;
		}else if(e instanceof EntityAnimalMrStabby){
			a = 1058;
		}else if(e instanceof EntityAnimalDwarfWarrior){
			a = 1059;
		}else if(e instanceof EntityAnimalBenenut){
			a = 1060;
		}else if(e instanceof EntityAnimalHellDog){
			a = 1061;
		}
		
		return a;
		
		
		
	}
	public static Entity getEntity(int id, float x, float y, World w){
		switch(id){
		case 0:
			return new EntityAnimalBeaver(x, y, w);
		case 1:
			return new EntityAnimalDigger(x, y, w);
		case 2:
			return new EntityAnimalRedFish(x, y, w);
		case 3:
			return new EntityAnimalCuteFlower(x, y, w);
		case 4:
			return new EntityAnimalDwarfKing(x, y, w);
		case 5:
			return new EntityAnimalHellFish(x,y,w);
		case 6:
			return new EntityAnimalDwarfMiner(x,y,w);
		case 7:
			return new EntityAnimalPrettyBird(x,y,w);
		case 8:
			return new EntityAnimalMrStabby(x,y,w);
		case 9:
			return new EntityAnimalDwarfWarrior(x,y,w);
		case 10:
			return new EntityAnimalBenenut(x,y,w);
		case 11:
			return new EntityAnimalHellDog(x,y,w);
		default:
			return null;
			
		}
	}

}
