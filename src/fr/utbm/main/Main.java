package fr.utbm.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.view.Camera;
import fr.utbm.world.World;

public class Main extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private Camera camera;
	private World w;
   @Override
   public void create() {
	   TextureManager.loadTextures();
	   batch = new SpriteBatch();
	   RenderManager.setBatch(batch);
	   w = new World();
	   camera = new Camera(w);
	   
   }
   public void update(){

	   w.update();
	   camera.update();
	   batch.setProjectionMatrix(camera.getProjectionMatrix());
	   
   }
   @Override
   public void render() {
	   update();
	   RenderManager.renderAll();
   }
   @Override
   public void dispose() {
	   
   }
   @Override
   public void resize(int width,int height){
	   camera.resize(width,height);
   }
   
}