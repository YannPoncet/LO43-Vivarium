package fr.utbm.ai;

import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimal;
import fr.utbm.entity.EntityAnimalCuteFlower;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AICuteFlower extends AIAnimal {

	public final int VISION = 25*16;
	public final int STEALTH_RANGE = 15*16;
	
	private EntityAnimal target; //food of the flower
	
	private EntityAnimalCuteFlower animal;
	private AIGoTo pathFinder;
	private int fullTime;
	
	public AICuteFlower(EntityAnimalCuteFlower e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(2, 0); //jump, walk
		this.pathFinder.setObjective(20*16);
		
		this.target = null;
		fullTime = 0;
	}
	
	public EntityAnimal getTarget() {
		return this.target;
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

		if(fullTime <= 0) {
			EntityAnimal potentialTarget;
			potentialTarget = getNearestTarget();
			 
			if(potentialTarget != null) {
				this.target = potentialTarget;
			}
			
			if(target != null) { //on a un target
				if(Math.abs(animal.getX()-target.getX()) > STEALTH_RANGE) { //on s'en rapproche si il n'est pas trop proche
					if((int)((target.getPosX()+animal.getWidth())/16)+1>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) //si jamais on risque d'aller sur le bord droit
					{
						this.pathFinder.setObjective(target.getPosX()-animal.getWidth());
					}
					else
					{
						this.pathFinder.setObjective(target.getPosX());
					}
					actionDecided = this.pathFinder.updateTask();
				} else { //on l'attend et on teste si il vient sur nous
					int whereIsTheTarget = besideTargetPos();
					if(whereIsTheTarget != 0 /*&& !voidUnder()*/) { //il est à portée ! on attaque et on remplit le ventre de la flower
						actionDecided = new Action(whereIsTheTarget,1,true);
						animal.setFull();
						fullTime = (int)(Math.random()*25000);
					}
				}
			}
			
			if(actionDecided == null) {
				actionDecided = new Action(0,-1,false);
			}
			actionDecided.setFinish(false);
		}
		else { //the flower is full, we do nothing except controlling if she's ready to eat again
			fullTime --;
			if(fullTime == 0) {
				animal.setEmpty();
			}
			actionDecided = new Action(0, -1, false);
		}	
	
		return actionDecided;
	}
	
	private boolean voidUnder()
	{
		int blockWidth = (int)((animal.getX()+animal.getWidth()-1)/16) - (int)(animal.getX()/16)+1;
		for(int i = 0 ; i < blockWidth ; i++)
		{
			if(animal.getWorldIn().getBlock((int)(animal.getX()/16) + i, (int)(animal.getY()/16)-1) == null || !animal.getWorldIn().getBlock((int)(animal.getX()/16) + i, (int)(animal.getY()/16)-1).isSolid())
			{
				return true;
			}
		}
		return false;
	}
	
	private EntityAnimal getNearestTarget(){
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
	
	private int besideTargetPos()
    {
        if(this.target==null)
        {
            return 0;
        }
        else
        {
            if(((int)((this.animal.getPosX()+this.animal.getWidth())/16+1) == (int)(target.getPosX()/16)))
            {
                return 1;
            }
            else if(((int)(((int)this.animal.getPosX())/16-1) == (int)(target.getPosX()+target.getWidth())/16))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
