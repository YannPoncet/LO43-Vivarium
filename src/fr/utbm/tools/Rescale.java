package fr.utbm.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Rescale {
	
	
	public static Texture rescale(Texture t, float x,float y){
		
		if (!t.getTextureData().isPrepared()) {
		    t.getTextureData().prepare();
		}
		Pixmap init = t.getTextureData().consumePixmap();
		Pixmap finalP = new Pixmap((int)(t.getWidth()*x),(int)( t.getHeight()*y), init.getFormat());
		finalP.drawPixmap(init,
		        0, 0, init.getWidth(), init.getHeight(),
		        0, 0, finalP.getWidth(), finalP.getHeight()
		);
		Texture finalT = new Texture(finalP);
		init.dispose();
		finalP.dispose();
		return finalT;
		}

}
