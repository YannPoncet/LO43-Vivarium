package fr.utbm.entity;

import fr.utbm.world.World;

public class AnimalLinking {

	
	public static void createEntityByID(int x, int y, int height, World w, int ID)
	{
		switch (ID) {				
		/*Animals*/
		case 200: w.addEntity(new EntityHellDog(x,y+1+height,w)); //hellHound
				break;
		
		case 208: w.addEntity(new EntityAnimalBenenut(x,y+1+height,w)); //benenut
				break;
				
		case 218: w.addEntity(new EntityAnimalBenenut(x,y+1+height,w)); //digger
				break;
				
		case 219: w.addEntity(new EntityAnimalCuteFlower(x,y+1+height,w)); //cuteFlower
				break;
		
		case 221: w.addEntity(new EntityBeaver(x,y+1+height,w)); //beaver
				break;
				
		case 222: w.addEntity(new EntityPrettyBird(x,y+1+height,w)); //prettyBird
				break;
		}
	}
}
