package main;

import java.awt.Color;

public class Mino_Z2 extends Mino{
	public Mino_Z2(){
		create(Color.blue);
	}
	
	public void setXY(int x, int y) {
		//   [3][2] 
		//[1][0]
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x - Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x;
		b[2].y = b[0].y - Block.SIZE;
		b[3].x = b[0].x + Block.SIZE;
		b[3].y = b[0].y - Block.SIZE;
	}
	
	public void getOrientation1() {
		
		//   [3][2] 
		//[1][0]	
			
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - Block.SIZE;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y - Block.SIZE;
		
		updateXY(1);
	}
	public void getOrientation2() {
		//[1]     
		//[0][2]
		//   [3]
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y + Block.SIZE;
				
		
		updateXY(2);
	}
	public void getOrientation3() {
		getOrientation1();
	}
	public void getOrientation4() {
		getOrientation2();
	}
}
