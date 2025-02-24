package main;

import javax.swing.JFrame;

public class Main {

		public static void main(String[] args) {
			
			JFrame window = new JFrame("Tetris"); // init window
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setAutoRequestFocus(false);
			
			GamePanel gamepanel = new GamePanel();
			window.add(gamepanel); //add GamePanel to window
			window.pack(); //adapt window to the panel size
			
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			
			gamepanel.StartGameThread();
		}
	
}
