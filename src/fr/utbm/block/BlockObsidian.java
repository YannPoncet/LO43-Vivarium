package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockObsidian extends Block {
	public BlockObsidian (float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(8), w);
		this.blockId = 8;
		this.blockHealth = 1000;
		this.maxHealth = 1000;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		isSolid = true;
	}
	
	public BlockObsidian (float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(8), w);
		this.blockId = 8;
		this.maxHealth = 1000;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		isSolid = true;
	}
}
