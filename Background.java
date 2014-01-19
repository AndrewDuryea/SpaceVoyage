/*
 * Andrew Duryea
 * September 18, 2013
 * Background.java
 *
 * A background class, really just used to hold the stars.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import Game.Star;

public class Background
{
	public static double VELOCITY;

	public static int NUM_STARS = 50;
	public int numCreated = 0;

	private ArrayList<Star> stars;

	Game game;

	//how far in the Z direction the background streches
	public int distance;

    public Background(Game g, double vel, int distance)
    {
    	this.game = g;

    	VELOCITY = vel;

    	stars = new ArrayList<Star>();

    	this.distance = distance;
    }

    public void update(long delta){
    	synchronized(stars){
	    	Iterator itr = stars.iterator();
	    	while(itr.hasNext()){
	    		Star s = (Star) itr.next();
	    		s.update(delta);

				/*if(s.X > Game.WIDTH || s.X < (0-Game.WIDTH) ||
					s.Y > Game.HEIGHT || s.Y < (0-Game.HEIGHT)){
					itr.remove();
				}*/

				if(s.model.Z < 0){
					itr.remove();
				}
	    	}
    	}
    }

    public void render(Graphics g, int width, int height){
    	synchronized(stars){
	    	if(stars.size() < NUM_STARS){
	    		if(numCreated < NUM_STARS){
		    		stars.add(new Star(this, VELOCITY, true));
		    		numCreated++;
	    		}
	    		else
		    		stars.add(new Star(this, VELOCITY, false));
	    	}

	    	g.setColor(Color.WHITE);

	    	Iterator itr = stars.iterator();
	    	while(itr.hasNext()){
	    		Star s = (Star) itr.next();
	    		//g.drawLine((int)(s.X + width), (int)(s.Y + height), (int)(s.X + width), (int)(s.Y + height));
	    		s.model.drawPoint(g, width, height);
	    	}
    	}
    	/*
    	for(Star s : stars){
    	//System.out.println("here2 " + s.X + ", " + s.Y);
    		g.drawLine((int)(s.X + width), (int)(s.Y + height), (int)(s.X + width), (int)(s.Y + height));
    	}*/
    }


}