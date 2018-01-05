package fr.utbm.world;

import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.FPSLogger;

import fr.utbm.block.Block;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockWater;
import fr.utbm.block.BlockWood;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimalDigger;
import fr.utbm.entity.EntityBeaver;
import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.entity.EntityPrettyBird;
import fr.utbm.generation.MapGenerator;
import fr.utbm.render.RenderManager;

public class World {
	
	private Map map;
	private CopyOnWriteArrayList<Entity> entities;
	private EntityFallingBlock test;
	private BlockSand bs;
	private EntityPrettyBird pb1;
	private EntityPrettyBird pb2;
	private EntityPrettyBird pb3;
	private EntityPrettyBird pb4;
	private EntityPrettyBird pb5;
	private EntityBeaver beaver;
	private EntityAnimalDigger digger;
	
	
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
	public Block getBlock(float i, float j)
	{
		return map.getBlock((int)i,(int)j);
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
	}
	
	/* Call at the World creation */
	public void create(){
		MapGenerator.generate(this, 888432309); //0 to generate a new seed ->6 / 14
		test = new EntityFallingBlock(4,320,16,16,this, new BlockSand(4,320,this));
		bs = new BlockSand(3,320,this);
		setBlock(3, 310, new BlockWater(3,310, 0,this));
		for(int i = 0; i < 6 ; i++)
		{
			setBlock(i+20, 320, new BlockWood(i+20,320, 0,this));
			setBlock(i+20, 325, new BlockWood(i+20,325, 0,this));
			setBlock(i+20, 319, new BlockWood(i+20,319, 0,this));
			if(i == 0)
			{
				setBlock(i+20, 321, new BlockWood(i+20,321, 0,this));
				setBlock(i+20, 322, new BlockWood(i+20,322, 0,this));
				setBlock(i+20, 323, new BlockWood(i+20,323, 0,this));
				setBlock(i+20, 324, new BlockWood(i+20,324, 0,this));
			}
			if(i == 5)
			{
				setBlock(i+20, 321, new BlockWood(i+20,321, 0,this));
				setBlock(i+20, 322, new BlockWood(i+20,322, 0,this));
				setBlock(i+20, 323, new BlockWood(i+20,323, 0,this));
				setBlock(i+20, 324, new BlockWood(i+20,324, 0,this));
			}
		}
		setBlock(3,320,bs);
		pb1 =  new EntityPrettyBird(10,325,this);
		pb2 =  new EntityPrettyBird(15,320,this);
		pb3 =  new EntityPrettyBird(20,315,this);
		pb4 =  new EntityPrettyBird(25,310,this);
		pb5 =  new EntityPrettyBird(30,305,this);
		beaver = new EntityBeaver(5,325,this);
		digger = new EntityAnimalDigger(8,325, this);
		entities.add(test);
		addEntity(test);
		entities.add(pb1);
		entities.add(pb2);
		entities.add(pb3);
		entities.add(pb4);
		entities.add(pb5);
		entities.add(beaver);
		addEntity(digger);
		render();
		//addEntity(beaver);
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