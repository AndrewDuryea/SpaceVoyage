/*
 * Andrew Duryea
 * October 29, 2013
 * GameEndState.java
 *
 * A parent class for all states that cause break in a level's gameplay, except pauseState.
 */

package Game;

import java.awt.*;

public abstract class GameEndState extends MenuState{

	int alpha = 0;

	String titleString;

    public GameEndState(Game g){
    	super(g);

		drawMenu = false;
    }

    public void update(long delta){
    	if(alpha < 255){
    		alpha += 2;
    	}
    }

    public void render(Graphics g, int width, int height){
    	//draw state below this one, should be a game state
    	if(alpha < 255){
			game.stateStack.get(game.stateStack.size()-2).render(g, width, height);
    	}
    	else{
    		alpha = 255;
    		drawMenu = true;
    	}

		//System.out.println(width + ", " + height);

		//alpha rectangle
		//System.out.println("here1 " + alpha);
		g.setColor(new Color(0,0,0,(alpha > 255)?255:alpha));
		for(int y = 0; y <= 2*height; y++){
			g.drawLine(0,y,2*width,y);
		}

		Font f = new Font(Font.SANS_SERIF, Font.ITALIC, 40);

		g.setFont(f);

		fm = g.getFontMetrics();

    	g.setColor(Color.WHITE);

    	double offset = fm.stringWidth(titleString) / 2;
		g.drawString(titleString, (int)(width-offset),(height-70));

		if(alpha >= 255){
			f = new Font(Font.SANS_SERIF, Font.PLAIN, 30);

			g.setFont(f);

			fm = g.getFontMetrics();

	    	g.setColor(Color.WHITE);
			offset = fm.stringWidth("Score: " + GameState.score) / 2;
			g.drawString("Score: " + GameState.score, (int)(width-offset),(height-30));

    		super.render(g, width, height);
		}
    }

	public abstract void submit();
}