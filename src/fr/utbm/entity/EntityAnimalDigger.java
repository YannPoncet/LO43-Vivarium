package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public class EntityAnimalDigger extends EntityAnimal {
	int dmg;
	int regenOnEat;
	
	public EntityAnimalDigger(float x, float y, World worldIn) {
		super(x, y, 48, 15, worldIn);
		text = TextureManager.getTexture(218);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(4);
		anim[1] = TextureManager.getAnimation(5);
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
		if(health <= 0) {
			dead = true;
		}
		
		if(!perform){
			if(directionToPerform == 1 && (world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)) == null || !world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).isSolid()))
			{
				actionToPerform = 1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			} else if ( directionToPerform == 1 && (
						world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).getBlockType() == BlockType.STONE ||
						world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).getBlockType() == BlockType.DIRT
					  )) {
				actionToPerform = 2;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			} else if ((world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)) == null || !world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)).isSolid())){
				actionToPerform = 1;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			} else if ((
						world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)).getBlockType() == BlockType.STONE ||
						world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)).getBlockType() == BlockType.DIRT
				  )) {
				actionToPerform = 2;
				directionToPerform = -1;
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
		case 0 :	move(0,0,0);
					break;
					
		case 1 :	if(isOnGround()) {
						move(1f*direction, 0, 0);
						health--;
					} else {
						move(0, 0, 0);
					}
					break;
		
		case 2: 	if (isOnGround()) {
						move(0, 0, 1);
						
						if(health+regenOnEat > maxHealth) {
							health=maxHealth;
						} else {
							health+=regenOnEat;
						}
								
						
						if(direction == 1) {
							if(world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)) != null) {
								world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).damage(dmg);
							}
						} else {
							if(world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)) != null) {
								world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)).damage(dmg);
							}
						}
					} else {
						move(0, 0, 1);
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
