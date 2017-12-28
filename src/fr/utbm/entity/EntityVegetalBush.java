package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalBush extends EntityVegetal{

	public EntityVegetalBush(float x, float y, World worldIn) {
		super(x, y, 66, 35, worldIn);
		this.text = TextureManager.getTexture(212);
	}
}
