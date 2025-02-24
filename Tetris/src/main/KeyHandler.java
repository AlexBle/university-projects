package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public static boolean up, down, left, right, pause; //keys being pressed
	
	@Override
	public void keyTyped(KeyEvent e) {
		// this is not needed but we have to override
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP) {
			up = true;
		}
		if(code == KeyEvent.VK_DOWN) {
			down = true;
		}
		if(code == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if(code == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			if(pause) {pause = false;}
			else {pause = true;}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// this is not needed but we have to override
	}

}
