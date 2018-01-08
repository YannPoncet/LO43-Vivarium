package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import fr.utbm.main.DesktopApplication;
import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.ux.panels.PanelBase;

public class MainUXFull extends GraphicScene{
	 	private int screenWidth;
	    private int screenHeight;
	    private int xPos = 0;
	    private int yPos = 0;
	    private boolean deploy,undeploy;
	    private Texture bg;
	    private float xTranslation;
	    private int category;
	    
	    
	    
	    private Image menuBar;
	    private ImageButton reduc;
	    
	    private ImageButton blocks;
	    private ImageButton entities;
	    private ImageButton exit;
	    
	    PanelBase elems;
	    
	public MainUXFull(){
		super();
		screenWidth = DesktopApplication.WIDTH;
		screenHeight = DesktopApplication.HEIGHT;
		deploy = true;
		undeploy = false;
		xTranslation  = screenWidth / 2;
		this.bg = TextureManager.getTexture(1001);
		elems = new PanelBase();
		this.category = 0;
	}
	
	@Override
	public void addActors(){
		
		menuBar = new Image(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1006))));
		menuBar.setPosition(xTranslation + screenWidth/2, screenHeight- 54);
		elems.addElement(menuBar);
		stage.addActor(menuBar);
		
		
        reduc = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1002)))); //Set the button up
        reduc.setPosition(xTranslation + screenWidth/2, (screenHeight / 2) - (reduc.getHeight()/2));
        elems.addElement(reduc);
        stage.addActor(reduc); //Add the button to the stage to perform rendering and take input.
        reduc.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                undeploy = true;
            };
        });
        
        blocks = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1003)))); //Set the button up
        blocks.setPosition(xTranslation + screenWidth/2, screenHeight - blocks.getHeight());
        elems.addElement(blocks);
        stage.addActor(blocks); //Add the button to the stage to perform rendering and take input.
        blocks.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	setMenu(0);
            };
        });
        
        
        entities = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1004)))); //Set the button up
        entities.setPosition(xTranslation + screenWidth/2 + blocks.getWidth() + 40, screenHeight - entities.getHeight());
        elems.addElement(entities);
        stage.addActor(entities); //Add the button to the stage to perform rendering and take input.
        entities.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	setMenu(1);
            	
            };
        });
        
        exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1005)))); //Set the button up
        exit.setPosition(xTranslation + screenWidth/2 +blocks.getWidth() + entities.getWidth() + 80, screenHeight - exit.getHeight());
        elems.addElement(exit);
        stage.addActor(exit); //Add the button to the stage to perform rendering and take input.
        exit.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	setMenu(2);
            };
        });
        
        
        
        Gdx.input.setInputProcessor(stage);
		
		

	}
	public void deploy(){
		if(xTranslation > 0){
			float dx = -screenWidth/200;
			xTranslation += dx;
			elems.translateAll(dx);
		}else{
			deploy =false;
		}
	}
	
	public void undeploy(){
		if(xTranslation < screenWidth/2){
			float dx = screenWidth/200;
			xTranslation += dx;
			elems.translateAll(dx);
		}else{
			MainUX mf = new MainUX();
            mf.create();
            RenderManager.setUI(mf);
		}
		
	}
	public void setMenu(int cat){
		this.category = cat;
		switch(cat){
			case 0:
				menuBar.setPosition(xTranslation + screenWidth/2, screenHeight- 54);
				break;
			case 1:
				menuBar.setPosition(xTranslation + screenWidth/2 + blocks.getWidth() + 40, screenHeight- 54);
				break;
			case 2:
				menuBar.setPosition(xTranslation + screenWidth/2 +  blocks.getWidth() + 80 + entities.getWidth(), screenHeight- 54);
				break;
		}
		
	}
	
	
	@Override
	public void render(){
		stage.act(Gdx.graphics.getDeltaTime());
		if(deploy){
			deploy();
		}
		if(undeploy){
			undeploy();
		}
		stage.getBatch().begin();
		stage.getBatch().draw(bg,xPos+xTranslation,yPos);
		stage.getBatch().end();
		stage.draw();
	}
}
