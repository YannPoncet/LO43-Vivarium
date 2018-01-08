package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalBenenutTree extends EntityVegetal{
	
	private EntityAnimalBenenut[] fruits;
	private String name = "Benenut Tree";
	
	public EntityVegetalBenenutTree(float x, float y, World worldIn) {
		
		super(x, y, 96, 144, worldIn);
		entityHealth = 100;
		maxHealth = 100;
		maturity = 6;
		text = TextureManager.getTexture(201 + maturity);
		fruits = new EntityAnimalBenenut[6];
		spawnFruits();
		maxHealth = 100;
		health = 100;
	}
	
	public EntityVegetalBenenutTree(float x, float y, int m, World worldIn)
	{
		super(x, y, 96, 144, worldIn);
		entityHealth = 100;
		maxHealth = 100;
		if(m > 6)
		{
			m = 6;
		}
		else if(m < 0)
		{
			m = 0;
		}
		maturity = m;
		text = TextureManager.getTexture(201 + maturity);
		fruits = new EntityAnimalBenenut[6];
		spawnFruits();
	}
	
	public void update()
	{
		for(int i = 0 ; i < fruits.length ; i++)
		{
			if(fruits[i] != null)
			{
				if(fruits[i].getMaturity() == 2)
				{
					fruits[i] = null;
				}
			}
		}
		
		if(maturity < 6)
		{
			if(iter == 10000)
			{
				iter = 0;
				maturity++;
				text = TextureManager.getTexture(201 + maturity);
			}
			else
			{
				iter++;
			}
		}
		spawnFruits();
		voidUnder();
	}
	
	public void spawnFruits()
	{
		if(maturity > 3)
		{
			if(fruits[0] == null)
			{
				fruits[0] = new EntityAnimalBenenut((x+7)/16, (y+18)/16, 0, world);
				world.addEntity(fruits[0]);
			}
			if(fruits[1] == null)
			{
				fruits[1] = new EntityAnimalBenenut((x+54)/16, (y+24)/16, 0, world);
				world.addEntity(fruits[1]);
			}
		}
		if(maturity > 4)
		{
			if(fruits[2] == null)
			{
				fruits[2] = new EntityAnimalBenenut((x+52)/16, (y+54)/16, 0, world);
				world.addEntity(fruits[2]);
			}
			if(fruits[3] == null)
			{
				fruits[3] = new EntityAnimalBenenut((x+75)/16, (y+66)/16, 0, world);
				world.addEntity(fruits[3]);
			}
		}
		if(maturity > 5)
		{
			if(fruits[4] == null)
			{
				fruits[4] = new EntityAnimalBenenut((x+17)/16, (y+74)/16, 0, world);
				world.addEntity(fruits[4]);
			}
			if(fruits[5] == null)
			{
				fruits[5] = new EntityAnimalBenenut((x+5)/16, (y+92)/16, 0, world);
				world.addEntity(fruits[5]);
			}
		}
	}
	
	public void voidUnder()
	{
		if(world.getBlock((int)((x/16)+1), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+2), (int)((y/16)-1)) == null)
		{
			for(int i = 0 ; i < fruits.length ; i++)
			{
				if(fruits[i] != null)
				{
					fruits[i].kill();
					fruits[i] = null;
				}
			}
			dead = true;
		}
	}
}
