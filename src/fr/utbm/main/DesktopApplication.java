package fr.utbm.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopApplication {
	public static void main (String[] arg) {
	      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	      config.title = "UTBM - LO43 - Vivarium du futur";
	      config.width = 800;
	      config.height = 480;
	      new LwjglApplication(new Main(), config);
	   }
}
