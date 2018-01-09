package fr.utbm.ai;

import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimal;
import fr.utbm.entity.EntityAnimalCuteFlower;

public class AICuteFlower extends AIAnimal {

	public final int VISION = 20*16;
	public final int STEALTH_RANGE = 8*16;
	
	private EntityAnimal target; //food of the flower
	
	private EntityAnimalCuteFlower animal;
	private AIGoTo pathFinder;
	
	public AICuteFlower(EntityAnimalCuteFlower e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(2, 0); //jump, walk
		this.pathFinder.setObjective(20*16);
		
		this.target = null;
	}
	
	@Override
	public Action updateTask() {
		
		/* Je suis la fleur !
		 * 0: je localise la nourriture la plus proche, si elle est très proche (<8 blocks) je ne bouge pas, sinon je m'en rapproche et je passe en 1
		 * 1: tant que la nourriture cible est proche, on vérifie si elle est à un block de distance, passe en 2 si oui, retourne en 0 si sortie 
		 * 2: on mange ! on damage ! on passe en 3 !
		 * 3: on est full, on fait rien jusqu'au moment ou on a digéré -> on repasse en 0
		 */
		
		Action actionDecided = null;
		switch (objective) {
		case 0:
			if(target == null) { //on a pas de target, on en cherche un
				this.target = getNearestTarget();
			}
			
			
			if(target != null) { //on a un target
				if(Math.abs(animal.getX()-target.getX()) > STEALTH_RANGE) { //on s'en rapproche si il n'est pas trop proche
					this.pathFinder.setObjective(target.getX());
					actionDecided = this.pathFinder.updateTask();
				} else { //on l'attend et on teste si il vient sur nous
					System.out.println("Il est proche !");
				}
			}
			
			if(actionDecided == null) {
				actionDecided = new Action(0,-1,false);
			}
			actionDecided.setFinish(false);
			
			break;
		case 1:
			//actionDecided = this.pathFinder.updateTask();
			break;
		case 2:
			//actionDecided = new Action(1,0,true);
			break;
		case 3:
			
			break;
		}
		/*
		if(actionDecided.isFinish()){
			objSwitch();
		}
		*/
		
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){
		switch(objective){
		case 0 :
			this.pathFinder.setObjective(this.animal.getNearestTree());
			
			this.objective = 1;
			break;
		case 1 :
			this.objective = 2;
			break;
		case 2 :
			this.objective = 3;
			break;
		case 3 :
			this.pathFinder.setObjective(this.animal.getNearestTree());
			this.objective = 1;
			break;
		}	
	}
	
	public EntityAnimal getNearestTarget(){
		float dist = Float.MAX_VALUE;
		EntityAnimal target = null;
		
		for(Entity e : animal.getWorldIn().getEntities()){ //we traverse the entites
			if((e.targetableBy(219)) && (e.getPosX()<animal.getPosX()+VISION && e.getPosX()>animal.getPosX()-VISION) && (e.getPosY()<animal.getPosY()+VISION && e.getPosY()>animal.getPosY()-VISION)){
				EntityAnimal potentialTarget = (EntityAnimal)e;
				float newDist = Math.abs(animal.getX()-potentialTarget.getPosX());
				if(newDist < dist){
					dist = newDist;
					target = potentialTarget;
				}
			}
		}
		return target;
	}
}
