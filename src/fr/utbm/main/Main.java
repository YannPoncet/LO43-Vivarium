package fr.utbm.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.view.Camera;
import fr.utbm.world.World;

public class Main extends ApplicationAdapter {
	
	private Texture test;
	private SpriteBatch batch;
	private Camera camera;
	private World w;
	private FPSLogger fps;
   @Override
   public void create() {
	   fps = new FPSLogger();
	   TextureManager.loadTextures();
	   batch = new SpriteBatch();
	   RenderManager.setBatch(batch);
	   camera = new Camera();
	   w = new World();
	   
   }
   public void update(){
	   fps.log();
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
