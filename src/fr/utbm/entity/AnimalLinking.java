package fr.utbm.entity;

import fr.utbm.world.World;

public class AnimalLinking {

	
	public static void createAnimalByID(int x, int y, int height, World w, int ID)
	{
		switch (ID) {				
		/*Animals*/
		case 200: w.addEntity(new EntityAnimalHellDog(x,y+1+height,w)); //hellHound
				break;
		
		case 208: w.addEntity(new EntityAnimalBenenut(x,y+1+height,w)); //benenut
				break;
				
		case 218: w.addEntity(new EntityAnimalDigger(x,y+1+height,w)); //digger
				break;
				
		case 219: w.addEntity(new EntityAnimalCuteFlower(x,y+1+height,w)); //cuteFlower
				break;
		
		case 221: w.addEntity(new EntityAnimalBeaver(x,y+1+height,w)); //beaver
				break;
				
		case 222: w.addEntity(new EntityAnimalPrettyBird(x,y+1+height,w)); //prettyBird
				break;
				
		case 224: w.addEntity(new EntityAnimalMrStabby(x,y+1+height,w)); //mrStabby
				break;
				
		case 226: w.addEntity(new EntityAnimalDwarfKing(x,y+1+height,w)); //dwarf-kingGechter
				break;
				
		case 227: w.addEntity(new EntityAnimalDwarfWarrior(x,y+1+height,w)); //dwarf-warrior
				break;
				
		case 228: w.addEntity(new EntityAnimalDwarfMiner(x,y+1+height,w)); //dwarf-miner
				break;
				
		}
	}
	
}
