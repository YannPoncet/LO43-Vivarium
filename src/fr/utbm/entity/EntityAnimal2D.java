package fr.utbm.entity;

import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public abstract class EntityAnimal2D extends EntityAnimal {

	protected float directionXToPerform;
	protected float directionYToPerform;
	protected int directionY = 0;

	public EntityAnimal2D(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
	}

	@Override
	public void move(float dx, float dy, int act) {

		this.activity = act;
		xa += dx;
		ya += dy;
		if (isOnGround()) {
			drag = 0.85f;
		} else {
			drag = 0.95f;
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

		
		if (dx > 0) {
			directionX = 1;
		} else if (dx < 0) {
			directionX = -1;
		}
		if (dy > 0) {
			directionY = 1;
		} else if (dy < 0) {
			directionY = -1;
		}

		xa *= drag;
		ya *= drag;

		if (activity != -1 && anim[activity].isAnimationFinished(stateTime)) {
			stateTime = 0;
		}
	}
}
