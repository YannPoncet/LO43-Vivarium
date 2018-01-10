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
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;
import fr.utbm.world.World;

public class AIDwarfMiner extends AIAnimal{

	public final int HOUSE_RIGHT_WIDTH = 5;
	public final int POWER = 24;
	
	private EntityAnimalDwarfMiner animal;
	private AIGoTo pathFinder;
	
	private EntityAnimalDwarfKing king;
	private int blockerInteger;
	private int noInfiniteTimer;
	
	private boolean hasAnObjective;
	private boolean isInitialized;
	
	private boolean isMining;
	private boolean isConstructing;
	
	private float homeCenter;

	private int lowestBlock;
	private int heighestBlock;
	private int middleHeight;
	
	
	public AIDwarfMiner(EntityAnimalDwarfMiner e, EntityAnimalDwarfKing king) {
		super(e);
		this.animal = e;
		this.king = king;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 0); //jump, walk
		
		this.hasAnObjective = false;
		this.isInitialized = false;
		this.isConstructing = false;
		this.isMining = false;
		
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
			resetMaxMin();
			if(!(heighestBlock == lowestBlock)) {
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
			} else {
				if(blockerInteger <= 0) {
					int leftOrRight;
					if (Math.random() < 0.5) {
						leftOrRight = -1;
					} else {
						leftOrRight = 1;
					}
					
					if(!hasAnObjective) {
						double whatToDo = Math.random();
						if (whatToDo < 0.5){ //we construct 
							actionDecided = new Action(leftOrRight,3,false);
							isConstructing = true;
							//constructRandom();
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
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) {
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
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH-1) {
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
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) {
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
			if(i>0 && i<Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH) {
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
			if(!isADwarfOn(toConstruct.getX())) {
				animal.getWorldIn().setBlock((int)(toConstruct.getX()/16),(int)(toConstruct.getY()/16+1), new BlockStone((int)(toConstruct.getX()/16),(int)(toConstruct.getY()/16+1), animal.getWorldIn()));
			}
		}
	}
	
	public boolean isADwarfOn(float x) {
		ArrayList<EntityAnimalDwarfMiner> miners = this.king.getMiners();
		ArrayList<EntityAnimalDwarfWarrior> warriors = this.king.getWarriors();
		
		
		return false;
	}
	
	public boolean isAnEntityOn(float x, Entity e) {
		return false;
	}
	
}
