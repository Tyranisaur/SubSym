package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.imageio.ImageIO;

import javafx.util.Pair;

public class Predator extends Boid {

	protected static String imageFilePath = ".\\.\\resources\\images\\Predator.png";

	public Predator(){
		super();
		try{
			image = ImageIO.read(new File(imageFilePath));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void tick(ArrayList<Boid> boidList, ArrayList<Predator> predatorList, ArrayList<Obstacle> obstacleList){
		followBoidGroup(boidList);
		@SuppressWarnings("unchecked")
		ArrayList<Boid> list = (ArrayList<Boid>) predatorList.clone();
		calculateSeparation(list);
		avoidObstacles(obstacleList);
		direction = Math.atan2(velY, velX);

		updateLocation();
	}

	private void followBoidGroup(ArrayList<Boid> boidList) {
		ArrayList<Boid> searchSpace = new ArrayList<Boid>();
		HashSet<Boid> biggestGroup = new HashSet<Boid>();
		HashSet<Boid> secondGroup;
		searchSpace.addAll(boidList);
		Boid boid;
		for(int i = searchSpace.size() - 1; i >=0; i--){
			boid = boidList.get(i);
			if(!searchSpace.contains(boid)){
				continue;
			}
			secondGroup = boid.recursiveNeighbors(boidList, null);
			if(secondGroup.size() > biggestGroup.size()){
				biggestGroup = secondGroup;
			}
			searchSpace.removeAll(secondGroup);

		}
		Pair<Integer, Integer> vector;
		double xPos = 0.0;
		double yPos = 0.0;
		for(Boid other: biggestGroup){
			vector = getVectorToObject(other);
			xPos += vector.getKey();
			yPos += vector.getValue();
		}
		xPos /= biggestGroup.size();
		yPos /= biggestGroup.size();

		velX += xPos;
		velY += yPos;


	}


	private void updateLocation(){
		double speed = Math.sqrt(velX*velX + velY*velY);
		if(speed == .0){
			return;
		}
		velX /= speed;
		velX *= 2;
		double p = random.nextDouble() * 2;
		int addition = 0;
		if( p < Math.abs(velX)){
			addition = new Double(Math.signum(velX) * 2 ).intValue();
		}

		x = Math.floorMod((x + addition), Parameters.width);

		velY /= speed;
		velY *= 2;
		addition = 0;
		//p = random.nextDouble();
		if( p < Math.abs(velY)){
			addition = new Double(Math.signum(velY) * 2 ).intValue();
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
}
