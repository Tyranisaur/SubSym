package logic;

import java.util.Random;


public class Board {

	private static Random random = 	new Random();
	private int trackerX;
	private int[] objectX;
	private int objectY;
	private int[] objectSize;
	private int currentObject;

	public Board(){
		trackerX = random.nextInt(30);
		currentObject = 0;
		objectX = new int[43];
		for(int i = 0; i < 43; i++){
			objectX[i] = random.nextInt(30);
		}
		objectY = 0;
		objectSize = new int[43];
		for(int i = 0; i < 43; i++){
			objectSize[i] = random.nextInt(6) + 1;
		}
	}
	public int getPlayerIndex(){
		return trackerX;
	}

	public CellType[] sensorInput(){
		if(objectY == 14){
			objectY = 0;
			currentObject++;
		}
		CellType[] ret = new CellType[5];

		int position = trackerX;
		int diff;
		for(int i = 0; i < 5; i++){
			position = (position + 1) % 30;
			diff = Math.floorMod(position - objectX[currentObject], 30);

			if(diff < objectSize[currentObject]){
				ret[i] = CellType.OBJECT;
			}
			else{
				ret[i] = CellType.EMPTY;
			}

		}

		return ret;
	}




	public Impact move(int move){

		
		trackerX += move;
		trackerX = Math.floorMod(trackerX, 30);

		objectY++;
		if(objectY < 14){
			return Impact.VOID;
		}
		CellType[] sensorData = sensorInput();
		int collisions = 0;
		for(int i = 0; i < 5; i++){
			if(sensorData[i] == CellType.OBJECT){
				collisions++;
			}
		}
		if(collisions == 0){
			if(objectSize[currentObject] > 4){
				return Impact.AVOIDBIG;
			}
			else{
				return Impact.AVOIDSMALL;
			}
		}
		if(objectSize[currentObject] > 4){
			return Impact.HIT;
			
		}
		if(collisions < objectSize[currentObject]){
			return Impact.PART;
		}
		if(collisions == objectSize[currentObject]){
			return Impact.CATCH;
		}
		System.out.println("this should never happen");
		return Impact.VOID;
	}



	public Board clone(){
		Board ret = new Board();
		ret.trackerX = trackerX;
		ret.objectX = objectX;
		ret.objectY = objectY;
		ret.objectSize = objectSize;
		return ret;
	}
/**
 * [xpos, ypos, width]
 * @return
 */
	public int[] getObjectData(){
		int[] ret = {objectX[currentObject], objectY, objectSize[currentObject]};
		return ret;
	}	
}
