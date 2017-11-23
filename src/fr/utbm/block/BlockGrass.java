package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.texture.TextureManager;

public class BlockGrass extends Block{

	public BlockGrass(float x, float y) {
		super(x, y, TextureManager.getTexture(2));
		this.blockId = 0;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
	}
	
	public BlockGrass(float x, float y, int bH) {
		super(x, y, TextureManager.getTexture(2));
		this.blockId = 0;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.DIRT;
		this.isGravitySensitive = false;
		super.damage(maxHealth-blockHealth);
	}
}
