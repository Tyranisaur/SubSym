package logic;

import java.util.Random;

public class Board {

	private static Random random = 	new Random();
	private CellType[] board;
	private Direction playerDirection;
	private int playerIndex;
	
	public Board(){
		playerIndex = -1;
		board = new CellType[100];
		for(int i = 0; i < 100; i ++){
			if(random.nextDouble() < 0.33){
				board[i] = CellType.FOOD;
			}
			else if(random.nextDouble() < 0.33){
				board[i] = CellType.POISON;
			}
			else if( playerIndex == -1){
				board[i] = CellType.PLAYER;
				playerIndex = i;
				
			}
			else{
				board[i] = CellType.EMPTY;
			}
		}
		playerDirection = Direction.UP;
		
		//Check this because fuck you
		if(playerIndex == -1){
			playerIndex = random.nextInt(100);
			board[playerIndex] = CellType.PLAYER;
		}
	}
	/**
	 * Sensor data represented as an array
	 * Array has three elements
	 * <br>
	 * index 0 is the cell in the left direction
	 * <br>
	 * index 1 is the cell in the forward direction
	 * <br>
	 * index 2 is the cell in the right direction
	 * @return CellType[] sensorData
	 */
	
	public CellType[] sensorInput(){
		CellType[] ret = new CellType[3];
		int x = playerIndex % 10;
		int y = playerIndex / 10;
		
		switch(playerDirection){
		case DOWN:
			ret[0] = board[y * 10 + (x + 1) % 10];
			ret[1] = board[ (y + 1) % 10 * 10 + x ];
			ret[2] = board[y * 10 + (x - 1) % 10];
			break;
		case LEFT:
			ret[0] = board[ (y + 1) % 10 * 10 + x ];
			ret[1] = board[y * 10 + (x - 1) % 10];
			ret[2] = board[ (y - 1) % 10 * 10 + x];
			break;
		case RIGHT:
			ret[0] = board[ (y - 1) % 10 * 10 + x];
			ret[1] = board[y * 10 + (x + 1) % 10];
			ret[2] = board[ (y + 1) % 10 * 10 + x ];
			break;
		case UP:
			ret[0] = board[y * 10 + (x - 1) % 10];
			ret[1] = board[ (y - 1) % 10 * 10 + x];
			ret[2] = board[y * 10 + (x + 1) % 10];
			break;
		default:
			break;
		
		}
		return ret;
	}
	
	/**
	 * Moves the player in the given direction and returns the type of the cell moved into
	 * @param 
	 * @return CellType cell
	 */
	public CellType move(Direction d){
		int x = playerIndex % 10;
		int y = playerIndex / 10;
		
		switch(d){
		case DOWN:
			y = (y + 1) % 10;
			break;
		case LEFT:
			x = (x - 1) % 10;
			break;
		case RIGHT:
			x = (x + 1) % 10;
			break;
		case UP:
			x = (x - 1) % 10;
			break;
		default:
			break;
		
		}
		playerDirection = d;
		int newIndex = y * 10 + x;
		CellType ret = board[newIndex];
		board[playerIndex] = CellType.EMPTY;
		board[newIndex] = CellType.PLAYER;
		playerIndex = newIndex;
		return ret;
	}
	

	
	public Board clone(){
		Board ret = null;
		try {
			ret = (Board) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ret.board = board.clone();
		return ret;
	}
	public CellType getCell(int index){
		return board[index];
	}
	public Direction getPlayerDirection(){
		return playerDirection;
	}
}
