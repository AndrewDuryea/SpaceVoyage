/**
 * Andrew Duryea
 * September 30, 2013
 * Canvas3D.java
 *
 * Made because JPanel needed to be subclassesed to override the paintComponent method.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Game.Game;
import Game.GameState;

public class Canvas3D extends JPanel
{
	public Color stringColor = Color.BLACK;

	public Canvas3D()
	{
		super();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//this will also move the canvas, but is too easy
		//g.translate(canvas.getWidth() / 2,canvas.getHeight() / 2);

		/*
		for(Object3D o : game.stateStack.get(game.stateStack.size()-1).objects)
		{
			o.drawObject(g, this.getWidth() / 2, this.getHeight() / 2);
		}
		*/

		Graphics3D.game.render(g, (int)(this.getWidth() / 2), (int)(this.getHeight() / 2));

		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

		if(Graphics3D.showDebugStrings){
        	g.drawString("N: "+Graphics3D.currentCamera.viewVec,0,10);
        	g.drawString("d: "+Graphics3D.currentCamera.viewDist,0,25);
        	g.drawString("R: "+Graphics3D.R,0,40);
        	g.drawString("Up: "+Graphics3D.standardUp,0,55);
        	g.drawString("FPS: "+Graphics3D.game.lastFPS,0,70);
        	g.drawString("Player: (" + Game.P_X + ", " + Game.P_Y + ", " + Game.P_Z + ")",0,85);
        	if(Graphics3D.game.stateStack.get(Graphics3D.game.stateStack.size() - 1).getClass().equals(GameState.class))
        		g.drawString("Shields: " + ((GameState)(Graphics3D.game.stateStack.get(Graphics3D.game.stateStack.size() - 1))).p.shields, 0, 100);
        	if(Graphics3D.game.stateStack.get(Graphics3D.game.stateStack.size() - 1).getClass().equals(GameState.class))
        		g.drawString("Score: " + GameState.score, 0, 115);
		}
	}
}