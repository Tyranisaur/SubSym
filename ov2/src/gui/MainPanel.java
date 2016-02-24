package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.EA;
import logic.Parameters;
import logic.Problem;
import logic.SelectionMode;

public class MainPanel extends JPanel implements ActionListener, ChangeListener, DocumentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton startButton;
	JRadioButton oneMaxButton;
	JRadioButton lolzButton;
	JRadioButton localSurpSeqButton;
	JRadioButton globalSurpSeqButton;
	ButtonGroup problemgroup;

	JTextField oneMaxTarget;
	JSpinner lolzCapSpinner;
	JSpinner lengthSpinner;
	JTextField crossOverField;
	JTextField mutationField;
	JSpinner birthSpinner;
	JSpinner adultsSpinner;
	JSpinner parentsSpinner;
	ButtonGroup selectionGroup;

	private JRadioButton fitnessScalingButton;

	private JRadioButton sigmaButton;

	private JRadioButton tournamentButton;

	private JRadioButton uniformButton;

	private JSpinner symbolsSpinner;

	Problem problem = Problem.ONEMAX;
	SelectionMode selectionMode = SelectionMode.FITNESS;

	private JSpinner tournamentSizeSpinner;

	private JTextField tournamentPValue;

	private JCheckBox survivalCheckBox;
	
	private Thread thread;
	EA ea;

	public MainPanel(){
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		startButton = new JButton("Start");
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(startButton, gbc);
		startButton.addActionListener(this);

		problemgroup = new ButtonGroup();
		oneMaxButton = new JRadioButton("One Max");
		problemgroup.add(oneMaxButton);
		gbc.gridy = 1;
		add(oneMaxButton, gbc);
		lolzButton = new JRadioButton("LOLZ");
		problemgroup.add(lolzButton);
		gbc.gridy = 2;
		add(lolzButton, gbc);
		localSurpSeqButton = new JRadioButton("Local surprising sequence");
		problemgroup.add(localSurpSeqButton);
		gbc.gridy = 3;
		add(localSurpSeqButton, gbc);
		globalSurpSeqButton = new JRadioButton("Global Surprising sequence");
		problemgroup.add(globalSurpSeqButton);
		gbc.gridy = 4;
		add(globalSurpSeqButton, gbc);
		oneMaxButton.addChangeListener(this);
		lolzButton.addChangeListener(this);
		localSurpSeqButton.addChangeListener(this);
		globalSurpSeqButton.addChangeListener(this);
		oneMaxButton.setSelected(true);

		JLabel oneMaxTargetLabel = new JLabel("One Max target");
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(oneMaxTargetLabel, gbc);
		oneMaxTarget = new JTextField(20);
		oneMaxTarget.getDocument().addDocumentListener(this);
		oneMaxTarget.setEditable(true);
		gbc.gridx = 2;
		add(oneMaxTarget, gbc);

		JLabel lolzCapLabel = new JLabel("LOLZ z cap");
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(lolzCapLabel, gbc);
		lolzCapSpinner = new JSpinner();
		lolzCapSpinner.setValue(10);
		gbc.gridx = 2;
		add(lolzCapSpinner, gbc);
		lolzCapSpinner.addChangeListener(this);


		JLabel lengthLabel = new JLabel("Problem length");
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(lengthLabel, gbc);
		lengthSpinner = new JSpinner();
		lengthSpinner.setValue(24);
		gbc.gridx = 2;
		add(lengthSpinner, gbc);
		lengthSpinner.addChangeListener(this);


		JLabel crossOverLabel = new JLabel("Cross over probablity");
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(crossOverLabel, gbc);
		crossOverField = new JTextField(10);
		crossOverField.getDocument().addDocumentListener(this);
		crossOverField.setEditable(true);
		gbc.gridx = 2;
		add(crossOverField, gbc);
		crossOverField.setText("0.5");

		JLabel mutationLabel = new JLabel("Mutation probablity");
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(mutationLabel, gbc);
		mutationField = new JTextField(10);
		mutationField.getDocument().addDocumentListener(this);
		mutationField.setEditable(true);
		gbc.gridx = 2;
		add(mutationField, gbc);
		mutationField.setText(Double.toString(1./(int)lengthSpinner.getValue()));

		JLabel birthLabel = new JLabel("Birth rate");
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(birthLabel, gbc);
		birthSpinner = new JSpinner();
		gbc.gridx = 1;
		add(birthSpinner, gbc);
		birthSpinner.setValue(10);
		birthSpinner.addChangeListener(this);

		JLabel adultsLabel = new JLabel("Adults");
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(adultsLabel, gbc);
		adultsSpinner = new JSpinner();
		gbc.gridx = 1;
		add(adultsSpinner, gbc);
		adultsSpinner.setValue(10);
		adultsSpinner.addChangeListener(this);


		
		

		gbc.gridx = 2;
		selectionGroup = new ButtonGroup();
		fitnessScalingButton = new JRadioButton("Fitness proportionate");
		selectionGroup.add(fitnessScalingButton);
		gbc.gridy = 5;
		add(fitnessScalingButton, gbc);
		sigmaButton = new JRadioButton("Sigma scaling");
		selectionGroup.add(sigmaButton);
		gbc.gridy = 6;
		add(sigmaButton, gbc);
		tournamentButton = new JRadioButton("Torunament selection");
		selectionGroup.add(tournamentButton);
		gbc.gridy = 7;
		add(tournamentButton, gbc);
		uniformButton = new JRadioButton("Uniform Selection");
		selectionGroup.add(uniformButton);
		gbc.gridy = 8;
		add(uniformButton, gbc);

		JLabel symbolsLabel = new JLabel("Symbols");
		gbc.gridx = 0;
		gbc.gridy = 7;
		add(symbolsLabel, gbc);
		symbolsSpinner = new JSpinner();
		gbc.gridx = 1;
		add(symbolsSpinner, gbc);
		symbolsSpinner.setValue(10);
		symbolsSpinner.addChangeListener(this);
		
		JLabel tournamentSizeLabel = new JLabel("Tournament size");
		gbc.gridx = 0;
		gbc.gridy = 8;
		add(tournamentSizeLabel, gbc);
		tournamentSizeSpinner = new JSpinner();
		gbc.gridx = 1;
		add(tournamentSizeSpinner, gbc);
		tournamentSizeSpinner.setValue(10);
		tournamentSizeSpinner.addChangeListener(this);
		
		JLabel tournamentPValueLabel = new JLabel("Tournament probability");
		gbc.gridx = 0;
		gbc.gridy = 9;
		add(tournamentPValueLabel, gbc);
		tournamentPValue = new JTextField(4);
		tournamentPValue.getDocument().addDocumentListener(this);
		tournamentPValue.setEditable(true);
		gbc.gridx = 1;
		add(tournamentPValue, gbc);
		tournamentPValue.setText("0.05");




		fitnessScalingButton.addChangeListener(this);
		sigmaButton.addChangeListener(this);
		tournamentButton.addChangeListener(this);
		uniformButton.addChangeListener(this);
		fitnessScalingButton.setSelected(true);

		survivalCheckBox = new JCheckBox("Adults survive");
		gbc.gridx = 2;
		gbc.gridy = 9;
		add(survivalCheckBox, gbc);
		survivalCheckBox.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == survivalCheckBox){
			if(!survivalCheckBox.isSelected()){
				if((int)birthSpinner.getValue() < (int)adultsSpinner.getValue()){
					birthSpinner.setValue(adultsSpinner.getValue());
				}
			}
		}
		if(e.getSource() == startButton){
			if(ea != null && ea.running){
				ea.stop();
				startButton.setText("Start");
				return;
			}

			Parameters.birthRate = (int) birthSpinner.getValue();
			Parameters.adults = (int) adultsSpinner.getValue();
			Parameters.adultsSurvive = survivalCheckBox.isSelected();
			Parameters.symbols = (int) symbolsSpinner.getValue();
			Parameters.lolzCap = (int) lolzCapSpinner.getValue();

			String s = oneMaxTarget.getText();
			Parameters.oneMaxTarget = s.equals("") ? null : s;
			for(int i = 0; i < s.length(); i++){
				if( !(s.charAt(i) == '1' || s.charAt(i) == '0')){
					Parameters.oneMaxTarget = null;
				}
			}
			Parameters.length = (int)lengthSpinner.getValue();
			Parameters.crossOverRate = Double.parseDouble(crossOverField.getText());
			Parameters.mutationRate = Double.parseDouble(mutationField.getText());
			Parameters.tournamentPValue = Double.parseDouble(tournamentPValue.getText());
			Parameters.tournamentSize = (int) tournamentSizeSpinner.getValue();
			Parameters.problem = problem;
			Parameters.selectionMode = selectionMode;
			

			//TODO execution
			ea = new EA();
			
			
			thread = new Thread(){
				public void run(){
					startButton.setText("Stop");
					ea.run();
					startButton.setText("Start");
				}
			};
			thread.start();

		}



	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int value;
		if(e.getSource() == lengthSpinner){
			value = (int) lengthSpinner.getValue();
			if(value < 0){
				lengthSpinner.setValue(0);
				return;
			}
			if(oneMaxTarget.getText().length() > value){
				oneMaxTarget.setText(oneMaxTarget.getText().substring(0, value));
			}
			else  if(oneMaxTarget.getText().length() > value){
				oneMaxTarget.setText("");
			}

			if(value < (int)lolzCapSpinner.getValue()){
				lolzCapSpinner.setValue(value);
			}
			mutationField.setText(Double.toString(1./value));
		}
		if(e.getSource() == lolzCapSpinner){
			value = (int) lolzCapSpinner.getValue();
			if(value > (int)lengthSpinner.getValue()){
				lengthSpinner.setValue(value);
			}
		}
		if(e.getSource() == adultsSpinner){
			value = (int) adultsSpinner.getValue();
			if(value > (int)birthSpinner.getValue() && !survivalCheckBox.isSelected()){
				birthSpinner.setValue(value);
			}
			if(value < (int)tournamentSizeSpinner.getValue()){
				tournamentSizeSpinner.setValue(value);
			}
			
		}
		if(e.getSource() == birthSpinner){
			value = (int) birthSpinner.getValue();
			if(value < (int) adultsSpinner.getValue() && ! survivalCheckBox.isSelected()){
				adultsSpinner.setValue(value);
			}
		}
		
		if(e.getSource() == tournamentSizeSpinner){
			value = (int) tournamentSizeSpinner.getValue();
			if(value > (int)adultsSpinner.getValue()){
				adultsSpinner.setValue(value);
			}
		}

		
		if(e.getSource() == oneMaxButton){
			if(oneMaxButton.isSelected()){
				problem = Problem.ONEMAX;
			}
		}
		if(e.getSource() == lolzButton){
			if(lolzButton.isSelected()){
				problem = Problem.LOLZ;
			}
		}
		if(e.getSource() == localSurpSeqButton){
			if(localSurpSeqButton.isSelected()){
				problem = Problem.LOCALSEQ;
			}
		}
		if(e.getSource() == globalSurpSeqButton){
			if(globalSurpSeqButton.isSelected()){
				problem = Problem.GLOBALSEQ;
			}
		}
		if(e.getSource() == fitnessScalingButton){
			if(fitnessScalingButton.isSelected()){
				selectionMode = SelectionMode.FITNESS;
			}
		}
		if(e.getSource() == sigmaButton){
			if(sigmaButton.isSelected()){
				selectionMode = SelectionMode.SIGMA;
			}
		}
		if(e.getSource() == tournamentButton){
			if(tournamentButton.isSelected()){
				selectionMode = SelectionMode.TOURNAMENT;
			}
		}
		if(e.getSource() == uniformButton){
			if(uniformButton.isSelected()){
				selectionMode = SelectionMode.UNIFORM;
			}
		}


	}

	@Override
	public void changedUpdate(DocumentEvent e) {


	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if(e.getDocument() == oneMaxTarget.getDocument()){
			String s = oneMaxTarget.getText();
			for(int i = 0; i < s.length(); i++){
				if( !(s.charAt(i) == '1' || s.charAt(i) == '0')){
					return;
				}
			}
			lengthSpinner.setValue(s.length());
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(e.getDocument() == oneMaxTarget.getDocument()){
			String s = oneMaxTarget.getText();
			for(int i = 0; i < s.length(); i++){
				if( !(s.charAt(i) == '1' || s.charAt(i) == '0')){
					return;
				}
			}
			if(s.length() != 0){
				lengthSpinner.setValue(s.length());
			}
		}
	}
}
