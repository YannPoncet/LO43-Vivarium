package fr.utbm.world;

import java.util.ArrayList;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;
import fr.utbm.world.Noise1D;

public class MapGenerator {
	public static void generate(World world)
	{
			final int grassHeight = 2;
			Noise1D noiseGen = new Noise1D();
			
			//To fill the parameters: generateAndGetNoise(double amplitude, double wawelength, int octaves, double divisor)
			//=>Increase wawelenth to get flat map locally
			//=>Decrease amplitude to get a flat map generally
			ArrayList<Integer> surface = noiseGen.generateAndGetNoise(80,128,15,4);
			
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				for(int j=0; j<Chunk.CHUNK_HEIGHT/2+surface.get(i);j++)
				{
					world.setBlock(i, j, new BlockDirt(i,j));
				}
				for(int j=0; j<grassHeight;j++)
				{
					world.setBlock(i, Chunk.CHUNK_HEIGHT/2+surface.get(i)+j, new BlockGrass(i,Chunk.CHUNK_HEIGHT/2+surface.get(i)+j));
				}
			}
	}
}
