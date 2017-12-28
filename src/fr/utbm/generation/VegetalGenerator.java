package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.biome.Biome;

public class VegetalGenerator extends PseudoRandom 
{

	public VegetalGenerator(double seed, long M) 
	{
		super(seed, M);
	}
	
	public ArrayList<Integer> surfaceVegetalGen(ArrayList<Biome> biomeList, ArrayList<Integer> surface)
	{
		/*
		 * Il faut 3 blocks de large sans pente pour pouvoir y placer un végétal
		 * On parcourt la liste de biomes (for biome b: biomeList), sachant que pour chaque biome, on a accès:
		 * - à la largeur du biome (b.getWidth() : int)
		 * - à une arrayList d'id et frequence  (b.getVegetalIdFrequence(): ArrayList<int[]>)
		 * à la fin on retourne une arrayList d'ID de plante
		 * pour chaque block de la surface : 0 si pas de plante, ID de la plante sinon
		 */
		
		ArrayList<Integer> vegetalList = new ArrayList<Integer>(); // à renvoyer
		vegetalList.set(0, 0); // il ne peut pas y avoir de plante sur le premier bloc (il faut 3 blocs vides)
		vegetalList.set(surface.size()-1, 0); // il ne peut pas y avoir de plante sur le dernier bloc (il faut 3 blocs vides)
		int sumBiomeLength=0; // pour ajuster l'index de surface
		
		//on traverse les biomes
		for(Biome b : biomeList)
		{
			int hauteur = 0; //pour voir si on a un terrain plat
			
			//on traverse les blocs du biome b
			for(int i=1; i<b.getWidth()-1; i++)
			{
				hauteur = surface.get(i);
				
				//on vérifie si 3 blocs sont à la même hauteur
				if(hauteur==surface.get(i+sumBiomeLength-1) && hauteur==surface.get(i+sumBiomeLength+1))
				{					
					double frequence = (super.getNextRandom()+0.5)*100; //random entre 0 et 100
					boolean vegetalAdded = false; //pour savoir si on a ajouté une plante
					int j=0; //pour parcourir les ID des plantes
					while(vegetalAdded==false && j<b.getVegetalIdFrequence().size()) //tant qu'on a pas ajouté de plante et qu'on a pas atteint la fin
					{
						if(frequence > b.getVegetalIdFrequence().get(j)[1]) //random frequence > frequence de la plante j
						{
							frequence -= b.getVegetalIdFrequence().get(j)[1]; //pour toujours travailler sur un intervalle de [0-fréquence]
						}
						else //on est dans la bonne range, on ajoute la plante à la liste
						{
							vegetalList.set(i+sumBiomeLength,b.getVegetalIdFrequence().get(j)[0]); // on ajoute l'index de la plante à la liste
							vegetalAdded = true; //on a ajouté une plante
						}
						j++;
					}
				}
			}
			sumBiomeLength+=b.getWidth(); //on ajoute la taille du biome parcouru à la taille totale des biomes parcourus
		}
		
		return vegetalList;
	}

}
