package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.EA;
import logic.Fitness;


public class Main {

	public static void main(String[] args) throws InterruptedException{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException ex) {
			
		}
		catch (InstantiationException ex) {
			
		}
		catch (IllegalAccessException ex) {
			
		}
		catch (UnsupportedLookAndFeelException ex) {
			
		}
		Fitness.init();
		EA ea = new EA();
		ea.run();
		JPanel panel = new JPanel();
		BoardPanel boardPanel = new BoardPanel(Fitness.getBoard(0));
		boardPanel.setPreferredSize(new Dimension(320, 320));
		panel.add(boardPanel);
		panel.add(new SettingsPanel(boardPanel));
		
		JFrame frame = new JFrame();
		frame.setTitle("Flatland");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
}
