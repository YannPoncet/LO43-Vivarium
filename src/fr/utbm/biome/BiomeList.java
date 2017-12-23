package fr.utbm.biome;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockSandSurface;
import fr.utbm.world.World;

public enum BiomeList {
	FOREST,
	MONTAIN;
	
	public final static int NUMBER_OF_BIOME=2;
	
	public static void createSurfaceBlock(int x, int y, World w, int biomeType)
	{
		switch (biomeType) {
			case 0: w.getMap().setBlock(x, y, new BlockDirt(x,y,w));
					break;
					
			case 1: w.getMap().setBlock(x, y, new BlockSand(x,y,w));
					break;
		}
	}
	
	public static void createSurfaceGrassBlock(int x, int y, World w, int biomeType)
	{
		switch (biomeType) {
			case 0: w.getMap().setBlock(x, y, new BlockGrass(x,y,w));
					break;
					
			case 1: w.getMap().setBlock(x, y, new BlockSandSurface(x,y,w));
					break;
		}
	}
}
