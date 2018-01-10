package fr.utbm.ai;

import java.util.ArrayList;

import fr.utbm.block.Block;
import fr.utbm.block.BlockAsh;
import fr.utbm.block.BlockLava;
import fr.utbm.block.BlockStone;
import fr.utbm.entity.Entity;
import fr.utbm.entity.EntityAnimalDwarfKing;
import fr.utbm.entity.EntityAnimalDwarfMiner;
import fr.utbm.entity.EntityAnimalDwarfWarrior;
import fr.utbm.tools.CollisionAABB;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;
import fr.utbm.world.World;

public class AIDwarfMiner extends AIAnimal{

	public final int HOUSE_RIGHT_WIDTH = 7;
	public final int POWER = 100;
	
	private EntityAnimalDwarfMiner animal;
	private AIGoTo pathFinder;
	
	private EntityAnimalDwarfKing king;
	private int blockerInteger;
	private int noInfiniteTimer;
	
	private boolean hasAnObjective;
	private boolean isInitialized;
	
	private boolean isMining;
	private boolean isConstructing;
	private boolean houseConstructed;
	
	private float homeCenter;

	private int lowestBlock;
	private int heighestBlock;
	private int middleHeight;
	
	private int houseState;
	
	public AIDwarfMiner(EntityAnimalDwarfMiner e, EntityAnimalDwarfKing king) {
		super(e);
		this.animal = e;
		this.king = king;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 0); //jump, walk
		
		this.hasAnObjective = false;
		this.isInitialized = false;
		this.isConstructing = false;
		this.houseConstructed = false;
		this.isMining = false;
		
