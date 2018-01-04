package fr.utbm.ai;

public class Action {
	private int direction;
	private int action;
	private boolean isFinish;
	public Action(int d, int a, boolean finish){
		this.direction = d;
		this.action = a;
		this.isFinish = finish;
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
	public boolean isFinish() {
		return isFinish;
	}
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
}
