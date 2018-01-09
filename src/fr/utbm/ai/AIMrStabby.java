package fr.utbm.ai;

import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimal;
import fr.utbm.entity.EntityAnimalMrStabby;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIMrStabby extends AIAnimal {
	
	private static int VISION=160; //the range at which it can see a target
	private EntityAnimalMrStabby animal; //the animal which contains this brain
	private EntityAnimal target; //mrStabby will try to kill a target
	private AIGoTo pathFinder; //to go to the target

	public AIMrStabby(EntityAnimalMrStabby e) {
		super(e);
		this.animal = e;
		
		this.target = null;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(3,0);
		this.pathFinder.setObjective(animal.getPosX());
	}
	
	//will get the nearest entityAnimal in a certain range (VISION)
	private EntityAnimal getNearestTarget(){
		float dist = 100000f;
		EntityAnimal target = null;
		
		for(Entity e : animal.getWorldIn().getEntities()){
			if((e.targetableBy(224)) && (e.getPosX()<animal.getPosX()+VISION && e.getPosX()>animal.getPosX()-VISION) && (e.getPosY()<animal.getPosY()+VISION && e.getPosY()>animal.getPosY()-VISION)){
				EntityAnimal potentialTarget = (EntityAnimal)e;
				float newDist = Math.abs(animal.getPosX()-potentialTarget.getPosX());
				if(newDist < dist){
					dist = newDist;
					target = potentialTarget;
				}
			}
		}
		return target;
	}
	
	private int besideTargetPos()
    {
        if(this.target==null)
        {
            return 0;
        }
        else
        {
            if((((int)((this.animal.getPosX()+this.animal.getWidth())/16+1)>(int)(target.getPosX()+target.getWidth())/16)) && (((int)((this.animal.getPosX())/16+1)<(int)(target.getPosX()+target.getWidth())/16)))
            {
                return -1;
            }
            else if((((int)((this.animal.getPosX())/16+1)<(int)(target.getPosX())/16)) && (((int)((this.animal.getPosX()+this.animal.getWidth())/16+1)>(int)(target.getPosX()+target.getWidth())/16)))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
	
	//mrStabby cherche une cible du regard, si il ne voit personne, il se déplace dans son biome neige
	private Action getTargetOrMove(Action actionDecided)
	{
		this.target = getNearestTarget();
		float toMove = 0.0f;
		//si il n'a pas de cible, il va se déplacer, sans sortir de son biome neige
		if(this.target==null){
			do
			{
				toMove = (float)(20*16*Math.random()-10*16);
			}while(this.animal.getWorldIn().getBiomeIn((int)((this.animal.getPosX()+toMove)/16))!=3 || this.animal.getWorldIn().getBiomeIn((int)((this.animal.getPosX()+this.animal.getWidth()+toMove)/16))!=3);
			//If we're at the end of the map, or the beginning, we change direction
			if((animal.getX()+16*animal.getDirectionX() <= 16) || ((animal.getX()+16*animal.getDirectionX()+animal.getWidth()) >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16))) {
				animal.changeDirectionX();
			}
			this.pathFinder.setObjective((this.animal.getPosX()+toMove)); //il va se déplacer entre -10 et 10 blocs autour de lui
			actionDecided = this.pathFinder.updateTask();
		}
		else
		{
			this.pathFinder.setObjective((this.target.getPosX()));
			actionDecided = this.pathFinder.updateTask();
		}
		return actionDecided;
	}

	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		
		if(this.animal.getWorldIn().getBiomeIn((int)(this.animal.getPosX()/16))!=3) //si il n'est pas sur de la neige, il fond
		{
			actionDecided = new Action(0,2,false);
		}
		else
		{
			if(target!=null)//si il a une cible
			{
				int targetPos = besideTargetPos();
				if(targetPos!=0) //si il est à coté de la cible, il lui donne un coup de couteau :o
				{
					actionDecided = new Action(targetPos,1,false);
					if(target.isDead()) //si la cibe est morte, on cherche une autre cible
					{
						actionDecided = getTargetOrMove(actionDecided);
					}
				}
				else //si il n'est pas à coté de la cible, il avance vers elle
				{
					if((int)((target.getPosX()+animal.getWidth())/16)+1>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH)
					{
						this.pathFinder.setObjective(target.getPosX()-animal.getWidth());
					}
					else
					{
						this.pathFinder.setObjective(target.getPosX());
					}
					actionDecided = this.pathFinder.updateTask();
				}
			}
			else //si il n'a pas de cible, il en cherche une
			{
				actionDecided = getTargetOrMove(actionDecided);
			}
		}
			
		return actionDecided;
	}
	
	public EntityAnimal getTarget()
	{
		return this.target;
	}

}
