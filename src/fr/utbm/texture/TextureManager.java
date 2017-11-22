package fr.utbm.texture;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/* L'Objectif de cette classe c'est de charger toutes les textures avant qu'on joue et de les classer pour deux raisons : 1- Si on charge les textures au fur et à mesure quand on joue, ca risque de faire lagger,
 * C'est l'interet de faire un écran de chargement	2- Si je fais deux blocks de terre, il faudra charger 2 fois la texture du blockTerre, avec le textureManager, elle sera chargé une seule fois peu importe le nb de blocks
 */

public class TextureManager {
	
	private static HashMap<Integer,Texture> textureList= new HashMap<Integer, Texture>();
	
	
	/* Permets d'obtenir une texture pré-chargé grâce à son ID
	 * IL FAUT AVOIR FAIT UN LOAD AVANT
	 */
	public static Texture getTexture(int id){
		Texture t = textureList.get(id);
		if(t == null){
			System.out.println("TEXTURE-MANAGER : Trying to load texture ID : " + id + " which doesn't exist");
			t = textureList.get(0);
		}
		return t;
	}
	
	/* Ajoute une texture à la HashMap */
	private static void addText(int id, Texture text){
		textureList.put(id, text);
	}
	
	/* Charge toutes les textures présentes dans "res/textures/textureManager.xml" */
	public static void loadTextures(){
		
		String xmlFile = "res/textures/textureManager.xml";
		XmlReader reader = new XmlReader();
		Element root = null;
		int nb = 0;
		try {
			root = reader.parse(Gdx.files.internal(xmlFile));
		} catch (IOException e) {
			System.out.println("TEXTURE-MANAGER : Erreur chargement TextureManager");
			e.printStackTrace();
		}
		Array<Element> items = root.getChildrenByName("texture");
		for (Element child : items)
		{
			int id = Integer.parseInt(child.get("id"));
			String text = child.get("file");
		    Texture t = new Texture("res/textures/" + text);
		    addText(id, t);
		    nb++;
		    
		}
		System.out.println("TEXTURE-MANAGER : Successfully added " + nb + " textures");
	}
	
	
}
