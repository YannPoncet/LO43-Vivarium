package fr.utbm.main;

import com.badlogic.gdx.Screen;

import fr.utbm.ux.GraphicScene;
import fr.utbm.ux.MainMenu;

public class MainMenuScreen implements Screen{

	private GraphicScene gs;
	
	private Main main;
	
	public MainMenuScreen(Main m){
		main = m;
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float arg0) {
		gs.render();
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		gs = new MainMenu(main);
		gs.create();
	}

}
