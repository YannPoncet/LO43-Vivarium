package fr.utbm.entity;

import fr.utbm.block.Block;
import fr.utbm.block.BlockSand;
import fr.utbm.world.World;

public class EntityFallingBlock extends Entity{

	protected boolean isGrounded;
	protected Block block;
	public EntityFallingBlock(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
		this.block = new BlockSand(0,203);
		this.text = block.getTexture();
		this.isGrounded = false;
	}
	public void update(){
		if(!isGrounded){
			fall();
		}
	}
	public void fall(){
		if(y%16 == 0 && x%16 == 0 && (world.getBlock((int) x/16, (int) (y/16)-1)!=null)){
			this.block.setPosition(x, y);
			world.setBlock((int) x/16, (int) y/16, block);
			dead = true;
		}else{
			y-=2f;
		}
	}

}
 