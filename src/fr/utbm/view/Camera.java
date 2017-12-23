package fr.utbm.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.utbm.render.RenderManager;
import fr.utbm.world.World;

public class Camera {
	
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	private World w;
	private int actualChunk;
	
	
	
	public Camera(World w){
			this.w = w;
		   this.gameCam = new OrthographicCamera();
		   this.actualChunk = 0;
		   this.gameCam.position.set(800 / 2f, (600 / 2f) + 300*16f , 0);
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
		   //System.out.println(gameCam.position.x + "      " + gameCam.position.y);
		   if(actualChunk != ((int) gameCam.position.x) / (50*16)){
			   actualChunk = (int) (gameCam.position.x / (50*16));
			   w.cameraSwitchChunkChunk(actualChunk);
		   }
		   RenderManager.setBgPos(gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y - gameCam.viewportHeight/2);
		   gameCam.update();
	}


}
