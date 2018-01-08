package fr.utbm.ai;

import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimal;
import fr.utbm.entity.EntityAnimalMrStabby;

public class AIMrStabby extends AIAnimal {
	
	private static int VISION=10; //the range at which it can see a target
	private EntityAnimalMrStabby animal; //the animal which contains this brain
	private EntityAnimal target; //mrStabby will try to kill a target
	private AIGoTo pathFinder; //to go to the target

	public AIMrStabby(EntityAnimal e) {
		super(e);
		
		this.target = null;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(0, 3);
		this.pathFinder.setObjective(animal.getPosX());
	}
	
	//will get the nearest entityAnimal in a certain range (VISION)
	public EntityAnimal getNearestTarget(){
		float dist = 100000f;
		EntityAnimal target = null;
		
		for(Entity e : animal.getWorldIn().getEntities()){
			if((e.targetableBy(224)) && (e.getPosX()<animal.getPosX()+VISION && e.getPosX()>animal.getPosX()-VISION) && (e.getPosY()<animal.getPosY()+VISION && e.getPosY()>animal.getPosY()-VISION)){
				EntityAnimal potentialTarget = (EntityAnimal)e;
				float newDist = Math.abs(animal.getX()-target.getPosX());
				if(newDist < dist){
					dist = newDist;
					target = potentialTarget;
				}
			}
		}
		return target;
	}

	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		
		if(animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosX()/16).getID()!=3) //si il n'est pas sur de la neige, il fond
		{
			actionDecided = new Action(0,0,false);
		}
			
		return actionDecided;
	}

}
