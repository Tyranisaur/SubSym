package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.util.Pair;

public class Boid implements Coordinates{

	protected static Random random = new Random();
	protected int x, y;
	protected double velX, velY;
	protected double direction;
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

	protected double distanceToObject(Coordinates other){
		int diffX = x - other.getX();
		if(Math.abs(diffX) > 0.5 * Parameters.width){
			diffX = Parameters.width - Math.abs(diffX);
		}
		int diffY = y - other.getY();
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
				distance = distanceToObject(other);
				if(distance < Parameters.boidLookRange){
					neighbors.add(other);
				}
			}
		}

		calculateSeparation(neighbors);
		calculateAlignment(neighbors);
		calculateCohesion(neighbors);
		avoidObstacles(obstacleList);
		avoidPredators(predatorList);

		updateLocation();

	}



	private void avoidPredators(ArrayList<Predator> predatorList) {

		int xPart, yPart;
		Pair<Integer, Integer> vectorToPredator;
		for(Predator predator: predatorList){
			if(distanceToObject(predator) < Parameters.boidLookRange){

				vectorToPredator = getVectorToObject(predator);
				xPart = -vectorToPredator.getKey();
				yPart = -vectorToPredator.getValue();

				velX = xPart;
				velY = yPart;
				direction = Math.atan2(yPart, xPart);

			}
		}

	}

	protected void calculateSeparation(ArrayList<Boid> neighbors){
		Pair<Integer, Integer> vector;
		double sepForceX = 0., sepForceY = 0.;
		int diffX = 0, diffY = 0;
		double distance;
		for(Boid other: neighbors){
			distance = distanceToObject(other);
			if(distance < Parameters.boidSeparationDistance){
				if(distance == 0.){
					continue;
				}
				vector = getVectorToObject(other);
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
		double difference = normalizeAngle(result - direction);
		direction += Math.signum(difference)*0.01;
		direction = normalizeAngle(direction);
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
			vector = getVectorToObject(other);
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

	protected Pair<Integer, Integer> getVectorToObject(Coordinates other){
		int diffX, diffY;
		diffX = x - other.getX();
		if(diffX > Parameters.width /2){
			diffX -= Parameters.width;
		}
		else if(diffX < - Parameters.width /2){
			diffX += Parameters.width;
		}
		diffY = y - other.getY();
		if(diffY > Parameters.height /2){
			diffY -= Parameters.height;
		}
		else if(diffY < - Parameters.height /2){
			diffY += Parameters.height;
		}
		return new Pair<Integer, Integer>(-diffX, -diffY);
	}


	protected void avoidObstacles(ArrayList<Obstacle> obstacleList) {
		double distance;
		int diffX, diffY;
		Pair<Integer, Integer> vectorToObstacle;
		synchronized(obstacleList){
			for(Obstacle o: obstacleList){
				distance = distanceToObject(o);
				if(distance < + image.getHeight()/2 + o.getRadius()){

				}
				else if(distance - image.getHeight()/2 - o.getRadius() < Parameters.boidLookRange / 4){
					vectorToObstacle = getVectorToObject(o);
					diffX = vectorToObstacle.getKey();
					diffY = vectorToObstacle.getValue();


					velX += Parameters.obstacleAvoidanceWeight * -diffX/distance;
					velY += Parameters.obstacleAvoidanceWeight * -diffY/distance;

				}

			}
		}


	}

	private double normalizeAngle(double a){
		double ret = a;
		while( ret <= Math.PI){
			ret += Math.PI * 2;
		}
		while(ret > Math.PI){
			ret -= Math.PI * 2;
		}
		return ret;

	}

	public ArrayList<Boid> getNeigbors(ArrayList<Boid> boidList){
		ArrayList<Boid> ret = new ArrayList<Boid>();
		for(Boid other: boidList){
			if(other != this){
				if(distanceToObject(other) < Parameters.boidLookRange){
					ret.add(other);
				}
			}
		}
		return ret;
	}

	public HashSet<Boid> recursiveNeighbors(ArrayList<Boid> boidList, HashSet<Boid> set){
		if(set == null){
			set = new HashSet<Boid>();

		}
		set.add(this);
		for(Boid other: boidList){
			if(!set.contains(other)){
				if(distanceToObject(other) < Parameters.boidLookRange){
					if(other != this){
						other.recursiveNeighbors(boidList, set);
					}
				}
			}
		}
		return set;
	}
}

