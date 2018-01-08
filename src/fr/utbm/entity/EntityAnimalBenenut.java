package fr.utbm.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.ai.AIBenenut;
import fr.utbm.ai.Action;
import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalBenenut extends EntityAnimal{
	
	private int maturity; //benenut has 3 states of maturity : 0 (younger can't move), 1 (still can't move), 2 (can move and plant until a random time)
	private int growingTime; //time left before he gets the superior state of maturity
	private int readyToPlant; //after a time, he will try to plant in order to generate a bennutTree
	private boolean hasJump = false;
	private AIBenenut brain; //little intelligence to enable movements
	private int ttd = 500; //time to death : decrement after he is ready to be planted
	private String name = "Benenut";
	
	public EntityAnimalBenenut(float x, float y, World worldIn) {
		
		super(x, y, 15, 18, worldIn);
		growingTime = 0;
		Random r = new Random();
		readyToPlant = r.nextInt(5000) + 5000;
		maturity = 2;
		text = TextureManager.getTexture(208 + maturity);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(16);
		anim[1] = TextureManager.getAnimation(17);
		maxHealth = 10;
		health = 10;
		directionX = r.nextInt(2); //Gives a random beginning direction
		if(directionX == 0)
		{
			directionX = -1;
		}
		activity = -1;
		perform = false;
		actionToPerform = 0; 
		directionToPerform = 1;
		brain = new AIBenenut(this);
	}
	
	public EntityAnimalBenenut(float x, float y, int m, World worldIn) {
		
		super(x, y, 15, 18, worldIn);
		Random r = new Random();
		growingTime = r.nextInt(10000) + 5000;
		readyToPlant = r.nextInt(5000) + 5000;
		if(m > 2)
		{
			m = 2;
		}
		else if(m < 0)
		{
			m = 0;
		}
		maturity = m;
		text = TextureManager.getTexture(208 + maturity);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(16);
		anim[1] = TextureManager.getAnimation(17);
		maxHealth = 10;
		health = 10;
		directionX = r.nextInt(2);
		if(directionX == 0)
		{
			directionX = -1;
		}
		activity = -1;
		perform = false;
		actionToPerform = 0;
		directionToPerform = 1;
		brain = new AIBenenut(this);
	}
	
	public boolean targetableBy(int id) {
		switch(id){
		case 219: return true; //targetable by the cuteFlower
		case 224: return true; //targetable by mrStabby
		default: return false;
		}
	}
	
	public int getMaturity() //Useful to refresh the array of entityVegetalBenenutTree
	{
		return maturity;
	}
	
	@Override
	public void update()
	{
		if(maturity < 2) //Don't allow benenut to move before he gets the 3rd stage of maturity
		{
			if(growingTime == 0) //if he is mature
			{
				maturity++; //he gets the superior maturity stage
				Random r = new Random();
				growingTime = r.nextInt(10000) + 5000; //and another random is assigned for the time before the next stage
			}
			else
			{
				growingTime--;
			}
			text = TextureManager.getTexture(208 + maturity);
		}
		else
		{
			if(readyToPlant <= 0) //if he is ready to be planted
			{
				text = TextureManager.getTexture(208 + maturity);
				readyToPlant = 0;
				planting(); //he will try to if conditions are there
			}
			else //otherwise, we continue to move
			{
				move();
				readyToPlant--;
			}
		}

		if(ttd < 1)
		{
			dead = true;
		}
	}
	
	public void planting()
	{
		ttd--; //if he is ready to be planted, but the function is still called, it means conditions weren't united, or the benenut is stuck somewhere, so we decrement the timeToDeath
		//if there is dirt under
		if(world.getBlock((int)(x/16), (int)((y/16)-1)) != null && world.getBlock((int)(x/16), (int)((y/16)-1)).getBlockType() == BlockType.DIRT && world.getBlock((int)(x/16), (int)(y/16)).getBlockType() != BlockType.LIQUID)
		{
			//if there is dirt under the block on the right and there are no solid block on the right (because the BenenutTree needs 2 blocks one next to the other)
			if(world.getBlock((int)((x/16)+1), (int)((y/16)-1)) != null && world.getBlock((int)((x/16)+1), (int)((y/16)-1)).getBlockType() == BlockType.DIRT && (world.getBlock((int)((x/16)+1), (int)(y/16)) == null || !world.getBlock((int)((x/16)+1), (int)(y/16)).isSolid()))
			{
				dead = true; //we kill it
				world.addEntity(new EntityVegetalBenenutTree((int)((x/16)-1), (int)(y/16), 0, world)); //and we create a benenutTree
			}
			else
			{
				move();
			}
		}
		else
		{
			move();
		}
	}
	
	public void action(int actionID, int direction) {
		switch (actionID) {
		case -1:
			move(0, 0, -1);
			break;
		case 0: //Jump
			if (isOnGround() && !hasJump) {
				move(0.4f * direction, 8f, 1);
				hasJump = true;
			} else {
				move(0.4f * direction, 0, activity);
			}
			break;
		case 1: //Walk
			if (isOnGround()) {
				move(0.1f * direction, 0, 0);
			}
			else
			{
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
	
	public void move()
	{
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
}
