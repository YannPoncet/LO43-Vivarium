package fr.utbm.render;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class RenderManager {

	private static SpriteBatch batch;
	private static ArrayList<Renderable> blockRender =  new ArrayList<Renderable>();
	
	
	private static ArrayList<Renderable> entitiesRender =  new ArrayList<Renderable>();
	
	public static void setBatch(SpriteBatch sb){
		RenderManager.batch = sb;
	}

	public static void renderAll(){
		Gdx.gl.glClearColor(0.52f,0.66f,0.97f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		/* Dirt background */
		/*Texture dirtBackground = new Texture(Gdx.files.internal("res/backgrounds/dirtBackground.png"));
		dirtBackground.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		TextureRegion dirtBackgroundRegion = new TextureRegion(dirtBackground);
		dirtBackgroundRegion.setRegion(0,0, dirtBackground.getWidth()*Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH, dirtBackground.getHeight()*(Map.LIMIT_SURFACE-Map.LIMIT_CAVE));

		batch.draw(dirtBackgroundRegion, 16, Map.LIMIT_CAVE*16+16);*/

		renderArray(blockRender);
		renderArray(entitiesRender);
		//System.out.println("RENDER-MANAGER : Show " + blockRender.size() + " elements");
		batch.end();
	}
	private static void renderArray(ArrayList<Renderable> rl){
			Iterator<Renderable> iter = rl.iterator();
			while (iter.hasNext()) {
				Renderable r = iter.next();
				if(r.isDead()){
					iter.remove();
				}else{
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
		RenderManager.entitiesRender.add(r);
	}
}
