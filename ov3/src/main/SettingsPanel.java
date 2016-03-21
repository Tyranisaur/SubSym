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

	JButton stepButton;
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
		add(new JLabel("Scenario index"), gbc);
		scenarioButtons = new JRadioButton[Parameters.scanarios];
		for(int i = 0; i < Parameters.scanarios; i++){
			gbc.gridx = 0;
			gbc.gridy++;
			add(new JLabel("" + i), gbc);
			gbc.gridx = 1;
			scenarioButtons[i] = new JRadioButton();
			add(scenarioButtons[i], gbc);
			scenarioButtons[i].addActionListener(this);
			btnGroup.add(scenarioButtons[i]);

		}
		scenarioButtons[0].setSelected(true);
		gbc.gridy++;
		gbc.gridx = 0;
		stepButton = new JButton("Step");
		add(stepButton, gbc);
		stepButton.addActionListener(this);
		gbc.gridx = 1;
		playButton = new JButton("Play");
		add(playButton, gbc);
		playButton.addActionListener(this);

		gbc.gridy++;
		gbc.gridx = 0;

		add(new JLabel("Step delay"), gbc);

		gbc.gridx = 1;
		delaySpinner = new JSpinner();
		delaySpinner.setValue(1000);
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
				boardPanel.setBoard(Fitness.getBoard(i));
				boardPanel.repaint();
				boardPanel.boardIndex = i;

			}
		}
		if(e.getSource() == stepButton){
			boardPanel.step();
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
