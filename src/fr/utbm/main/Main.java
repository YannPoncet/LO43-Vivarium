package fr.utbm.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.utbm.render.RenderManager;
import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;

public class Main extends ApplicationAdapter {
	
	private Texture test;
	private SpriteBatch batch;
	private RenderManager rm;
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	
   @Override
   public void create() {
	   TextureManager.loadTextures();
	   batch = new SpriteBatch();
	   gameCam = new OrthographicCamera();
	   gameCam.position.set(800 / 2f, 600 / 2f, 0);
	   gamePort = new FitViewport(800,600,gameCam);
	   test = TextureManager.getTexture(1);
	   rm = new RenderManager(batch);
	   for(int i = 0;i<70;i++){
		   for(int j=0;j<5;j++){
			   rm.addToBlockRender(new Renderable(16*i,16*j,test));
		   }
	   }
	   rm.addToBlockRender(new Renderable(16*69,16*5,test));
	   
   }
   public void update(){
	   if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
		   gameCam.position.x += 3;
	   }
	   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
		   gameCam.position.x -= 3;
	   }
	   if (Gdx.input.isKeyPressed(Input.Keys.UP)){
		   gameCam.position.y += 3;
	   }
	   if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		   gameCam.position.y -= 3;
	   }
	   gameCam.update();
	   batch.setProjectionMatrix(gameCam.combined);
   }
   @Override
   public void render() {
	   update();
	   rm.renderAll();
   }
   @Override
   public void dispose() {
	   
   }
   @Override
   public void resize(int width,int height){
	   gamePort.update(width,height);
   }
   
}
