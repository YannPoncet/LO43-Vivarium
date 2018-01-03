package fr.utbm.ai;

import fr.utbm.entity.EntityBeaver;

public class AIBeaver extends AIAnimal {

	private boolean isHomeFinish;
	private AIGoTo pathFinder;
	
	public AIBeaver(EntityBeaver e) {
		super(e);
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 4);
		this.pathFinder.setObjective(400*16);
	}
	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		switch (objective) {
		case 0:
			actionDecided = this.pathFinder.updateTask();
			break;
		}
		
		
		return actionDecided;
	}

}
