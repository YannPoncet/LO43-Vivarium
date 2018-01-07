package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIBeaver;
import fr.utbm.ai.Action;
import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalCuteFlower extends EntityAnimal {

	private boolean hasJump, isEmpty;
	private AIBeaver brain;
	/*
	 * Beaver activity : 0 EAT - 1 JUMP - 2 PUT - 3 TAKE - 4 WALK
	 */

	public EntityAnimalCuteFlower(float x, float y, World worldIn) {
		super(x, y, 150, 150, worldIn);
		this.text = TextureManager.getTexture(219);
		anim = new Animation[6];
		anim[0] = TextureManager.getAnimation(6); //walk
		anim[1] = TextureManager.getAnimation(7); //eat
		anim[2] = TextureManager.getAnimation(19); //jump
		isEmpty = true;
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = -1;
		//brain = new AI(this);
	}

	public void update() {
		if(!perform){
			this.stateTime = 0;
			hasJump = false;
			if (Gdx.input.isKeyPressed(Input.Keys.A)){
				actionToPerform = -1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
				actionToPerform = 0;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.E)){
				actionToPerform = 1;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.R)){
				actionToPerform = 2;
				directionToPerform = 1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.S)){
				actionToPerform = 0;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				actionToPerform = 1;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else if(Gdx.input.isKeyPressed(Input.Keys.F)){
				actionToPerform = 2;
				directionToPerform = -1;
				action(actionToPerform,directionToPerform);
			}else{
				move(0, 0, -1);
			}
		}else{
			action(actionToPerform,directionToPerform);
		}
		
		
		/*
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
		}*/
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
			if (isOnGround()) {
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
		this.isEmpty = false;
	}

	public void setEmpty() {
		this.text = TextureManager.getTexture(219);
		this.isEmpty = true;
	}
	
	//TODO
	public void eatTree(){
		for(Entity e : this.world.getEntities()){
			if(e instanceof EntityVegetalTree){
				EntityVegetalTree tree = (EntityVegetalTree) e;
				if(Math.abs(this.getX()-tree.getTrunkPos()) < 55){
					tree.kill();
				}
			}
		}
	}
	
	//TODO
	public float getNearestTree(){
		float dist = 100000f;
		float pos = 0f;
		
		for(Entity e : this.world.getEntities()){
			if(e instanceof EntityVegetalTree){
				EntityVegetalTree tree = (EntityVegetalTree) e;
				float newDist = Math.abs(this.getX()-tree.getTrunkPos());
				if(newDist < dist){
					dist = newDist;
					pos = tree.getPosX();
				}
			}
		}
		return pos;
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
