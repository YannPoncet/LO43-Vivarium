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
		if (this.animal.isOnGround() && this.animal.getCapacity() >4) {
			for (int i = 0; i < 3; i++) {
				this.animal.getWorldIn().setBlock((int) this.animal.getPosX() / 16 + i,
						(int) this.animal.getPosY() / 16 - 1, new BlockWood((int) this.animal.getPosX() / 16 + i,
								(int) this.animal.getPosY() / 16 - 1, this.animal.getWorldIn()));
			}
				this.animal.getWorldIn().setBlock((int) this.animal.getPosX() / 16 ,
						(int) this.animal.getPosY() / 16 +4, new BlockWood((int) this.animal.getPosX() / 16,
								(int) this.animal.getPosY() / 16 +4, this.animal.getWorldIn()));
				this.animal.getWorldIn().setBlock((int) this.animal.getPosX() / 16 +1 ,
						(int) this.animal.getPosY() / 16 +5, new BlockWood((int) this.animal.getPosX() / 16 + 1,
								(int) this.animal.getPosY() / 16 +5, this.animal.getWorldIn()));
				this.animal.getWorldIn().setBlock((int) this.animal.getPosX() / 16 +2 ,
						(int) this.animal.getPosY() / 16 +4, new BlockWood((int) this.animal.getPosX() / 16 + 2,
								(int) this.animal.getPosY() / 16 +4, this.animal.getWorldIn()));
			this.animal.setCapacity(0);
		}
		return new Action(1, 2, true);
	}

}
