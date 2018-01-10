package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalRedFish;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class AIRedFish extends AIAnimal{
	
	/*Le prettyBird peut actuellement:
	 * Se poser au sol
	 * Au sol: 
	 * - bouger dans une direction aléatoire
	 * - rester au sol pendant un temps aléatoire
	 * En vol:
	 * - garder sa direction pendant un temps aléatoire 
	 * - changer de direction aléatoirement dans un angle possible (il ne fait pas demi-tour)
	 * Il change également de direction lorsqu'il rencontre un obstacle
	 * Il ne rentre pas dans l'eau
	 * Si il est dans l'eau pour une quelconque raison, il fui vers le haut
	 * (si il est dans une grotte, il se cogne, c'est pas très malin un piaf (mis à part les corbeaux mais là,
	 * le prettyBird est un petit oiseau rose et il est trèèès loin d'être un corbeau (oui il est un peu con)))
	 */

	private EntityAnimalRedFish animal;
	private int timeDoingAction; //combien de temps on a déjà fait l'action
	private int timeToDoAction; //combien de temps on doit faire l'action
	private Action2D actionDecided; //l'action que l'oiseau va faire
	private boolean hasJump=false;
	private boolean hasGravity=false;
	
	public AIRedFish(EntityAnimalRedFish e) {
		super(e);
		this.animal = e;
		this.timeDoingAction = 0;
		this.timeToDoAction = (int)(20*Math.random()+10); //entre 10 et 30
		this.actionDecided = new Action2D((float)(2*Math.random()-1),(float)(2*Math.random()-1),0,true);
	}

	@Override
	public Action2D updateTask() {
		
		if(!isInWater())
		{
			if(animal.isOnGround()) //si il n'est pas dans l'eau et est sur le sol, il saute
			{
				hasJump=true;
				actionDecided.setAction(0);
				actionDecided.setDirectionX(0);
				actionDecided.setDirectionY(0.5f);
				this.animal.damage(10);
			}
			else //sinon la gravité s'applique à lui
			{
				hasGravity=true;
				actionDecided.setAction(0);
				actionDecided.setDirectionX(0);
				actionDecided.setDirectionY(animal.getWorldIn().getGravity());
				this.animal.damage(1);
			}
		}
		else //il est dans l'eau
		{
			if(timeDoingAction>=timeToDoAction) //si il doit changer d'action
			{
				timeToDoAction = (int)(20*Math.random()+10); //on change la durée de l'action (pour qu'elle varie)
				
				//on crée une nouvelle direction entre la direction précédente et la direction précédente +/-0.5
				float dx= actionDecided.getDirectionX()+(float)(Math.random()-0.5);
				float dy= actionDecided.getDirectionY()+(float)(Math.random()-0.5);
				
				//on vérifie que le poisson ne va pas trop vite, on le ralenti le cas échéant
				if(dx>1){
					dx-=0.5;
				}
				if(dx<1){
					dx+=0.5;
				}
				if(dy>1){
					dy-=0.5;
				}
				if(dy<1){
					dy+=0.5;
				}
				
				actionDecided.setDirectionX(dx);
				actionDecided.setDirectionY(dy);
				
				timeDoingAction=0;
			}
			else
			{
				timeDoingAction++;
			}
			
			if(hasJump || hasGravity)
			{
				hasJump=false;
				hasGravity=false;
				actionDecided.setDirectionX((float)(Math.random()-0.5));
				actionDecided.setDirectionY((float)(Math.random()-0.5));
			}
			
			//si il rencontre un obstacle en X, il change de direction
			if(hasXCollision()){
				actionDecided.setDirectionX(-actionDecided.getDirectionX());
			}
			
			//si il y n'a pas d'eau au dessus de lui ou qu'il y a un obstacle en dessous de lui, il change de sens en Y
			if(hasYCollision()){
				actionDecided.setDirectionY(-actionDecided.getDirectionY());
			}
		}
		
		return actionDecided;
	}
	
	//pour savoir si il est dans l'eau
	private boolean isInWater()
	{
		for(int i=0; i<(int)(animal.getWidth()/16+1); i++)
		{
			for(int j=0; j<(int)(animal.getHeight()/16+1); j++)
			{
				if(animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16+j)==null || animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16+j).getID()!=104)
				{
					return false;
				}
			}	
		}
		return true;
	}
	
	//pour savoir si on a une colision à droite ou à gauche du poisson
	private boolean hasXCollision()
	{
		if(actionDecided.getDirectionX()>0)//si il va vers la droite
		{
			for(int i=0; i<(int)(animal.getHeight()/16+1); i++) //+1 en cas de chevauchement
			{
				if(((animal.getPosX()+animal.getWidth())/16+1<=Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) //pour ne pas dépasser de la map à droite
					&& (animal.getWorldIn().getBlock((animal.getPosX()+animal.getWidth())/16+1, animal.getPosY()/16+i)==null //et que le bloc de droite est null
						|| animal.getWorldIn().getBlock((animal.getPosX()+animal.getWidth())/16+1, animal.getPosY()/16+i).getID()!=104))//ou que le bloc dde droite n'est pas un bloc d'eau
				{
					return true;
				}
			}
		}
		else if(actionDecided.getDirectionX()<0)//si il va vers la gauche
		{
			for(int i=0; i<(int)(animal.getHeight()/16+1); i++)
			{
				if(animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16+i)==null //et que le bloc de gauche est null
						|| animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16+i).getID()!=104)//ou que le bloc de gauche n'est pas un bloc d'eau
				{
					return true;
				}
			}
		}
		return false;
	}
	
	//pour savoir si on a une colision au dessus ou en dessous du poisson
	private boolean hasYCollision()
	{
		if(actionDecided.getDirectionY()>0)//si il va vers le haut
		{
			for(int i=0; i<(int)(animal.getWidth()/16+1); i++)
			{
				if(animal.getWorldIn().getBlock(animal.getPosX()/16+i, (animal.getPosY()+animal.getHeight())/16+2)==null //et que le bloc du dessus est null
					|| animal.getWorldIn().getBlock(animal.getPosX()/16+i, (animal.getPosY()+animal.getHeight())/16+2).getID()!=104)//ou que le bloc du dessus n'est pas un bloc d'eau
				{
					return true;
				}
			}
		}
		else if(actionDecided.getDirectionY()<0)//si il va vers le bas
		{
			for(int i=0; i<(int)(animal.getWidth()/16+1); i++)
			{
				if(animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16-1)==null //et que le bloc du dessous est null
					|| animal.getWorldIn().getBlock(animal.getPosX()/16+i, animal.getPosY()/16-1).getID()!=104)//ou que le bloc du dessous n'est pas un bloc d'eau
				{
					return true;
				}
			}
		}
		return false;
	}

}
