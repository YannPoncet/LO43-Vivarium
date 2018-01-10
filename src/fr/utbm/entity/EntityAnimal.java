package fr.utbm.entity;

import com.badlogic.gdx.graphics.g2d.Animation;

import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public abstract class EntityAnimal extends Entity{

	protected int directionX = 0;
	protected Animation anim[];
	protected float stateTime;
	protected int activity;
	protected boolean perform;
	protected int actionToPerform;
	protected int directionToPerform;
	protected float xa, ya;
	protected float drag = 0;
	
	public EntityAnimal(float x, float y, int w, int h, World worldIn) {
		super(x, y, w, h, worldIn);
	}
	public void move(float dx, float dy, int act) {
		ya += world.getGravity() * 0.1f;
		this.activity = act;
		xa += dx;
		ya += dy;
		if(isSensitiveTo(104) && isInWater())
		{
			drag = 0.7f;
			ya *= 1.3;
		}
		else if (isOnGround()) {
			drag = 0.85f;
		} else {
			drag = 0.95f;
		}
		if (dx > 0) {
			directionX = 1;
		} else if (dx < 0) {
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
	
	public int getDirectionX()
	{
		return directionX;
	}
	public void changeDirectionX()
	{
		directionX = -directionX;
	}
}
