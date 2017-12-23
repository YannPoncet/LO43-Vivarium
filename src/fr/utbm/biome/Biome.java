package fr.utbm.biome;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.utbm.world.World;

public class Biome {
	int id;
	String name;
	int width;
	
	double surfaceAmplitude;
	double surfaceWavelength;
	
	public Biome(int type, int width) {
		this.id = type;
		this.width = width;
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
}
