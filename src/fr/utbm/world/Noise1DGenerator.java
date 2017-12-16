package fr.utbm.world;

import java.util.ArrayList;

public class Noise1DGenerator {
	private double seed;
	private long M;
	private int A = 1664525;
	private int C = 1;
	private int width = Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;
	private ArrayList<Integer> noise;
	
	public Noise1DGenerator(double seed, long M) 
	{
		this.seed = seed;
		this.M = M;
	}
	
	public ArrayList<Integer> generateAndGetNoise(double amp, double wl, int octaves, double divisor) 
	{
		this.noise = CombineNoise(GenerateNoise(amp, wl, octaves, divisor));
		return this.noise;
	}

	//octave generator
	private ArrayList<double[]> GenerateNoise(double amp, double wl, int octaves, double divisor)
	{
		ArrayList<double[]> result = new ArrayList<>();
		for(int i = 0; i < octaves; i++)
		{
			result.add(Perlin(amp, wl));
			amp /= divisor;
			wl /= divisor;
		}
		return result;
	}
	
	private double pseudoRandom() 
	{
		seed = (A * seed + C) % M;
		return (seed / M - 0.5);
	}
	
	private double[] Perlin(double amp, double wl)
	{
		int x = 0;
		double a = pseudoRandom();
		double b = pseudoRandom();
		double[] pos = new double[width];
		while(x < width){
			if(x % wl == 0){
				a = b;
				b = pseudoRandom();
				pos[x] = a*amp;
			}
			else
			{
				pos[x] = Interpolate(a, b, (double)(x % wl)/wl) * amp;
			}
			x++;
		}
		return pos;
	}
	
	private double Interpolate(double pa, double pb, double px) {
		double ft = px * Math.PI;
		double f = (1 - Math.cos(ft)) * 0.5;

		return pa * (1 - f) + pb * f;
	}
	
	//combines octaves together
	private ArrayList<Integer> CombineNoise(ArrayList<double[]> pl){
		ArrayList<Integer> result = new ArrayList<>();
		double total;
		for(int i = 0; i < width; i++){
			total = 0;

			for(int j = 0; j < pl.size(); j++){
				total += pl.get(j)[i];
				//System.out.println(j+" "+i+" "+pl.get(j)[i]);
			}
			result.add((int)total);
		}
		return result;
	}
	
	
}
