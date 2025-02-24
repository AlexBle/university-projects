package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;


public class GameManager {
	
	//game area
	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;
	
	// mino variables
	public static ArrayList<Block> staticBlocks = new ArrayList<>();
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y;
	Mino nextMino;
	final int NEXT_MINO_X;
	final int NEXT_MINO_Y;
	
	public static int dropInterval = 60; //mino drops ever 60 frames (1 second)
	
	//effects
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();
	
	//score
	int level = 1;
	int lines;
	int score;
	
	//statistics
	Integer minoCount [] = new Integer[7];
	
	//music speed
	boolean music2X = false;
	
	boolean gameOver;
	public GameManager() {
		
		//positioning game frame
		left_x = (GamePanel.WIDTH/2)-(WIDTH/2); 
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
		
		MINO_START_X = 	left_x + (WIDTH/2) - Block.SIZE;
		MINO_START_Y =  top_y + Block.SIZE;
		NEXT_MINO_X = right_x + 175;
		NEXT_MINO_Y = top_y + 525;
		
		for(int i = 0; i<7; i++) {
			minoCount[i] = 0;
		} 
		
		currentMino = randMino();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino = randMino();
		nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
		
	}
	
	private Mino randMino() {
		//create a random mino
		Mino mino = null;
		int i = new Random().nextInt(7);
		minoCount[i]++;
		switch(i) {
		case 0: mino = new Mino_Bar();break;
		case 1: mino = new Mino_Z1();break;
		case 2: mino = new Mino_L2();break;
		case 3: mino = new Mino_Square();break;
		case 4: mino = new Mino_L1();break;
		case 5: mino = new Mino_T();break;
		case 6: mino = new Mino_Z2();break;
		}
		
		return mino;
	}
	
	public void update() {
		
		if(!currentMino.active) { //if the currentMino is not active
			for(int i=0;i<4;i++) {
				staticBlocks.add(currentMino.b[i]);//add it to the list of static blocks
			}
			//check for gameOver
			if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
				//if the mino stopped in the mino start area, then the game is over 
				gameOver = true;
				GamePanel.soundEffect.play(2, false);
			}
			
			currentMino.deactivating = false;
			//replace the currentMino with the nextMino 
			currentMino = nextMino;
			currentMino.setXY(MINO_START_X, MINO_START_Y);
			//select the new nextMino
			nextMino = randMino();
			nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
			
			//when the current mino is deactivated, check if some lines should be deleted
			checkDelete();
		}
		else {
			currentMino.update();
		}
		
	}
	
	private void checkDelete() {
		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;
		
		while(x < right_x && y < bottom_y) {
			//count the static blocks in each row
			for(int i = 0; i<staticBlocks.size(); i++) {
				if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
					blockCount++;
				}
			}
			
			x+= Block.SIZE;
			if(x == right_x) {
				
				if(blockCount == 12) {
					//full row
					
					effectCounterOn = true; 
					effectY.add(y); //effects will be applied to this y-th row
					
					for(int i = staticBlocks.size()-1; i > -1; i--) {
						//remove the blocks from the full row
						if(staticBlocks.get(i).y==y) {
							staticBlocks.remove(i);
						}
					}
					
					//increment the amount of lines cleared 
					lineCount++; 
					lines++;
					
					//increase the drop speed and level after clearing 5 lines
					if(lines % 5 == 0 && dropInterval > 1) {
						level++;
						if(dropInterval > 5) {
							dropInterval -= 5;
						}
						else {
							dropInterval -= 1;
						}
						if(level>=10 && !music2X) {
							GamePanel.music.stop();//stop playing the theme song
							GamePanel.music.play(5, true); //start playing the 2X version
							GamePanel.music.loop();
							music2X = true;
							
						}
					}
					
					//move the remaining blocks down
					for(int i = 0; i<staticBlocks.size(); i++) {
						if(staticBlocks.get(i).y < y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
					}
				}
				
				blockCount = 0;
				x = left_x;
				y += Block.SIZE;
			}
		}
		
		//add score
		if(lineCount>0) {
			GamePanel.soundEffect.play(0, false);
			switch(lineCount) {
				case 1: score += 40*level;break;
				case 2: score += 100*level;break;
				case 3: score += 300*level;break;
				case 4: score += 500*level;break;
				default: score += 500*level;break;
			}
				
		}
	}
	
	public void draw(Graphics2D graphics2D) {
		
		//draw main game frame
		graphics2D.setColor(Color.white);
		graphics2D.setStroke(new BasicStroke(4f)); //frame width is 4 pixels
		graphics2D.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8); //adjusting the panel dimension because of the frame
		
		//draw "Next Tetromino" frame
		int x = right_x + 100;
		int y = bottom_y - 200;
		graphics2D.drawRect(x, y, 200, 200);
		graphics2D.setFont(new Font("Arial",Font.PLAIN,30));
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2D.drawString("NEXT", x+60, y+60);
		
		//draw score frame
		graphics2D.drawRect(x, top_y, 250, 300);
		x += 35;
		y = top_y + 90;
		graphics2D.drawString("LEVEL: " + level, x, y);
		graphics2D.drawString("LINES: " + lines, x, y+70);
		graphics2D.drawString("SCORE: " + score, x, y+140);
		
		//draw mino statistics
		x = 90;
		y = 30;
		graphics2D.drawRect(x, y, 300, 660);
		Mino minos[] = new Mino[7];
		minos[0] = new Mino_Bar();
		minos[1] = new Mino_Z1();
		minos[2] = new Mino_L2();
		minos[3] = new Mino_Square();
		minos[4] = new Mino_L1();
		minos[5] = new Mino_T();
		minos[6] = new Mino_Z2();
		for(int i = 0; i < 7; i++) {
			minos[i].setXY(x + 40, y + 20 + i*100);
			minos[i].draw(graphics2D);
			graphics2D.drawString(minoCount[i].toString(), x + 200, y + 45 + i*100);
		}
				
		//draw currentMino
		if(currentMino!=null) {
			currentMino.draw(graphics2D);
		}
		
		//draw nextMino
		nextMino.draw(graphics2D);
		
		//draw staticBlocks
		for(int i=0;i<staticBlocks.size();i++) {
			staticBlocks.get(i).draw(graphics2D);		
		}
		
		//draw pause and game over
		graphics2D.setColor(Color.red);
		graphics2D.setFont(new Font("Arial",Font.BOLD,45));
		if(gameOver) {
			x = left_x + 40;
			y = top_y + 170;
			graphics2D.drawString("GAME OVER!",x,y);
			GamePanel.music.stop();//stop playing the theme song
		}
		else if(KeyHandler.pause) {
			x = left_x + 14;
			y = top_y + 170;
			graphics2D.drawString("GAME PAUSED",x,y);
			GamePanel.music.stop();//stop playing the theme song
			GamePanel.music.play(4, true); //start playing the 2X version
			GamePanel.music.loop();
		}
		
		//draw effects
		if(effectCounterOn) {
			effectCounter++;
			
			graphics2D.setColor(Color.white);
			for(int i = 0; i < effectY.size(); i++) {
				graphics2D.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
			}
			
			if(effectCounter == 10) { //wait for 10 frames
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
		}
	}
}
