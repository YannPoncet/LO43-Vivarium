package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIDigger;
import fr.utbm.ai.Action;
import fr.utbm.block.Block;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalDigger extends EntityAnimal {
	private int dmg;
	private int regenOnEat;
	private AIDigger brain;
	private boolean hasJump;
	private Block toEat;
	public final String name = "Digger";
	
	public EntityAnimalDigger(float x, float y, World worldIn) {
		super(x, y, 50, 16, worldIn);
		text = TextureManager.getTexture(218);
		anim = new Animation[3];
		anim[0] = TextureManager.getAnimation(4);
		anim[1] = TextureManager.getAnimation(5);
		anim[2] = TextureManager.getAnimation(18);
		maxHealth = 300;
		health = 100;
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = 0;
		directionToPerform = 1;
		regenOnEat = 50;
		dmg = 24;
		brain = new AIDigger(this);
		hasJump = false;
	}
	
	public void update(){
		suffocating();
		if(health <= 0) {
			dead = true;
		}
		
		if (!perform) {
			hasJump = false;
			
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
	
	public void action(int actionID, int direction) {
		switch (actionID) {
		case -1 :	move(0,0,-1);
					break;
					
		case 0 :	if(isOnGround()) {
						move(0.5f*direction, 0, 0);
						//health--;
					} else {
						move(0, 0, activity);
					}
					break;
		
		case 1: 	if (isOnGround()) { //We damage the block to the left or to the right and we regen the digger
						move(0, 0, 1);
						directionX = direction;
						
						if(health+regenOnEat > maxHealth) { //regen
							health=maxHealth;
						} else {
							health+=regenOnEat;
						}

						if(this.toEat != null) {
							this.toEat.damage(dmg);
						}
					} else { //We apply gravity
						move(0, 0, activity);
					}
					break;
		
		case 2: //Jump
					if (isOnGround() && !hasJump) {
						move(0.1f, 10f, 2);
						hasJump = true;
					} else {
						move(0.1f * direction, 0, activity);
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
	
	public void setToEat(Block b) {
		this.toEat = b;
	}
	public Block getToEat() {
		return this.toEat;
	}
}
