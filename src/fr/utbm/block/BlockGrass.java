package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockGrass extends BlockDirt{

	public BlockGrass(float x, float y, World w) {
		super(x, y, w);
		this.blockId = 2;
		text = TextureManager.getTexture(blockId);
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockGrass(float x, float y, int bH, World w) {
		super(x, y, w);
		this.blockId = 2;
		text = TextureManager.getTexture(blockId);
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		super.damage(maxHealth-blockHealth);
	}
	
	@Override
	public void update()
	{
		super.update();
		if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null && world.getBlock((int)(this.x/16), (int)((this.y/16)+1)).getID() != 4)
		{
			dead = true;
			world.setBlock((int)(x/16), (int)(y/16), new BlockDirt(x/16, y/16, this.world));
		}
	}
}
