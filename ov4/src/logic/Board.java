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
		trackerX = random.nextInt(Parameters.gametype == GameType.NOWRAP ? 30 - 5 :30);
		currentObject = 0;
		objectY = 0;
		objectSize = new int[Parameters.objects];
		for(int i = 0; i < Parameters.objects; i++){
			objectSize[i] = random.nextInt(6) + 1;
		}
		objectX = new int[Parameters.objects];
		for(int i = 0; i < Parameters.objects; i++){
			objectX[i] = random.nextInt(Parameters.gametype == GameType.NOWRAP ? 30 - objectSize[i] :30);
		}
	}
	public int getPlayerIndex(){
		return trackerX;
	}

	public SensorType[] sensorInput(){

		SensorType[] ret = new SensorType[Parameters.sensorLength];

		int position;
		int diff;
		for(int i = 0; i < 5; i++){
			position = (trackerX + i) % 30;
			diff = Math.floorMod(position - objectX[currentObject], 30);

			if(diff < objectSize[currentObject]){
				ret[i] = SensorType.OBJECT;
			}
			else{
				ret[i] = SensorType.EMPTY;
			}
		}
		if(ret.length == 7){
			ret[5] = trackerX == 0 ? SensorType.WALL : SensorType.EMPTY;
			ret[6] = trackerX == 25 ? SensorType.WALL : SensorType.EMPTY;
		}

		return ret;
	}




	public Impact move(int move){

		if(move != 42){
			trackerX += move;
		}
		else{
			objectY = 14;
		}
		if(Parameters.gametype == GameType.NOWRAP){

			if(trackerX < 0){
				trackerX = 0;
			}
			if(trackerX > 25){
				trackerX = 25;
			}
		}
		else{
			trackerX = Math.floorMod(trackerX, 30);
		}
		

		if(objectY < 14){
			objectY++;
			return Impact.VOID;
		}

		SensorType[] sensorData = sensorInput();


		int collisions = 0;
		for(int i = 0; i < 5; i++){
			if(sensorData[i] == SensorType.OBJECT){
				collisions++;
			}
		}
		if(collisions == 0){
			if(objectSize[currentObject] > 4){
				objectY = 0;
				currentObject++;
				return Impact.AVOIDBIG;
			}
			else{
				objectY = 0;
				currentObject++;
				return Impact.AVOIDSMALL;
			}
		}
		if(objectSize[currentObject] > 4){
			if(collisions == 5){
				objectY = 0;
				currentObject++;
				return Impact.HIT;
			}
			if(collisions < 5){
				objectY = 0;
				currentObject++;
				return Impact.BIGPART;
			}
		}
		if(collisions < objectSize[currentObject]){
			objectY = 0;
			currentObject++;
			return Impact.SMALLPART;
		}
		if(collisions == objectSize[currentObject]){
			objectY = 0;
			currentObject++;
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
