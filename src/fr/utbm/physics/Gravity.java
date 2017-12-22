package fr.utbm.physics;

import fr.utbm.entity.Entity;

public class Gravity {
	
	private static final float gravity = 8f;
	
	public static void applyGravity(Entity e){
		if(!e.isOnGround()){
			e.setPosition(e.getPosX(), e.getPosY() - gravity);
		}
	}
	
}
