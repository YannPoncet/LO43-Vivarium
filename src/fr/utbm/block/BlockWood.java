package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockWood extends Block{
	
	public BlockWood(float x, float y, World w)
	{
		super(x, y, TextureManager.getTexture(9), w);
		this.blockId = 9;
		this.blockHealth = 100;
		this.maxHealth = 100;
		this.blockType = BlockType.WOOD;
		this.isGravitySensitive = false;
		isSolid = true;
	}
	
	public BlockWood(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(9), w);
		this.blockId = 9;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.WOOD;
		this.isGravitySensitive = false;
		isSolid = true;
	}
}
