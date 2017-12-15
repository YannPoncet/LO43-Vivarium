package fr.utbm.render;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderManager {

	private static SpriteBatch batch;
	private static ArrayList<Renderable> blockRender =  new ArrayList<Renderable>();
	private static ArrayList<Renderable> entitiesRender =  new ArrayList<Renderable>();
	
	public static void setBatch(SpriteBatch sb){
		RenderManager.batch = sb;
	}

	public static void renderAll(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderArray(blockRender);
		renderArray(entitiesRender);
		System.out.println("RENDER-MANAGER : Show " + blockRender.size() + " elements");
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
	public static void addToBlockRender(Renderable r){
		RenderManager.blockRender.add(r);
	}
	public static void addToEntitiesRender(Renderable r){
		RenderManager.entitiesRender.add(r);
	}
}
