package fr.utbm.block;

import fr.utbm.entity.EntityFallingBlock;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockSand extends Block{

	public BlockSand(float x, float y, World w) {
		super(x, y, TextureManager.getTexture(3), w);
		this.blockId = 3;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
	}
	
	public BlockSand(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(3), w);
		this.blockId = 3;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
		super.damage(maxHealth-blockHealth);
	}
	
	@Override
	public void update(){
		if(world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null)
		{
			EntityFallingBlock e = new EntityFallingBlock(x/16,y/16,16,16,world, new BlockSand(x/16,y/16,world));
			world.addEntity(e);
			dead = true;

		}
	}
	
}