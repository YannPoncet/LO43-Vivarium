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

import fr.utbm.block.Block;
import fr.utbm.block.BlockGlass;
import fr.utbm.entity.Entity;
import fr.utbm.main.DesktopApplication;
import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.tools.ObjectGetter;
import fr.utbm.ux.panels.PanelBase;
import fr.utbm.world.Chunk;
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
	public boolean trashMode;

	private Image menuBar;
	private ImageButton reduc;

	private ImageButton blocks;
	private ImageButton entities;
	private ImageButton exit;
	private ImageButton trash;

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
		this.trashMode = false;
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
		trash = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1007))),
				new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1008))),
				new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1008)))); // Set
		// the
		// button
		// up
		trash.setPosition(10 + xTranslation + screenWidth / 2, 10);
		elems.addElement(trash);
		stage.addActor(trash); // Add the button to the stage to perform
		// rendering and take input.
		trash.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeTrashMode();
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
			this.world.backMenu();
			break;
		}

	}

	public void changeTrashMode() {
		if (trashMode) {
			trash.setChecked(false);
			trashMode = false;
		} else {
			trash.setChecked(true);
			trashMode = true;
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
			for (int i = 0; i < 10; i++) {
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
			if (!trashMode) {
				int rX = (int) ((x + world.getXCam()) / 16);
				int rY = (int) ((y + world.getYCam()) / 16);
				switch (category) {
				case 0:
					if(rX > 0 && rY > 0 && rX < ((world.getMap().NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH)-1) && rY < (Chunk.CHUNK_HEIGHT-1))
					{
						world.setBlock(rX, rY, ObjectGetter.getBlock(selected, rX, rY, world));
					}
					break;
				case 1:
					if(rX > 0 && rY > 0 && rX < ((world.getMap().NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH)-1) && rY < (Chunk.CHUNK_HEIGHT-1))
					{
						Entity e = ObjectGetter.getEntity(selected,rX, rY, world);
						if(!CollisionAABB.enterInCollisionAt(e, 0, 0)){
							world.addEntity(e);
						}
					}
					break;
				}
			}else{
				
				destroyElement(x + world.getXCam(),y + world.getYCam());
			}
		}
	}
	public void destroyElement(float x, float y){
		Entity e = this.world.getEntityAt(x, y);
		if(e != null){
			e.damage(e.getMaxHealth());
		}else{
			int rX = (int) (x / 16);
			int rY = (int) (y / 16);
			Block b = this.world.getBlock(rX, rY);
			if(b != null && !(b instanceof BlockGlass)){
				b.kill();
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
