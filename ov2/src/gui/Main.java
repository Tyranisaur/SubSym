package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(String[] args) {
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
		MainPanel panel = new MainPanel();
		
		JFrame frame = new JFrame();
		frame.setTitle("Evolutionary algorithm engine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		
	}

}
