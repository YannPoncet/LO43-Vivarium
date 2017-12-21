package fr.utbm.generation;

public class PseudoRandom {
	private double seed;
	private long M;
	private int A = 1664525;
	private int C = 1;
	
	public PseudoRandom(double seed, long M)
	{
		this.seed = seed;
		this.M = M;
	}
	
	public double getNextRandom()
	{
		seed = (A * seed + C) % M;
		return (seed / M - 0.5);
	}
}
