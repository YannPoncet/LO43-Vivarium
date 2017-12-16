package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.Map;

public class BlockDirt extends Block{
	
	public BlockDirt(float x, float y, Map mapIn)
	{
		super(x, y, TextureManager.getTexture(1), mapIn);
		this.blockId = 1;
		this.blockHealth = 100;
		this.maxHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockDirt(float x, float y, int bH, Map mapIn) {
		super(x, y, TextureManager.getTexture(1), mapIn);
		this.blockId = 1;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}

}
