package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.Board;


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
		
		
		JPanel panel = new JPanel();
		BoardPanel boardPanel = new BoardPanel(new Board());
		boardPanel.setPreferredSize(new Dimension(30*16, 15*16));
		panel.add(boardPanel);
		panel.add(new SettingsPanel(boardPanel));
		
		JFrame frame = new JFrame();
		frame.setTitle("Beer tracker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
}
