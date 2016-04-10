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
import logic.GameType;
import logic.Parameters;

public class SettingsPanel extends JPanel implements ActionListener, ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4074725417996834554L;
	ButtonGroup btnGroup;
	GridBagConstraints gbc;
	JRadioButton standardButton;
	JRadioButton pullButton;
	JRadioButton nowrapButton;
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
		gbc.gridx = 0;
		gbc.gridy++;
		add(new JLabel("Standard"), gbc);
		gbc.gridx = 1;
		standardButton = new JRadioButton();
		add(standardButton, gbc);
		standardButton.addActionListener(this);
		btnGroup.add(standardButton);

		gbc.gridx = 0;
		gbc.gridy++;
		add(new JLabel("Pull"), gbc);
		gbc.gridx = 1;
		pullButton = new JRadioButton();
		add(pullButton, gbc);
		pullButton.addActionListener(this);
		btnGroup.add(pullButton);

		gbc.gridx = 0;
		gbc.gridy++;
		add(new JLabel("No wrap"), gbc);
		gbc.gridx = 1;
		nowrapButton = new JRadioButton();
		add(nowrapButton, gbc);
		nowrapButton.addActionListener(this);
		btnGroup.add(nowrapButton);


		standardButton.setSelected(true);
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
		if(e.getSource() == standardButton){
			Parameters.gametype = GameType.STANDARD;
		}
		if(e.getSource() == pullButton){
			Parameters.gametype = GameType.PULL;
		}
		if(e.getSource() == nowrapButton){
			Parameters.gametype = GameType.NOWRAP;
		}

		if(e.getSource() == startButton){
			if(Parameters.gametype == GameType.STANDARD){
				Parameters.objects = 41;
				Parameters.length = 34;
				Parameters.sensorLength = 5;
			}
			if(Parameters.gametype == GameType.PULL){
				Parameters.objects = 600;
				Parameters.length = 44;
				Parameters.sensorLength = 5;
			}
			if(Parameters.gametype == GameType.NOWRAP){
				Parameters.objects = 41;
				Parameters.length = 38;
				Parameters.sensorLength = 7;
			}
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
