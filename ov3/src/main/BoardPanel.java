package main;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import logic.Board;
import logic.CellType;
import logic.Direction;
import logic.Fitness;

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
	
	public BoardPanel(Board board){
		super();
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


	public void play() throws InterruptedException {
		Direction[] moves = Fitness.getMoves();
		
		for(Direction move: moves){
			Thread.sleep(1000);
			board.move(move);
			repaint();
		}
		
	}

}
