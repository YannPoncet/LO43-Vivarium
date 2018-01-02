package fr.utbm.texture;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.utbm.tools.Rescale;

/* L'Objectif de cette classe c'est de charger toutes les textures avant qu'on joue et de les classer pour deux raisons : 1- Si on charge les textures au fur et à mesure quand on joue, ca risque de faire lagger,
 * C'est l'interet de faire un écran de chargement	2- Si je fais deux blocks de terre, il faudra charger 2 fois la texture du blockTerre, avec le textureManager, elle sera chargé une seule fois peu importe le nb de blocks
 */

public class TextureManager {
	
	private static HashMap<Integer,Texture> textureList= new HashMap<Integer, Texture>();
	private static HashMap<Integer,Animation> animationList= new HashMap<Integer, Animation>();
	
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
	public static Animation getAnimation(int id){
		Animation a = animationList.get(id);
		if(a == null){
			System.out.println("TEXTURE-MANAGER : Trying to load animation ID : " + id + " which doesn't exist");
			a = animationList.get(0);
		}
		return a;
	}
	
	/* Ajoute une texture à la HashMap */
	private static void addText(int id, Texture text){
		textureList.put(id, text);
	}
	private static void addAnim(int id, Animation anim){
		animationList.put(id, anim);
	}
	
	/* Charge toutes les textures présentes dans "res/textures/textureManager.xml" */
	public static void loadTextures(){
		
		String xmlFile = "res/textures/textureManager.xml";
		XmlReader reader = new XmlReader();
		Element root = null;
		int nb = 0;
		int nb2 = 0;
		try {
			root = reader.parse(Gdx.files.internal(xmlFile));
		} catch (IOException e) {
			System.out.println("TEXTURE-MANAGER : Erreur chargement TextureManager");
			e.printStackTrace();
		}
		Array<Element> textures = root.getChildrenByName("texture");
		for (Element child : textures)
		{
			int id = Integer.parseInt(child.get("id"));
			String text = child.get("file");
		    Texture t = new Texture("res/textures/" + text);
		    addText(id, t);
		    nb++;
		    
		}
		Array<Element> animations = root.getChildrenByName("animation");
		for (Element child : animations)
		{
			int id = Integer.parseInt(child.get("id"));
			String path = child.get("file");
			int wD = Integer.parseInt(child.get("wD"));
			int hD = Integer.parseInt(child.get("hD"));
			float speed = Float.parseFloat(child.get("speed"));
			float size = Float.parseFloat(child.get("size"));
			Texture t;
			t = Rescale.rescale(new Texture("res/textures/" + path), size, size);
			TextureRegion[][] tmp = TextureRegion.split(t, 
					t.getWidth() / wD,
					t.getHeight() / hD);
			TextureRegion[] textR1D = new TextureRegion[wD * hD];
			int index = 0;
			for (int i = 0; i < hD; i++) {
				for (int j = 0; j < wD; j++) {
					textR1D[index++] = tmp[i][j];
				}
			}
			Animation fAnim = new Animation(speed, textR1D);
			addAnim(id, fAnim);
			nb2++;
		    
		}
		
		System.out.println("TEXTURE-MANAGER : Successfully added " + nb + " textures and " + nb2 + " Animations");
	}
	
	
}
