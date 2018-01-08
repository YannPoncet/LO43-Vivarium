package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	    
	    private ImageButton button;
	    
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
	}
	
	@Override
	public void addActors(){
        button = new ImageButton(new TextureRegionDrawable(new TextureRegion(TextureManager.getTexture(1002)))); //Set the button up
        button.setPosition(xTranslation + screenWidth/2, (screenHeight / 2) - (button.getHeight()/2));
        elems.addElement(button);
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        button.addListener( new ClickListener() {              
            @Override
            public void clicked(InputEvent event, float x, float y) {
                undeploy = true;
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
