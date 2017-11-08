package fr.utbm.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	
	private Texture test;
	private SpriteBatch batch;
	
   @Override
   public void create() {
	   
	   batch = new SpriteBatch();
	   test = new Texture(Gdx.files.internal("res/textures/test.png"));
   }
   @Override
   public void render() {
	   batch.begin();
	   batch.draw(test, 50, 50);
	   batch.end();
   }
   @Override
   public void dispose() {
	   
   }
   
}
