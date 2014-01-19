/*
 * Andrew Duryea
 * September 16, 2013
 * PauseState.java
 *
 * This state allows the player to pause, but still draws the game state below it.
 *
 * October 13,2013: updated to be child of MenuState
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import Game.State;

public class PauseState extends MenuState
{
    public PauseState(Game g){
    	super(g);

    	//menu.add(new MenuItem("continue", Game.WIDTH,Game.HEIGHT,120,30));
    	//menu.add(new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));

		int index = addMenu();

    	addMenuItem(index, new MenuItem("continue", Game.WIDTH,Game.HEIGHT,120,30));
    	addMenuItem(index, new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,120,30));
    }

	public void show(){
		super.show();
    	selectedIndex = 0;
	}

    public void update(long delta){}

    public void render(Graphics g, int width, int height){
    	//draw state below this one, should be a game state
		game.stateStack.get(game.stateStack.size()-2).render(g, width, height);

		//System.out.println(width + ", " + height);

		//alpha rectangle
		g.setColor(new Color(0f,0f,0f,0.5f));
		for(int y = 0; y <= 2*height; y++){
			g.drawLine(0,y,2*width,y);
		}

		Font f = new Font(Font.SANS_SERIF, Font.ITALIC, 40);

		g.setFont(f);

		fm = g.getFontMetrics();

    	g.setColor(Color.WHITE);

    	double offset = fm.stringWidth("PAUSED") / 2;
		g.drawString("PAUSED", (int)(width-offset),(height-40));

    	super.render(g, width, height);
    }

    public void submit(){
    	switch(selectedIndex){
    		case 0: //continue
    			Game.playSound(Game.SOUND_M_CONFIRM);
	    		game.popState();
	    		break;
    		case 1: //quit, pop back to menu
    			Game.playSound(Game.SOUND_M_CONFIRM);
    			game.popX(2);
    			break;
    		default:
    			break;
    	}
    }
}