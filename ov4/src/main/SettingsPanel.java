package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.EA;
import logic.Fitness;
import logic.Parameters;

public class SettingsPanel extends JPanel implements ActionListener, ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4074725417996834554L;
	ButtonGroup btnGroup;
	GridBagConstraints gbc;
	JRadioButton[] scenarioButtons;
	BoardPanel boardPanel;

	JButton startButton;
	JButton playButton;
	JSpinner delaySpinner;

	public SettingsPanel(BoardPanel boardPanel) {
		super();
		this.boardPanel = boardPanel;
		setLayout(new GridBagLayout());
		btnGroup = new ButtonGroup();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Scenario "), gbc);
		scenarioButtons = new JRadioButton[3];
			gbc.gridx = 0;
			gbc.gridy++;
			add(new JLabel("Standard"), gbc);
			gbc.gridx = 1;
			scenarioButtons[0] = new JRadioButton();
			add(scenarioButtons[0], gbc);
			scenarioButtons[0].addActionListener(this);
			btnGroup.add(scenarioButtons[0]);
			
			gbc.gridx = 0;
			gbc.gridy++;
			add(new JLabel("Pull"), gbc);
			gbc.gridx = 1;
			scenarioButtons[1] = new JRadioButton();
			add(scenarioButtons[1], gbc);
			scenarioButtons[1].addActionListener(this);
			btnGroup.add(scenarioButtons[1]);
			
			gbc.gridx = 0;
			gbc.gridy++;
			add(new JLabel("No wrap"), gbc);
			gbc.gridx = 1;
			scenarioButtons[2] = new JRadioButton();
			add(scenarioButtons[2], gbc);
			scenarioButtons[2].addActionListener(this);
			btnGroup.add(scenarioButtons[2]);

		
		scenarioButtons[0].setSelected(true);
		gbc.gridy++;
		gbc.gridx = 0;
		startButton = new JButton("Generate");
		add(startButton, gbc);
		startButton.addActionListener(this);
		gbc.gridx = 1;
		playButton = new JButton("Play");
		add(playButton, gbc);
		playButton.addActionListener(this);

		gbc.gridy++;
		gbc.gridx = 0;

		add(new JLabel("Step delay"), gbc);

		gbc.gridx = 1;
		delaySpinner = new JSpinner();
		delaySpinner.setValue(100);
		add(delaySpinner, gbc);
		delaySpinner.addChangeListener(this);


	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == delaySpinner){
			Parameters.millisPerVisualiztionStep = (int) delaySpinner.getValue();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < scenarioButtons.length; i++){
			if(e.getSource() == scenarioButtons[i]){
				//TODO handle different scenario

			}
		}
		if(e.getSource() == startButton){
			EA ea = new EA();
			ea.run();
		}
		else if(e.getSource() == playButton){
			new Thread(){
				public void run(){
					boardPanel.play();					
				}
			}.start();
		}

	}
}
