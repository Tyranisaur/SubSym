package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import logic.Board;
import logic.CellType;
import logic.Direction;
import logic.Fitness;
import logic.Parameters;

public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7554184256341905418L;

	Board board;
	BufferedImage emptyImage;
	BufferedImage poisonImage;
	BufferedImage foodImage;
	BufferedImage upImage;
	BufferedImage downImage;
	BufferedImage leftImage;
	BufferedImage rightImage;
	Direction[] moves;
	int doneSteps;
	int boardIndex;
	
	public BoardPanel(Board board){
		super();
		boardIndex = 0;
		this.board = board;
		try{
			emptyImage = ImageIO.read(new File(".\\.\\resources\\images\\empty.png"));
			poisonImage = ImageIO.read(new File(".\\.\\resources\\images\\poison.png"));
			foodImage = ImageIO.read(new File(".\\.\\resources\\images\\food.png"));
			upImage = ImageIO.read(new File(".\\.\\resources\\images\\playerUP.png"));
			downImage = ImageIO.read(new File(".\\.\\resources\\images\\playerDOWN.png"));
			leftImage = ImageIO.read(new File(".\\.\\resources\\images\\playerLEFT.png"));
			rightImage = ImageIO.read(new File(".\\.\\resources\\images\\playerRIGHT.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		doneSteps = 0;
	}

	
	protected void paintComponent(Graphics g){
		CellType cell;
		BufferedImage image = null;
		int x, y;
		for(int i = 0; i < 100; i++){
			cell = board.getCell(i);
			switch(cell){
			case EMPTY:
				image = emptyImage;
				break;
			case FOOD:
				image = foodImage;
				break;
			case PLAYER:
				switch(board.getPlayerDirection()){
				case DOWN:
					image = downImage;
					break;
				case LEFT:
					image = leftImage;
					break;
				case NONE:
					break;
				case RIGHT:
					image = rightImage;
					break;
				case UP:
					image = upImage;
					break;
				default:
					break;
				
				}
				break;
			case POISON:
				image = poisonImage;
				break;
			default:
				break;
			
			}
			x = (i % 10) * image.getWidth();
			y = (i / 10) * image.getHeight();
			g.drawImage(image, x, y, null);
		}
	}
	public void step(){
		if(moves == null){
			moves = Fitness.getMoves(boardIndex);
		}
		board.move(moves[doneSteps]);
		repaint();
		doneSteps++;
		if(doneSteps == Parameters.stepsPerGeneration){
			doneSteps = 0;
			moves = null;
			try {
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			board = Fitness.getBoard(boardIndex);
			repaint();
		}
		
	}
	
	public void setBoard(Board b){
		board = b;
		moves = null;
		doneSteps = 0;
	}


	public void play() {
		if(moves == null){
			moves = Fitness.getMoves(boardIndex);
			doneSteps = 0;
		}
		
		for(int i = doneSteps; i < Parameters.stepsPerGeneration; i++){
			try {
				Thread.sleep(Parameters.millisPerVisualiztionStep);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			board.move(moves[i]);
			repaint();
		}
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board = Fitness.getBoard(boardIndex);
		repaint();
		moves = null;
		doneSteps = 0;
		
	}

}
