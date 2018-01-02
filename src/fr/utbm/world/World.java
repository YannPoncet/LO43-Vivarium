package fr.utbm.world;

import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.FPSLogger;

import fr.utbm.block.Block;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockWater;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityBeaver;
import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.entity.EntityHellDog;
import fr.utbm.generation.MapGenerator;
import fr.utbm.render.RenderManager;

public class World {
	
	private Map map;
	private CopyOnWriteArrayList<Entity> entities;
	private EntityFallingBlock test;
	private BlockSand bs;
	private EntityBeaver hd;
	
	
	private float gravity = -4f;
	private FPSLogger fps;
	private int currentChunkCam;
	private int currentYCam;
	
	public World()
	{
		map = new Map();
		entities = new CopyOnWriteArrayList<Entity>();
		this.create();
		fps = new FPSLogger();
		currentChunkCam = 0;
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
	public CopyOnWriteArrayList<Entity> getEntities(){
		return entities;
	}
	public void addEntity(Entity e){
		entities.add(e);
		RenderManager.addToEntitiesRender(e);
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
		MapGenerator.generate(this, 0); //0 to generate a new seed ->6 / 14
		test = new EntityFallingBlock(4,320,16,16,this, new BlockSand(4,320,this));
		bs = new BlockSand(3,320,this);
		setBlock(3, 310, new BlockWater(3,310, 0,this));
		setBlock(3,320,bs);
		hd =  new EntityBeaver(5,380,this);
		entities.add(test);
		//entities.add(new EntityBenenutTree(20, 360, this));
		entities.add(hd);
		render();
	}
	
	public void update()
    {
        this.map.update(currentChunkCam);
        fps.log();
        for (Entity e: entities) {
            if(e.isDead()){
                entities.remove(e);
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
			currentChunkCam = cID;
			map.render(cID);
		}
	}

	
}