package fr.utbm.world;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.FPSLogger;

import fr.utbm.block.Block;
import fr.utbm.block.BlockDirt;
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
	private EntityHellDog hd;
	
	
	private float gravity = -4f;
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
		test = new EntityFallingBlock(4,320,16,16,this);
		test2 = new EntityFallingBlock(1,315,16,16,this);
		hd =  new EntityHellDog(5,330,this);
		setBlock(9, 245, new BlockDirt(9,345,this));
		for(int i = 0; i < 20 ; i++)
		{
			setBlock(i+20, 330, new BlockWater(i+20,330,0,this));
			for(int j = 0 ; j < 5 ; j++)
			{
				if(i == j)
				{
					setBlock(i+20, j+312, new BlockDirt(i+20,j+312,this));
				}
				/*if(i == j+14)
				{
					setBlock(i+20, j+312, new BlockGrass(i+20,j+312,this));
				}*/
				if(i == 0)
				{
					setBlock(19, j+312, new BlockDirt(19,j+312,this));
				}
				if(i == 19)
				{
					setBlock(40, j+312, new BlockDirt(40,j+312,this));
				}
			}
		}
		setBlock(34, 316, new BlockGrass(34,316,this));
		setBlock(35, 315, new BlockGrass(35,315,this));
		setBlock(36, 314, new BlockGrass(36,314,this));
		setBlock(37, 313, new BlockGrass(37,313,this));
		setBlock(38, 312, new BlockGrass(38,312,this));
		//setBlock(33, 340, new BlockWater(33,380,0,this));
		//setBlock(30, 380,new BlockWater(30,380,0,this));
		//setBlock(32, 320,new BlockWater(32,320,0,this));
		/*setBlock(40, 312, new BlockGrass(40,312,this));
		setBlock(40, 313, new BlockGrass(40,313,this));
		setBlock(19, 312, new BlockGrass(19,312,this));
		setBlock(19, 313, new BlockGrass(19,313,this));*/
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
	public void cameraSwitchChunkChunk(int cID){
		RenderManager.cleanRender();
		if (cID > -1 && cID < Map.NUMBER_OF_CHUNKS+1) {
			map.render(cID);
		}
	}

	
}