package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockSandSurface extends BlockSand{

	public BlockSandSurface(float x, float y, World w) {
		super(x, y, w);
		this.blockId = 15;
		text = TextureManager.getTexture(this.blockId);
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
	}
	
	public BlockSandSurface(float x, float y, int bH, World w) {
		super(x, y, w);
		this.blockId = 15;
		text = TextureManager.getTexture(this.blockId);
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
		super.damage(maxHealth-blockHealth);
	}
	
	@Override
	public void update()
	{
		super.update();
		if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).blockType != BlockType.WATER)
		{
			dead = true;
			world.setBlock((int)(x/16), (int)(y/16), new BlockSand(x/16, y/16, this.world));
		}
	}
}
