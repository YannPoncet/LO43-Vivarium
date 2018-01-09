package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalHellFish;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIHellFish extends AIAnimal{
	
	private EntityAnimalHellFish animal;
	
	public AIHellFish(EntityAnimalHellFish e) {
		super(e);
		this.animal = e;
	}
	
	@Override
	public Action2D updateTask() {
		Action2D actionDecided = null;
		
		return actionDecided;
	}
}