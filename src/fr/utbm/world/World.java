package fr.utbm.world;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.FPSLogger;

import fr.utbm.block.Block;
import fr.utbm.block.BlockGrass;
import fr.utbm.block.BlockWater;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.entity.EntityHellDog;
import fr.utbm.generation.MapGenerator;
import fr.utbm.render.RenderManager;

public class World {
	
	private Map map;
	private ArrayList<Entity> entities;
	private EntityFallingBlock test;
	private EntityFallingBlock test2;
	private BlockWater testWater;
	private EntityHellDog hd;
	private float gravity = -8.81f;
	private FPSLogger fps;
	
	public World()
	{
		map = new Map();
		entities = new ArrayList<Entity>();
		this.create();
		fps = new FPSLogger();
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
	public Map getMap(){
		return map;
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
		MapGenerator.generate(this, 0); //0 to generate a new seed
		test = new EntityFallingBlock(0,320,16,16,this);
		test2 = new EntityFallingBlock(1,315,16,16,this);
		hd =  new EntityHellDog(5,330,64,32,this);
		setBlock(9, 245, new BlockGrass(9,345,this));
		testWater = new BlockWater(9,314,0,this);
		setBlock(9, 314, testWater);
		//setBlock(6, 245, new BlockGrass(6,245,this.map));
		entities.add(test);
		entities.add(test2);
		entities.add(hd);
		render();
	}
	
	public void update()
	{
		this.map.update(0);
		fps.log();
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

	public float getGravity() {
		return gravity;
	}

	
}
