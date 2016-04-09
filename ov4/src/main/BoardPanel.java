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
import logic.NeuralNetwork;
import logic.Parameters;

public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7554184256341905418L;

	Board board;
	BufferedImage objectImage;
	BufferedImage trackerImage;
	BufferedImage emptyImage;


	public BoardPanel(Board board){
		super();
		this.board = board;
		try{

			objectImage = ImageIO.read(new File(".\\.\\resources\\images\\objectpart.png"));
			trackerImage = ImageIO.read(new File(".\\.\\resources\\images\\trackerpart.png"));
			emptyImage = ImageIO.read(new File(".\\.\\resources\\images\\empty.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	protected void paintComponent(Graphics g){
		int x, y;
		for(int i = 0; i < 450; i++){

			x = (i % 30) * emptyImage.getWidth();
			y = (i / 30) * emptyImage.getHeight();
			g.drawImage(emptyImage, x, y, null);
		}
		int trackerIndex = board.getPlayerIndex();
		for(int i = trackerIndex; i < trackerIndex + 5; i++){
			g.drawImage(trackerImage, (i % 30) *trackerImage.getWidth(), 14 * trackerImage.getHeight(), null);
		}
		int[] objectData = board.getObjectData();
		for(int i = objectData[0]; i < objectData[0] + objectData[2]; i++){
			g.drawImage(objectImage, (i % 30) *trackerImage.getWidth(), objectData[1] * trackerImage.getHeight(), null);
		}

	}



	public void play() {
		board = Fitness.getBoard();
		NeuralNetwork network;
		int move;
		CellType[] sensorData;
		network = new NeuralNetwork(Fitness.getBest());
		for(int step = 0; step < Parameters.stepsPerGeneration; step++){
			sensorData = board.sensorInput();
			move = network.caluculateMove(sensorData);
			System.out.println(move);
			board.move(move);
			try {
				Thread.sleep(Parameters.millisPerVisualiztionStep);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}


	}

}
