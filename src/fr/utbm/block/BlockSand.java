package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockSand extends Block{

	public BlockSand(float x, float y, World w) {
		super(x, y, TextureManager.getTexture(3), w);
		this.blockId = 3;
		this.maxHealth = 100;
		this.blockHealth = 100;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
	}
	
	public BlockSand(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(3), w);
		this.blockId = 3;
		this.maxHealth = 100;
		this.blockHealth = bH;
		this.blockType = BlockType.GRAVITY;
		this.isGravitySensitive = true;
		super.damage(maxHealth-blockHealth);
	}
	@Override
	public void update(){
		
	}
	
}