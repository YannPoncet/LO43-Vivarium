package fr.utbm.entity;

import com.badlogic.gdx.graphics.g2d.Animation;

import fr.utbm.ai.AIHellFish;
import fr.utbm.ai.AIPrettyBird;
import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityAnimalHellFish extends EntityAnimal2D {

	private AIHellFish brain;
	private String name = "Hell Fish";

	public EntityAnimalHellFish(float x, float y, World worldIn) {
		super(x, y, 16, 16, worldIn);
		text = TextureManager.getTexture(222);
		anim = new Animation[2];
		anim[0] = TextureManager.getAnimation(14);
		anim[1] = TextureManager.getAnimation(15);
		directionX = (int)(Math.random()+0.5);
		directionY = (int)(Math.random()+0.5);
		activity = -1;
		brain = new AIHellFish(this);
		directionXToPerform = (float)(2*Math.random()-1);
		directionYToPerform = (float)(2*Math.random()-1);
		perform = false;
		actionToPerform = 0;
		maxHealth = 100;
		health = 100;
	}
}