package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockGrass extends Block{
	
	public BlockGrass(float x, float y, int c, World w)
	{
		super(x, y, TextureManager.getTexture(100), w);
		this.blockId = 100;
		text = TextureManager.getTexture(this.blockId + c);
		this.blockHealth = 1;
		this.maxHealth = 1;
		this.blockType = BlockType.GRASS;
		this.isGravitySensitive = false;
		isSolid = false;
	}
	
	@Override
	public void update()
	{
		if(world.getBlock((int)(this.x/16), (int)((this.y/16)+1)) != null || world.getBlock((int)(this.x/16), (int)((this.y/16)-1)) == null || (world.getBlock((int)((this.x/16)+1), (int)(this.y/16)) != null && world.getBlock((int)((this.x/16)+1), (int)(this.y/16)).blockType == BlockType.LIQUID) || (world.getBlock((int)((this.x/16)-1), (int)(this.y/16)) != null && world.getBlock((int)((this.x/16)-1), (int)(this.y/16)).blockType == BlockType.LIQUID))
		{
			dead = true;
		}
	}
}
