package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.Map;

public class BlockGrass extends Block{

	public BlockGrass(float x, float y, Map mapIn) {
		super(x, y, TextureManager.getTexture(2), mapIn);
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockGrass(float x, float y, int bH, Map mapIn) {
		super(x, y, TextureManager.getTexture(2), mapIn);
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		super.damage(maxHealth-blockHealth);
	}
}
