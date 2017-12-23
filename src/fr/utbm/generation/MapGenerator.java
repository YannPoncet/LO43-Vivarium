package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.block.BlockAsh;
import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGlass;
import fr.utbm.block.BlockGrass;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;
import fr.utbm.world.World;

public class MapGenerator {
	public final static int DIRT_SURFACE = 10; //Height of the dirt at the surface
	public final static int GRASS_SURFACE = 2;
	
	public static void generate(World w, double seed)
	{
			long M = 4294967296L;
			if (seed == 0) 
			{
				seed = Math.floor(Math.random() * M);
			}
			
			Surface1DGenerator noiseGen = new Surface1DGenerator(seed, M);
			//To fill the parameters: generateAndGetNoise(double amplitude, double wavelength, int octaves, double divisor)
			//=>Increase wavelength to get flat map generally
			//=>Decrease amplitude to get a flat map locally
			ArrayList<Integer> surface = noiseGen.generateAndGetNoise(0,64,15,4);
			
			
			//Change the value of the last parameter (0 to 100) to increase the dirt ratio
			Cave2DGenerator caveGen = new Cave2DGenerator(seed, M, 75, Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH, Map.LIMIT_SURFACE-Map.LIMIT_CAVE+1);
			Cave2DGenerator hellGen = new Cave2DGenerator(seed, M, 45, Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH, Map.LIMIT_CAVE+1);

			
			ArrayList<ArrayList<Integer>> caves = caveGen.generateAndGetCaves();
			ArrayList<ArrayList<Integer>> hellCaves = hellGen.generateAndGetCaves();
			
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				/* HELL */
				for(int j=0; j<Map.LIMIT_CAVE;j++)
				{
					if(hellCaves.get(i).get(j) == 1) 
					{
						w.getMap().setBlock(i, j, new BlockAsh(i,j,w)); 
					}
				}
				
				/* CAVES */
				for(int j=Map.LIMIT_CAVE; j<Map.LIMIT_SURFACE;j++)
				{
					if(caves.get(i).get(j-Map.LIMIT_CAVE+1) == 1) 
					{
						w.getMap().setBlock(i, j, new BlockDirt(i,j,w)); 
					}
				}
				
				/* SURFACE */
				for(int j=Map.LIMIT_SURFACE; j<Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+MapGenerator.GRASS_SURFACE+surface.get(i);j++)
				{
					if (j<Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+surface.get(i)) //dirt
					{
						w.getMap().setBlock(i, j, new BlockDirt(i,j,w));
					}
					else //grass
					{
						w.getMap().setBlock(i, j, new BlockGrass(i,j,w));
					}
				}
			}
			
			//Placing the glass borders
			for(int i=0; i<Chunk.CHUNK_HEIGHT;i++)
			{
				w.getMap().setBlock(0, i, new BlockGlass(0,i,w,0));
				w.getMap().setBlock(Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1, i, new BlockGlass(Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1, i,w,0));
			}
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				w.getMap().setBlock(i, 0, new BlockGlass(i,0,w,1));
				w.getMap().setBlock(i, Chunk.CHUNK_HEIGHT-1, new BlockGlass(i, Chunk.CHUNK_HEIGHT-1,w,1));
			}
	}
}
