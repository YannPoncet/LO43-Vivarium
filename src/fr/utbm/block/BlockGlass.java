package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockGlass extends Block{
	
	public BlockGlass(float x, float y, World w, int HorV)
	{
		//HorV is used to know if the block shall be vertical or horizontal [0 for V, 1 for H]
		super(x, y, TextureManager.getTexture(12+HorV), w);
		this.blockId = 12;
		this.blockHealth = Integer.MAX_VALUE;
		this.maxHealth = Integer.MAX_VALUE;
		this.blockType = BlockType.GLASS;
		this.isGravitySensitive = false;
	}
	
	public BlockGlass(float x, float y, int bH, World w) {
		super(x, y, TextureManager.getTexture(12), w);
		this.blockId = 12;
		this.maxHealth = Integer.MAX_VALUE;
		this.blockHealth = bH;
		this.blockType = BlockType.GLASS;
		this.isGravitySensitive = false;
	}

}

