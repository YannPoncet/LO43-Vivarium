package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalCactus extends EntityVegetal {
	public EntityVegetalCactus(float x, float y, int bigOrSmall, World worldIn) {
		//0 for smallCactus, 1 for bigCactus
		super(x, y, 32, 62+bigOrSmall*23, worldIn);
		this.text = TextureManager.getTexture(216+bigOrSmall);
	}
}
