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

		this.activity = act; //surtout pour les anims
		xa += dx;
		ya += dy;
		
		//drag correspond à la vitesse de l'animal en fonction de où il est
		if (isOnGround()) {
			drag = 0.85f; //plus lent sur le sol qu'en l'air
		} else {
			drag = 0.95f;
		}

		int xStep = (int) Math.abs(xa * 100); 
		for (int i = 0; i < xStep; i++) {
			if (!CollisionAABB.enterInCollisionAt(this, xa / xStep, 0)) { //si n'y a pas de collisions en x, on avance
				this.x += xa / xStep;
			} else {
				xa = 0; //sinon on s'arrête (pour ne pas traverser les blocs
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

		//pour l'affichage de l'animal
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
