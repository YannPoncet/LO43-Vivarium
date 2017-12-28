package fr.utbm.block;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class BlockGlass extends Block{
	
	public BlockGlass(float x, float y, World w, int HorV)
	{
		//HorV is used to know if the block shall be vertical or horizontal [0 for V, 1 for H]
		super(x, y, TextureManager.getTexture(102+HorV), w);
		this.blockId = 102;
		this.blockHealth = Integer.MAX_VALUE;
		this.maxHealth = Integer.MAX_VALUE;
		this.blockType = BlockType.UNBREAKABLE;
		this.isGravitySensitive = false;
		isSolid = true;
	}
}

