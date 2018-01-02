package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.block.BlockType;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public abstract class EntityAnimalFlying extends EntityAnimal {

	public EntityAnimalFlying(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
	}
	
	@Override
	public void move(float dx, float dy, int act)
	{

		ya += world.getGravity() * 0.1f;
		this.activity = act;
		xa += dx;
		ya += dy;
		if (isOnGround()) {
			drag = 0.85f;
		} else {
			drag = 0.95f;
		}
		if (dx > 0) {
			directionX = 1;
		} else if (dx < 0) {
			directionX = -1;
		}
		if (dy > 0) {
			directionX = 1;
		} else if (dy < 0) {
			directionX = -1;
		}
		
		int xStep = (int) Math.abs(xa * 100);
		for (int i = 0; i < xStep; i++) {
			if (!CollisionAABB.enterInCollisionAt(this, xa / xStep, 0)) {
				this.x += xa / xStep;
			} else {
				xa = 0;
			}
		}
		int yStep = (int) Math.abs(ya * 100);
		for (int i = 0; i < yStep; i++) {
			if (!CollisionAABB.enterInCollisionAt(this, 0, ya / yStep)) {
				this.y += ya / yStep;
			} else {
				ya = 0;
			}
		}

		xa *= drag;
		ya *= drag;
		if (activity != -1 && anim[activity].isAnimationFinished(stateTime)) {
			stateTime = 0;
		}
	}
}
