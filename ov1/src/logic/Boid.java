package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.util.Pair;

public class Boid {

	private static Random random = new Random();
	private int x, y;
	private double velX, velY;
	private double direction;
	protected static String imageFilePath = ".\\.\\resources\\images\\Boid.png";
	public BufferedImage image;

	public Boid(){

		x = random.nextInt(Parameters.width);
		y = random.nextInt(Parameters.height);
		direction = random.nextDouble()*Math.PI*2 - Math.PI;
		try{
			image = ImageIO.read(new File(imageFilePath));
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

	public double getDirection(){
		return direction;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	protected double distanceToBoid(Boid other){
		int diffX = x - other.x;
		if(Math.abs(diffX) > 0.5 * Parameters.width){
			diffX = Parameters.width - Math.abs(diffX);
		}
		int diffY = y - other.y;
		if(Math.abs(diffY) > 0.5 * Parameters.height){
			diffY = Parameters.height - Math.abs(diffY);
		}
		return Math.pow(Math.pow(diffX, 2) + Math.pow(diffY, 2), .5);
	}

	public void tick(ArrayList<Boid> boidList, ArrayList<Predator> predatorList, ArrayList<Obstacle> obstacleList){
		double distance;
		ArrayList<Boid> neighbors = new ArrayList<Boid>();
		for(Boid other: boidList){
			if(other != this){
				distance = distanceToBoid(other);
				if(distance < Parameters.boidLookRange){
					neighbors.add(other);
				}
			}
		}

		calculateSeparation(neighbors);
		calculateAlignment(neighbors);
		calculateCohesion(neighbors);

		updateLocation();

	}


	private void calculateSeparation(ArrayList<Boid> neighbors){
		Pair<Integer, Integer> vector;
		double sepForceX = 0., sepForceY = 0.;
		int diffX = 0, diffY = 0;
		double distance;
		for(Boid other: neighbors){
			distance = distanceToBoid(other);
			if(distance < Parameters.boidSeparationDistance){
				if(distance == 0.){
					continue;
				}
				vector = getVectorToBoid(other);
				diffX = -vector.getKey();
				diffY = -vector.getValue();
				sepForceX += diffX/(distance );
				sepForceY += diffY/(distance );
			}
		}

		velX += Parameters.separationWeight * sepForceX;
		velY += Parameters.separationWeight * sepForceY;
	}

	private void calculateAlignment(ArrayList<Boid> neighbors){
		if(neighbors.size() == 0){
			velX += Parameters.alignmentWeight * Math.cos(direction);
			velY += Parameters.alignmentWeight * Math.sin(direction);
			return;
		}
		double result = 0.0;
		double xPart = 0.0;
		double yPart = 0.0;
		for(Boid boid: neighbors){
			xPart += Math.cos(boid.direction);
			yPart += Math.sin(boid.direction);
		}
		result = Math.atan2(yPart, xPart);
		double difference = result - direction;
		if(difference < - Math.PI){
			difference += Math.PI * 2;
		}
		if(difference > Math.PI){
			difference -= Math.PI * 2;
		}
		direction += Math.signum(difference)*0.01;
		if(direction < - Math.PI){
			direction += Math.PI * 2;
		}
		if(direction > Math.PI){
			direction -= Math.PI * 2;
		}
		velX += Parameters.alignmentWeight * Math.cos(direction);
		velY += Parameters.alignmentWeight * Math.sin(direction);

	}

	private void calculateCohesion(ArrayList<Boid> neighbors){
		if(neighbors.size() == 0){
			return;
		}
		Pair<Integer, Integer> vector;
		double xPos = 0.0;
		double yPos = 0.0;
		for(Boid other: neighbors){
			vector = getVectorToBoid(other);
			xPos += vector.getKey();
			yPos += vector.getValue();
		}
		xPos /= neighbors.size();
		yPos /= neighbors.size();
		
		velX += Parameters.cohesionWeight * xPos;
		velY += Parameters.cohesionWeight * yPos;
	}

	private void updateLocation(){
		double speed = Math.sqrt(velX*velX + velY*velY);
		if(speed == .0){
			return;
		}
		velX /= speed;
		double p = random.nextDouble();
		int addition = 0;
		if( p < Math.abs(velX)){
			addition = new Double(Math.signum(velX)).intValue();
		}

		x = Math.floorMod((x + addition), Parameters.width);

		velY /= speed;
		addition = 0;
		//p = random.nextDouble();
		if( p < Math.abs(velY)){
			addition = new Double(Math.signum(velY)).intValue();
		}
		y = Math.floorMod((y + addition), Parameters.height);
		if(x < 0){
			System.out.println("too far left");
		}
		if(y < 0){
			System.out.println("too far up");
		}
		if(x > Parameters.width){
			System.out.println("too far right");
		}
		if(y > Parameters.height){
			System.out.println("too far down");
		}
	}
	
	private Pair<Integer, Integer> getVectorToBoid(Boid other){
		int diffX, diffY;
		diffX = x - other.x;
		if(diffX > Parameters.width /2){
			diffX -= Parameters.width;
		}
		else if(diffX < - Parameters.width /2){
			diffX += Parameters.width;
		}
		diffY = y - other.y;
		if(diffY > Parameters.height /2){
			diffY -= Parameters.height;
		}
		else if(diffY < - Parameters.height /2){
			diffY += Parameters.height;
		}
		return new Pair<Integer, Integer>(-diffX, -diffY);
	}
}



