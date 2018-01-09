package fr.utbm.ux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GraphicScene {
	
	protected Stage stage;
	
	public GraphicScene(){

	}
	public void create(){
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        this.addActors();
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
	}
	public void addActors(){
		
	}
	public void translate(float dx){
		
	}

	
	public void render(){
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	public Stage getStage(){
		return this.stage;
	}

}
