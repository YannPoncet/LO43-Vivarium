package fr.utbm.ai;

import java.util.ArrayList;

import fr.utbm.entity.EntityAnimalDwarfKing;
import fr.utbm.entity.EntityAnimalDwarfMiner;
import fr.utbm.entity.EntityAnimalDwarfWarrior;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIDwarfKing extends AIAnimal {

	public final int VISION = 25*16;
	public final int NB_MINER = 2;
	public final int NB_WARRIOR = 2;
	public final int HOME_SPAWN_FACTOR = 15*16;
	
	private float homeCenter; 
	
	private EntityAnimalDwarfKing animal;
	private AIGoTo pathFinder;
	
	private ArrayList<EntityAnimalDwarfMiner> miners;
	private ArrayList<EntityAnimalDwarfWarrior> warriors;
	
	private int blockerInteger;
	
	private boolean hasAnObjective;
	
	public AIDwarfKing(EntityAnimalDwarfKing e) {
		super(e);
		this.animal = e;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 0); //jump, walk
		
		this.miners = new ArrayList<>();
		this.warriors = new ArrayList<>();
		
		this.homeCenter = -1;
		this.hasAnObjective = false;
		
		this.blockerInteger = 0;
		
		EntityAnimalDwarfWarrior tempWarrior = new EntityAnimalDwarfWarrior(animal.getX()/16, (animal.getY())/16, animal.getWorldIn());
		animal.getWorldIn().addEntity(tempWarrior);
		
		EntityAnimalDwarfMiner tempMiner = new EntityAnimalDwarfMiner(animal.getX()/16, (animal.getY())/16, animal.getWorldIn());
		animal.getWorldIn().addEntity(tempMiner);
	}
	
	@Override
	public Action updateTask() {
		
		/* Je suis le roi des nains !
		 * Je peux faire pop NB_WARRIOR warriors et NB_MINER miner.
		 * Je définis une maison qui me plait sur la map et j'agite mes bras pour que les nains fassent le travail.
		 * Je reste toujours à côté de ma maison.
		 */
		Action actionDecided = null;
		if(blockerInteger <= 0) {
			if(!hasAnObjective) {
				if(this.miners.size() <= NB_MINER && Math.random()<0.005) { //si il n'y a pas le maxi on a une chance sur 500 d'en faire pop un
					EntityAnimalDwarfWarrior tempWarrior = new EntityAnimalDwarfWarrior(animal.getX()/16, (animal.getY())/16, animal.getWorldIn());
					animal.getWorldIn().addEntity(tempWarrior);
				}
				else if(this.warriors.size() <= NB_WARRIOR && Math.random()<0.005) { //si il n'y a pas le maxi on a une chance sur 500 d'en faire pop un
					EntityAnimalDwarfMiner tempMiner = new EntityAnimalDwarfMiner(animal.getX()/16, (animal.getY())/16, animal.getWorldIn());
					animal.getWorldIn().addEntity(tempMiner);
				}
				else {
					if(this.homeCenter == -1) { //if the dwarf has no home
						int putHomeAtXFromPos = (int)(Math.random()*(HOME_SPAWN_FACTOR));
						if (Math.random()<0.5 && !((animal.getX()-putHomeAtXFromPos)/16 <= 1)) { //we put the home left if possible
							homeCenter = animal.getX()-putHomeAtXFromPos;
						} 
						else if (!((animal.getX()+putHomeAtXFromPos)/16 >= Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1)) { //we put the home right if possible
							homeCenter = animal.getX()+putHomeAtXFromPos;
						}
						else {
							homeCenter = -1;
						}
					}
					else //the king has a home, he needs to turn around and command
					{
						double whatToDo = Math.random();
						if(whatToDo < 0.2) { //he turns around
							int leftToTheHome;
							if (Math.random() < 0.5) {
								leftToTheHome = -1;
							} else {
								leftToTheHome = 1;
							}
							
							
							float toGo = (float)(homeCenter+Math.random()*HOME_SPAWN_FACTOR*leftToTheHome);
							this.pathFinder.setObjective(toGo);
							this.hasAnObjective = true;
							
							if(toGo/16 <= 1) { //we're to close to the left
								System.out.println("TO CLOSE");
								this.hasAnObjective = false;
							} else if((toGo+animal.getWidth())/16 >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1)) { //we're to close to the right
								this.hasAnObjective = false;
							}
						} else if (whatToDo < 0.7){ //he commands
							if(Math.random() < 0.5) { //he commands to the right
								actionDecided = new Action(1,2, false);
							} else { //he commands to the left
								actionDecided = new Action(-1,2, false);
							}
						}
						else { //do nothing
							this.blockerInteger = (int)(Math.random()*100);
							actionDecided = new Action(0,-1,false);
						}
					}
				}	
			} else {
				actionDecided = this.pathFinder.updateTask();
				if(actionDecided.isFinish()) {
					this.hasAnObjective = false;
				}
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
	
}