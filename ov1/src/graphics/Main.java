package graphics;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		BoidArea area = new BoidArea();
		JPanel panel = new JPanel();
		panel.add(area);
		SettingsPanel settings = new SettingsPanel();
		panel.add(settings);
		settings.addPropertyChangeListener(area);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		new Thread(area).start();

	}


}
