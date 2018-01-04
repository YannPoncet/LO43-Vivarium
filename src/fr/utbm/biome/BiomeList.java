package fr.utbm.biome;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockDirtSurface;
import fr.utbm.block.BlockGrass;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockSandSurface;
import fr.utbm.block.BlockSnowSurface;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityVegetalBenenutTree;
import fr.utbm.entity.EntityVegetalBush;
import fr.utbm.entity.EntityVegetalCactus;
import fr.utbm.entity.EntityVegetalTree;
import fr.utbm.world.World;

public enum BiomeList {
	FOREST,
	DESERT,
	MONTAIN,
	SNOW;
	
	public final static int NUMBER_OF_BIOME=4;
	
	public static void createSurfaceBlock(int x, int y, World w, int biomeType)
	{
		switch (biomeType) {
			case 0: w.getMap().setBlock(x, y, new BlockDirt(x,y,w)); //forest
					break;
					
			case 1: w.getMap().setBlock(x, y, new BlockSand(x,y,w)); //desert
					break;
					
			case 2: w.getMap().setBlock(x, y, new BlockDirt(x,y,w)); //montain
					break;
			
			case 3: w.getMap().setBlock(x, y, new BlockDirt(x,y,w)); //snow
					break;
		}
	}
	
	public static void createSurfaceGrassBlock(int x, int y, World w, int biomeType)
	{
		switch (biomeType) {
			case 0: w.getMap().setBlock(x, y, new BlockDirtSurface(x,y,w)); //forest
					w.getMap().setBlock(x, y+1, new BlockGrass(x,y+1,0,w));
					break;
					
			case 1: w.getMap().setBlock(x, y, new BlockSandSurface(x,y,w)); //desert
					w.getMap().setBlock(x, y+1, new BlockGrass(x,y+1,1,w));
					break;
					
			case 2: w.getMap().setBlock(x, y, new BlockDirtSurface(x,y,w)); //montain
					break;
	
			case 3: w.getMap().setBlock(x, y, new BlockSnowSurface(x,y,w)); //snow
					break;
		}
	}
	
	public static void createEntityByID(int x, int y, World w, int ID)
	{
		switch (ID) {
		case 201: w.addEntity((Entity)(new EntityVegetalBenenutTree(x,y+1,w))); //benenut tree
				break;
				
		case 211: w.addEntity((Entity)(new EntityVegetalTree(x,y+1,0,w))); //treeForest
				break;
				
		case 212: w.addEntity((Entity)(new EntityVegetalTree(x,y+1,1,w))); //bigTreeForest
				break;
				
		case 213: w.addEntity((Entity)(new EntityVegetalTree(x,y+1,2,w))); //treeSnow
				break;
		
		case 214: w.addEntity((Entity)(new EntityVegetalTree(x,y+1,3,w))); //bigTreeSnow
				break;
				
		case 215: w.addEntity((Entity)(new EntityVegetalBush(x,y+1,w))); //bush
				break;
				
		case 216: w.addEntity((Entity)(new EntityVegetalCactus(x,y+1,0,w))); //small cactus
				break;
		
		case 217: w.addEntity((Entity)(new EntityVegetalCactus(x,y+1,1,w))); //big cactus
				break;
	}
		
	}
}
