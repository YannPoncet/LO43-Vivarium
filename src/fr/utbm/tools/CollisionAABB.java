package fr.utbm.tools;

import fr.utbm.entity.Entity;

public class CollisionAABB {

	public static boolean isCol(float xA, float yA, int wA, int hA,float xB,float yB,int wB,int hB){	
		 if((xB >= xA + wA) || (xB + wB <= xA) || (yB >= yA + hA) || (yB + hB <= yA)){
			 return false;
		 }else{
			 return true;
		 }
	}
	public static boolean enterInCollisionAt(Entity e,float dx, float dy){
		boolean b = false;
		int p = ((int) (e.getPosX() + dx))/16;
		int s = ((int) (e.getPosX() + dx + e.getWidth() -1))/16;
		int pY = (((int) (e.getPosY() + dy))/16);
		int sY = (((int) (e.getPosY() + dy + e.getHeight()))/16);
		while(!(b || pY>sY)){
			while(!(b || p>s)){
				if(e.getWorldIn().getBlock(p, pY) != null){
					b=true;
				}
				p+=1;
			}
			p=((int) (e.getPosX() + dx))/16;
			pY+=1;
		}
		return b;

	}
}
