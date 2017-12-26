package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.biome.Biome;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class Surface1DGenerator extends PseudoRandom {
	private ArrayList<Integer> noise;

	public Surface1DGenerator(double seed, long M) 
	{
		super(seed, M);
	}
	
	public ArrayList<Integer> generateAndGetNoise(double amp, double wl, int octaves, double divisor, ArrayList<Biome> biomeList) 
	{
		if(amp < 0 || amp > 2 || wl < 0 || wl > 2) {
			System.err.println("\n[Surface Generator] Warning: amplitude and wavelength should stay between 0 and 2");
		}
		
		this.noise = new ArrayList<>();
		ArrayList<Integer> temp;
		
		int oldHeight = 0;
		int newHeight = 0;
		double q = 0;
		boolean firstIter = true;
		for (Biome b: biomeList) {
			temp = CombineNoise(GenerateNoise(b.getSurfaceAmplitude()*amp, b.getSurfaceWavelength()*wl, octaves, divisor, b.getWidth()), b.getWidth());
			
			if(firstIter) {
				firstIter = false;
				this.noise.addAll(temp);
			} else {
				oldHeight = this.noise.get(this.noise.size()-1);
				newHeight = temp.get(0);
				q=oldHeight-newHeight;
				
				for (Integer height: temp) {
					height = (int)Math.floor(height+q);
					this.noise.add(height);
					q=q-q/temp.size();
				}
			}
		}
		return this.noise;
	}

	//octave generator
	private ArrayList<double[]> GenerateNoise(double amp, double wl, int octaves, double divisor, int width)
	{
		ArrayList<double[]> result = new ArrayList<>();
		for(int i = 0; i < octaves; i++)
		{
			result.add(Perlin(amp, wl, width));
			amp /= divisor;
			wl /= divisor;
		}
		return result;
	}
	
	private double[] Perlin(double amp, double wl, int width)
	{
		int x = 0;
		double a = getNextRandom();
		double b = getNextRandom();
		double[] pos = new double[width];
		while(x < width){
			if(x % wl == 0){
				a = b;
				b = getNextRandom();
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
	private ArrayList<Integer> CombineNoise(ArrayList<double[]> pl, int width){
		ArrayList<Integer> result = new ArrayList<>();
		double total;
		for(int i = 0; i < width; i++){
			total = 0;

			for(int j = 0; j < pl.size(); j++){
				total += pl.get(j)[i];
			}
			result.add((int)total);
		}
		return result;
	}
	
	
}
