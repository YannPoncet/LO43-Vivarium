package fr.utbm.ai;

import fr.utbm.block.BlockType;
import fr.utbm.entity.EntityAnimalHellFish;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIHellFish extends AIAnimal{
	
	private EntityAnimalHellFish animal;
	private int timeDoingAction = 0;
	private int timeToDoAction = (int)(20*Math.random()+10); //entre 10 et 30
	
	public AIHellFish(EntityAnimalHellFish e) {
		super(e);
		this.animal = e;
	}
	
	@Override
	public Action2D updateTask() {
		Action2D actionDecided = null;
		switch(objective) //we stay in a switch if we want to implement a function to eat
		{
		case 0 : //he is in lava
			
			if(timeDoingAction>=timeToDoAction) //if he needs to change action
			{
				timeToDoAction = (int)(20*Math.random()+10); //we change the duration of the action (to have variable one)
				float dx= actionDecided.getDirectionX()+(float)(Math.random()-0.5);
				float dy= actionDecided.getDirectionY()+(float)(Math.random()-0.5);
				
				//if it's too fast, we slow him down
				if(dx>1){
					dx-=0.5;
				}
				else if(dx<-1){
					dx+=0.5;
				}
				
				if(dy>1){
					dy-=0.5;
				}
				else if(dy<-1){
					dy+=0.5;
				}
				
				actionDecided.setDirectionX(dx);
				actionDecided.setDirectionY(dy);
				actionDecided.setAction(0);
				timeDoingAction=0;
			}
			else
			{
				timeDoingAction++;
			}
			
			
			//si il rencontre un obstacle en X, il change de direction
			if((actionDecided.getDirectionX()<0 && animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16)!=null)
				|| (actionDecided.getDirectionX()>0 && animal.getWorldIn().getBlock((animal.getPosX() + animal.getWidth())/16, animal.getPosY()/16)!=null))
			{
				actionDecided.setDirectionX(-actionDecided.getDirectionX());
			}
			
			//si il y a de l'eau sur lui, il fui !
			if(animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16)!=null && animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16).getBlockType()==BlockType.LIQUID)
			{
				actionDecided.setDirectionY(1);
			}
			else
			{
				//si il rencontre un obstacle en Y, il change de direction (solide en haut, liquide en bas pour qu'il puisse se poser si il y a du sol)
				if((actionDecided.getDirectionY()<0 && animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16-1)!=null && !animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16-1).isSolid())
					|| (actionDecided.getDirectionY()>0 && animal.getWorldIn().getBlock(animal.getPosX()/16, (animal.getPosY()+animal.getHeight())/16+1)!=null))
				{
					actionDecided.setDirectionY(-actionDecided.getDirectionY());
				}
			}
			break;
		case 1 : //il n'est pas dans la lave
			actionDecided.setDirectionX(0);
			actionDecided.setDirectionY(5);
			actionDecided.setAction(1);
			break;
		case 2 :
			
			break;
		}
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){
		switch(objective){
		case 0 :
			break;
		case 1 :
			break;
		case 2 :
			break;
		}
		
	}
}