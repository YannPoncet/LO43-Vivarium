package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;

import fr.utbm.texture.TextureManager;

public class BlockGrass extends Block{

	public BlockGrass(float x, float y) {
		super(x, y, TextureManager.getTexture(0));
	}

}
