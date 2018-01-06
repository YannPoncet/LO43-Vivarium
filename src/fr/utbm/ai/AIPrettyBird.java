package fr.utbm.ai;

import fr.utbm.block.BlockType;
import fr.utbm.entity.EntityPrettyBird;

public class AIPrettyBird extends AIAnimal {
	
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

	private EntityPrettyBird animal;
	private int timeDoingAction; //combien de temps on a déjà fait l'action
	private int timeToDoAction; //combien de temps on doit faire l'action
	private int timeStayingOnGround; //depuis combien de temps il est au sol
	private int timeToStayOnGround; //combien de temps il reste au sol
	private int timeMovingOnGround; //pour le faire bouger un peu si il est au sol
	private int timeToMoveOnGround; //tout les combien de temps il bouge
	private float directionToMoveOnGround; //<0 gauche, >0 droite
	private Action2D actionDecided; //l'action que l'oiseau va faire
	
	public AIPrettyBird(EntityPrettyBird e) {
		super(e);
		this.animal = e;
		this.timeDoingAction = 0;
		this.timeToDoAction = (int)(20*Math.random()+10); //entre 10 et 30
		this.timeStayingOnGround = 0;
		this.timeToStayOnGround = (int)(20*Math.random()); //entre 0 et 20
		this.timeMovingOnGround = 0;
		this.timeToMoveOnGround = (int)(3*Math.random()+1); //entre 1 et 4
		this.directionToMoveOnGround = (float)(2*Math.random()-1);//entre -1 et 1
		this.actionDecided = new Action2D((float)(2*Math.random()-1),(float)(2*Math.random()-1),0,true);
	}

	@Override
	public Action2D updateTask() {
		
		//si il est sur le sol
		if(animal.isOnGround())
		{
			//si il doit encore rester sur le sol
			if(timeStayingOnGround<timeToStayOnGround)
			{
				//si il doit rester immobile
				if(timeMovingOnGround<timeToMoveOnGround)
				{
					actionDecided.setDirectionX(0);
					timeMovingOnGround++;
				}
				else //si il doit bouger
				{
					timeToMoveOnGround = (int)(3*Math.random()+1); //on reset le temps pour bouger (pour qu'il varie)
					directionToMoveOnGround = (float)(2*Math.random()-1); //on change la direction (pour qu'elle varie)
					actionDecided.setDirectionX(directionToMoveOnGround);
				}
				
				actionDecided.setDirectionY(0);
				timeStayingOnGround++;
			}
			else //il doit décoller du sol
			{
				timeToStayOnGround = (int)(20*Math.random());
				actionDecided.setDirectionX((float)(2*Math.random()-1));
				actionDecided.setDirectionY(1);
			}
		}
		else //il n'est pas sur le sol
		{
			if(timeDoingAction>=timeToDoAction) //si il doit changer d'action
			{
				timeToDoAction = (int)(20*Math.random()+10); //on change la durée de l'action (pour qu'elle varie)
				
				//on crée une nouvelle direction entre la direction précédente et la direction précédente +/-0.5
				float dx= actionDecided.getDirectionX()+(float)(Math.random()-0.5);
				float dy= actionDecided.getDirectionY()+(float)(Math.random()-0.5);
				
				//on vérifie que l'oiseau ne va pas trop vite, on le ralenti le cas échéant
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
				timeDoingAction=0;
			}
			else
			{
				timeDoingAction++;
			}
		}
		
		
		//si il rencontre un obstacle en X, il change de direction
		if((actionDecided.getDirectionX()<0 && animal.getWorldIn().getBlock(animal.getPosX()/16-1, animal.getPosY()/16)!=null)
			|| (actionDecided.getDirectionX()>0 && animal.getWorldIn().getBlock((animal.getPosX() + animal.getWidth())/16, animal.getPosY()/16)!=null))
		{
			actionDecided.setDirectionX(-actionDecided.getDirectionX());
		}
		
		//si il rencontre un obstacle en Y, il change de direction (solide en haut, liquide en bas pour qu'il puisse se poser si il y a du sol)
		if((actionDecided.getDirectionY()<0 && animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16-1)!=null && !animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16-1).isSolid())
			|| (actionDecided.getDirectionY()>0 && animal.getWorldIn().getBlock(animal.getPosX()/16, (animal.getPosY()+animal.getHeight())/16+1)!=null))
		{
			actionDecided.setDirectionY(-actionDecided.getDirectionY());
		}
		
		//si il y a de l'eau sur lui, il fui !
		if(animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16)!=null && animal.getWorldIn().getBlock(animal.getPosX()/16, animal.getPosY()/16).getBlockType()==BlockType.LIQUID)
		{
			actionDecided.setDirectionY(1);
		}
		
		return actionDecided;
	}

}
