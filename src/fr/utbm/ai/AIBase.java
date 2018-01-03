package fr.utbm.ai;

import fr.utbm.entity.EntityAnimal;

public abstract class AIBase {

	protected EntityAnimal animal;
	protected boolean isFinish;
	
	public AIBase(EntityAnimal e) {
		this.animal = e;
		this.isFinish = false;
	}
	public abstract Action updateTask();
	
	
	
	
}
