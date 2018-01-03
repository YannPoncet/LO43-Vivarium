package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityPrettyBird extends EntityAnimalFlying {

	private int tmp;
	/*
	 * Bird activity : -1 Dont do anything 0 Idle 1 Fly horizontaly 2 Fly
	 * diagonally 3 Fly verticaly
	 */

	public EntityPrettyBird(float x, float y, World worldIn) {
		super(x, y, 16, 16, worldIn);
		text = TextureManager.getTexture(222);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(14);
		anim[1] = TextureManager.getAnimation(15);
		directionX = -1;
		directionY = -1;
		activity = -1;
		perform = false;
		actionToPerform = 0;
		tmp = 0;
	}

	public void update() {
		if (!perform) {

			tmp++;
			if (tmp > 10) {
				actionToPerform = (actionToPerform + 1) % 4;
				tmp = 0;
			}

			action(actionToPerform);
		} else {
			action(actionToPerform);
		}
	}

	public void action(int actionID) {
		switch (actionID) {

		case 0:
			move(0, 0, 1);
			break;
		case 1:
			move(0.1f * directionX, 0, 0);
			break;
		case 2:
			move(0.1f * directionX, 0.1f * directionY, 0);
			break;
		case 3:
			move(0, 0.1f * directionY, 1);
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
