package fr.utbm.ai;

import fr.utbm.block.Block;
import fr.utbm.block.BlockGlass;
import fr.utbm.block.BlockWood;
import fr.utbm.entity.EntityAnimalBeaver;

public class AIBeaverHouse extends AIBase {

	private EntityAnimalBeaver animal;
	private int homeAdvancment;
	
	public AIBeaverHouse(EntityAnimalBeaver e) {
		super(e);
		this.animal = e;
		this.homeAdvancment = 0;
	}
	
	public Action updateTask() {
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				Block b = this.animal.getWorldIn().getBlock((int)this.animal.getPosX()/16 -1 + i, (int)this.animal.getPosY()/16 -1 + j);
				if(b != null){
					b.kill();
				}
			}	
		}	
		
		
		
		for(int i=0;i<6;i++){
			this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 -1 + i, (int)this.animal.getPosY()/16 -1, new BlockWood((int)this.animal.getPosX()/16 - 1 + i, (int) this.animal.getPosY()/16 -1 , this.animal.getWorldIn()));
		}		
		for(int i=0;i<6;i++){
			this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 -1, (int)this.animal.getPosY()/16 + i, new BlockWood((int)this.animal.getPosX()/16 - 1 , (int) this.animal.getPosY()/16 +i , this.animal.getWorldIn()));
		}
		for(int i=0;i<6;i++){
			this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 +4, (int)this.animal.getPosY()/16 + i, new BlockWood((int)this.animal.getPosX()/16 +4 , (int) this.animal.getPosY()/16 +i , this.animal.getWorldIn()));
		}
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 -2, (int)this.animal.getPosY()/16 + 4, new BlockWood((int)this.animal.getPosX()/16 -2 , (int) this.animal.getPosY()/16 + 4 , this.animal.getWorldIn()));
		this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 +5, (int)this.animal.getPosY()/16 + 4, new BlockWood((int)this.animal.getPosX()/16 +5 , (int) this.animal.getPosY()/16 + 4 , this.animal.getWorldIn()));
		for(int i=0;i<4;i++){
			this.animal.getWorldIn().setBlock((int)this.animal.getPosX()/16 + i, (int)this.animal.getPosY()/16 + 6, new BlockWood((int)this.animal.getPosX()/16 + i  , (int) this.animal.getPosY()/16 +6 , this.animal.getWorldIn()));
		}
		return new Action(1, 2, true);
	}
	

}
