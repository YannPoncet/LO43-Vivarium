package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AICuteFlower;
import fr.utbm.ai.Action;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalCuteFlower extends EntityAnimal {

	public final int POWER = 25;
	
	private boolean hasJump;
	private AICuteFlower brain;
	
	/*
	 * Flower activity : -1 WAIT - 0 WALK - 1 EAT - 2 JUMP 
	 */

	public EntityAnimalCuteFlower(float x, float y, World worldIn) {
		super(x, y, 32, 96, worldIn);
		this.name = "Cute Flower";
		this.text = TextureManager.getTexture(219);
		anim = new Animation[6];
		anim[0] = TextureManager.getAnimation(6); //walk
		anim[1] = TextureManager.getAnimation(7); //eat
		anim[2] = TextureManager.getAnimation(19); //jump
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = -1;
		brain = new AICuteFlower(this);
		health = 100;
		maxHealth = 100;
	}

	public void update() {
		suffocating();

		if (!perform) {
			hasJump = false;
			this.stateTime = 0;
			
			Action a = brain.updateTask();
			if (!a.isFinish()) {
				actionToPerform = a.getAction();
				directionToPerform = a.getDirection();
				action(actionToPerform, directionToPerform);
			} else {
				actionToPerform = a.getAction();
				directionToPerform = this.directionX;
				action(actionToPerform, directionToPerform);
			}

		} else {
			action(actionToPerform, directionToPerform);
		}
	}
	
	public void eat()
	{
		this.brain.getTarget().damage(POWER);
	}

	public void action(int actionID, int direction) {
		switch (actionID) {
		case -1: //wait 
			move(0, 0, -1);
			break;
		case 0: //walk
			if (isOnGround()) {
				move(0.1f*direction, 0, 0);
			} else {
				move(0, 0, activity);
			}
			break;
		case 1: //eat
			directionX = direction;
			if (isOnGround()) {
				eat();
				move(0, 0, 1);
			} else {
				move(0, 0, activity);
			}
			break;
		case 2: //jump
			if (isOnGround() && !hasJump) {
				move(0.1f, 10f, 2);
				hasJump = true;
			} else {
				move(0.1f * direction, 0, activity);
			}
			break;
		}
	}
	
	public void setFull() {
		this.text = TextureManager.getTexture(220);
	}

	public void setEmpty() {
		this.text = TextureManager.getTexture(219);
	}
	
	@Override
	public void render(SpriteBatch sp) {
		if (activity > -1) {
			stateTime += Gdx.graphics.getDeltaTime();
			TextureRegion currentFrame = anim[activity].getKeyFrame(stateTime, true);
			if (directionX == -1) {
				sp.draw(currentFrame, this.x-59f, this.y);
			} else if (directionX == 1) {
				sp.draw(currentFrame, this.x-59f + currentFrame.getRegionWidth(), this.y, -currentFrame.getRegionWidth(),
						currentFrame.getRegionHeight());
			}
			perform = !anim[activity].isAnimationFinished(stateTime);
		} else {
			if (directionX == -1) {
				sp.draw(this.text, this.x-59f, this.y);
			} else if (directionX == 1) {
				sp.draw(this.text, this.x-59f + this.text.getWidth(), this.y, -this.text.getWidth(), this.text.getHeight());
			}
		}
	}
	
}
