package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIBeaver;
import fr.utbm.ai.Action;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.Rescale;
import fr.utbm.world.World;

public class EntityBeaver extends EntityAnimal {

	private boolean hasJump, isEmpty;
	private AIBeaver brain;
	/*
	 * Dog activity : -1 Dont do anything 0 Idle 1 Walk 2 Run 3 Jump
	 */

	public EntityBeaver(float x, float y, World worldIn) {
		super(x, y, 54, 32, worldIn);
		this.text = Rescale.rescale(TextureManager.getTexture(223),0.5f,0.5f);
		anim = new Animation[6];
		anim[0] = TextureManager.getAnimation(8);
		anim[1] = TextureManager.getAnimation(9);
		anim[2] = TextureManager.getAnimation(10);
		anim[3] = TextureManager.getAnimation(11);
		anim[4] = TextureManager.getAnimation(12);
		anim[5] = TextureManager.getAnimation(13);
		isEmpty = true;
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = -1;
		brain = new AIBeaver(this);
	}

	public void update() {
		if (!perform) {
			hasJump = false;
			/*if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				actionToPerform = 0;
				directionToPerform = 1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				actionToPerform = 1;
				directionToPerform = 1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
				actionToPerform = 2;
				directionToPerform = 1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.R)) {
				actionToPerform = 3;
				directionToPerform = 1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
				actionToPerform = 4;
				directionToPerform = 1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
				actionToPerform = 0;
				directionToPerform = -1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				actionToPerform = 1;
				directionToPerform = -1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				actionToPerform = 2;
				directionToPerform = -1;
				action(actionToPerform, directionToPerform);
			} else if (Gdx.input.isKeyPressed(Input.Keys.F)) {
				actionToPerform = 3;
				directionToPerform = -1;
				action(actionToPerform, directionToPerform);
			} else {
				move(0, 0, -1);
			}*/
			Action a = brain.updateTask();
			
			if(a != null){
				actionToPerform = a.getAction();
				directionToPerform = a.getDirection();
				action(actionToPerform, directionToPerform);
			}else{
				move(0,0,-1);
			}

			
			
			
		} else {
			action(actionToPerform, directionToPerform);
		}
	}
	public void setFull(){
		this.text = Rescale.rescale(TextureManager.getTexture(221),0.5f,0.5f);
		this.isEmpty = false;
	}
	public void setEmpty(){
		this.text = Rescale.rescale(TextureManager.getTexture(223),0.5f,0.5f);
		this.isEmpty = true;
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
			if (isOnGround() && !hasJump) {
				move(0.1f, 10f, 1);
				hasJump = true;
			} else {
				move(0.1f * direction, 0, activity);
			}
			break;
		case 2:
			if (isOnGround()) {
				move(0, 0, 2);
			} else {
				move(0, 0, activity);
			}
			break;
		case 3:
			if (isOnGround()) {
				move(0, 0, 3);
			} else {
				move(0, 0, activity);
			}
			break;
		case 4:
			if (isOnGround()) {
				if(isEmpty){
					move(0.1f * direction, 0, 4);
				}else{
					move(0, 0, 5);
				}

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
				sp.draw(currentFrame, this.x, this.y,this.text.getWidth(),this.text.getHeight());
			} else if (directionX == 1) {
				sp.draw(currentFrame, this.x + currentFrame.getRegionWidth(), this.y, -currentFrame.getRegionWidth(),
						currentFrame.getRegionHeight());
			}
			perform = !anim[activity].isAnimationFinished(stateTime);
		} else {
			if (directionX == -1) {
				sp.draw(this.text, this.x, this.y,this.text.getWidth(),this.text.getWidth());
			} else if (directionX == 1) {
				sp.draw(this.text, this.x + this.text.getWidth(), this.y, -this.text.getWidth(), this.text.getHeight());
			}
		}
	}
}
