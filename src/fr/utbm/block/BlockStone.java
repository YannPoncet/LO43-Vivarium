package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockStone extends Block{
	
	public BlockStone(float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(7), w);
		this.blockId = 7;
		this.blockHealth = 300;
		this.maxHealth = 300;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		isSolid = true;
	}
	
	public BlockStone(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(7), w);
		this.blockId = 7;
		this.maxHealth = 300;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		isSolid = true;
	}
}
