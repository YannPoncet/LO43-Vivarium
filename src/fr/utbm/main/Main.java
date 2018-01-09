package fr.utbm.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.view.Camera;
import fr.utbm.world.World;

public class Main extends Game {
	
	private SpriteBatch batch;

   @Override
   public void create() {
	   TextureManager.loadTextures();
	   batch = new SpriteBatch();
	   this.setScreen(new MainMenuScreen(this));
   }

   @Override
   public void render() {
	   super.render();
   }
   @Override
   public void dispose() {
	   
   }
   @Override
   public void resize(int width,int height){
	   
   }
   public void startGame(double seed){
	   this.setScreen(new World(this,batch));
   }
   public void backMenu(){
	   this.setScreen(new MainMenuScreen(this));
	   RenderManager.cleanAllRender();
   }
   
}