package fr.utbm.world;

import fr.utbm.block.BlockDirt;
import fr.utbm.block.BlockGrass;

public class MapGenerator {

	public static void generate(Map m)
	{
			for(int i=0; i<Chunk.CHUNK_WIDTH;i++)
			{
				for(int j=0; j<Chunk.CHUNK_HEIGHT/2;j++)
				{
					m.setBlock(i, j, new BlockDirt(i,j));
				}
			}
			for(int i=0; i<Chunk.CHUNK_WIDTH;i++)
			{
				m.setBlock(i, Chunk.CHUNK_HEIGHT/2, new BlockGrass(i,Chunk.CHUNK_HEIGHT/2));
			}
			
		

	}
}
