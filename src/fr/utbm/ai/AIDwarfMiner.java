package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalDwarfMiner;
import fr.utbm.entity.EntityAnimalDwarfWarrior;

public class AIDwarfMiner extends AIAnimal{

	public final int VISION = 25*16;

	private EntityAnimalDwarfMiner animal;
	private AIGoTo pathFinder;
	
	private int blockerInteger;
	
	private boolean hasAnObjective;
	
	public AIDwarfMiner(EntityAnimalDwarfMiner e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 0); //jump, walk
		
		this.hasAnObjective = false;
		
		this.blockerInteger = 0;
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
		
		
		
		
		if(actionDecided == null) {
			actionDecided = new Action(0,-1,false);
		}
		
		return actionDecided;
	}
	
}
