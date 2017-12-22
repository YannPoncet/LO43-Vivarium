package fr.utbm.entity;

import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public abstract class Entity extends Renderable{

	protected World world;
	protected int width;
	protected int height;
	
	public Entity(float x, float y, int w, int h, World worldIn) {
		super(x*16,y*16,TextureManager.getTexture(0));
		this.world = worldIn;
		this.width = w;
		this.height = h;
	}
	public boolean isOnGround(){
		boolean b = false;
		int p = ((int) x)/16;
		int s = ((int) x + width -1)/16;
		int nY = (((int) y)/16) - 1;
		while(!(b || p>s)){
			if(world.getBlock(p, nY) != null && (((int) y)%16) == 0){
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

}
