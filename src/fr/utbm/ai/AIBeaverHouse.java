package fr.utbm.ai;

import fr.utbm.block.BlockWood;
import fr.utbm.entity.EntityAnimalBeaver;

public class AIBeaverHouse extends AIBase {

	private EntityAnimalBeaver animal;
	
	public AIBeaverHouse(EntityAnimalBeaver e) {
		super(e);
		this.animal = e;
	}
	
	public Action updateTask() {
		if(this.animal.isOnGround()){
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16, (int)this.animal.getPosY()/16 + 4, new BlockWood(this.animal.getPosX()/16, this.animal.getPosY()/16 + 4, this.animal.getWorldIn()));
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16, (int)this.animal.getPosY()/16 + 5, new BlockWood(this.animal.getPosX()/16, this.animal.getPosY()/16 + 5, this.animal.getWorldIn()));
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16, (int)this.animal.getPosY()/16 + 6, new BlockWood(this.animal.getPosX()/16, this.animal.getPosY()/16 + 6, this.animal.getWorldIn()));
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 +1, (int)this.animal.getPosY()/16 + 3, new BlockWood(this.animal.getPosX()/16 +1, this.animal.getPosY()/16 + 3, this.animal.getWorldIn()));
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 -1, (int)this.animal.getPosY()/16 + 3, new BlockWood(this.animal.getPosX()/16 -1, this.animal.getPosY()/16 + 3, this.animal.getWorldIn()));
		}
		return new Action(1, 2, true);
	}

}
