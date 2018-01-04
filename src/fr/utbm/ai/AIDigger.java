package fr.utbm.ai;

import fr.utbm.block.Block;
import fr.utbm.block.BlockType;
import fr.utbm.entity.EntityAnimalDigger;

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
				if(isEatable(b)) { //if this block is eatable
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
								if(i<=this.animal.getWidth()/2) {rightOrLeft = -1;} else {rightOrLeft = 1;}
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

				this.pathFinder.setObjective(animal.getX()+16*rightOrLeft);
				actionDecided = this.pathFinder.updateTask();
			}
	
			break;
		case 1:
			actionDecided = new Action(1,1*rightOrLeft,true);
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
			
			if(Math.random() < 0.2) {
				rightOrLeft = -1;
			} else {
				rightOrLeft = 1;
			}
			
			hasAnObjective = false;
			this.objective = 1;
			
			break;
		case 1 :
			
			this.objective = 0;
			break;
		}
		
	}

}
