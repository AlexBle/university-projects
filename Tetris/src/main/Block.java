package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle{
	
	public int x,y;
	public static final int SIZE = 30; //block size is 30 by 30 pixels
	public Color color;
	
	public Block(Color c) {
		this.color = c;
	}
	
	public void draw(Graphics2D graphics2D) {
		int margin = 2;
		graphics2D.setColor(color);
		graphics2D.fillRect(x+margin, y+margin, SIZE-(margin*2), SIZE-(margin*2));
	}

}
