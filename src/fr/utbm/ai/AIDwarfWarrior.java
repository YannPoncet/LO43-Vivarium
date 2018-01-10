package fr.utbm.ai;

import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimal;
import fr.utbm.entity.EntityAnimalDwarfWarrior;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;


public class AIDwarfWarrior extends AIAnimal {

	public final int VISION = 25*16;
	public final int KING_SAFE_ZONE = 20*16;
	public final int AROUND_KING_MAX = 15*16;

	private EntityAnimalDwarfWarrior animal;
	private AIGoTo pathFinder;
	
	private int blockerInteger;
	private int noInfiniteTimer;
	private EntityAnimal target; 
	
	private boolean hasAnObjective;
	
	public AIDwarfWarrior(EntityAnimalDwarfWarrior e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 0); //jump, walk
		
		this.hasAnObjective = false;
		
		this.blockerInteger = 0;
		this.noInfiniteTimer = 0;
		
		this.target = null;
	}
	
	@Override
	public Action updateTask() {
		
		/* Je suis un nain warrior !
		 * Je dois protéger le roi: si une entité dangereuse se rapproche du roi:
		 * Je me rapproche d'elle avant qu'il ne soit trop tard
		 * Je la tappe
		 * Sinon, je bouge aux alentours du roi
		 */
		Action actionDecided = null;
		
		float kingPos = animal.getKing().getX();
		
		this.target = getNearestTarget();
		
		if(target != null && Math.abs(target.getX()-kingPos) <= KING_SAFE_ZONE) { //on a un target et il est dans la zone du roi, on le prend pour objectif 				
			int beside = besideTargetPos();
			if(beside == 0) { //la cible n'est pas à côté on s'en rapproche
				if((int)((target.getPosX()+animal.getWidth())/16)+1>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) //si jamais on risque d'aller sur le bord droit
				{
					this.pathFinder.setObjective(target.getPosX()-animal.getWidth());
				}
				else
				{
					this.pathFinder.setObjective(target.getPosX());
				}
				actionDecided = this.pathFinder.updateTask();
			} else { //la cible est à portée, on tape
				actionDecided = new Action(beside, 2, false);
			}
		} 
		else if(blockerInteger <= 0) { //on a pas de target, on bouge autour du roi
			if(!hasAnObjective) {
				if(Math.random() < 0.5) { //on se rapproche du roi
					int leftToTheKing;
					if (Math.random() < 0.5) {
						leftToTheKing = -1;
					} else {
						leftToTheKing = 1;
					}
					
					float toGo = (float)(kingPos+Math.random()*AROUND_KING_MAX*leftToTheKing);
					if(((toGo+animal.getWidth()))>=(Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-3)*16) //si jamais on risque d'aller sur le bord droit
					{
						this.hasAnObjective = false;
					} else {
						this.hasAnObjective = true;
					}
					this.pathFinder.setObjective(toGo);
					
					
					if(toGo/16 <= 1) { //we're to close to the left
						this.hasAnObjective = false;
					}
					noInfiniteTimer = 20;
				}
				else { //do nothing
					this.blockerInteger = (int)(Math.random()*100);
					actionDecided = new Action(0,-1,false);
				}
			}
			else {
				actionDecided = this.pathFinder.updateTask();
				if(actionDecided.isFinish() || noInfiniteTimer <= 0) {
					this.hasAnObjective = false;
				}
				noInfiniteTimer--;
			}
		}
		else {
			blockerInteger --;
		}
		
		
		if(actionDecided == null) {
			actionDecided = new Action(0,-1,false);
		}
		
		return actionDecided;
	}
	
	public EntityAnimal getTarget() {
		return this.target;
	}
	
	private EntityAnimal getNearestTarget(){
		float dist = Float.MAX_VALUE;
		EntityAnimal target = null;
		
		for(Entity e : animal.getWorldIn().getEntities()){
			if((e.targetableBy(227)) && (e.getPosX()<animal.getPosX()+VISION && e.getPosX()>animal.getPosX()-VISION) && (e.getPosY()<animal.getPosY()+VISION && e.getPosY()>animal.getPosY()-VISION)){
				EntityAnimal potentialTarget = (EntityAnimal)e;
				float newDist = Math.abs(animal.getPosX()-potentialTarget.getPosX());
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
            if((((int)((this.animal.getPosX()+this.animal.getWidth())/16+1)>(int)(target.getPosX()+target.getWidth())/16)) && (((int)((this.animal.getPosX())/16+1)<(int)(target.getPosX()+target.getWidth())/16)))
            {
                return -1;
            }
            else if((((int)((this.animal.getPosX())/16+1)<(int)(target.getPosX())/16)) && (((int)((this.animal.getPosX()+this.animal.getWidth())/16+1)>(int)(target.getPosX()+target.getWidth())/16)))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
	
}
