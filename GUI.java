/*
 * Andrew Duryea
 * September 16, 2013
 * Player.java
 *
 * The player and the model for the player.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI
{
	static Font f = new Font(Font.MONOSPACED, Font.PLAIN, 20);
	static FontMetrics fm;
	static int offset = 0;

    public static void drawScore(int score, Graphics g, int width, int height){
    	g.setColor(Color.WHITE);
    	g.setFont(f);
    	String text = "Score ";
    	int x = offset - fm.stringWidth(text);
//System.out.println(offset + " " + x + " " + fm.stringWidth(text));
    	g.drawString(text + score, x, 30);
    	//g.drawRect(x, 20-fm.getHeight(), fm.stringWidth(text), fm.getHeight());
    }

	//call this one first
    public static void drawShieldBar(int shields, int maxShields, Graphics g, int width, int height){
    	//g.drawLine((int)(p.X + xOffset), (int)(-p.Y + yOffset), (int)(p.X + xOffset), (int)(-p.Y + yOffset));

    	g.setFont(f);

		fm = g.getFontMetrics();

    	int x = 20;
    	int y = 55;

    	g.setColor(Color.WHITE);
    	String text = "Shields ";
		offset = x + fm.stringWidth(text);

    	g.drawString(text, x, y);
    	//g.drawRect(x, y-fm.getHeight(), fm.stringWidth(text), fm.getHeight());

    	x = offset;
    	y -= 15;
    	int h = 20;
    	int w = 200;

    	g.setColor(Color.WHITE);

    	//left edge
		g.drawLine((x - 1), y, (x - 1), (y + h));

    	//right edge
		g.drawLine((x + w + 1), y, (x + w + 1), (y + h));

    	//top edge
		g.drawLine(x, (y - 1), (x + w), (y - 1));

    	//bottom edge
		g.drawLine(x, (y + h + 1), (x + w), (y + h + 1));

		if(shields > (maxShields / 2))
			g.setColor(Color.GREEN);
		else if(shields > (maxShields / 4))
			g.setColor(Color.YELLOW);
		else
			g.setColor(Color.RED);

		int xi = (int)((shields / (float)maxShields) * w);

		//System.out.println(shields + " / " + maxShields + " : " + xi + " / " + w);

		//if prevents drawing something when shields == 0
		// may not matter since it would game over anyway
		if(x != (x + xi)){
			for(int yi = y; yi < (y + h + 1); yi++){
				//System.out.println("here0");
				g.drawLine(x, yi, (x + xi), yi);
			}
		}
    }

}