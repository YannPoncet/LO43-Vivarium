package fr.utbm.ai;

public class Action2D extends Action{

	private float directionX;
	private float directionY;
	
	public Action2D(float dx, float dy, int a, boolean finish) {
		super(0, a, finish);
		this.directionX=dx;
		this.directionY=dy;
	}
	public float getDirectionX() {
		return directionX;
	}
	public void setDirectionX(float directionX) {
		this.directionX = directionX;
	}
	public float getDirectionY() {
		return directionY;
	}
	public void setDirectionY(float directionY) {
		this.directionY = directionY;
	}
}
