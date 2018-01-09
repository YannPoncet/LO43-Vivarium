package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fr.utbm.main.DesktopApplication;
import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.ObjectGetter;
import fr.utbm.ux.panels.PanelBase;
import fr.utbm.world.World;

public class MainUXFull extends GraphicScene {
	private int screenWidth;
	private int screenHeight;
	private int xPos = 0;
	private int yPos = 0;
	private World world;
	private boolean deploy, undeploy;
	private Texture bg;
	private float xTranslation;
	private int category;

	private Image menuBar;
	private ImageButton reduc;

	private ImageButton blocks;
	private ImageButton entities;
	private ImageButton exit;

	private int selected;

	private Image selector;

	PanelBase elems;

	public MainUXFull(World w) {
		super();
		this.world = w;
		screenWidth = DesktopApplication.WIDTH;
		screenHeight = DesktopApplication.HEIGHT;
		deploy = true;
		undeploy = false;
		xTranslation = screenWidth / 2;
		this.bg = TextureManager.getTexture(1001);
		elems = new PanelBase();
		this.category = 0;
		this.selected = 1;
	}

	@Override
	public void addActors() {
		this.stage = new Stage(new ScreenViewport());

		this.stage.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				actionOnClick(x, y);
			};
		});

		menuBar = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1006))));
		menuBar.setPosition(xTranslation + screenWidth / 2, screenHeight - 54);
		elems.addElement(menuBar);
		stage.addActor(menuBar);

		reduc = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1002)))); // Set
																												// the
																												// button
																												// up
		reduc.setPosition(xTranslation + screenWidth / 2, (screenHeight / 2) - (reduc.getHeight() / 2));
		elems.addElement(reduc);
		stage.addActor(reduc); // Add the button to the stage to perform
								// rendering and take input.
		reduc.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				undeploy = true;
			};
		});

		blocks = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1003)))); // Set
																													// the
																													// button
																													// up
		blocks.setPosition(xTranslation + screenWidth / 2, screenHeight - blocks.getHeight());
		elems.addElement(blocks);
		stage.addActor(blocks); // Add the button to the stage to perform
								// rendering and take input.
		blocks.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMenu(0);
			};
		});

		entities = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1004)))); // Set
																													// the
																													// button
																													// up
		entities.setPosition(xTranslation + screenWidth / 2 + blocks.getWidth() + 40,
				screenHeight - entities.getHeight());
		elems.addElement(entities);
		stage.addActor(entities); // Add the button to the stage to perform
									// rendering and take input.
		entities.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMenu(1);

			};
		});

		exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1005)))); // Set
																												// the
																												// button
																												// up
		exit.setPosition(xTranslation + screenWidth / 2 + blocks.getWidth() + entities.getWidth() + 80,
				screenHeight - exit.getHeight());
		elems.addElement(exit);
		stage.addActor(exit); // Add the button to the stage to perform
								// rendering and take input.
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMenu(2);
			};
		});
		addActorCat(category);
		Gdx.input.setInputProcessor(stage);
	}

	public void deploy() {
		if (xTranslation > 0) {
			float dx = -screenWidth / 200;
			xTranslation += dx;
			elems.translateAll(dx);
		} else {
			deploy = false;
		}
	}

	public void undeploy() {
		if (xTranslation < screenWidth / 2) {
			float dx = screenWidth / 200;
			xTranslation += dx;
			elems.translateAll(dx);
		} else {
			MainUX mf = new MainUX(world);
			mf.create();
			RenderManager.setUI(mf);
		}

	}

	public void setMenu(int cat) {
		this.category = cat;
		switch (cat) {
		case 0:
			this.category = 0;
			this.selected = 1;
			addActors();
			menuBar.setPosition(xTranslation + screenWidth / 2, screenHeight - 54);
			break;
		case 1:
			this.category = 1;
			this.selected = 1;
			addActors();
			menuBar.setPosition(xTranslation + screenWidth / 2 + blocks.getWidth() + 40, screenHeight - 54);
			break;
		case 2:
			Gdx.app.exit();
			break;
		}

	}

	public void addActorCat(int cat) {
		switch (cat) {
		case 0:
			for (int i = 0; i < 11; i++) {
				addBlockButton(i);
			}
			selector = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1009))));
			selector.setPosition(xTranslation + screenWidth / 2 + 50 + (1 % 5) * (100 + 0),
					screenHeight - 200 - (1 / 5) * (100 + 0));
			elems.addElement(selector);
			stage.addActor(selector);
			break;
		case 1:
			for (int i = 0; i < 9; i++) {
				addEntityButton(i);
			}
			selector = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1009))));
			selector.setPosition(xTranslation + screenWidth / 2 + 50 + (1 % 5) * (100 + 0),
					screenHeight - 200 - (1 / 5) * (100 + 0));
			elems.addElement(selector);
			stage.addActor(selector);
			break;
		}
	}

	public void actionOnClick(float x, float y) {
		if (x < screenWidth / 2) {
			switch (category) {
			case 0:
				int rX = (int)((x + world.getXCam())/16);
				int rY = (int)((y + world.getYCam())/16);
				world.setBlock(rX, rY, ObjectGetter.getBlock(selected, rX, rY, world));
				break;
			case 1:
				break;
			}
		}

	}

	public void addBlockButton(int nb) {
		ImageButton button;
		button = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1030 + nb))));
		button.setPosition(xTranslation + screenWidth / 2 + 50 + (nb % 5) * (button.getWidth() + 0),
				screenHeight - 200 - (nb / 5) * (button.getHeight() + 0));
		elems.addElement(button);
		stage.addActor(button);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSelected(nb);
			};
		});
	}

	public void addEntityButton(int nb) {
		ImageButton button;
		button = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1050 + nb))));
		button.setPosition(xTranslation + screenWidth / 2 + 50 + (nb % 5) * (button.getWidth() + 0),
				screenHeight - 200 - (nb / 5) * (button.getHeight() + 0));
		elems.addElement(button);
		stage.addActor(button);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSelected(nb);
			};
		});
	}

	public void setSelected(int nb) {
		selector.setPosition(xTranslation + screenWidth / 2 + 50 + (nb % 5) * (selector.getWidth() + 0),
				screenHeight - 200 - (nb / 5) * (selector.getHeight() + 0));
		this.selected = nb;
	}

	@Override
	public void render() {
		stage.act(Gdx.graphics.getDeltaTime());
		if (deploy) {
			deploy();
		}
		if (undeploy) {
			undeploy();
		}
		stage.getBatch().begin();
		stage.getBatch().draw(bg, xPos + xTranslation, yPos);
		stage.getBatch().end();
		stage.draw();

	}
}
