package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {
	
	public Block b[] = new Block[4];
	public Block tempB[] = new Block[4];
	int autoDropCounter = 0;
	public int orientation = 1; // There are 4 orientations
	boolean leftCollision, rightCollision, bottomCollision; // Collision variables
	public boolean active = true; //variable to see if this mino is the "playable" one
	public boolean deactivating;
	int deactivateCounter = 0;
	
	public void create(Color c) {
		for (int i = 0; i<4;i++) {
			b[i] = new Block(c);
			tempB[i] = new Block(c);
		}
	}
	
	//Methods to check collisions
	public void checkMovementCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlocksCollision();
		
		//left wall
		for(int i=0;i<b.length;i++) {
			if(b[i].x == GameManager.left_x) { //if a block hit the left wall
				leftCollision = true;
			}
		}
		//right wall
		for(int i=0;i<b.length;i++) {
			if(b[i].x + Block.SIZE == GameManager.right_x) { //if a block hit the right wall
				rightCollision = true;
			}
		}
		//bottom wall
		for(int i=0;i<b.length;i++) {
			if(b[i].y + Block.SIZE == GameManager.bottom_y) { //if a block hit the bottom wall
				bottomCollision = true;
			}
		}
	}
	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlocksCollision();
		
		//left wall
		for(int i=0;i<b.length;i++) {
			if(tempB[i].x < GameManager.left_x) { //if a block hit the left wall
				leftCollision = true;
			}
		}
		//right wall
		for(int i=0;i<b.length;i++) {
			if(tempB[i].x + Block.SIZE > GameManager.right_x) { //if a block hit the right wall
				rightCollision = true;
			}
		}
		//bottom wall
		for(int i=0;i<b.length;i++) {
			if(tempB[i].y + Block.SIZE > GameManager.bottom_y) { //if a block hit the bottom wall
				bottomCollision = true;
			}
		}
	}
	public void checkStaticBlocksCollision() {
		for(int i=0;i<GameManager.staticBlocks.size();i++) {
				
			int x = GameManager.staticBlocks.get(i).x;
			int y = GameManager.staticBlocks.get(i).y;
			
			//check if there are blocks under the current mino's blocks
			for(int j=0;j<b.length;j++) {
				if(b[j].x == x && b[j].y + Block.SIZE == y) {
					bottomCollision = true;
				}
			}
			//check for left collision
			for(int j=0;j<b.length;j++) {
				if(b[j].x - Block.SIZE == x && b[j].y == y) {
					leftCollision = true;
				}
			}
			//check for right collision
			for(int j=0;j<b.length;j++) {
				if(b[j].x + Block.SIZE == x && b[j].y == y) {
					rightCollision = true;
				}
			}
		}
	}
	
	//these methods will be implemented in each mino class
	public void setXY(int x, int y) {}
	public void getOrientation1() {}
	public void getOrientation2() {}
	public void getOrientation3() {}
	public void getOrientation4() {}
	
	
	public void updateXY(int orientation) {
		
		checkRotationCollision();
		
		if(!leftCollision && !rightCollision && !bottomCollision) {
			//rotate the mino accordingly to the orientation set in the subclass
			this.orientation = orientation;
			for (int i = 0; i<4;i++) {
				b[i].x = tempB[i].x;
				b[i].y = tempB[i].y;
			}
			GamePanel.soundEffect.play(3,false);
		}
		
	}
	
	public void update() {
		
		if(deactivating) {
			deactivating();
		}
		//movement
		if(KeyHandler.up) {
			switch(orientation) { // rotate the mino to the next orientation
			case 1: getOrientation2();break;
			case 2: getOrientation3();break;
			case 3: getOrientation4();break;
			case 4: getOrientation1();break;
			}
			
			KeyHandler.up = false;//reset key
		}
		
		checkMovementCollision(); //check for collision
		
		if(KeyHandler.down) {
			if(!bottomCollision) {
				//move the mino downwards
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				
				autoDropCounter = 0; //reset counter
			}
			
			KeyHandler.down = false; //reset key
		}
		
		if(KeyHandler.right) {
			if(!rightCollision) {
				//move the mino to the right
				b[0].x += Block.SIZE;
				b[1].x += Block.SIZE;
				b[2].x += Block.SIZE;
				b[3].x += Block.SIZE;
			}
			
			KeyHandler.right = false; //reset key
		}
		
		if(KeyHandler.left) {
			if(!leftCollision) {
				//move the mino to the left
				b[0].x -= Block.SIZE;
				b[1].x -= Block.SIZE;
				b[2].x -= Block.SIZE;
				b[3].x -= Block.SIZE;
			}
			
			KeyHandler.left = false; //reset key
		}
		
		if(bottomCollision) {
			if(deactivating == false) {//play sound effect only once (when the mino touches the floor)
				GamePanel.soundEffect.play(1, false);
			}
			deactivating = true;
		}
		else {
			autoDropCounter++; //counter increases every frame
			if(autoDropCounter == GameManager.dropInterval) {
				//move the mino down
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				autoDropCounter = 0; //reset counter
			}
		}
		
	}
	
	public void deactivating() {
		deactivateCounter++;
		
		//delay 30 frames (0.5 sec) until deactivating the current mino
		if(deactivateCounter == 30) {
			deactivateCounter = 0;
			
			checkMovementCollision(); // check if the mino is still on the bottom wall
			if(bottomCollision) {
				active = false;
			}
		}
	}
	
	public void draw(Graphics2D graphics2D) {
		
		int margin = 2; //each block has a 2 pixel margin frame so we can distinguish between blocks
		graphics2D.setColor(b[0].color);
		graphics2D.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		graphics2D.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		graphics2D.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		graphics2D.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
	}
}
