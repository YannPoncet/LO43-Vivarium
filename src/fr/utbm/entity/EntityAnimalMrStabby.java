package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIMrStabby;
import fr.utbm.ai.Action;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalMrStabby extends EntityAnimal {
	
	public static final int POWER = 10;
	private boolean hasJump;
	private boolean hasMelt;
	private boolean isStabbing;
	private AIMrStabby brain;

	public EntityAnimalMrStabby(float x, float y, World worldIn) {
		super(x, y, 48, 44, worldIn);
		this.name = "Mr Stabby";
		this.text = TextureManager.getTexture(224);
		anim = new Animation[3];
		anim[0] = TextureManager.getAnimation(20); //walk
		anim[1] = TextureManager.getAnimation(21); //stab
		anim[2] = TextureManager.getAnimation(22); //melt
		maxHealth = 300;
		health = 100;
		directionX = 1;
		activity = -1;
		perform = false;
		hasMelt = false;
		isStabbing = false;
		actionToPerform = -1;
		brain = new AIMrStabby(this);
	}
	
	public void update() {
		suffocating();
		if (!perform) {
			isStabbing = false;
			hasJump = false;
			if(hasMelt)
			{
				this.dead=true;
			}

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
	
	public void stab()
	{
		this.brain.getTarget().damage(POWER);
	}

	public void action(int actionID, int direction) {
		switch (actionID) {
		case -1:
			move(0, 0, -1);
			break;
		case 0:
			if (isOnGround()) {
				move(0.12f*direction, 0, 0);
			} else {
				move(0, 0, activity);
			}
			break;
		case 1:
			if (isOnGround() && !isStabbing) {
				stab();
				move(0, 0, 1);
				isStabbing = true;
			} else {
				move(0, 0, activity);
			}
			break;
		case 2:
			if (isOnGround()) {
				move(0, 0, 2);
				hasMelt = true;
			} else {
				move(0, 0, activity);
			}
			break;
		case 3:
			if (isOnGround() && !hasJump) {
				move(0.12f, 10f, 0);
				hasJump = true;
			} else {
				move(0.12f * direction, 0, activity);
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
				sp.draw(currentFrame, this.x, this.y, this.text.getWidth(), this.text.getHeight());
			} else if (directionX == 1) {
				sp.draw(currentFrame, this.x + currentFrame.getRegionWidth(), this.y, -currentFrame.getRegionWidth(),
						currentFrame.getRegionHeight());
			}
			perform = !anim[activity].isAnimationFinished(stateTime);
		} else {
			if (directionX == -1) {
				sp.draw(this.text, this.x, this.y, this.text.getWidth(), this.text.getWidth());
			} else if (directionX == 1) {
				sp.draw(this.text, this.x + this.text.getWidth(), this.y, -this.text.getWidth(), this.text.getHeight());
			}
		}
	}

}
