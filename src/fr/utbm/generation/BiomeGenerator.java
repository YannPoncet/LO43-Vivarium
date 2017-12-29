package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.biome.Biome;
import fr.utbm.biome.BiomeList;

public class BiomeGenerator extends PseudoRandom {
	private int biomeMaxSize;
	private int biomeMinSize;
	private int width;
	private ArrayList<Biome> biomeList;
	
	public BiomeGenerator(double seed, long M, int width, int biomeMinSize, int biomeMaxSize) {
		super(seed, M);
		if(biomeMinSize >= biomeMaxSize || biomeMinSize<20 || biomeMaxSize>=width) {
			System.err.println("\n[BIOME GENERATOR] Incorrect arguments");
			biomeMaxSize = 100;
			biomeMinSize = 50;
		}
		this.biomeMaxSize = biomeMaxSize;
		this.biomeMinSize = biomeMinSize;
		this.width = width;
		this.initializeList();
	}
	
	private void initializeList(){
		this.biomeList = new ArrayList<>();
		
		int totalWidth = 0;
		while (totalWidth < this.width) {
			double temp = super.getNextRandom()+0.5; //Random value between 0 and 1
			int biomeId = (int) Math.floor(temp*BiomeList.NUMBER_OF_BIOME); //Random biome id
			
			temp = super.getNextRandom()+0.5;
			int biomeWidth = (int) Math.floor(temp*(biomeMaxSize-biomeMinSize))+biomeMinSize+1;
			
			if (totalWidth + biomeWidth >= this.width){
				biomeWidth -= totalWidth + biomeWidth - width;
			}
			this.biomeList.add(new Biome(biomeId, biomeWidth));
			totalWidth += biomeWidth;
		}
	}
	
	public ArrayList<Biome> getBiomeList() {
		return this.biomeList;	
	}
	
}
