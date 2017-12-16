package fr.utbm.block;

import fr.utbm.texture.TextureManager;

public class BlockWater extends BlockLiquid{
	
	public BlockWater(float x, float y, int state)
	{
		super(x, y, TextureManager.getTexture(4));
		this.blockId = 4;
		this.maxHealth = 1;
		this.blockHealth = 1;
		this.blockType = BlockType.LIQUID;
		this.isGravitySensitive = true;
		this.state = state;
	}

}
