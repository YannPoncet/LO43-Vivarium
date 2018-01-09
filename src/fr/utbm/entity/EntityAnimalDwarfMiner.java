package fr.utbm.entity;

import fr.utbm.world.World;

public class EntityAnimalDwarfMiner extends EntityAnimalDwarf {
	
	public EntityAnimalDwarfMiner(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 64, 64, worldIn, 228, 31, 34, 32, 33);
		this.name = "Dwarf miner";

	}

	@Override
	protected void performSpecialAction(int actionID, int direction) {
		if (actionID == 2) {
			/* THE DWARF SHALL MINE HERE */
			
			//TODO
			directionX = direction;
			if (isOnGround()) {
				move(0, 0, 2);
			} else {
				move(0, 0, activity);
			}
		}
		else {
			/* THE DWARF SHALL BUILD HERE */

			//TODO
			directionX = direction;
			if (isOnGround()) {
				move(0, 0, 3);
			} else {
				move(0, 0, activity);
			}
		}

	}
}
