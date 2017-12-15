package fr.utbm.world;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;

public class MapGenerator {

	public static void generate(World world)
	{
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				for(int j=0; j<Chunk.CHUNK_HEIGHT/2;j++)
				{
					world.setBlock(i, j, new BlockDirt(i,j));
				}
			}
			for(int i=0; i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;i++)
			{
				world.setBlock(i, Chunk.CHUNK_HEIGHT/2, new BlockGrass(i,Chunk.CHUNK_HEIGHT/2));
			}
			
		

	}
}
