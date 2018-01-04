package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalBenenut;

public class AIBenenut extends AIAnimal{
	
	private EntityAnimalBenenut animal;
	private AIGoTo pathFinder;
	private AIBeaverHouse construction;
	
	public AIBenenut(EntityAnimalBenenut e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 4);
		this.pathFinder.setObjective(20*16);
	}
	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		switch (objective) {
		case 0:
			actionDecided = this.pathFinder.updateTask();
			break;
		case 1:
			actionDecided = this.pathFinder.updateTask();
			break;
		case 2:
			actionDecided = new Action(1,0,true);
			break;
		case 3:
			actionDecided = this.construction.updateTask();
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
			this.objective = 2;
			break;
		case 2 :
			this.objective = 3;
			break;
		case 3 :
			this.objective = 1;
			break;
		}
		
	}
}
