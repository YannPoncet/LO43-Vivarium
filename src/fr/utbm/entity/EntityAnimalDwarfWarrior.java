package fr.utbm.entity;

import fr.utbm.world.World;

public class EntityAnimalDwarfWarrior extends EntityAnimalDwarf {
	
	public EntityAnimalDwarfWarrior(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 48, 49, worldIn, 227, 28, 30, 29);
		this.name = "Dwarf warrior";

	}

	@Override
	protected void performSpecialAction(int actionID, int direction) {
		/* THE DWARF SHALL ATTACK HERE */
		directionX = direction;
		if (isOnGround()) {
			move(0, 0, 2);
		} else {
			move(0, 0, activity);
		}
	}

}
