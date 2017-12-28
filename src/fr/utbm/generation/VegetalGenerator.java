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
		int sumBiomeLength=0; // pour ajuster l'index de surface
		
		//on traverse les biomes
		for(Biome b : biomeList)
		{
			int hauteur = 0; //pour voir si on a un terrain plat
			//on traverse les blocs du biome b
			for(int i=0; i<b.getWidth(); i++)
			{
				//on test si on est sur les bords de map (qui contiennent du verre)
				if(i+sumBiomeLength<1 || i+sumBiomeLength>surface.size())
				{
					vegetalList.add(0);
				}
				else
				{
					hauteur = surface.get(i+sumBiomeLength);
					boolean isFlat = true;
					boolean vegetalAdded = false; //pour savoir si on a ajouté une plante
					
					double frequence = (super.getNextRandom()+0.5)*100; //random entre 0 et 100
					int j=0; //pour parcourir les ID des plantes
					
					while(vegetalAdded==false && j<b.getVegetalIdFrequence().size()) //tant qu'on a pas ajouté de plante et qu'on a pas atteint la fin
					{
						if(frequence > b.getVegetalIdFrequence().get(j)[1]) //random frequence > frequence de la plante j
						{
							frequence -= b.getVegetalIdFrequence().get(j)[1]; //pour toujours travailler sur un intervalle de [0-fréquence]
						}
						else //on est dans la bonne range, on vérifie qu'on a la place de l'ajouter
						{
							//on vérifie si le nombre de blocs requis pour la plante est à la bonne hauteur (on a une surface plate)
							for(int k=1; k<b.getVegetalIdFrequence().get(j)[2]+1; k++)
							{
								if(i+sumBiomeLength+k>surface.size()-1 || hauteur!=surface.get(i+sumBiomeLength+k))
								{
									isFlat=false;
								}
							}
							
							if(isFlat)
							{					
								vegetalList.add(b.getVegetalIdFrequence().get(j)[0]); // on ajoute l'index de la plante à la liste
								vegetalAdded = true; //on a ajouté une plante
								
								//on ajoute on ajout la place necessaire à la plante-1 blocs sur lesquels on ne peut pas ajouter de plante (ID=0)
								//ce qui correspond à la taille de la plante à l'écran
								for(int k=1; k<b.getVegetalIdFrequence().get(j)[2]; k++)
								{
									if(i+k<b.getWidth() && i+sumBiomeLength+k<surface.size()) //pour ne pas ajouter plus que la taille de la carte
									{
										vegetalList.add(0);
										i++;
									}
								}
							}
						}
						
						j++;
					}
					
					if(vegetalAdded==false)
					{
						vegetalList.add(0); // on indique qu'il n'y a pas de plante sur ce bloc
					}
				}
			}
			sumBiomeLength+=b.getWidth(); //on ajoute la taille du biome parcouru à la taille totale des biomes parcourus
		}

		return vegetalList;
	}

}