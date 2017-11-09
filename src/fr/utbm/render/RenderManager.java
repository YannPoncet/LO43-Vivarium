package fr.utbm.render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderManager {

	private SpriteBatch batch;
	private ArrayList<Renderable> blockRender;
	
	public RenderManager(SpriteBatch sb){
		this.batch = sb;
		this.blockRender =  new ArrayList<Renderable>();
	}
	public void renderAll(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderArray(blockRender);
		batch.end();
	}
	private void renderArray(ArrayList<Renderable> rl){
		for(Renderable r : rl){
			if(r.isDead()){
				rl.remove(r);
			}else{
				r.render(batch);
			}
		}
	}
	public void addToBlockRender(Renderable r){
		blockRender.add(r);
	}
	
	
	
	
	
	
	
	
}
