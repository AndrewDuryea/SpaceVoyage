/**
 * Andrew Duryea
 * September 16, 2013
 * Game.java
 *
 * The game class containing the central loop.
 *
 * September 30, 2013: created Game package and moved everything that is not graphics into it
 * October 25, 2013: changed update to pass the time since the last render to all subsequent update methods
 * October 30, 2013: I've probably added other things here and there, but added logging to a file, which is surprisingly fast
 * November 12, 2013: Put on the final touches for the class, and jar'd it for Mike
 * December 4, 2031: changed all of the sounds to use URLs instead of Files so it works when jar'd
 *
 * note to self: get rid of show walls flag, change the default background color in Graphics3D,
 * change the update and render methods in GameState, and get rid of stringColor in Canvas3D
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.*;
import java.util.Date;

import Graphics3D.Graphics3D;
import Game.State;
import Game.GameState;

public class Game extends JFrame implements Runnable
{
	Graphics3D g3D = new Graphics3D(this);

	public ArrayList<State> stateStack = new ArrayList<State>();

	public static int WIDTH;
	public static int HEIGHT;

	public static double P_X = 0;
	public static double P_Y = 0;
	public static double P_Z = 0;

	static ArrayList<URL> sounds;
	public static int SOUND_SHOOT = 0;
	public static int SOUND_EXPLODE = 1;
	public static int SOUND_DAMAGE = 2;
	public static int SOUND_M_MOVE = 3;
	public static int SOUND_M_CONFIRM = 4;
	public static int SOUND_ASTEROID_HIT = 5;

	//public static BufferedWriter writer = null;

    public Game()
    {
		/*try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("log.txt"), "utf-8"));
		} catch(Exception e){e.printStackTrace();}*/

    	if(logOutput){
	    	BufferedWriter writer = null;

			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("log.txt"), "utf-8"));
			    writer.write((new Date()) + ":\tGame started");
		    	writer.newLine();
			} catch (IOException ex){
			  ex.printStackTrace();
			} finally {
			   try {writer.close();} catch (Exception ex) {}
			}
    	}

		//add states before canvas so there's something to draw
    	//pushState(new GameState(this));
    	pushState(new MainMenuState(this));

    	//adds canvas from Graphics3D to this frame
    	add(g3D.canvas, BorderLayout.CENTER);

		//g3D.canvas.setFocusable(true);
		//g3D.canvas.requestFocusInWindow();

		g3D.showDebugStrings(false);

		Thread animation = new Thread(this);
		animation.start();

		//load sounds
		sounds = new ArrayList<URL>();
		sounds.add(getClass().getResource("/Sounds/Laser_Shoot.wav"));
		sounds.add(getClass().getResource("/Sounds/Explosion.wav"));
		sounds.add(getClass().getResource("/Sounds/Hit.wav"));
		sounds.add(getClass().getResource("/Sounds/Menu_Move.wav"));
		sounds.add(getClass().getResource("/Sounds/Menu_Confirm.wav"));
		sounds.add(getClass().getResource("/Sounds/AsteroidHit.wav"));
    }

	public int frames = 0;
	public long lastTime = 0;
	public int lastFPS = 0;

	public void run(){
		lastTime = System.currentTimeMillis();
		//INFINITE LOOP!!!!!
		while(true){
			//System.out.println(System.currentTimeMillis() - lastTime);
			log(stateStack.size() + " : " + stateStack.get(stateStack.size() - 1).getClass());
			/*log("" + this.getKeyListeners().length);
			for(KeyListener k : this.getKeyListeners()){
				log("" + k.getClass());
			}*/
			stateStack.get(stateStack.size() - 1).update(System.currentTimeMillis() - lastTime);

			g3D.render();

			lastTime = System.currentTimeMillis();
			//small pause
			try
			{Thread.sleep(16);}
			catch(Exception e){System.out.println("help?");}

			//only compute FPS if debug strings are showing
			if(g3D.showDebugStrings){
				frames++;
				//System.out.println(frames + " " + System.currentTimeMillis() + " " + lastTime);
				if(System.currentTimeMillis() - lastTime >= 1000){
					lastFPS = frames;
					frames = 0;
				}
			}
		}
	}

	public void render(Graphics g, int width, int height){
		WIDTH = width;
		HEIGHT = height;

		stateStack.get(stateStack.size()-1).render(g, width, height);
	}

	//helpful when initializing
	public void pushState(State newState){
		log("push1 " + newState.getClass());

		if(stateStack.size() > 0){
			stateStack.get(stateStack.size()-1).hide();
		}

		stateStack.add(newState);
		newState.show();
	}

	public void pushState(State oldState, State newState){
		log("push2 " + oldState.getClass() + " : " + newState.getClass());

		oldState.hide();
		stateStack.add(newState);
		newState.show();
	}

	public void popState(){
		log("pop1 " + stateStack.get(stateStack.size()-1).getClass());
		stateStack.get(stateStack.size()-1).dispose();
		stateStack.remove(stateStack.size()-1);
		stateStack.get(stateStack.size()-1).show();
		printStack();
	}

	//pops top X states
	public void popX(int x){
		log("pop2 " + x);
		for(int i = 0; i < x; i++){
			//popState();
			stateStack.get(stateStack.size()-1).dispose();
			stateStack.remove(stateStack.size()-1);
			printStack();
		}
		//only show the last state
		stateStack.get(stateStack.size()-1).show();
	}

	public static void playSound(int s){
		if(s < sounds.size() && s >= 0){
			try{
				Clip clip = AudioSystem.getClip();
				AudioInputStream ais = AudioSystem.getAudioInputStream(sounds.get(s));
				clip.open(ais);

				FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(-20f);

				clip.start();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static Clip playMusic(URL f){
		Clip clip = null;

		try{
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(f);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-12f);

			clip.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return clip;
	}

	public void printStack(){
		for(State s : stateStack)
			log(s.getClass().toString());

		log("");
	}

    public static void main(String s[])
    {
    	WIDTH = (int)(850 / 2);
    	HEIGHT = (int)(660 / 2);

    	Game f = new Game();

		f.setResizable(false);

    	//window listener so close works
		f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e) {
            	/*try {
            		while(writing){}
            		writer.flush();
            		writer.close();
            	} catch(Exception ex){ex.printStackTrace();}*/
            	System.exit(0);
            }
        });

		f.setBounds(200, 50, 850, 660);
		f.setTitle("Space Voyage");
		f.setVisible(true);
    }

	public static boolean logOutput = false;

    public static void log(String s){
    	/*if(logOutput){
	    	FileWriter writer = null;

			try {
			    writer = new FileWriter(new File("log.txt"), true);
			    writer.write((new Date()) + ":\t" + s + System.lineSeparator());
		    	//writer.newLine();
			} catch (IOException ex){
			  ex.printStackTrace();
			} finally {
			   try {writer.close();} catch (Exception ex) {}
			}
    	}
    	else{
    		System.out.println(s);
    	}*/
    }
}