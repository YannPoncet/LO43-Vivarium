package fr.utbm.ai;

import fr.utbm.block.BlockType;
import fr.utbm.entity.EntityAnimalHellFish;

public class AIHellFish extends AIAnimal{
	
	private EntityAnimalHellFish animal;
	private Action2D actionDecided;
	
	public AIHellFish(EntityAnimalHellFish e) {
		super(e);
		this.animal = e;
		if(animal.isInLava())
		{
			objective = 0;
		}
		else
		{
			objective = 1;
		}
		actionDecided = new Action2D(0,0,0,true);
	}
	
	@Override
	public Action2D updateTask() {
		switch(objective)
		{
		case 0 : //Managing with collisions, directions,... When in lava
			
			if(actionDecided.getDirectionX() != 0)
			{
				actionDecided.setDirectionX(1*(actionDecided.getDirectionX()/Math.abs(actionDecided.getDirectionX())));
			}
			else
			{
				actionDecided.setDirectionX(1);
			}
			
			if(actionDecided.getDirectionY() != 0)
			{
				actionDecided.setDirectionY(1*(actionDecided.getDirectionY()/Math.abs(actionDecided.getDirectionY())));
			}
			else
			{
				actionDecided.setDirectionY(1);
			}
			actionDecided.setAction(0);
			
			//Obstacle on X, change direction
			if(hasXCollision())
			{
				actionDecided.setDirectionX(-actionDecided.getDirectionX());
			}
			//Obstacle on Y, change direction
	        if(hasYCollision())
	        {
	            actionDecided.setDirectionY(-actionDecided.getDirectionY());
	        }
			objSwitch();
			break;
		case 1 : //When not in lava
			actionDecided.setDirectionX(0);
			actionDecided.setDirectionY(50f);
			actionDecided.setAction(1);
			objSwitch();
			break;
		}
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){
		switch(objective){
		case 0 :
			if(!animal.isInLiquid())
			{
				objective = 1;
			}
			else
			{
				objective = 0;
			}
			break;
		case 1 :
			if(animal.isInLiquid())
			{
				objective = 0;
			}
			else
			{
				objective = 1;
			}
			break;
		}
		
	}

	
	//Collision on the top or on the bottom
    private boolean hasYCollision()
    {
        if(actionDecided.getDirectionY()>0)
        {
            for(int i=0; i<(int)(animal.getWidth()/16+1); i++)
            {
                if(animal.getWorldIn().getBlock(animal.getPosX()/16+i, ((animal.getPosY()+animal.getHeight())/16)+1)==null
                    || animal.getWorldIn().getBlock(animal.getPosX()/16+i, ((animal.getPosY()+animal.getHeight())/16)+1).getBlockType() != BlockType.LIQUID)
                {
                    return true;
                }
            }
        }
        else if(actionDecided.getDirectionY()<0)//si il va vers le bas
        {
            for(int i=0; i<(int)(animal.getWidth()/16+1); i++)
            {
                if(animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16-1)==null
                    || animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16-1).getBlockType() != BlockType.LIQUID)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
  //Collision on the right or on the left
    private boolean hasXCollision()
    {
        if(actionDecided.getDirectionX()>0)
        {
            for(int i=0; i<(int)(animal.getHeight()/16+1); i++)
            {
                if(animal.getWorldIn().getBlock((animal.getPosX()+animal.getWidth())/16+1, animal.getPosY()/16+i)==null
                    || animal.getWorldIn().getBlock((animal.getPosX()+animal.getWidth())/16+1, animal.getPosY()/16+i).getBlockType() != BlockType.LIQUID)
                {
                    return true;
                }
            }
        }
        else if(actionDecided.getDirectionX()<0)
        {
            for(int i=0; i<(int)(animal.getHeight()/16+1); i++)
            {
                if(animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16+i)==null
                        || animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16+i).getBlockType() != BlockType.LIQUID)
                {
                    return true;
                }
            }
        }
        return false;
    }
}