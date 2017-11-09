package fr.utbm.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderable {
	private boolean dead;
	private Texture text;
	private float x,y;

	public Renderable(float x, float y,Texture text){
		this.text = text;
		this.x = x;
		this.y = y;
	}
	void render(SpriteBatch batch){
		batch.draw(text, x, y);
	}
	public boolean isDead() {
		return dead;
	}
}
