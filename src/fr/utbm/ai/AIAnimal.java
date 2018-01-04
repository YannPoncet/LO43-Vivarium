package fr.utbm.ai;

import fr.utbm.entity.EntityAnimal;

public abstract class AIAnimal extends AIBase{
	protected EntityAnimal animal;
	protected int objective;
	
	public AIAnimal(EntityAnimal e) {
		super(e);
		this.objective = 0;
	}
	
	

	public abstract Action updateTask();
	
	public void objSwitch(){
		
	}
	
}
