package fr.utbm.ux.panels;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class PanelBase {
	
	ArrayList<Actor> elements;
	
	public PanelBase(){
		elements = new ArrayList<Actor>();
	}
	public void addElement(Actor a){
		elements.add(a);
	}
	public void translateAll(float dx){
		for(Actor a : elements){
			a.setPosition(a.getX() + dx, a.getY());
		}
	}

}
