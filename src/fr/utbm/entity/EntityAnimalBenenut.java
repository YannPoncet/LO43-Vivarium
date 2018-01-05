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
	
	private int maturity;
	private int growingTime;
	private int readyToPlant;
	private boolean hasJump = false;
	private AIBenenut brain;
	private int ttd = 500;
	
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
		maxHealth = 100;
		health = 100;
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
		maxHealth = 100;
		health = 100;
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
	
	public int getMaturity()
	{
		return maturity;
	}
	
	@Override
	public void update()
	{
		if(maturity < 2)
		{
			if(growingTime == 0)
			{
				maturity++;
				Random r = new Random();
				growingTime = r.nextInt(10000) + 5000;
			}
			else
			{
				growingTime--;
			}
			text = TextureManager.getTexture(208 + maturity);
		}
		else
		{
			if(readyToPlant <= 0)
			{
				text = TextureManager.getTexture(208 + maturity);
				readyToPlant = 0;
				planting();
			}
			else
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
		ttd--;
		if(world.getBlock((int)(x/16), (int)((y/16)-1)) != null && (world.getBlock((int)(x/16), (int)((y/16)-1)).getID() == 1 || world.getBlock((int)(x/16), (int)((y/16)-1)).getID() == 2))
		{
			if(world.getBlock((int)((x/16)+1), (int)((y/16)-1)) != null && world.getBlock((int)((x/16)+1), (int)((y/16)-1)).getBlockType() == BlockType.DIRT && (world.getBlock((int)((x/16)+1), (int)(y/16)) == null || !world.getBlock((int)((x/16)+1), (int)(y/16)).isSolid()))
			{
				dead = true;
				world.addEntity(new EntityVegetalBenenutTree((int)((x/16)-1), (int)(y/16), 0, world));
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
		case 0:
			if (isOnGround()) {
				move(0, 0, 0);
			} else {
				move(0, 0, activity);
			}
			break;
		case 1:
			if (isOnGround() && !hasJump) {
				move(0.4f * direction, 8f, 1);
				hasJump = true;
			} else {
				move(0.4f * direction, 0, activity);
			}
			break;
		case 2:
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
