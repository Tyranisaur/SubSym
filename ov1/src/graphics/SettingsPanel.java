package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.Parameters;


public class SettingsPanel extends JPanel implements ActionListener, ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton addObstacleButton;
	private JButton removeObstaclesButton;
	private JButton addPredatorButton;
	private JButton removePredatorsButton;
	
	private JSlider separationSlider;
	private JSlider alignmentSlider;
	private JSlider cohesionSlider;
	private PropertyChangeSupport pcs;
	
	
	public SettingsPanel(){
		super();
		
		pcs = new PropertyChangeSupport(this);
		
		addObstacleButton = new JButton("Add obstacle");
		removeObstaclesButton = new JButton("Revove obstacles");
		addPredatorButton = new JButton("Add predator");
		removePredatorsButton = new JButton("Remove predators");
		addObstacleButton.addActionListener(this);
		removeObstaclesButton.addActionListener(this);
		addPredatorButton.addActionListener(this);
		removePredatorsButton.addActionListener(this);
		
		separationSlider = new JSlider(0, 100, Parameters.separationWeight);
		alignmentSlider = new JSlider(0, 100, Parameters.alignmentWeight);
		cohesionSlider = new JSlider(0, 100, Parameters.cohesionWeight);
		separationSlider.addChangeListener(this);
		alignmentSlider.addChangeListener(this);
		cohesionSlider.addChangeListener(this);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(addObstacleButton, gbc);
		gbc.gridx = 1;
		add(removeObstaclesButton, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		add(addPredatorButton, gbc);
		gbc.gridx = 1;
		add(removePredatorsButton, gbc);
		gbc.gridy = 2;
		gbc.gridx = 0;
		add(new JLabel("Separation"), gbc);
		gbc.gridx = 1;
		add(separationSlider, gbc);
		gbc.gridy = 3;
		gbc.gridx = 0;
		add(new JLabel("Alignment"), gbc);
		gbc.gridx = 1;
		add(alignmentSlider, gbc);
		gbc.gridy = 4;
		gbc.gridx = 0;
		add(new JLabel("Cohesion"), gbc);
		gbc.gridx = 1;
		add(cohesionSlider, gbc);
		
	}

	@Override
	public void stateChanged(ChangeEvent e){
		if(e.getSource() == separationSlider){
			pcs.firePropertyChange("Separation weight", 0, separationSlider.getValue());
		}
		if(e.getSource() == alignmentSlider){
			pcs.firePropertyChange("Alignment weight", 0, alignmentSlider.getValue());
		}
		if(e.getSource() == cohesionSlider){
			pcs.firePropertyChange("Cohesion weight", 0, cohesionSlider.getValue());
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addObstacleButton){
			pcs.firePropertyChange("Add obstacle", 0, 1);
		}
		if(e.getSource() == removeObstaclesButton){
			pcs.firePropertyChange("Remove obstacles", 1, 0);
		}
		if(e.getSource() == addPredatorButton){
			pcs.firePropertyChange("Add predator", 0, 1);
		}
		if(e.getSource() == removePredatorsButton){
			pcs.firePropertyChange("Remove predators", 1, 0);
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener){
		pcs.removePropertyChangeListener(listener);
	}
}
