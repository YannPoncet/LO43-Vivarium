package fr.utbm.texture;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class TextureManager {
	
	private static HashMap<Integer,Texture> textureList= new HashMap<Integer, Texture>();
	
	public static Texture getTexture(int id){
		Texture t = textureList.get(id);
		if(t == null){
			System.out.println("TEXTURE-MANAGER : Trying to load texture ID : " + id + " which doesn't exist");
			t = textureList.get(0);
		}
		return t;
	}
	private static void addText(int id, Texture text){
		textureList.put(id, text);
	}
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
