package fr.utbm.world;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fr.utbm.biome.Biome;
import fr.utbm.block.Block;
import fr.utbm.block.BlockSand;
import fr.utbm.block.BlockWater;
import fr.utbm.block.BlockWood;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimalCuteFlower;
import fr.utbm.entity.EntityAnimalDwarfKing;
import fr.utbm.entity.EntityAnimalDwarfMiner;
import fr.utbm.entity.EntityAnimalDwarfWarrior;
import fr.utbm.entity.EntityAnimalHellFish;
import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.generation.MapGenerator;
import fr.utbm.main.Main;
import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.ux.GraphicScene;
import fr.utbm.ux.MainUX;
import fr.utbm.view.Camera;

public class World implements Screen{
	
	private Map map;
	private CopyOnWriteArrayList<Entity> entities;
	private EntityFallingBlock test;
	
	ArrayList<Biome> biomeList;
	
	private float gravity = -4f;
	private FPSLogger fps;
	private int currentChunkCam;
	private int currentYCam;
	
	private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    
    private GraphicScene gs;
    
    private float xCam,yCam;
	private Camera camera;
	private Main main;
	private double seed;
	
	public World(Main m,SpriteBatch batch, double seed)
	{
		this.seed =seed;
		this.main = m;
		RenderManager.setBatch(batch);
		camera = new Camera(this);
		map = new Map();
		entities = new CopyOnWriteArrayList<Entity>();
		gs = new MainUX(this);
		RenderManager.setUI(gs);
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
	public Entity getEntityAt(float x, float y){
		Entity entity = null;
		for(Entity e : entities){
			if(CollisionAABB.isCol(e.getPosX(),e.getPosY(),e.getWidth(),e.getHeight(),x,y,1,1)){
				entity = e;
			}
		}
		return entity;
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
		
	}
	
	/* Call at the World creation */
	public void create(){
		gs.create();
		MapGenerator.generate(this, seed); //0 to generate a new seed ->6 / 14
		test = new EntityFallingBlock(4,320,16,16,this, new BlockSand(4,320,this));
		map.render(0);
		
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
        camera.update();
        RenderManager.setProjectionMatrix(camera.getProjectionMatrix());
        RenderManager.renderAll();
        
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
	public void setCamPos(float x, float y){
		this.xCam = x;
		this.yCam = y;
	}
	public float getXCam(){
		return this.xCam;
	}
	public float getYCam(){
		return this.yCam;
	}

	public void setBiomeList(ArrayList<Biome> biomeList) {
		this.biomeList = biomeList;		
	}
	
	public int getBiomeIn(int xToFind)
	{
		int x=0;
		for(Biome b: this.biomeList)
		{
			x += b.getWidth();
			if(xToFind < x)
			{
				return b.getId();
			}
		}
		
		return -1;
	}
	public void backMenu(){
		main.backMenu();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float arg0) {
		update();
		
		
		
		
		
		
	}

	@Override
	public void resize(int width,int height) {
		camera.resize(width,height);
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
		
	}

	
}