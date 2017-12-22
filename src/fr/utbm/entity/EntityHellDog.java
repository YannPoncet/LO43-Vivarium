package fr.utbm.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.utbm.physics.Gravity;
import fr.utbm.texture.TextureManager;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.World;

public class EntityHellDog extends EntityAnimal{
	
	private Animation anim[];
	private float stateTime;
	private int activity;
	/*
	 * Dog activity :
	 * -1 Dont do anything
	 *  0 Idle
	 *  1 Walk
	 *  2 Run
	 *  3 Jump
	 */

	
	
	
	
	public EntityHellDog(float x, float y, World worldIn) {
		super(x, y, 54, 32, worldIn);
		this.text = TextureManager.getTexture(100);
		anim = new Animation[4];
		anim[0] = TextureManager.getAnimation(0);
		anim[1] = TextureManager.getAnimation(1);
		anim[2] = TextureManager.getAnimation(2);
		anim[3] = TextureManager.getAnimation(3);
		directionX = 1;
		activity = -1;
	}
	public void update(){
		System.out.println(isOnGround());
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isOnGround()){
				move(3f,10f,3);
		 }else{
			 if(isOnGround()){
				 move(0.3f,0,2);
			 }else{
				 move(0,0,activity);
			 }

		 }

	}
	
	private float xa,ya;
	float drag = 0;

	public void move(float dx, float dy, int act){
			ya+= world.getGravity()*0.1f;
			this.activity = act;
			xa+=dx;
			ya+=dy;
			if (isOnGround()) {
				drag = 0.85f;
			} else {
				drag = 0.95f;
			}
			if(dx>0){
				directionX = 1;
			}else if(dx< 0){
				directionX = 0;
			}
			int xStep = (int) Math.abs(xa * 100);
			for (int i = 0; i < xStep; i++) {
				if (!CollisionAABB.enterInCollisionAt(this, xa / xStep,0)) {
					this.x += xa / xStep;
				} else {
					xa = 0;
				}
			}
			int yStep = (int) Math.abs(ya * 100);
			for (int i = 0; i < yStep; i++) {
				if (!CollisionAABB.enterInCollisionAt(this, 0, ya / yStep)) {
					this.y += ya / yStep;
				} else {
					ya = 0;
				}
			}
			
			xa *= drag;
			ya *= drag;			
			
	}
	@Override
	public void render(SpriteBatch sp) {
		if(activity >0){
			stateTime += Gdx.graphics.getDeltaTime();
			TextureRegion currentFrame = anim[activity].getKeyFrame(stateTime, true);
			if(directionX == 0){
				sp.draw(currentFrame, this.x, this.y);
			}
			else if(directionX == 1){
				sp.draw(currentFrame, this.x + currentFrame.getRegionWidth(), this.y,-currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
			}
		}else{
			if(directionX == 0){
				sp.draw(this.text, this.x, this.y);
			}
			else if(directionX == 1){
				sp.draw(this.text, this.x + this.text.getWidth(), this.y,-this.text.getWidth(),this.text.getHeight());
			}
		}
	}
}
