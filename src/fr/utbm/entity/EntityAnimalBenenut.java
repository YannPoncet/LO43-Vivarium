package fr.utbm.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalBenenut extends EntityAnimal{
	
	private int maturity;
	private int growingTime;
	private int readyToPlant;
	private boolean hasJump = false;
	
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
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = 0;
		directionToPerform = 1;
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
		directionX = 1;
		activity = -1;
		perform = false;
		actionToPerform = 0;
		directionToPerform = 1;
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
				//System.out.println("Dans le benenut en " + (int)(x/16) + " ; " + (int)(y/16) + " et le bloc de trou est ");
				if(!perform){
					if(directionToPerform == 1 && (world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)) == null || !world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).isSolid()))
					{
						actionToPerform = 1;
						directionToPerform = 1;
						action(actionToPerform,directionToPerform);
					}
					else if(directionToPerform == 1 && world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)) != null && world.getBlock((int)(((this.x+width)/16)), (int)(this.y/16)).isSolid())
					{
						actionToPerform = 3;
						directionToPerform = 1;
						action(actionToPerform,directionToPerform);
					}
					else if ((world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)) == null || !world.getBlock((int)(((this.x)/16-1)), (int)(this.y/16)).isSolid())){
						actionToPerform = 1;
						directionToPerform = -1;
						action(actionToPerform,directionToPerform);
					}
					else
					{
						directionToPerform = -directionToPerform;
						activity = -1;
					}
				}
				else
				{
		            action(actionToPerform, directionToPerform);
		        }
				readyToPlant--;
			}
		}
	}
	
	public void planting()
	{
		if(world.getBlock((int)(x/16), (int)((y/16)-1)) != null && (world.getBlock((int)(x/16), (int)((y/16)-1)).getID() == 1 || world.getBlock((int)(x/16), (int)((y/16)-1)).getID() == 2))
		{
			if(world.getBlock((int)((x/16)+1), (int)((y/16)-1)) != null && (world.getBlock((int)((x/16)+1), (int)((y/16)-1)).getID() == 1 || world.getBlock((int)((x/16)+1), (int)((y/16)-1)).getID() == 2))
			{
				//ANIMATION
				dead = true;
				world.addEntity(new EntityVegetalBenenutTree((int)((x/16)-1), (int)(y/16), 0, world));
			}
		}
		else
		{
			//move();
		}
	}
	
	public void action(int actionID, int direction) {
		switch (actionID) {
		case 0 :	move(0,0,0);
					break;
					
		case 1 :	if(isOnGround()) {
						move(0.1f*direction, 0, 0);
					} else {
						move(0, 0, 0);
					}
					break;
		
		case 2: 	if (isOnGround()) {
						move(0, 0, 1);
					}
					break;
					
		case 3:		if (isOnGround() && !hasJump) {
						move(0.1f, 10f, 1);
						hasJump = true;
					} else {
						move(0.3f * direction, 0, activity);
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
