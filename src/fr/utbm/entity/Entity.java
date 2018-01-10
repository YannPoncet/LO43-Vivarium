package fr.utbm.entity;

import fr.utbm.block.Block;
import fr.utbm.physics.RigidBox;
import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public abstract class Entity extends Renderable{

	protected World world;
	protected int width;
	protected int height;
	protected int maxHealth;
	protected int health;
	protected RigidBox hitBox;
	protected String name;
	
	public Entity(float x, float y, int w, int h, World worldIn) {
		super(x*16,y*16,TextureManager.getTexture(0));
		this.name = "";
		this.world = worldIn;
		this.width = w;
		this.height = h;
	}
	public int getHealth(){
		return this.health;
	}
	public int getMaxHealth(){
		return this.maxHealth;
	}
	public void damage(int dmg)
    {
        if(this.health > 0)
        {
            health -= dmg;
            if(health < 0){
            	health = 0;
            }
        }
        if(health <= 0) {
            dead = true;
        }
    }
	
	public void suffocating()
	{
		boolean isInBlock = false;
		int blockWidth = (int)((x+width-1)/16) - (int)(x/16)+1;
		int blockHeight = (int)((y+height-1)/16) - (int)(y/16)+1;
		for(int i = 0 ; i < blockWidth ; i++)
		{
			for(int j = 0 ; j < blockHeight ; j++)
			{
				if(isSensitiveTo(112))
				{
					if(world.getBlock((int)(x/16) + i, (int)(y/16) + j) != null && (world.getBlock((int)(x/16) + i, (int)(y/16) + j).isSolid() || world.getBlock((int)(x/16) + i, (int)(y/16) + j).getID() == 112))
					{
						isInBlock = true;
					}
				}
				else
				{
					if(world.getBlock((int)(x/16) + i, (int)(y/16) + j) != null && world.getBlock((int)(x/16) + i, (int)(y/16) + j).isSolid())
					{
						isInBlock = true;
					}
				}
			}
		}
		if(isInBlock)
		{
			damage(2);
		}
	}
	
	public boolean isSensitiveTo(int id) {
		switch(id){
		case 104: return true; //sensitive to water (they will try to swim)
		case 112: return true; //sensitive to lava
		default: return false;
		}
	}
	
	public boolean targetableBy(int id) 
	{
		return false;
	}

	public boolean isOnGround(){
		boolean b = false;
		int p = ((int) x)/16;
		int s = ((int) x + width -1)/16;
		int nY = (((int) (y-1))/16);
		while(!(b || p>s)){
			Block block = world.getBlock(p, nY);
			if(block != null && block.isSolid()){
				b=true;
			}
			p+=1;
		}
		return b;
	}
	
	public boolean isInWater()
	{
		boolean isInWater = false;
		int blockWidth = (int)((x+width-1)/16) - (int)(x/16)+1;
		int blockHeight = (int)((y+height-1)/16) - (int)(y/16)+1;
		for(int i = 0 ; i < blockWidth ; i++)
		{
			for(int j = 0 ; j < blockHeight ; j++)
			{
				if(world.getBlock((int)(x/16) + i, (int)(y/16) + j) != null && world.getBlock((int)(x/16) + i, (int)(y/16) + j).getID() == 104)
				{
					isInWater = true;
				}
			}
		}
		return isInWater;
	}
	
	public void update(){

	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getPosX(){
		return this.x;
	}
	
	public float getPosY(){
		return this.y;
	}
	
	public World getWorldIn(){
		return this.world;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	public RigidBox getHitBox(){
		return this.hitBox;
	}
	public String getName()
	{
		return this.name;
	}
}
