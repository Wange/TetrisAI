package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TetrisTile extends GameTile{

	private Color color;
	
	public TetrisTile(Color c){
		color = c;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	
	public void draw(Graphics g, int x, int y, Dimension d){
		
		Graphics2D g2 = (Graphics2D) g;
	
		// credit to http://psnbtech.blogspot.se/2012/10/tutorial-java-tetris-game.html
		
		int TILE_SIZE = d.width;
		int SHADE_WIDTH = 7;
		
		g2.setColor(color);
		g2.fillRect(x, y, d.width, d.height);
		
		/*
		 * Fill the bottom and right edges of the tile with the dark shading color.
		 */
		g2.setColor(color.darker());
		g2.fillRect(x, y + TILE_SIZE - SHADE_WIDTH, TILE_SIZE, SHADE_WIDTH);
		g2.fillRect(x + TILE_SIZE - SHADE_WIDTH, y, SHADE_WIDTH, TILE_SIZE);
		
		/*
		 * Fill the top and left edges with the light shading. We draw a single line
		 * for each row or column rather than a rectangle so that we can draw a nice
		 * looking diagonal where the light and dark shading meet.
		 */
		g2.setColor(color.brighter());
		for(int i = 0; i < SHADE_WIDTH; i++) {
			g2.drawLine(x, y + i, x + TILE_SIZE - i - 1, y + i);
			g2.drawLine(x + i, y, x + i, y + TILE_SIZE - i - 1);
		}
		
		
		g2.setColor(Color.black);
		g2.drawRect(x, y, d.width, d.height);
		
		
		
	}
	
}
