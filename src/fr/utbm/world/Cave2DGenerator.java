package fr.utbm.world;

import java.util.ArrayList;

public class Cave2DGenerator {
	private double seed;
	private long M;
	private int A = 1664525;
	private int C = 1;
	private int width = Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;
	private int height = Chunk.CHUNK_HEIGHT;
	private ArrayList<ArrayList<Integer>> caves;
	
	public Cave2DGenerator(double seed, long M, double initialPercentageOfFill) 
	{
		this.seed = seed;
		this.M = M;
		initializeCells(initialPercentageOfFill);
	}
	
	
    public void initializeCells(double initialPercentageOfFill) {
    	this.caves = new ArrayList<>();
        for (int i=0; i<width; i++) {
        	ArrayList<Integer> tmp = new ArrayList<>();
            for (int j=0; j<height; j++) {
            	if (i == 0 || i >= width || pseudoRandom() < initialPercentageOfFill/300) {
            		tmp.add(1);
            	}
            	else{ tmp.add(0); }
            }
            caves.add(tmp);
        }
     }
    
    public ArrayList<ArrayList<Integer>> generateAndGetCaves()
    {
    	this.caves = launchAutomaton(1,20);
    	this.caves = launchAutomaton(2,1);
    	return this.caves;
    }
    
    public ArrayList<ArrayList<Integer>> launchAutomaton(int mode, int numIterations) {
    	
    	ArrayList<ArrayList<Integer>> tmpCaves = new ArrayList<>();
        for (int i=0; i<width; i++) 
        {
        	ArrayList<Integer> tmpCol = new ArrayList<>();
            for (int j=0; j<height; j++) 
            {
            	tmpCol.add(caves.get(i).get(j));
            }
            tmpCaves.add(tmpCol);
        }
        
        while (numIterations > 0) {
        	numIterations--;
            for (int i=0; i<width; i++) 
            {
                for (int j=0; j<height; j++) 
                {
                	//Rule to say if the cell stays alive when at the border
                	if (i == 0 || i >= width-1 || j == 0 || j >= height-1) 
                	{
                		caves.get(i).set(j,1);
                	} 
                	else 
                	{
	                    int aliveAround = 0;
	
	                    //Number of cells alive around the current cell
	                    aliveAround += caves.get(i-1).get(j-1);
	                    aliveAround += caves.get(i-1).get(j);
	                    aliveAround += caves.get(i-1).get(j+1);
	                    aliveAround += caves.get(i).get(j-1);
	                    aliveAround += caves.get(i).get(j+1);
	                    aliveAround += caves.get(i+1).get(j-1);
	                    aliveAround += caves.get(i+1).get(j);
	                    aliveAround += caves.get(i+1).get(j+1);

	                    // apply B678/S345678
	                    int currentState = caves.get(i).get(j);
	                    if (mode == 1)
	                    {
	                    	if ((currentState == 0 && (aliveAround>=7 && aliveAround<=8))||
	                    		(currentState == 1 && (aliveAround>=3 && aliveAround<=8))) 
	                    	{
	                    		if (currentState == 0) { tmpCaves.get(i).set(j,1); }
	                    		
	                    	}
	                    	else 
	                    	{ 	if (currentState == 1) { tmpCaves.get(i).set(j,0); } }
	                    }
	                    // B5678/S5678
	                    else if (mode == 2)
	                    {
	                    	if ((currentState == 0 && (aliveAround>=5 && aliveAround<=8))||
		                    	(currentState == 1 && (aliveAround>=5 && aliveAround<=8))) 
	                    	{
	                    		if (currentState == 0) { tmpCaves.get(i).set(j,1); }
	                    	}
	                    	else 
	                    	{ 	if (currentState == 1) { tmpCaves.get(i).set(j,0); } }
	                    }
                	}
                 }
              }
           }
        return tmpCaves;
    }
	
	private double pseudoRandom() 
	{
		seed = (A * seed + C) % M;
		return (seed / M - 0.5);
	}
}
