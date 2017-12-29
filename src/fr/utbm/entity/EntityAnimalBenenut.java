package fr.utbm.entity;

import java.util.Random;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalBenenut extends EntityAnimal{
	
	private int maturity;
	private int growingTime;
	private int readyToPlant;
	
	public EntityAnimalBenenut(float x, float y, World worldIn) {
		
		super(x, y, 15, 18, worldIn);
		growingTime = 0;
		Random r = new Random();
		readyToPlant = r.nextInt(5000) + 5000;
		maturity = 2;
		text = TextureManager.getTexture(208 + maturity);
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
	}
	
	public int getMaturity()
	{
		return maturity;
	}
	
	@Override
	public void update()
	{
		//System.out.println("je suis dans le benenut : " + x/16 + " ; " + y/16 + "\nJe suis à la maturité : " + maturity + "\nMon nombre de ticks avant le lvl supérieur est : " + growingTime);
		text = TextureManager.getTexture(208 + maturity);
		
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
		}
		else
		{
			if(readyToPlant <= 0)
			{
				readyToPlant = 0;
				planting();
			}
			else
			{
				move();
				readyToPlant--;
			}
		}
	}
	
	public void move()
	{
		if(y%16 == 0 && (world.getBlock((int) x/16, (int) (y/16)-1)!=null) && (world.getBlock((int) x/16, (int) (y/16)-1).isSolid()))
		{
			
		}
		else
		{
			y-=2f;
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
			move();
		}
	}
}
