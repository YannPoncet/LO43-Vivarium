package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalCuteFlower extends EntityAnimal {
	int dmg;
	int regenOnEat;
	boolean nextFrame;
	
	public EntityAnimalCuteFlower(float x, float y, World worldIn) {
		super(x, y, 80, 150, worldIn);
		text = TextureManager.getTexture(219);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(6);
		anim[1] = TextureManager.getAnimation(7);
		maxHealth = 300;
		health = 100;
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = 0;
		directionToPerform = 1;
		regenOnEat = 50;
		dmg = 24;
	}
	
	public void update(){
		if(!perform){
			if(health>200) {
				directionToPerform = -directionToPerform;
				activity = -1;
			} else if (Math.random() > 0/*health/maxHealth*/) {
				actionToPerform = 1;
				activity = 1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}
		}else{
			
		}
	}
	
	public void action(int actionID, int direction) {
		switch (actionID) {
		case 0 :	move(0,0,0);
					break;
					
		case 1 :	if(isOnGround()) {
						move(0.05f*direction, 0, 0);
						health--;
					} else {
						move(0, 0, 0);
					}
		System.out.println("huhi");
					break;
		
		case 2: 	
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
			perform = false;
			//perform = !anim[activity].isAnimationFinished(stateTime);
		} else {
			if (directionX == -1) {
				sp.draw(this.text, this.x, this.y);
			} else if (directionX == 1) {
				sp.draw(this.text, this.x + this.text.getWidth(), this.y, -this.text.getWidth(), this.text.getHeight());
			}
		}
	}
}
