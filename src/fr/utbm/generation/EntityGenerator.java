package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.entity.Entity;

public class EntityGenerator extends PseudoRandom {
	public EntityGenerator(double seed, long M) {
		super(seed, M);
	}
	
	public ArrayList<Entity> caveEntityGen(ArrayList<ArrayList<Integer>> caves, ArrayList<Entity> entities) {
		/*
		Entrée: tableau de cave du cave generator (avec 0 et 1), liste des entités pouvant être présentes
		Sortie: liste des entités à ajouter
		Algo:
		1- traverser le tableau
		2- dès qu'on trouve un bloc 0 (vide) qui n'est pas marqué, on marque tous les blocs 0 de sa grotte (lui inclus) dans un tableau temporaire ou autre si 
			tu trouves mieux (sans doute en récursif)
		3- on regarde avec aléatoire pour chaque entité si oui ou non on doit la mettre dans la grotte (fréquence d'apparition ?)
		4- on vérifie si on a la place dans cette grotte de mettre l'entité en question*/
		
		//tableau temporaire pour savoir si on a déjà traité une case ou non
		int[][] tmpArray = new int[caves.size()][caves.get(0).size()];
		ArrayList<Entity> createdEntities = new ArrayList<Entity>();
		
		/*on traverse le tableau de cave du cave generator (avec 0 et 1)
		 *si le block est marqué 1, on ajoute met pareil dans le tmpArray
		 *sinon on lance l'algo récursif qui doit remplir la cave correspondante de liquide
		 */
		for(int i=0; i<caves.size(); i++)
		{
			for(int j=caves.get(i).size()-1; j>=0; j--)
			{
				if(caves.get(i).get(j)==1)
				{
					tmpArray[i][j]=1;
				}
				else if(caves.get(i).get(j)==0 && tmpArray[i][j]==0)
				{ 
					// hauteur d'air dans les grottes aléatoire entre minHeight et maxHeight
					int doesItSpawn = (int)(super.getNextRandom()+0.5);
					recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
				}
			}
		}
		
		return createdEntities;
	}
	
	
	private void recursiveCaveLiquidGen(ArrayList<ArrayList<Integer>> caves, int[][] tmpArray, ArrayList<Entity> entities, ArrayList<Entity> createdEntities ,int i, int j, int doesItSpawn)
	{		
		tmpArray[i][j] = -1; //signifie qu'on a traité ce block
		
		
		//si la proba de spawn>does it spawn, on appelle la méthode sur le block suivant
		if(doesItSpawn>entities.get(0).getPosX())
		{
			//on transmet en dessous
			if((j>0) && (caves.get(i).get(j-1)==0) && (tmpArray[i][j-1]==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
			//on transmet à gauche
			else if((i>0) && (caves.get(i-1).get(j)==0) && (tmpArray[i-1][j]==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
			//on transmet à droite
			else if((i<caves.size()-1) && (caves.get(i+1).get(j)==0) && (tmpArray[i+1][j]==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
		}
		//si height<=0, on remplit la case de liquide et on appelle la méthode sur les voisins
		else
		{
			caves.get(i).set(j, 2); //le block en (i,j) sera un block d'eau
			
			//on transmet en dessous
			if((j>0) && (caves.get(i).get(j-1)==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
			
			//on transmet à gauche
			if((i>0) && (caves.get(i-1).get(j)==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
			
			//on transmet à droite
			if((i<caves.size()-1) && (caves.get(i+1).get(j)==0))
			{
				recursiveCaveLiquidGen(caves, tmpArray, entities, createdEntities, i, j, doesItSpawn);
			}
		}
	}

	public int[] surfaceLiquidGen(ArrayList<Integer> surface, int maxWidth, int minWidth, double liquidFrequence) {
		int[] liquids = new int[surface.size()];
		
		recursiveSurfaceLiquidGen(liquids, surface, maxWidth, minWidth, liquidFrequence, 0);
		return liquids;
	}
	
	private void recursiveSurfaceLiquidGen(int[] liquids, ArrayList<Integer> surface, int maxWidth, int minWidth, double liquidFrequence, int i) {
		while (i<surface.size()) { //On place i au début d'une pente descendante
			int height = surface.get(i);
			
			int j=i+1;
			while(j<surface.size() && surface.get(j) < surface.get(i)) { //On augmente j le long de la pente
				j++;
			}
			
			if (j>i+minWidth) { //On a une descente assez large
				if (j>i+maxWidth) { //On est trop large, on recommence en avançant i
					recursiveSurfaceLiquidGen(liquids, surface, maxWidth, minWidth, liquidFrequence, i+1);
					i=Integer.MAX_VALUE;
				}
				else
				{
					if (j==surface.size()) { //La descente n'est pas acceptable on est à droite, on recommence en avançant i
						recursiveSurfaceLiquidGen(liquids, surface, maxWidth, minWidth, liquidFrequence, i+1);
						i=Integer.MAX_VALUE;
					}
					else
					{
						if(super.getNextRandom()+0.5<liquidFrequence/100) {
							for (int k=i+1; k<j; k++) {
								liquids[k] = height-surface.get(k);
							}
						}
					}
				}
			}
			
			if (i != Integer.MAX_VALUE) {
				i=j;
			}
		}
	}
}
