package fr.utbm.entity;

import fr.utbm.ai.AIDwarfWarrior;
import fr.utbm.ai.Action;
import fr.utbm.world.World;

public class EntityAnimalDwarfWarrior extends EntityAnimalDwarf {
	public static final int POWER = 10;
	private EntityAnimalDwarfKing king;
	private AIDwarfWarrior brain;
	private boolean isAttacking;
	
	public EntityAnimalDwarfWarrior(float x, float y, World worldIn, EntityAnimalDwarfKing king) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 48, 49, worldIn, 227, 28, 30, 29);
		this.king = king;
		this.name = "Dwarf warrior";
		this.brain = new AIDwarfWarrior(this);
		this.isAttacking = false;
		health = 100;
	}
	
	public EntityAnimalDwarfWarrior(float x, float y, World worldIn) {
		/* super(x, y, worldIn, normal, walkId, jumpId, specialId)*/
		super(x, y, 48, 49, worldIn, 227, 28, 30, 29);
		this.name = "Dwarf warrior";
		this.brain = new AIDwarfWarrior(this);
		this.isAttacking = false;
		health = 100;
		maxHealth = 100;
		
		for(Entity e : worldIn.getEntities()){
			if(e instanceof EntityAnimalDwarfKing){
				this.king = (EntityAnimalDwarfKing) e;
			}
		}
	}
	
	public void update() {
		suffocating();	
		
		if (!perform) {
			hasJump = false;
			this.stateTime = 0;
			isAttacking = false;
			
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
	
	public void attack()
	{
		this.brain.getTarget().damage(POWER);
	}
	

	public EntityAnimalDwarfKing getKing() {
		return this.king;
	}
	
	@Override
	protected void performSpecialAction(int actionID, int direction) {
		/* THE DWARF SHALL ATTACK HERE */
		directionX = direction;
		if (isOnGround() && !isAttacking) {
			isAttacking = true;
			attack();
			move(0, 0, 2);
		} else {
			move(0, 0, activity);
		}
	}

}
