package fr.utbm.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopApplication {
	public final static int HEIGHT=675;
	public final static int WIDTH=1200;
	
	public static void main (String[] arg) {
	      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.title = "UTBM - LO43 - Vivarium du futur";
	      config.width = WIDTH;
	      config.height = HEIGHT;
	      new LwjglApplication(new Main(), config);

	      //rodolphe dmr
	   }
}
