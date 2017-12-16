package fr.utbm.block;

import fr.utbm.texture.TextureManager;

public class BlockSand extends Block{

	public BlockSand(float x, float y) {
		super(x, y, TextureManager.getTexture(3));
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
	}
	
	public BlockSand(float x, float y, int bH) {
		super(x, y, TextureManager.getTexture(3));
		this.blockId = 2;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
		super.damage(maxHealth-blockHealth);
	}
	
	@Override
	public void update()
	{
		
	}
	
}