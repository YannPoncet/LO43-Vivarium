package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fr.utbm.main.Main;
import fr.utbm.texture.TextureManager;

public class MainMenu extends GraphicScene {
	private Image menuBG;
	private ImageButton generate;
	private Main main;
	public MainMenu(Main m) {
		this.main = m;
	}

	public void addActors() {
		menuBG = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1010))));
		menuBG.setPosition(0, 0);
		stage.addActor(menuBG);
		generate = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1011)))); // Set
		// the
		// button
		// up
		generate.setPosition(450, 30);
		stage.addActor(generate); // Add the button to the stage to perform
		// rendering and take input.
		generate.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				main.startGame(0);
			};
		});
		Gdx.input.setInputProcessor(stage);
	}

	public void translate(float dx) {

	}
}
