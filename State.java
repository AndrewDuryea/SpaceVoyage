/*
 * Andrew Duryea
 * September 16, 2013
 * State.java
 *
 * This is the base State class from which all others should be subclassed.
 * It also implements KeyListener.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.sound.sampled.*;
import java.net.URL;

import Game.Game;

public abstract class State implements KeyListener
{
	Game game;

	URL musicURL = null;
	Clip musicClip = null;

    public State(Game g)
    {
    	this.game = g;
    }

	public void show(){
		game.addKeyListener(this);

		if(musicURL != null && musicClip == null){
			musicClip = Game.playMusic(musicURL);
		}
		if(musicClip != null){
			musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			musicClip.start();
		}
	}

	public void hide(){
		game.removeKeyListener(this);

		if(musicClip != null){
			musicClip.stop();
		}
	}

	public void dispose(){
		hide();
		if(musicClip != null){
			musicClip.close();
		}
	}

	//delta is the time since the last render
    public abstract void update(long delta);

    //public abstract void render(Graphics g, double width, double height);

    public abstract void render(Graphics g, int width, int height);

    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){}

    public void keyReleased(KeyEvent e){}
}