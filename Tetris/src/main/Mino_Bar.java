package main;

import java.awt.Color;

public class Mino_Bar extends Mino{
	public Mino_Bar(){
		create(Color.orange);
	}
	
	public void setXY(int x, int y) {
		//[1][0][2][3]
		
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x - Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE + Block.SIZE;
		b[3].y = b[0].y;
	}
	
	public void getOrientation1() {
		//[1][0][2][3]
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE + Block.SIZE;
		tempB[3].y = b[0].y;
		
		updateXY(1);
	}
	
	public void getOrientation2() {
		//[1]
		//[0]
		//[2]
		//[3]
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y + Block.SIZE;
		tempB[3].x = b[0].x;
		tempB[3].y = b[0].y + Block.SIZE + Block.SIZE;
		
		updateXY(2);
	}
	
	public void getOrientation3() {
		getOrientation1();
	}
	public void getOrientation4() {
		getOrientation2();
	}
	
}
