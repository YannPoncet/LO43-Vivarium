package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalBenenut;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIBenenut extends AIAnimal{
	
	private EntityAnimalBenenut animal;
	private AIGoTo pathFinder;
	
	public AIBenenut(EntityAnimalBenenut e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 2);
	}
	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		switch (objective) {
		case 0:
			if((animal.getX()+16*animal.getDirectionX() <= 16) || ((animal.getX()+16*animal.getDirectionX()+animal.getWidth()) >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16)))
			{
				animal.changeDirectionX();
			}
			
			pathFinder.setObjective((animal.getPosX()+16)*animal.getDirectionX());
			actionDecided = this.pathFinder.updateTask();
			break;
		case 1:
			actionDecided = new Action(1,1,true);
			break;
		}
		if(actionDecided.isFinish()){
			objSwitch();
		}
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){
		switch(objective){
		case 0 :
			this.objective = 1;
			break;
		case 1 :
			this.objective = 0;
			break;
		}
		
	}
}
