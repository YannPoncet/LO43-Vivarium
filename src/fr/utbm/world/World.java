package fr.utbm.world;

import java.util.ArrayList;

import fr.utbm.block.Block;
import fr.utbm.block.BlockGrass;
import fr.utbm.entity.Entity;
import fr.utbm.render.RenderManager;

public class World {
	
	private Map map;
	private ArrayList<Entity> entities;
	
	public World()
	{
		map = new Map();
		entities = new ArrayList<>();
		this.create();
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
		map.render(0);
	}
	
	/* Call at the World creation */
	public void create(){
		MapGenerator gen = new MapGenerator();
		MapGenerator.generate(this);
		render();
	}
	
	public void update()
	{
		for(Entity e : this.entities)
		{
			e.update();
		}
		
	}
	public void resize(){
		
	}
	
}
