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

		}
		for(Obstacle o: obstacleList){
			g.drawOval(o.getX() - o.getRadius(), o.getY() - o.getRadius(), o.getRadius() * 2, o.getRadius() * 2);
		}

	}
	private void addObstacle(){
		obstacleList.add(new Obstacle());
	}
	private void removeObstacles(){
		obstacleList.clear();
	}
	private void addPredator(){
		//TODO
	}
	private void removePredators(){
		//TODO
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(Boid boid: boidList){
				boid.tick(boidList, predatorList, obstacleList);
			}
			for(Predator predator: predatorList){
				predator.tick(boidList, obstacleList);
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