		this.houseState = 0;
		this.blockerInteger = 0;
		this.noInfiniteTimer = 0;
	}
	
	@Override
	public Action updateTask() {
		
        /* Je suis un nain miner !
         * Je dois rendre le terrain plat sur une certaine zone autour du homeCenter
         * Je calcule la moyenne de la zone. Je mine le block le plus haut, tant qu'il y en a au dessus de la moyenne.
         * Je comble les points les plus bas, tant qu'il ne sont pas tous à la moyenne. 
         * Je commence à construire la maison en prenant le côté bas
         */
		Action actionDecided = null;
		
		if(!this.isInitialized) {
			initialize();
		}
		
		if(this.isInitialized) {
			if(!(heighestBlock == lowestBlock)) {
				resetMaxMin();
				if(blockerInteger <= 0) {
					int leftOrRight;
					if (Math.random() < 0.5) {
						leftOrRight = -1;
					} else {
						leftOrRight = 1;
					}
					
					if(!hasAnObjective) {
						double whatToDo = Math.random();
						if (whatToDo < 0.3) { //we mine
							actionDecided = new Action(leftOrRight,2,false);
							isMining = true;
							mineRandom();
							this.blockerInteger = (int)(Math.random()*10);
						}
						else if (whatToDo < 0.5){ //we construct 
							actionDecided = new Action(leftOrRight,3,false);
							isConstructing = true;
							constructRandom();
							this.blockerInteger = (int)(Math.random()*10);
						} 
						else if (whatToDo < 0.8){ //we move
							float toGo = (float)(homeCenter+Math.random()*HOUSE_RIGHT_WIDTH*16*leftOrRight);
							this.pathFinder.setObjective(toGo);
							this.hasAnObjective = true;
							
							if(toGo/16 <= 1) { //we're to close to the left
								this.hasAnObjective = false;
							} else if((toGo+animal.getWidth())/16 >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1)) { //we're to close to the right
								this.hasAnObjective = false;
							}
							this.noInfiniteTimer = 20;
						} 
						else { //we do nothing
							this.blockerInteger = (int)(Math.random()*100);
							actionDecided = new Action(0,-1,false);
							isMining = false; 
							isConstructing = false;
						}
					} else {
						actionDecided = this.pathFinder.updateTask();
						if(actionDecided.isFinish() || noInfiniteTimer <= 0) {
							this.hasAnObjective = false;
						}
						noInfiniteTimer--;
					}
				}
				else {
					blockerInteger --;
					if(isMining) {
						actionDecided = new Action(animal.getDirectionX(),2,false);
						if(blockerInteger <= 0) {
							isMining =false;
						}
					} else if(isConstructing) {
						actionDecided = new Action(animal.getDirectionX(),3,false);
						if(blockerInteger <= 0) {
							isConstructing =false;
						}
					}
				}
			} else { //le terrain est plat, on peut construire la maison
				if(blockerInteger <= 0) {
					int leftOrRight;
					if (Math.random() < 0.5) {
						leftOrRight = -1;
					} else {
						leftOrRight = 1;
					}
					
					if(!hasAnObjective) {
						double whatToDo = Math.random();
						if (whatToDo < 0.5 && !houseConstructed){ //we construct 
							actionDecided = new Action(leftOrRight,3,false);
							isConstructing = true;
							constructHouse();
							this.blockerInteger = (int)(Math.random()*10);
						} 
						else if (whatToDo < 0.8){ //we move
							float toGo = (float)(homeCenter+Math.random()*HOUSE_RIGHT_WIDTH*16*leftOrRight);
							if((int)((toGo+animal.getWidth())/16)+1>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1) //si jamais on risque d'aller sur le bord droit
							{
								toGo = toGo -32;
							}
							this.pathFinder.setObjective(toGo);
							this.hasAnObjective = true;
							
							if(toGo/16 <= 1) { //we're to close to the left
								this.hasAnObjective = false;
							} else if((toGo+animal.getWidth())/16 >= (Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1)) { //we're to close to the right
								this.hasAnObjective = false;
							}
							this.noInfiniteTimer = 20;
						} 
						else { //we do nothing
							this.blockerInteger = (int)(Math.random()*100);
							actionDecided = new Action(0,-1,false);
							isMining = false; 
							isConstructing = false;
						}
					} else {
						actionDecided = this.pathFinder.updateTask();
						if(actionDecided.isFinish() || noInfiniteTimer <= 0) {
							this.hasAnObjective = false;
						}
						noInfiniteTimer--;
					}
				}
				else {
					blockerInteger --;
					if(isMining) {
						actionDecided = new Action(animal.getDirectionX(),2,false);
						if(blockerInteger <= 0) {
							isMining =false;
						}
					} else if(isConstructing) {
						actionDecided = new Action(animal.getDirectionX(),3,false);
						if(blockerInteger <= 0) {
							isConstructing =false;
						}
					}
				}
			}
		}
			
		
		if(actionDecided == null) {
			actionDecided = new Action(0,-1,false);
		}
		
		return actionDecided;
	}
	
	public void initialize() {
		homeCenter = this.king.getHomeCenter();
		if(homeCenter != -1) {
			World world = animal.getWorldIn();
			
			Block tmp = getHeighestBlockAt((int)(homeCenter/16), world);
			if(tmp != null) {
				this.isInitialized = true;
				this.setMiddle(tmp);
			}
		}
	}
	
	public void setMiddle(Block b) {
		Block tmp;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int mid = 0;
		int count = 0;
		
		for(int i = (int)(b.getX()/16-HOUSE_RIGHT_WIDTH); i<(int)(b.getX()/16+HOUSE_RIGHT_WIDTH); i++) {
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-2) {
				tmp = getHeighestBlockAt(i, animal.getWorldIn());
				if(tmp != null) {
					if(max<=tmp.getY()/16) {
						max = (int)(tmp.getY()/16);
					}
					if(min>=tmp.getY()/16) {
						min = (int)(tmp.getY()/16);
					}
					mid += (int)(tmp.getY()/16);
					count ++;
				}
			}
		}
		middleHeight = mid/count;
		heighestBlock = max;
		lowestBlock = min;
	}
	
	public void resetMaxMin() {
		Block b = getHeighestBlockAt((int)(homeCenter/16), animal.getWorldIn());
		Block tmp;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		
		for(int i = (int)(b.getX()/16-HOUSE_RIGHT_WIDTH); i<(int)(b.getX()/16+HOUSE_RIGHT_WIDTH); i++) {
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-2) {
				tmp = getHeighestBlockAt(i, animal.getWorldIn());
				if(tmp != null && tmp.isSolid()) {
					if(max<=tmp.getY()/16) {
						max = (int)(tmp.getY()/16);
					}
					if(min>=tmp.getY()/16) {
						min = (int)(tmp.getY()/16);
					}
				}
			}
		}
		heighestBlock = max;
		lowestBlock = min;
	}
	
	public Block getHeighestBlockAt(int x, World world) {
		Block tmp;
		int y = Chunk.CHUNK_HEIGHT-2;
		
		do {
			tmp = world.getBlock(x, y);
			y--;
		} while(y>=0 && (tmp == null || !tmp.isSolid()));
		
		return tmp;
	}
	
	public void mineRandom() {
		Block homeBlock = getHeighestBlockAt((int)(homeCenter/16), animal.getWorldIn());
		Block tmp;
		Block toDamage = null;
		
		for(int i = (int)(homeBlock.getX()/16-HOUSE_RIGHT_WIDTH); i<(int)(homeBlock.getX()/16+HOUSE_RIGHT_WIDTH); i++) {
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-2) {
				tmp = getHeighestBlockAt(i, animal.getWorldIn());
				if(tmp != null) {
					if(this.middleHeight<tmp.getY()/16) {
						toDamage = tmp;
					}
				}
			}
		}
		if(toDamage != null) {
			toDamage.damage(POWER);
		}
	}
	
	public void constructRandom() {
		Block homeBlock = getHeighestBlockAt((int)(homeCenter/16), animal.getWorldIn());
		Block tmp = null;
		Block toConstruct = null;
		
		for(int i = (int)(homeBlock.getX()/16-HOUSE_RIGHT_WIDTH); i<(int)(homeBlock.getX()/16+HOUSE_RIGHT_WIDTH); i++) {
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-2) {
				tmp = getHeighestBlockAt(i, animal.getWorldIn());
				if(tmp != null) {
					if(this.middleHeight>tmp.getY()/16) {
						if(tmp.getY()/16 < Chunk.CHUNK_HEIGHT) {
							toConstruct = tmp;
						}
					}
				}
			}
		}
		if(toConstruct != null) {
			if(!isADwarfOn(toConstruct.getX(), toConstruct.getY())) {
				animal.getWorldIn().setBlock((int)(toConstruct.getX()/16),(int)(toConstruct.getY()/16+1), new BlockStone((int)(toConstruct.getX()/16),(int)(toConstruct.getY()/16+1), animal.getWorldIn()));
			}
		}
	}

	public boolean isADwarfOn(float x, float y) {
		ArrayList<EntityAnimalDwarfWarrior> warriors = this.king.getWarriors();
		
		if(!isAnEntityOn(x, y, king) && !isAnEntityOn(x, y, this.animal)) { //si le roi ne dérange pas
			for(EntityAnimalDwarfWarrior e: warriors) { //si les warriors ne dérangent pas
				if(isAnEntityOn(x, y, e)) {
					return true;
				}
			}
		} else {
			return true;
		}
		
		return false;
	}
	
	public boolean isAnEntityOn(float x, float y, Entity e) {
        if(CollisionAABB.isCol(e.getPosX(),e.getPosY(),e.getWidth(),e.getHeight(),x,y,16,16)){
            return true;
        }
		return false;
		/*
		System.out.println("test inc:"+e.getX()/16+" : "+(e.getX()+e.getWidth())/16+" : "+x/16);
		if(e.getX()<=x && e.getX()+e.getWidth()>=x) { //L'entité est dedans en x
			if(e.getY()<=y && e.getY()+e.getHeight()>=y) { //L'entité est dedans en y
				System.out.println("entité!");
				return true;
			}
		}
		return false;*/
	}
	
	public void constructHouse() {
		int constructY = middleHeight +1;
		if(constructY < Chunk.CHUNK_HEIGHT-2) { //si on est pas trop haut
			if(constructBlock(houseState)) {
				houseState++;
			}
		}
	}
	
	public boolean constructBlock(int index) {
		boolean done = false;
		switch(index) {
		case 0: done=constructBlock(homeCenter-5*16,middleHeight*16+1*16); break;
		case 1: done=constructBlock(homeCenter-5*16,middleHeight*16+2*16); break;
		case 2: done=constructBlock(homeCenter-4*16,middleHeight*16+1*16); break;
		case 3: done=constructBlock(homeCenter-4*16,middleHeight*16+2*16); break;
		case 4: done=constructBlock(homeCenter-4*16,middleHeight*16+3*16); break;
		case 5: done=constructBlock(homeCenter-4*16,middleHeight*16+4*16); break;
		case 6: done=constructBlock(homeCenter-4*16,middleHeight*16+5*16); break;
		case 7: done=constructBlock(homeCenter-3*16,middleHeight*16+5*16); break;
		case 8: done=constructBlock(homeCenter-3*16,middleHeight*16+6*16); break;
		case 9: done=constructBlock(homeCenter-2*16,middleHeight*16+5*16); break;
		case 10: done=constructBlock(homeCenter-2*16,middleHeight*16+6*16); break;
		case 11: done=constructBlock(homeCenter-1*16,middleHeight*16+5*16); break;
		case 12: done=constructBlock(homeCenter-1*16,middleHeight*16+6*16); break;
		case 13: done=constructBlock(homeCenter,middleHeight*16+5*16); break;
		case 14: done=constructBlock(homeCenter,middleHeight*16+6*16); break;
		case 15: done=constructBlock(homeCenter+1*16,middleHeight*16+5*16); break;
		case 16: done=constructBlock(homeCenter+1*16,middleHeight*16+6*16); break;
		case 17: done=constructBlock(homeCenter+2*16,middleHeight*16+5*16); break;
		case 18: done=constructBlock(homeCenter+2*16,middleHeight*16+6*16); break;
		case 19: done=constructBlock(homeCenter+3*16,middleHeight*16+5*16); break;
		case 20: done=constructBlock(homeCenter+3*16,middleHeight*16+6*16);  
				if(done) { 
					houseConstructed = true;
				}
				break;

		}
		
		return done;
	}
	
	public boolean constructBlock(float x, float y) {
		if(!isADwarfOn(x,y)) {
			this.animal.getWorldIn().setBlock((int)(x/16),(int)(y/16), new BlockStone((int)(x/16),(int)(y/16), animal.getWorldIn()));
			return true;
		}
		return false;
	}
}
