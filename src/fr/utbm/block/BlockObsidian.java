package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockObsidian extends Block {
	public BlockObsidian (float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(26), w);
		this.blockId = 26;
		this.blockHealth = 300;
		this.maxHealth = 300;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockObsidian (float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(26), w);
		this.blockId = 26;
		this.maxHealth = 300;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
}
