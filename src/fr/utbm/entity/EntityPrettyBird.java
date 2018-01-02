package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityPrettyBird extends EntityAnimalFlying {
	
	/*
	 * Bird activity : -1 Dont do anything 0 Idle 1 Fly horizontaly 2 Fly verticaly
	 */
	
	public EntityPrettyBird(float x, float y, World worldIn) {
		super(x, y, 16, 16, worldIn);
		text = TextureManager.getTexture(222);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(15);
		anim[1] = TextureManager.getAnimation(16);
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = -1;
		directionToPerform = 1;
	}
	
	public void update(){
		if(!perform){
			if(directionToPerform == 1) {
				actionToPerform = 1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			} else if (directionToPerform == 2) {
				actionToPerform = 2;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}
			else {
				directionToPerform = -directionToPerform;
				activity = -1;
			}
		}else{
			
		}
	}
	
	public void action(int actionID, int direction) {
		switch (actionID) {

		case 0:
			if (isOnGround()) {
				move(0, 0, 0);
			} else {
				move(0, 0, -1);
			}
			break;
		case 1:
			if (isOnGround()) {
				move(0.2f * direction, 0, 1);
			} else {
				move(0, 0, activity);
			}
			break;
		case 2:
			if (isOnGround()) {
				move(0.5f * direction, 0, 2);
			} else {
				move(0, 0, activity);
			}
			break;
		}
	}
	
	@Override
	public void render(SpriteBatch sp) {
		if (activity > -1) {
			stateTime += Gdx.graphics.getDeltaTime();
			TextureRegion currentFrame = anim[activity].getKeyFrame(stateTime, true);
			if (directionX == -1) {
				sp.draw(currentFrame, this.x, this.y);
			} else if (directionX == 1) {
				sp.draw(currentFrame, this.x + currentFrame.getRegionWidth(), this.y, -currentFrame.getRegionWidth(),
						currentFrame.getRegionHeight());
			}
			perform = !anim[activity].isAnimationFinished(stateTime);
		} else {
			if (directionX == -1) {
				sp.draw(this.text, this.x, this.y);
			} else if (directionX == 1) {
				sp.draw(this.text, this.x + this.text.getWidth(), this.y, -this.text.getWidth(), this.text.getHeight());
			}
		}
	}

}
