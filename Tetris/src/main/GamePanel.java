package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	GameManager gameManager;
	public static Sound music = new Sound();
	public static Sound soundEffect = new Sound();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
		gameManager = new GameManager();
		
	}
	
	public void StartGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); //call run method
		
		music.play(4, true); //theme song
		music.loop();
	}

	@Override
	public void run() {
		//game loop
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta>=1) {//we call the methods 60 times per second 
				update();
				repaint();
				delta--;
			}
		}
		
	}
	
	private void update() {
		if(!KeyHandler.pause && !gameManager.gameOver) {
			gameManager.update();
		}
		
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2D = (Graphics2D) graphics;
		gameManager.draw(graphics2D);
	}
}
