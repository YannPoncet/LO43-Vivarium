package fr.utbm.entity;

import fr.utbm.world.World;

public class EntityAnimalDwarfKing extends EntityAnimalDwarf {
	
	public EntityAnimalDwarfKing(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 48, 50, worldIn, 226, 25, 27, 26);
		

	}

	@Override
	protected void performSpecialAction(int actionID, int direction) {
		/* THE DWARF SHALL COMMAND HERE */
		directionX = direction;
		if (isOnGround()) {
			move(0, 0, 2);
		} else {
			move(0, 0, activity);
		}
	}

}
