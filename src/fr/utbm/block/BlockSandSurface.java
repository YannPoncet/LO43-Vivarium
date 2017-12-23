package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockSandSurface extends Block {

	public BlockSandSurface(float x, float y, World w) {
		super(x, y, TextureManager.getTexture(15), w);
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockSandSurface(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(15), w);
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		super.damage(maxHealth-blockHealth);
	}
}
