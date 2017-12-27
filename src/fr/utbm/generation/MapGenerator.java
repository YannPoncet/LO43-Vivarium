package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.biome.Biome;
import fr.utbm.biome.BiomeList;
import fr.utbm.block.BlockAsh;
import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGlass;
import fr.utbm.block.BlockLava;
import fr.utbm.block.BlockObsidian;
import fr.utbm.block.BlockStone;
import fr.utbm.block.BlockWater;
import fr.utbm.render.RenderManager;
import fr.utbm.tools.Chrono;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;
import fr.utbm.world.World;

public class MapGenerator {
	public final static int DIRT_SURFACE = 20; //Height of the dirt at the surface
	
	public static void generate(World w, double seed)
	{
			Chrono chrono = new Chrono();
			Chrono chrono2 = new Chrono();
			System.out.println("\n---- STARTING WORLD GENERATION ----");
			
			long M = 4294967296L;
			if (seed == 0) 
			{
				seed = Math.floor(Math.random() * M);
			}
			
			chrono.reset();
			System.out.print("Generating Biomes...");
				//You can adjust minimum and maximum size of biomes
				//Care: if the biomes are too small, it wont give a good result, minimum 50 is advised
				BiomeGenerator biomeGen = new BiomeGenerator(seed, M, Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH, 50, 100);
				ArrayList<Biome> biomeList = biomeGen.getBiomeList();
			System.out.println(" "+chrono.getTime()+"ms");
			
			RenderManager.setBiomeList(biomeList);
			
			chrono.reset();
			System.out.print("Generating Surface...");
				Surface1DGenerator noiseGen = new Surface1DGenerator(seed, M);
				//To fill the parameters: generateAndGetNoise(double amplitude, double wavelength, int octaves, double divisor)
				//=>Increase wavelength to get flat map generally ---> BETWEEN 0 & 1 <---
				//=>Decrease amplitude to get a flat map locally ---> BETWEEN 0 & 1 <---
				ArrayList<Integer> surface = noiseGen.generateAndGetNoise(1,1,15,4, biomeList);
			System.out.println(" "+chrono.getTime()+"ms");
			
			chrono.reset();
			System.out.print("Generating Caves...");
				//2nd parameter: stone ratio in caves
				//3rd parameter: ash ratio in hell caves
				Cave2DGenerator caveGen = new Cave2DGenerator(seed, M, 45, 75, Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH, Map.LIMIT_CAVE, Map.LIMIT_SURFACE-Map.LIMIT_CAVE+1);
				ArrayList<ArrayList<Integer>> caves = caveGen.generateAndGetCaves();
			System.out.println(" "+chrono.getTime()+"ms");
			
			chrono.reset();
			System.out.print("Generating Liquids...");
				LiquidGenerator liquidGen = new LiquidGenerator(seed, M);
				int[] surfaceLiquid = liquidGen.surfaceLiquidGen(surface, 70, 3, 50);
				/*caveLiquidGen(caves, minHeight, maxHeight)
				 *minHeight is the minimal height of air in the cave
				 *maxHeight is the maximal height of air in the cave
				 */
				caves = liquidGen.caveLiquidGen(caves, 5, 50);
			System.out.println(" "+chrono.getTime()+"ms");
			
			chrono.reset();
			System.out.print("Placing the Blocks...");
			int k = 0;
			int lastSwitchI = 0;
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				if(i == lastSwitchI+biomeList.get(k).getWidth()) {
					k++;
					lastSwitchI = i;
				}
				
				/* 
				for(int j=0; j<Map.LIMIT_CAVE;j++)
				{
					if(caves.get(i).get(j) == 1) 
					{
						w.getMap().setBlock(i, j, new BlockAsh(i,j,w)); 
					}
				} */
				
				/* CAVES */
				for(int j=0; j<Map.LIMIT_SURFACE+surface.get(i);j++)
				{
					if (j<Map.LIMIT_SURFACE) {
						if(caves.get(i).get(j) == 1) 
						{	
							if (j<Map.LIMIT_CAVE+surface.get(i)) 
							{
								w.getMap().setBlock(i, j, new BlockAsh(i,j,w)); 
							}
							else
							{
								w.getMap().setBlock(i, j, new BlockStone(i,j,w)); 
							}
						}
						else if(caves.get(i).get(j) == 2)
						{
							if(j<Map.LIMIT_CAVE) {
								w.getMap().setBlock(i, j, new BlockLava(i,j,0,w));
							}
							else {
								w.getMap().setBlock(i, j, new BlockWater(i,j,0,w));
							}
						}
					}
					else
					{
						w.getMap().setBlock(i, j, new BlockStone(i,j,w)); 
					}
				}
				
				/* SURFACE */
				for(int j=Map.LIMIT_SURFACE+surface.get(i); j<Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+surface.get(i)+surfaceLiquid[i]+1;j++)
				{
					if (j<Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+surface.get(i)) //dirt
					{
						if (j==Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+surface.get(i)-1) { //grass
							BiomeList.createSurfaceGrassBlock(i, j, w, biomeList.get(k).getId());
						}
						else { //dirt
							BiomeList.createSurfaceBlock(i, j, w, biomeList.get(k).getId());
						}
						
						
						//w.getMap().setBlock(i, j, new BlockDirt(i,j,w));
					}
					else if (j<Map.LIMIT_SURFACE+MapGenerator.DIRT_SURFACE+surface.get(i)+surfaceLiquid[i]) //water
					{
						w.getMap().setBlock(i, j, new BlockWater(i,j,0,w));
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
			System.out.println(" "+chrono.getTime()+"ms");
			System.out.println("---- GENERATION DONE, TOTAL TIME: "+chrono2.getTime()+"ms ----\n");
	}
}
