package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalHellFish;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIHellFish extends AIAnimal{
	
	private EntityAnimalHellFish animal;
	private AIGoTo pathFinder;
	
	public AIHellFish(EntityAnimalHellFish e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(0, 1);
	}
	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		if(objective == 0)
		{
			if((animal.getX()+16*animal.getDirectionX() <= 16) || ((animal.getX()+16*animal.getDirectionX()+animal.getWidth()) >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16)))
			{
				animal.changeDirectionX();
			}
			
			pathFinder.setObjective((animal.getPosX()+16)*animal.getDirectionX());
			actionDecided = this.pathFinder.updateTask();
		}
		return actionDecided;
	}
}