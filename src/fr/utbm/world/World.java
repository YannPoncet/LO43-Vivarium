package fr.utbm.world;

import java.util.ArrayList;

import fr.utbm.block.Block;
import fr.utbm.entity.Entity;

public class World {
	
	private Map map;
	private ArrayList<Entity> entities;
	
	public World()
	{
		map = new Map();
		entities = new ArrayList<>();
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
	
	public Entity getEntity(int i)
	{
		//TODO
		return entities.get(i);
	}
	
	public void render()//ici mettre l'ID du chunk sur lequel se trouve la camera
	{
		map.render(5);
	}
	
	public void update()
	{
		for(Entity e : this.entities)
		{
			e.update();
		}
		
	}
	
}
