package fr.utbm.world;

import java.util.ArrayList;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;

public class MapGenerator {
	public static void generate(Map m, double seed)
	{
			final int grassHeight = 2;
			
			long M = 4294967296L;
			if (seed == 0) 
			{
				seed = Math.floor(Math.random() * M);
			}
			
			Noise1DGenerator noiseGen = new Noise1DGenerator(seed, M);
			//To fill the parameters: generateAndGetNoise(double amplitude, double wavelength, int octaves, double divisor)
			//=>Increase wavelength to get flat map generally
			//=>Decrease amplitude to get a flat map locally
			ArrayList<Integer> surface = noiseGen.generateAndGetNoise(20,128,15,4);
			
			
			//Change the value of the last parameter (0 to 100) to increase the dirt ratio
			Cave2DGenerator caveGen = new Cave2DGenerator(seed, M, 75);
			

			
			ArrayList<ArrayList<Integer>> caves = caveGen.generateAndGetCaves();
			/*
	        for (int i=0; i<caves.size(); i++) {
	            for (int j=0; j<caves.get(i).size(); j++) {
	            	System.out.print(caves.get(i).get(j));
	           }
	           System.out.println(" ");
	        }*/
			
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				for(int j=0; j<Chunk.CHUNK_HEIGHT/2+surface.get(i);j++)
				{
					if(caves.get(i).get(j) == 1) 
					{
						m.setBlock(i, j, new BlockDirt(i,j,m)); 
					}
				}
				for(int j=0; j<grassHeight;j++)
				{
					m.setBlock(i, Chunk.CHUNK_HEIGHT/2+surface.get(i)+j, new BlockGrass(i,Chunk.CHUNK_HEIGHT/2+surface.get(i)+j,m));
				}
			}
	}
}
