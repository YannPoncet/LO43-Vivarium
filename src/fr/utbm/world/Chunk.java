package fr.utbm.world;

import fr.utbm.block.Block;

public class Chunk {
	public final static int CHUNK_WIDTH=50;
	public final static int CHUNK_HEIGHT=400;
	private int chunkID;
	private Block[][] blocks;
	//private Biome biome;
	
	
	public Chunk()
	{
		chunkID = 0;
		blocks = new Block[CHUNK_HEIGHT][CHUNK_WIDTH];
	}
	
	public Chunk(int ID)
	{
		chunkID = ID;
		blocks = new Block[CHUNK_HEIGHT][CHUNK_WIDTH];
	}
	
	public int getID()
	{
		return this.chunkID;
	}
	
	public Block getBlock(int i, int j)
	{
		return this.blocks[i][j];
	}
	
	public void setBlock(int i, int j, Block block)
	{
		this.blocks[i][j] = block;
	}
	
}
