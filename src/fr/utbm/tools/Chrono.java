package fr.utbm.tools;

import java.util.Date;

public class Chrono {
	private Date launchTime;
	private Date stopTime;
	private boolean isStopped;
	
	public Chrono() {
		this.launchTime = new Date();
		this.stopTime = new Date();
		this.isStopped = false;
	}
	
	public void resume() {
		this.isStopped = false;
		Date resumeDate = new Date();
		long timeSinceStop = resumeDate.getTime() - stopTime.getTime();
		this.launchTime.setTime(launchTime.getTime()+timeSinceStop);
	}
	
	public long getTime() {
		Date temp = new Date();
		if(isStopped == false) {
			return temp.getTime() - launchTime.getTime();
		}
		else {
			return stopTime.getTime() - launchTime.getTime();
		}
	}
	
	public void stop() {
		stopTime = new Date();
		isStopped = true;
	}
	
	public void reset() {
		this.launchTime = new Date();
	}
}
