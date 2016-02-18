package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import logic.Boid;
import logic.Obstacle;
import logic.Parameters;
import logic.Predator;

public class BoidArea extends JPanel implements Runnable, PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<Boid> boidList;
	ArrayList<Predator> predatorList;
	ArrayList<Obstacle> obstacleList;

	public BoidArea(){
		super();
		setBackground(Color.gray);
		setPreferredSize(new Dimension(Parameters.width, Parameters.height));
		boidList = new ArrayList<Boid>();
		predatorList = new ArrayList<Predator>();
		obstacleList = new ArrayList<Obstacle>();
		for(int i = 0; i < 250; i++){
			boidList.add(new Boid());
		}

	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		AffineTransform xform;
		AffineTransformOp op;
		for(Boid boid: boidList){

			xform = new AffineTransform();
			xform.setToTranslation(.5*boid.image.getWidth(), .5*boid.image.getHeight());
			xform.rotate(boid.getDirection());
			xform.translate(-.5*boid.image.getWidth(), -.5*boid.image.getHeight());

			op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

			g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 , boid.getY()-boid.image.getHeight()/2, null);
			if(boid.getX() - boid.image.getHeight()/2 < 0){
				g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 + Parameters.width, boid.getY()-boid.image.getHeight()/2, null);
			}
			if(boid.getY() - boid.image.getHeight()/2 < 0){
				g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 , boid.getY()-boid.image.getHeight()/2 + Parameters.height, null);
			}
			if(boid.getY() - boid.image.getHeight()/2 < 0 && boid.getX() - boid.image.getHeight()/2 < 0){
				g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 + Parameters.width, boid.getY()-boid.image.getHeight()/2 + Parameters.height, null);
			}
			if( boid.getX() + boid.image.getWidth()/2 > Parameters.width){
				g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 - Parameters.width, boid.getY()-boid.image.getHeight()/2, null);
			}
			if(boid.getY() + boid.image.getHeight()/2 > Parameters.height){
				g.drawImage(op.filter(boid.image, null), boid.getX() -boid.image.getWidth()/2 , boid.getY()-boid.image.getHeight()/2 - Parameters.height, null);
			}
			if(boid.getX() + boid.image.getWidth()/2 > Parameters.width && boid.getY() + boid.image.getHeight()/2 > Parameters.height){
				g.drawImage(op.filter(boid.image, null), boid.getX() - boid.image.getWidth()/2 - Parameters.width, boid.getY()-boid.image.getHeight()/2 - Parameters.height, null);
			}
			if(boid.getX() - boid.image.getHeight()/2 < 0 && boid.getY() + boid.image.getHeight()/2 > Parameters.height){
				g.drawImage(op.filter(boid.image, null), boid.getX() + boid.image.getWidth()/2 + Parameters.width, boid.getY()-boid.image.getHeight()/2 - Parameters.height, null);
			}
			if(boid.getX() + boid.image.getWidth()/2 > Parameters.width && boid.getY() - boid.image.getHeight() < 0){
				g.drawImage(op.filter(boid.image, null), boid.getX() + boid.image.getWidth()/2 - Parameters.width, boid.getY()-boid.image.getHeight()/2 + Parameters.height, null);
			}

		}
		synchronized(predatorList){
			for(Predator pred: predatorList){
				xform = new AffineTransform();
				xform.setToTranslation(.5*pred.image.getWidth(), .5*pred.image.getHeight());
				xform.rotate(pred.getDirection());
				xform.translate(-.5*pred.image.getWidth(), -.5*pred.image.getHeight());

				op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

				g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 , pred.getY()-pred.image.getHeight()/2, null);
				if(pred.getX() - pred.image.getHeight()/2 < 0){
					g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 + Parameters.width, pred.getY()-pred.image.getHeight()/2, null);
				}
				if(pred.getY() - pred.image.getHeight()/2 < 0){
					g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 , pred.getY()-pred.image.getHeight()/2 + Parameters.height, null);
				}
				if(pred.getY() - pred.image.getHeight()/2 < 0 && pred.getX() - pred.image.getHeight()/2 < 0){
					g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 + Parameters.width, pred.getY()-pred.image.getHeight()/2 + Parameters.height, null);
				}
				if( pred.getX() + pred.image.getWidth()/2 > Parameters.width){
					g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 - Parameters.width, pred.getY()-pred.image.getHeight()/2, null);
				}
				if(pred.getY() + pred.image.getHeight()/2 > Parameters.height){
					g.drawImage(op.filter(pred.image, null), pred.getX() -pred.image.getWidth()/2 , pred.getY()-pred.image.getHeight()/2 - Parameters.height, null);
				}
				if(pred.getX() + pred.image.getWidth()/2 > Parameters.width && pred.getY() + pred.image.getHeight()/2 > Parameters.height){
					g.drawImage(op.filter(pred.image, null), pred.getX() - pred.image.getWidth()/2 - Parameters.width, pred.getY()-pred.image.getHeight()/2 - Parameters.height, null);
				}
				if(pred.getX() - pred.image.getHeight()/2 < 0 && pred.getY() + pred.image.getHeight()/2 > Parameters.height){
					g.drawImage(op.filter(pred.image, null), pred.getX() + pred.image.getWidth()/2 + Parameters.width, pred.getY()-pred.image.getHeight()/2 - Parameters.height, null);
				}
				if(pred.getX() + pred.image.getWidth()/2 > Parameters.width && pred.getY() - pred.image.getHeight() < 0){
					g.drawImage(op.filter(pred.image, null), pred.getX() + pred.image.getWidth()/2 - Parameters.width, pred.getY()-pred.image.getHeight()/2 + Parameters.height, null);
				}
			}
		}
		synchronized(obstacleList){
			for(Obstacle o: obstacleList){
				g.setColor(Color.black);
				g.fillOval(o.getX() - o.getRadius(), o.getY() - o.getRadius(), o.getRadius() * 2, o.getRadius() * 2);
				if(o.getX() - o.getRadius() < 0){
					g.fillOval(o.getX() - o.getRadius() + Parameters.width, o.getY() - o.getRadius(), o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getY() - o.getRadius() < 0){
					g.fillOval(o.getX() - o.getRadius(), o.getY() - o.getRadius() + Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getY() - o.getRadius() < 0 && o.getX() - o.getRadius() < 0){
					g.fillOval(o.getX() - o.getRadius() + Parameters.width, o.getY() - o.getRadius() + Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
				if( o.getX() + o.getRadius() > Parameters.width){
					g.fillOval(o.getX() - o.getRadius() - Parameters.width, o.getY() - o.getRadius(), o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getY() + o.getRadius() > Parameters.height){
					g.fillOval(o.getX() - o.getRadius(), o.getY() - o.getRadius() - Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getX() + o.getRadius() > Parameters.width && o.getY() + o.getRadius() > Parameters.height){
					g.fillOval(o.getX() - o.getRadius() - Parameters.width, o.getY() - o.getRadius() - Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getX() - o.getRadius() < 0 && o.getY() + o.getRadius() > Parameters.height){
					g.fillOval(o.getX() - o.getRadius() + Parameters.width, o.getY() - o.getRadius() - Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
				if(o.getX() + o.getRadius() > Parameters.width && o.getY() - o.getRadius() < 0){
					g.fillOval(o.getX() - o.getRadius() - Parameters.width, o.getY() - o.getRadius() + Parameters.height, o.getRadius() * 2, o.getRadius() * 2);
				}
			}
		}

	}
	private void addObstacle(){
		synchronized(obstacleList){
			obstacleList.add(new Obstacle());
		}

	}
	private void removeObstacles(){
		synchronized(obstacleList){

			obstacleList.clear();
		}


	}
	private void addPredator(){
		synchronized(predatorList){
			predatorList.add(new Predator());
		}

	}
	private void removePredators(){
		synchronized(predatorList){
			predatorList.clear();
		}

	}

	@Override
	public void run() {
		while(true){
			
			try {
				Thread.sleep(5);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(Boid boid: boidList){
				boid.tick(boidList, predatorList, obstacleList);
			}
			synchronized(predatorList){
				for(Predator predator: predatorList){
					predator.tick(boidList, predatorList, obstacleList);
				}
			}
			repaint();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if(e.getPropertyName().equals("Separation weight")){
			Parameters.separationWeight = (int) e.getNewValue();
		}
		if(e.getPropertyName().equals("Alignment weight")){
			Parameters.alignmentWeight = (int) e.getNewValue();
		}
		if(e.getPropertyName().equals("Cohesion weight")){
			Parameters.cohesionWeight = (int) e.getNewValue();
		}

		if(e.getPropertyName().equals("Add obstacle")){
			addObstacle();
		}
		if(e.getPropertyName().equals("Remove obstacles")){
			removeObstacles();
		}
		if(e.getPropertyName().equals("Add predator")){
			addPredator();
		}
		if(e.getPropertyName().equals("Remove predators")){
			removePredators();
		}
	}

}
