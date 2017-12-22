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
}
