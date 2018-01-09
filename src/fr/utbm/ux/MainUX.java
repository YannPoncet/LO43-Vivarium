package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import fr.utbm.block.Block;
import fr.utbm.entity.Entity;
import fr.utbm.main.DesktopApplication;
import fr.utbm.render.RenderManager;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.ObjectGetter;
import fr.utbm.world.World;

public class MainUX extends GraphicScene{
	  	private Texture myTexture;
	    private TextureRegion myTextureRegion;
	    private TextureRegionDrawable myTexRegionDrawable;
	    private ImageButton button;
	    private int screenWidth;
	    private int screenHeight;
	    private boolean deploy,undeploy;
	    private float xTranslation;
	    private World world;
	public MainUX(World w){
		super();
		this.world = w;
		screenWidth = DesktopApplication.WIDTH;
		screenHeight = DesktopApplication.HEIGHT;
		deploy = true;
		undeploy = false;
		xTranslation = 0;
	}
	@Override
	public void addActors(){
		this.stage = new Stage(new ScreenViewport());
		this.stage.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				actionOnClick(x + world.getXCam(), y + world.getYCam());
			};
		});
		
		 	myTexture = TextureManager.getTexture(1000);
	        myTextureRegion = new TextureRegion(myTexture);
	        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
	        button = new ImageButton(myTexRegionDrawable); //Set the button up
	        button.setPosition(xTranslation + screenWidth - button.getWidth(), (screenHeight / 2) - (button.getHeight()/2));
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
			xTranslation -= 58/20;
			button.setPosition(xTranslation + screenWidth - button.getWidth(), (screenHeight / 2) - (button.getHeight()/2));
			
		}else{
			deploy =false;
		}
	}
	public void descBlock(Block b){
		
	}
	public void descEntity(Entity e){
		
	}
	
	
	public void undeploy(){
		if(xTranslation < 58){
			xTranslation += 58/20;
			button.setPosition(xTranslation + screenWidth - button.getWidth(), (screenHeight / 2) - (button.getHeight()/2));
		}else{
			MainUXFull mf = new MainUXFull(world);
            mf.create();
            RenderManager.setUI(mf);
            this.stage.dispose();
		}	
	}
	public void actionOnClick(float x, float y) {
		Entity e = this.world.getEntityAt(x, y);
		if(e != null){
			e.kill();
		}else{
			int rX = (int) (x/ 16);
			int rY = (int) (y / 16);
			Block b = this.world.getBlock(rX, rY);
			if(b != null){
				b.kill();
			}
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
		stage.draw();
	}
	

}
