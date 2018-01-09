package fr.utbm.entity;

import fr.utbm.block.Block;
import fr.utbm.block.BlockType;
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
	
	public Entity(float x, float y, int w, int h, World worldIn) {
		super(x*16,y*16,TextureManager.getTexture(0));
		this.world = worldIn;
		this.width = w;
		this.height = h;
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
		int blockWidth = (int)((x+width)/16) - (int)(x/16);
		int blockHeight = (int)((y+height)/16) - (int)(y/16);
		for(int i = 0 ; i < blockWidth ; i++)
		{
			for(int j = 0 ; j < blockHeight ; j++)
			{
				if(world.getBlock((int)(x/16) + i, (int)(y/16) + j) != null && world.getBlock((int)(x/16) + i, (int)(y/16) + j).isSolid())
				{
					isInBlock = true;
				}
			}
		}
		if(isInBlock)
		{
			damage(1);
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

}
