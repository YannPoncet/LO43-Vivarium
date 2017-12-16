package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityHellDog extends EntityAnimal{
	
	Animation walkAnimation; 
	Texture walkSheet;
	float stateTime;
	
	public EntityHellDog(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
		create();
		
		
		
		
		
		
	}
	public void create(){
		
		walkSheet = TextureManager.getTexture(10);
		
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
				walkSheet.getWidth() / 12,
				walkSheet.getHeight());
		TextureRegion[] walkFrames = new TextureRegion[12 * 1];
		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 12; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.1f, walkFrames);
		
	}
	float y = 30*16;
	@Override
	public void render(SpriteBatch sp) {

		y-=0.5f;
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		sp.draw(currentFrame, y, 201*16);
	}
}
