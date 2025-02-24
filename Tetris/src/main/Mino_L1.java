package main;

import java.awt.Color;

public class Mino_L1 extends Mino{

	public Mino_L1(){
		create(Color.cyan);
	}
	
	public void setXY(int x, int y) {
		// [2][0][1] 
		// [3]	
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x + Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x - Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x - Block.SIZE;
		b[3].y = b[0].y + Block.SIZE;
	}
	
	public void getOrientation1() {
		// [2][0][1] 
		// [3]	
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x + Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x - Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x - Block.SIZE;
		tempB[3].y = b[0].y + Block.SIZE;
		
		updateXY(1);
	}
	public void getOrientation2() {
		//[3][2]
		//   [0]
		//   [1]
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y + Block.SIZE;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - Block.SIZE;
		tempB[3].x = b[0].x - Block.SIZE;
		tempB[3].y = b[0].y - Block.SIZE;
		
		updateXY(2);
	}
	public void getOrientation3() {
		//		[3]
		//[1][0][2]
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y - Block.SIZE;
		
		updateXY(3);
	}
	public void getOrientation4() {
		//   [1]  
		//   [0]   
		//	 [2][3] 
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y + Block.SIZE;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y + Block.SIZE;
		
		updateXY(4);
	}
	
}
