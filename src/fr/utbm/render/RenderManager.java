package fr.utbm.render;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.biome.Biome;
import fr.utbm.texture.TextureManager;
import fr.utbm.ux.GraphicScene;
import fr.utbm.view.Camera;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class RenderManager {

	private static SpriteBatch batch;
	private static ArrayList<Biome> biomeList;
	private static ArrayList<Renderable> blockRender =  new ArrayList<Renderable>();
	private static ArrayList<Renderable> entitiesRender =  new ArrayList<Renderable>();
	private static float x=0;
	private static float y=0;
	private static int margin = 30;
	private static GraphicScene ux;
	
	
	
	public static void setBatch(SpriteBatch sb){
		RenderManager.batch = sb;
	}
	//public static long t;
	public static void renderAll(){
		//t =  TimeUtils.millis();
		Gdx.gl.glClearColor(0.52f,0.66f,0.97f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		drawBackground();
		renderArray(blockRender);
		renderArray(entitiesRender);
		//System.out.println("RENDER-MANAGER : Show " + blockRender.size() + " elements");
		
		batch.end();
		ux.render();
		//System.out.println("TIME :" + TimeUtils.timeSinceMillis(t));
	}
	private static void renderArray(ArrayList<Renderable> rl){
			Iterator<Renderable> iter = rl.iterator();
			while (iter.hasNext()) {
				Renderable r = iter.next();
				if(r.isDead()){
					iter.remove();
				}else if(r.getX() >= x-(16*margin) && r.getX() <= (x+Camera.WIDTH+(16*margin)) && r.getY() >= y-(16*margin) && r.getY() <= (y+Camera.HEIGHT+(16*margin))){
					r.render(batch);
				}
			}
	}
	public static void cleanRender(){
		blockRender.clear();
	}
	public static void addToBlockRender(Renderable r){
		RenderManager.blockRender.add(r);
	}
	public static void addToEntitiesRender(Renderable r){
		if(!RenderManager.entitiesRender.contains(r)){
			RenderManager.entitiesRender.add(r);
		}

	}
	public static void removeEntityRender(Renderable r){
		RenderManager.entitiesRender.remove(r);
	}
	public static void setBiomeList(ArrayList<Biome> biomeList) {
		RenderManager.biomeList = biomeList;
	}
	public static void drawBackground(){
		//500 -> sky [above forest.png]
		//501 -> forest [above Map.LIMIT_SURFACE] [depending on the biome]
		//502 -> cave [between Map.LIMIT_CAVE and Map.LIMIT_SURFACE]
		//503 -> hell [below Map.LIMIT_CAVE]
		int backgroundsWidth = 1200;
		int backgroundsHeight = 675;
		
		//the part below is used to know in which texture to apply in the surface depending on x pos
		int textureId = 501; //default
		if(biomeList.size()>0) {
			textureId = biomeList.get(0).getTextureId(); //better default
			int totalWidth = 0;
			boolean gotIt = true;
			int index = 0;
			while(index < biomeList.size() && gotIt) {
				totalWidth += biomeList.get(index).getWidth();
				if (x/16<=totalWidth && x/16>totalWidth-biomeList.get(index).getWidth()) {
					gotIt = false;
					textureId = biomeList.get(index).getTextureId();
				}
				index++;
			}
		}
		
		
		if (x<0) { //left border
			x -= x-16;
		}
		else if (x==0){
			x=16;
		}
		else if (x>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16-Camera.WIDTH) { //right border
			x=Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16-Camera.WIDTH-16;
		}
		
		if (y<16) { //bottom of the map
			y -=y-16;
			batch.draw(TextureManager.getTexture(503),x,y);
			batch.draw(TextureManager.getTexture(503),x,y+backgroundsHeight);
		}
		else if (y+16>Chunk.CHUNK_HEIGHT*16-Camera.HEIGHT) { //top border
			y -= (y-Chunk.CHUNK_HEIGHT*16)+Camera.HEIGHT+16;
			int overflow = (int)(y+backgroundsHeight-Chunk.CHUNK_HEIGHT*16+16);
			batch.draw(TextureManager.getTexture(500),x,y, 0, 0, backgroundsWidth, backgroundsHeight-overflow);
			//batch.draw(TextureManager.getTexture(500),x,y, 0, 0, backgroundsWidth, 200);
		}
		else if (y < Map.LIMIT_CAVE*16) { 
			batch.draw(TextureManager.getTexture(503),x,y);
			batch.draw(TextureManager.getTexture(503),x,y+backgroundsHeight);
			if (y/16+Camera.HEIGHT/16 > Map.LIMIT_CAVE) { //if close to the caves
				batch.draw(TextureManager.getTexture(502),x,Map.LIMIT_CAVE*16);
				batch.draw(TextureManager.getTexture(502),x,Map.LIMIT_CAVE*16+backgroundsHeight);
			}
		}
		else if (y/16 < Map.LIMIT_SURFACE) {
			batch.draw(TextureManager.getTexture(502),x,y);
			batch.draw(TextureManager.getTexture(502),x,y+backgroundsHeight);
			if (y/16+Camera.HEIGHT/16 > Map.LIMIT_SURFACE) { //if close to the surface
				batch.draw(TextureManager.getTexture(textureId),x,Map.LIMIT_SURFACE*16);
				batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight);
			}
		}
		else if (y/16 < Map.LIMIT_SURFACE+backgroundsHeight/16) {
			batch.draw(TextureManager.getTexture(textureId),x,Map.LIMIT_SURFACE*16);
			batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight);
			batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight*2);
		}
		else {
			batch.draw(TextureManager.getTexture(500),x,y);
			batch.draw(TextureManager.getTexture(500),x,y+backgroundsHeight);
		}
		
		
		
	}
	public static void setUI(GraphicScene gs){
		ux = gs;
	}
	public static void setBgPos(float x, float y){
		RenderManager.x = x;
		RenderManager.y = y;
	}
}
