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
	public final static int HEIGHT=675;
	public final static int WIDTH=1200;
	
	private OrthographicCamera gameCam;
	private Viewport gamePort;
	private World w;
	private int actualChunk;
	
	
	public Camera(World w){
			this.w = w;
		   this.gameCam = new OrthographicCamera();
		   this.actualChunk = 0;
		   this.gameCam.position.set(WIDTH / 2f, (HEIGHT / 2f) + 300*16f , 0);
		   this.gamePort = new FitViewport(WIDTH,HEIGHT,gameCam);
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
		   w.setCamPos(gameCam.position.x - WIDTH / 2f, gameCam.position.y-HEIGHT / 2f);
		   RenderManager.setBgPos(gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y - gameCam.viewportHeight/2);
		   gameCam.update();
	}


}
