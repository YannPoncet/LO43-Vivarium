package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockDirt extends Block{
	
	public BlockDirt(float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(1), w);
		this.blockId = 1;
		this.blockHealth = 100;
		this.maxHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockDirt(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(1), w);
		this.blockId = 1;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}

}
