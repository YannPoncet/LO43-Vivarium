package fr.utbm.biome;

import java.io.IOException;
import java.util.HashMap;

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
	HashMap<Integer, Integer> vegetalIdFrequence;
	
	public Biome(int type, int width) {
		this.id = type;
		this.width = width;
		this.vegetalIdFrequence = new HashMap<>();
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
					System.out.println("[Biome.java] ID="+Integer.parseInt(vegetal.get("id"))+"Frequence="+Integer.parseInt(vegetal.get("frequence")));
					this.vegetalIdFrequence.put(Integer.parseInt(vegetal.get("id")), Integer.parseInt(vegetal.get("frequence")));
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
}
