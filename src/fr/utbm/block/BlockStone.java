package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockStone extends Block{
	public BlockStone(float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(24), w);
		this.blockId = 24;
		this.blockHealth = 100;
		this.maxHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockStone(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(24), w);
		this.blockId = 24;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}

}
