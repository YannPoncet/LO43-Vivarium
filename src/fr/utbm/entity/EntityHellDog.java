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
		walkAnimation = TextureManager.getAnimation(0);
	}
	@Override
	public void render(SpriteBatch sp) {
		if(!isOnGround()){
			y-=8f;
		}
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		sp.draw(currentFrame, this.x, this.y);
	}
}
