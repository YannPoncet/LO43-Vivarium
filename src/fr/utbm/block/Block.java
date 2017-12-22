package fr.utbm.block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.render.Renderable;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public abstract class Block extends Renderable{
	
	protected World world;
	protected int blockId;
	protected int maxHealth;
	protected int blockHealth;
	protected BlockType blockType;
	protected boolean isGravitySensitive;
	protected Texture breakingStage;
	
	public Block(float x, float y,Texture text, World worldIn) {
		super(x*16, y*16, text);
		breakingStage = null;
		world = worldIn;
	}
	
	public void update()
	{
		
	}
	
	public void damage(int dmg)
	{
		this.blockHealth =- dmg;
		int i = (blockHealth / maxHealth) * 100 / 25;
		breakingStage = TextureManager.getTexture(200 + i);
	}
	
	@Override
	public void render(SpriteBatch batch){
		batch.draw(text, x, y);
		if(breakingStage != null)
		{
			batch.draw(breakingStage, x, y);
		}
	}
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
	}

}
