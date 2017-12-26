package fr.utbm.biome;

import fr.utbm.block.BlockAsh;
import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockSandSurface;
import fr.utbm.block.BlockSnowSurface;
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
			case 0: w.getMap().setBlock(x, y, new BlockGrass(x,y,w)); //forest
					break;
					
			case 1: w.getMap().setBlock(x, y, new BlockSandSurface(x,y,w)); //desert
					break;
					
			case 2: w.getMap().setBlock(x, y, new BlockGrass(x,y,w)); //montain
					break;
	
			case 3: w.getMap().setBlock(x, y, new BlockSnowSurface(x,y,w)); //snow
					break;
		}
	}
}
