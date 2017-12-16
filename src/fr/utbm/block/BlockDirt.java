package fr.utbm.block;

import fr.utbm.texture.TextureManager;

public class BlockDirt extends Block{
	
	public BlockDirt(float x, float y)
	{
		super(x, y, TextureManager.getTexture(1));
		this.blockId = 0;
		this.blockHealth = 100;
		this.maxHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockDirt(float x, float y, int bH) {
		super(x, y, TextureManager.getTexture(1));
		this.blockId = 0;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}

}
