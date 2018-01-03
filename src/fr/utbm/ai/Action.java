package fr.utbm.ai;

public class Action {
	private int direction;
	private int action;
	public Action(int d, int a){
		this.direction = d;
		this.action = a;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
}
