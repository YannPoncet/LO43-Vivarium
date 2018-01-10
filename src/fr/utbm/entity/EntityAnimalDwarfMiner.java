package fr.utbm.entity;

import fr.utbm.ai.AIDwarfMiner;
import fr.utbm.ai.Action;
import fr.utbm.world.World;

public class EntityAnimalDwarfMiner extends EntityAnimalDwarf {
	private EntityAnimalDwarfKing king;
	private AIDwarfMiner brain;
	
	public EntityAnimalDwarfMiner(float x, float y, World worldIn,  EntityAnimalDwarfKing king) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 64, 64, worldIn, 228, 31, 34, 32, 33);
		this.name = "Dwarf miner";
		this.king = king;
		health = 100;
		maxHealth = 100;
		this.brain = new AIDwarfMiner(this, king);
	}
	
	public EntityAnimalDwarfMiner(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 64, 64, worldIn, 228, 31, 34, 32, 33);
		this.name = "Dwarf miner";
		health = 100;
		maxHealth = 100;
		for(Entity e : worldIn.getEntities()){
			if(e instanceof EntityAnimalDwarfKing){
				this.king = (EntityAnimalDwarfKing) e;
			}
		}
		if (king == null) {
			EntityAnimalDwarfKing tempKing = new EntityAnimalDwarfKing(x/16, y/16, worldIn);
			worldIn.addEntity(tempKing);
		}
		
		this.brain = new AIDwarfMiner(this, king);
	}
	
	public EntityAnimalDwarfKing getKing() {
		return this.king;
	}
	
	public void update() {
		suffocating();	
		
		if (!perform) {
			hasJump = false;
			this.stateTime = 0;
			
			Action a = brain.updateTask();
			if (!a.isFinish()) {
				actionToPerform = a.getAction();
				directionToPerform = a.getDirection();
				action(actionToPerform, directionToPerform);
			} else {
				actionToPerform = a.getAction();
				directionToPerform = this.directionX;
				action(actionToPerform, directionToPerform);
			}

		} else {
			action(actionToPerform, directionToPerform);
		}
	}

	@Override
	protected void performSpecialAction(int actionID, int direction) {
		if (actionID == 2) {
			/* THE DWARF SHALL MINE HERE */

			directionX = direction;
			if (isOnGround()) {
				move(0, 0, 2);
			} else {
				move(0, 0, activity);
			}
		}
		else {
			/* THE DWARF SHALL BUILD HERE */

			directionX = direction;
			if (isOnGround()) {
				move(0, 0, 3);
			} else {
				move(0, 0, activity);
			}
		}

	}
}
