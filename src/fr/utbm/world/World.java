package fr.utbm.world;

import java.util.ArrayList;
import java.util.Iterator;

import fr.utbm.block.Block;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.render.RenderManager;

public class World {
	
	private Map map;
	private ArrayList<Entity> entities;
	private EntityFallingBlock test;
	private EntityFallingBlock test2;
	public World()
	{
		map = new Map();
		entities = new ArrayList<Entity>();
		this.create();
	}
	
	public Block getBlock(int i, int j)
	{
		return map.getBlock(i,j);
	}
	
	public void setBlock(int i, int j, Block block)
	{
		this.map.setBlock(i, j, block);
		RenderManager.addToBlockRender(block);
	}
	
	public Chunk getChunk(int i)
	{
		return this.map.getChunk(i);
	}
	
	public Entity getEntity(int i)
	{
		return entities.get(i);
	}
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void render()//ici mettre l'ID du chunk sur lequel se trouve la camera
	{
		map.render(0);
		for(Entity e : this.entities)
		{
			RenderManager.addToEntitiesRender(e);
		}
	}
	
	/* Call at the World creation */
	public void create(){
		MapGenerator.generate(map);
		test = new EntityFallingBlock(0,210,16,16,this);
		test2 = new EntityFallingBlock(1,215,16,16,this);
		entities.add(test);
		entities.add(test2);
		render();
	}
	
	public void update()
	{
		Iterator<Entity> iter = entities.iterator();
		while (iter.hasNext()) {
			Entity e = iter.next();
			if(e.isDead()){
				iter.remove();
			}else{
				e.update();
			}
		}
		
	}
	public void resize(){
		
	}
	
}
