package fr.utbm.world;

import fr.utbm.block.Block;

public class World {
	private Map map;
	
	public World()
	{
		map = new Map();
	}
	
	public Block getBlock(int i, int j)
	{
		return map.getBlock(i,j);
	}
	
	public void setBlock(int i, int j, Block block)
	{
		this.map.setBlock(i, j, block);
	}
	
	public Chunk getChunk(int i)
	{
		return this.map.getChunk(i);
	}
	
}
