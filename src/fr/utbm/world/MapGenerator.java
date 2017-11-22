package fr.utbm.world;

import fr.utbm.block.BlockGrass;

public class MapGenerator {

	public void generate(World world)
	{
		for(int i=0; i<Chunk.CHUNK_HEIGHT/2;i++)
		{
			for(int j=0; j<Chunk.CHUNK_WIDTH;j++)
			{
				world.setBlock(i, j, new BlockGrass(i,j));
			}
		}
	}
}
