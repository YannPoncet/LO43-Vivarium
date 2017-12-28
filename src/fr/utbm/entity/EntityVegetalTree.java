package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalTree extends EntityVegetal {

	public EntityVegetalTree(float x, float y, int bigOrSmall, World worldIn) {
		//0 for smallForest, 1 for bigForest, 2 for smallSnow, 3 for bigSnow
		super(x, y, 150, 203, worldIn);
		this.text = TextureManager.getTexture(208+bigOrSmall);
	}
}
