package fr.utbm.ai;

import fr.utbm.block.Block;
import fr.utbm.block.BlockType;
import fr.utbm.entity.EntityAnimalDigger;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIDigger extends AIAnimal {
	private EntityAnimalDigger animal;
	private AIGoTo pathFinder;
	private boolean hasAnObjective;
	private int rightOrLeft;
	
	public AIDigger(EntityAnimalDigger e) {
		super(e);
		this.animal = e;
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(2, 0); //Jump, walk
		hasAnObjective = false;
		rightOrLeft = 0;
	}
	
	public boolean isEatable(Block b) {
		if(b != null) {
			if (b.getBlockType() == BlockType.STONE || b.getBlockType() == BlockType.DIRT) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Action updateTask() { //depending of the objective of the Digger, we decide of an action to do, and return it
		Action actionDecided = null;
		switch (objective) {
		case 0:
			Block b;
			b = animal.getWorldIn().getBlock((animal.getPosX()+animal.getWidth()+1)/16, animal.getPosY()/16); //the block to the right
			if(isEatable(b) && (rightOrLeft == 1 || Math.random()<0.4)) {
				animal.setToEat(b);
				rightOrLeft = 1;
				hasAnObjective = true;
				actionDecided = new Action(0,0,true);
			} else {
				
				b = animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16); //the block to the left
				if(isEatable(b) && (Math.random()<0.5)) { //if this block is eatable
					animal.setToEat(b);
					hasAnObjective = true;
					rightOrLeft = -1;
					actionDecided = new Action(0,0,true);
				} else {
					for (int i=0; i<this.animal.getWidth(); i+=16) { //Blocks below
						b = animal.getWorldIn().getBlock((animal.getPosX()+i)/16, animal.getPosY()/16-1);
						if(!hasAnObjective) {
							if(isEatable(b) && Math.random()<0.2) { //if this block is eatable
								animal.setToEat(b);
								hasAnObjective = true;
								System.out.println(i+" | "+(this.animal.getWidth()/2));
								if(i<=this.animal.getWidth()/2 && rightOrLeft == 1) {
									rightOrLeft = -1; 
								} else if(i>this.animal.getWidth()/2 && rightOrLeft == -1) {
									rightOrLeft = 1;
								}
								actionDecided = new Action(0,0,true);
							}
						}
					}
				}
			}
			
			if (!hasAnObjective) { //nothing to eat, we move to the right or to the left
				if(rightOrLeft == 0) {
					if(Math.random() < 0.5) {
						rightOrLeft = -1;
					} else {
						rightOrLeft = 1;
					}
				}
				
				//If we're at the end of the map, or the beginning, we change direction
				if((animal.getX()+16*rightOrLeft <= 16) || ((animal.getX()+16*rightOrLeft+animal.getWidth()) >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16))) {
					rightOrLeft = -rightOrLeft;
				}
				
				this.pathFinder.setObjective(animal.getX()+16*rightOrLeft);
				actionDecided = this.pathFinder.updateTask();
			}
	
			break;
		case 1:
			actionDecided = new Action(1*rightOrLeft,1,true);
			break;
		}
		if(actionDecided.isFinish()){
			objSwitch();
		}
		
		actionDecided.setFinish(false);
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){ //used to know the next objective to do
		switch(objective){
		case 0 :
			if(hasAnObjective) {
				hasAnObjective = false;
				this.objective = 1;
			} else {
				this.objective = 0;
			}
			
			break;
		case 1 :
			this.objective = 0;
			break;
		}
		
	}

}
