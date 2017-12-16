package fr.utbm.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderable {
	protected boolean dead;
	protected Texture text;
	protected float x,y;

	public Renderable(float x, float y,Texture text){
		this.text = text;
		this.dead = false;
		this.x = x;
		this.y = y;
	}
	public void render(SpriteBatch batch){
		batch.draw(text, x, y);
	}
	public boolean isDead() {
		return dead;
	}
	public Texture getTexture(){
		return this.text;
	}
}
