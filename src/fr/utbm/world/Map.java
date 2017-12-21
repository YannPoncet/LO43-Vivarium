package fr.utbm.world;

import fr.utbm.block.Block;

public class Map {
	public final static int LIMIT_SURFACE=300;
	public final static int LIMIT_CAVE=100;
	
	public final static int NUMBER_OF_CHUNKS=100;
	private Chunk[] chunks;
	
	public Map()
	{
		//créer le tableau
		chunks = new Chunk[NUMBER_OF_CHUNKS]; 
		
		//remplir le tableau de chunks vides
		for(int i=0; i<NUMBER_OF_CHUNKS; i++)
		{
			this.chunks[i] = new Chunk(i);
		}
		
	}
	
	public Chunk getChunk(int i)
	{
		return this.chunks[i];
	}
	
	public Block getBlock(int i, int j)
	{
		return chunks[i/Chunk.CHUNK_WIDTH].getBlock(i%Chunk.CHUNK_WIDTH,j);
	}
	
	public void setBlock(int i, int j, Block block)
	{
		this.chunks[i/Chunk.CHUNK_WIDTH].setBlock(i%Chunk.CHUNK_WIDTH,j,block);
	}
	
	public void render(int i) //call render on the 5 chunks (2 left, this, and 2 right)
	{
		this.chunks[i].render();
		
		if(i!=0)
		{
			this.chunks[i-1].render();
			if(i!=1)
			{
				this.chunks[i-2].render();
			}
		}
		
		if(i!=NUMBER_OF_CHUNKS-1)
		{
			this.chunks[i+1].render();
			if(i!=NUMBER_OF_CHUNKS-2)
			{
				this.chunks[i+2].render();
			}
		}	
	}
	public void update(int i) //call render on the 5 chunks (2 left, this, and 2 right)
	{
		this.chunks[i].update();	
	}
	
	
	
}
