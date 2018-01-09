package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIHellFish;
import fr.utbm.ai.Action2D;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalHellFish extends EntityAnimal2D {

	private AIHellFish brain;
	public final String name = "Hell Fish";

	public EntityAnimalHellFish(float x, float y, World worldIn) {
		super(x, y, 91, 38, worldIn);
		text = TextureManager.getTexture(225);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(23);
		anim[1] = TextureManager.getAnimation(24);
		directionX = (int)(Math.random()+0.5);
		directionY = (int)(Math.random()+0.5);
		activity = -1;
		brain = new AIHellFish(this);
		directionXToPerform = (float)(2*Math.random()-1);
		directionYToPerform = (float)(2*Math.random()-1);
		perform = false;
		actionToPerform = 0;
		maxHealth = 100;
		health = 100;
	}
	
	public boolean targetableBy(int id) {
		switch(id){
		
		default: return false;
		}
	}
	
	public void update() {
		suffocating();
		if (!perform) { //si on ne fait plus rien, on update l'action (donc les directions)
			Action2D action = brain.updateTask();
			directionXToPerform = action.getDirectionX();
			directionYToPerform = action.getDirectionY();
			
			action(directionXToPerform, directionYToPerform);
		} else {
			
			action(directionXToPerform, directionYToPerform);
		}
	}
	
	public void action(float dx, float dy) {
			if(isOnGround())
			{
				move(0.1f*dx, 0.1f*dy, 1);
			}
			else
			{
				move(0.1f*dx, 0.1f*dy, 0);
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