package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
	
	public void tick(ArrayList<Boid> boidList){
		//TODO
		
	}
}
