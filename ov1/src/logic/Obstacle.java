package logic;

import java.util.Random;

public class Obstacle implements Coordinates{

	private int x, y, radius;
	private static Random random = new Random();
	public Obstacle(){
		
		x = random.nextInt(Parameters.width);
		y = random.nextInt(Parameters.height);
		radius = random.nextInt(Parameters.maxObstacleSize) + 5;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getRadius(){
		return radius;
	}
}
