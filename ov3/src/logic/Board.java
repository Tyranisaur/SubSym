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
			ret[2] = board[y * 10 + Math.floorMod((x - 1) , 10)];
			break;
		case LEFT:
			ret[0] = board[ (y + 1) % 10 * 10 + x ];
			ret[1] = board[y * 10 + Math.floorMod((x - 1) , 10)];
			ret[2] = board[ Math.floorMod((y - 1) , 10) * 10 + x];
			break;
		case RIGHT:
			ret[0] = board[ Math.floorMod((y - 1) , 10) * 10 + x];
			ret[1] = board[y * 10 + (x + 1) % 10];
			ret[2] = board[ (y + 1) % 10 * 10 + x ];
			break;
		case UP:
			ret[0] = board[y * 10 + Math.floorMod((x - 1) , 10)];
			ret[1] = board[ Math.floorMod((y - 1) , 10) * 10 + x];
			ret[2] = board[y * 10 + (x + 1) % 10];
			break;
		default:
			break;
		
		}
		return ret;
	}
	
	/**
	 * The player moves in the given direction.
	 * The direction is relative to the player's orientation.
	 * @param 
	 * @return CellType cell
	 */
	public CellType move(Direction d){
		int x = playerIndex % 10;
		int y = playerIndex / 10;
		
		switch(playerDirection){
		case DOWN:
			if(d == Direction.LEFT){
				x = (x + 1) % 10;
				playerDirection = Direction.RIGHT;
			}
			else if(d == Direction.UP){
				y = (y + 1) % 10;
				playerDirection = Direction.DOWN;
			}
			else if(d == Direction.RIGHT){
				x = Math.floorMod((x - 1) , 10);
				playerDirection = Direction.LEFT;
			}
			break;
		case LEFT:
			if(d == Direction.LEFT){
				y = (y + 1) % 10;
				playerDirection = Direction.DOWN;
			}
			else if(d == Direction.UP){
				x = Math.floorMod((x - 1) , 10);
				playerDirection = Direction.LEFT;
			}
			else if(d == Direction.RIGHT){
				y = Math.floorMod((y - 1) , 10);
				playerDirection = Direction.UP;
			}
			break;
		case RIGHT:
			if(d == Direction.LEFT){
				y = Math.floorMod((y - 1) , 10);
				playerDirection = Direction.UP;
			}
			else if(d == Direction.UP){
				x = (x + 1) % 10;
				playerDirection = Direction.RIGHT;
			}
			else if(d == Direction.RIGHT){
				y = (y + 1) % 10;
				playerDirection = Direction.DOWN;
			}
			break;
		case UP:
			if(d == Direction.LEFT){
				x = Math.floorMod((x - 1) , 10);
				playerDirection = Direction.LEFT;
			}
			else if(d == Direction.UP){
				y = Math.floorMod((y - 1) , 10);
				playerDirection = Direction.UP;
			}
			else if(d == Direction.RIGHT){
				x = (x + 1) % 10;
				playerDirection = Direction.RIGHT;
			}
			break;
		default:
			break;
		
		}
		int newIndex = y * 10 + x;
		CellType ret = board[newIndex];
		if(d == Direction.NONE){
			ret = CellType.EMPTY;
		}
		board[playerIndex] = CellType.EMPTY;
		board[newIndex] = CellType.PLAYER;
		playerIndex = newIndex;
		return ret;
	}
	

	
	public Board clone(){
		Board ret = new Board();
		ret.playerDirection = playerDirection;
		ret.playerIndex = playerIndex;
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
