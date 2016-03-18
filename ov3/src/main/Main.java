package main;

import java.awt.Dimension;

import javax.swing.JFrame;
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
		EA ea = new EA();
		ea.run();
		BoardPanel panel = new BoardPanel(Fitness.getBoard());
		panel.setPreferredSize(new Dimension(320, 320));
		
		JFrame frame = new JFrame();
		frame.setTitle("Flatland");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		
		panel.play();
		
		
	}
}
