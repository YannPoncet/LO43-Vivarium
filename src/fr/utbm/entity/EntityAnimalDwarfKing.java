package fr.utbm.entity;

import java.util.ArrayList;

import fr.utbm.ai.AIDwarfKing;
import fr.utbm.ai.Action;
import fr.utbm.world.World;

public class EntityAnimalDwarfKing extends EntityAnimalDwarf {
	private AIDwarfKing brain;
	
	public EntityAnimalDwarfKing(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 48, 50, worldIn, 226, 25, 27, 26);
		this.name = "Dwarf king";
		this.brain = new AIDwarfKing(this);
		health = 200;
		maxHealth = 200;
	}
	

	@Override
	protected void performSpecialAction(int actionID, int direction) {
		/* THE DWARF SHALL COMMAND HERE */
		directionX = direction;
		if (isOnGround()) {
			move(0, 0, 2);
		} else {
			move(0, 0, activity);
		}
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
	
	public float getHomeCenter() {
		return this.brain.getHomeCenter();
	}
	
	public ArrayList<EntityAnimalDwarfMiner> getMiners() {
		return brain.getMiners();
	}
	
	public ArrayList<EntityAnimalDwarfWarrior> getWarriors() {
		return brain.getWarriors();
	}

}
