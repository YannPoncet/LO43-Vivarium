package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIRedFish;
import fr.utbm.ai.Action2D;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalRedFish extends EntityAnimal2D {

	private AIRedFish brain;

	public EntityAnimalRedFish(float x, float y, World worldIn) {
		super(x, y, 20, 15, worldIn);
		this.name = "Red fish";
		text = TextureManager.getTexture(229);
		anim = new Animation[1];
		anim[0] = TextureManager.getAnimation(35);
		directionX = (int)(Math.random()+0.5);
		directionY = (int)(Math.random()+0.5);
		activity = -1;
		brain = new AIRedFish(this);
		directionXToPerform = (float)(2*Math.random()-1);
		directionYToPerform = (float)(2*Math.random()-1);
		perform = false;
		actionToPerform = 0;
		maxHealth = 50;
		health = 50;
	}

	public void update() {
		suffocating();
		
		if (!perform) { //si on ne fait plus rien, on update l'action (donc les directions)
			
			Action2D action = brain.updateTask();
			actionToPerform = action.getAction();
			directionXToPerform = action.getDirectionX();
			directionYToPerform = action.getDirectionY();
			
			action(actionToPerform, directionXToPerform, directionYToPerform);
		} else {
			action(actionToPerform, directionXToPerform, directionYToPerform);
		}
	}
	
	public void action(int actionID, float dx, float dy) {
		switch(actionID){
		case -1: move(0,0,-1);
			break;
		
		case 0:	move(0.01f*dx, 0.01f*dy, 0);
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
