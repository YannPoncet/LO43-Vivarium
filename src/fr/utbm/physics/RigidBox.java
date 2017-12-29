package fr.utbm.physics;

import fr.utbm.tools.Vector4;

public class RigidBox {

	private float x,y;
	private float w,h;
	
	public RigidBox(float x, float y,float w,float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public Vector4 getCurrentBox(float xE, float yE){
		return new Vector4(xE+x,yE+y,w,h);
	}
	
	
	
	
}
