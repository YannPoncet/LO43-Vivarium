package fr.utbm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
	
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	
	public Camera(){
		   this.gameCam = new OrthographicCamera();
		   this.gameCam.position.set(800 / 2f, 600 / 2f, 0);
		   this.gamePort = new FitViewport(800,600,gameCam);
	}
	public void resize(int width, int height){
		gamePort.update(width,height);
	}
	public Matrix4 getProjectionMatrix(){
		return gameCam.combined;
	}
	public void update(){
		this.move();
	}
	public void move(){
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			   gameCam.position.x += 10;
		   }
		   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			   gameCam.position.x -= 10;
		   }
		   if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			   gameCam.position.y += 10;
		   }
		   if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			   gameCam.position.y -= 10;
		   }
		   gameCam.update();
	}
	
	
	

}
