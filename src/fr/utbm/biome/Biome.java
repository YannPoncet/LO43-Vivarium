package fr.utbm.biome;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Biome {
	int id;
	String name;
	int width;
	
	double surfaceAmplitude;
	double surfaceWavelength;
	int textureId;
	ArrayList<int[]> vegetalIdFrequence;
	
	public Biome(int type, int width) {
		this.id = type;
		this.width = width;
		this.vegetalIdFrequence = new ArrayList<>();
		if(initializeBiome(type) == 0) {
			System.err.println("Error: the biome "+type+" doesn't exist");
		}
	}
	
	private int initializeBiome(int type) {
		
		String xmlFile = "res/biomeManager.xml";
		XmlReader reader = new XmlReader();
		Element root = null;
		
		try {
			root = reader.parse(Gdx.files.internal(xmlFile));
		} catch (IOException e) {
			System.out.println("BIOME-Initialisation : Erreur chargement BiomeManager.xml");
			e.printStackTrace();
		}
		Array<Element> biomes = root.getChildrenByName("biome");
		for (Element child : biomes)
		{
			if (type == Integer.parseInt(child.get("id"))) {
				this.name = child.get("name");
				this.surfaceAmplitude = Double.parseDouble(child.get("surfaceAmplitude"));
				this.surfaceWavelength = Double.parseDouble(child.get("surfaceWavelength"));
				this.textureId = Integer.parseInt(child.get("textureId"));
				
				Array<Element> vegetalList = child.getChildByName("vegetalList").getChildrenByName("vegetal");
				for (Element vegetal : vegetalList)
				{
					System.out.print("\n[Biome.java] ID="+Integer.parseInt(vegetal.get("id"))+" Frequence="+Integer.parseInt(vegetal.get("frequence")));
					int [] temp = new int[2];
					temp[0] = Integer.parseInt(vegetal.get("id"));
					temp[1] = Integer.parseInt(vegetal.get("frequence"));
					this.vegetalIdFrequence.add(temp);
				}
				return 1;
			}
		}
		return 0;
	}
		
	public int getWidth(){
		return this.width;
	}
	public int getId(){
		return this.id;
	}
	public double getSurfaceAmplitude() {
		return this.surfaceAmplitude;
	}
	public double getSurfaceWavelength() {
		return this.surfaceWavelength;
	}
	public int getTextureId() {
		return this.textureId;
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<int[]> getVegetalIdFrequence() {
		return this.vegetalIdFrequence;
	}
}
