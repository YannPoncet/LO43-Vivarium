package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public class EntityAnimalHellDog extends EntityAnimal {

	private boolean hasJump;
	private String name = "Hell Dog";
	/*
	 * Dog activity : -1 Dont do anything 0 Idle 1 Walk 2 Run 3 Jump
	 */

	public EntityAnimalHellDog(float x, float y, World worldIn) {
		super(x, y, 54, 32, worldIn);
		this.text = TextureManager.getTexture(200);
		anim = new Animation[4];
		anim[0] = TextureManager.getAnimation(0);
		anim[1] = TextureManager.getAnimation(1);
		anim[2] = TextureManager.getAnimation(2);
		anim[3] = TextureManager.getAnimation(3);
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = -1;
		maxHealth = 100;
		health = 100;
	}

	public void update(){
		suffocating();
		if(!perform){
			hasJump = false;
			if (Gdx.input.isKeyPressed(Input.Keys.A)){
				actionToPerform = 1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
				actionToPerform = 2;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.E)){
				actionToPerform = 3;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.R)){
				actionToPerform = 0;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.Q)){
				actionToPerform = 1;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
				actionToPerform = 2;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				actionToPerform = 3;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.F)){
				actionToPerform = 0;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else{
				move(0, 0, -1);
			}
		}else{
			action(actionToPerform,directionToPerform);
		}
	}
	
	
	
	
	
	
	
	


	public void action(int actionID, int direction) {
		switch (actionID) {

		case 0:
			if (isOnGround()) {
				move(0, 0, 0);
			} else {
				move(0, 0, activity);
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
		case 3:
			if (isOnGround() && !hasJump) {
				move(0.1f, 10f, 3);
				hasJump = true;
			} else {
				move(0.3f * direction, 0, activity);
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
