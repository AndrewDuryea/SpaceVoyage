/*
 * Andrew Duryea
 * October 14, 2013
 * MainMenuState.java
 *
 * The title screen with menu options.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import Game.Game;
import Game.Player;
import Graphics3D.Object3D;
import Graphics3D.Point3D;
import Graphics3D.Graphics3D;

public class MainMenuState extends MenuState
{
	Background back;

	int titleAlpha;

	GameState gs;

	Player player;

	boolean levelMode = false;
	boolean endlessMode = false;

    public MainMenuState(Game g){
    	super(g);

		back = new Background(game, 0.1, 4 * Game.WIDTH);

		drawMenu = false;

    	//menu.add(new MenuItem("start", Game.WIDTH,Game.HEIGHT,90,30));
    	//menu.add(new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 60,90,30));

		//main menu
		int index = addMenu();

    	addMenuItem(index, new MenuItem("start", Game.WIDTH,Game.HEIGHT,90,30));
    	addMenuItem(index, new MenuItem("about", Game.WIDTH,Game.HEIGHT + 60,90,30));
    	addMenuItem(index, new MenuItem("quit", Game.WIDTH,Game.HEIGHT + 120,90,30));

		//mode select
		index = addMenu();

    	addMenuItem(index, new MenuItem("Level Mode", Game.WIDTH,Game.HEIGHT,90,30));
    	addMenuItem(index, new MenuItem("Endless Mode", Game.WIDTH,Game.HEIGHT + 60,90,30));
    	addMenuItem(index, new MenuItem("back", Game.WIDTH,Game.HEIGHT + 120,90,30));

    	//ship select
    	index = addMenu();

    	//normal
    	addMenuItem(index, new MenuItem("Serenity", 60,Game.HEIGHT - 90,90,30, false));
    	//fast
    	addMenuItem(index, new MenuItem("Interceptor", 60,Game.HEIGHT - 30,90,30, false));
		//slow
    	addMenuItem(index, new MenuItem("Ptolemaios", 60,Game.HEIGHT + 30,90,30, false));
    	//back
    	addMenuItem(index, new MenuItem("back", 60,Game.HEIGHT + 90,90,30, false));

    	//about screen
    	index = addMenu();

    	addMenuItem(index, new MenuItem("back", Game.WIDTH,(2*Game.HEIGHT) - 100,90,30));

		musicURL = getClass().getResource("/Sounds/DST_CrossedStars.wav");
    }

	public void show(){
		super.show();

		if(musicClip != null && musicClip.isOpen()){
			musicClip.setFramePosition(0);
		}

		game.g3D.R = new Point3D(0,0,2*Game.WIDTH);
		game.g3D.currentCamera.setViewVector(-100,0,1);

		menuIndex = 0;

		titleAlpha = 0;
	}

	public void update(long delta){
		back.update(delta);

		if(menuIndex < 2){
			if(titleAlpha < 255)
				titleAlpha += 3;
			else
				drawMenu = true;
		}
		else{
			if(player != null){
				switch(selectedIndex){
					case 0:
						if(!player.getClass().equals(NormalShip.class)){
							player = new NormalShip(game, gs);
							player.model.translate(200,75,100);
						}
						break;
					case 1:
						if(!player.getClass().equals(FastShip.class)){
							player = new FastShip(game, gs);
							player.model.translate(200,75,100);
						}
						break;
					case 2:
						if(!player.getClass().equals(SlowShip.class)){
							player = new SlowShip(game, gs);
							player.model.translate(200,75,100);
						}
						break;
					default:
						break;
				}

				player.model.rotateDyn(0,0.5,0);
			}
		}

	}

	public void render(Graphics g, int width, int height){
		back.render(g, width, height);

		if(menuIndex < 2){ //title screen & game type select
			Font f = new Font(Font.SANS_SERIF, Font.ITALIC, 80);

			g.setFont(f);

			fm = g.getFontMetrics();

	    	//g.setColor(Color.WHITE);
	    	g.setColor(new Color(255,255,255,titleAlpha));

	    	int offset = fm.stringWidth("SPACE");
	    	g.drawString("SPACE", (width-offset + 20), (height - fm.getHeight()));

	    	g.drawString("VOYAGE", (width - 70), (height - 30));

			if(titleAlpha >= 255){
	    		super.render(g, width, height);

				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

				fm = g.getFontMetrics();

				g.setColor(Color.WHITE);

	    		offset = fm.stringWidth("Created by Andrew Duryea")+fm.getHeight();
	    		g.drawString("Created by Andrew Duryea",(2*width)-offset, (2*height)-fm.getHeight());
			}
		}
		else if(menuIndex == 2){ //ship select
			Font f = new Font(Font.SANS_SERIF, Font.ITALIC, 40);

			g.setFont(f);

			fm = g.getFontMetrics();

	    	//g.setColor(Color.WHITE);
	    	g.setColor(new Color(255,255,255));

	    	int offset = fm.stringWidth("Choose a ship:");
	    	g.drawString("Choose a ship:", 30, (height  - 120));

	    	//draw rectangle background for ship
	    	int x = width - 100;
	    	int y = 100;
	    	int h = height;
	    	int w = width + 20;

	    	g.setColor(Color.WHITE);

	    	//left edge
			g.drawLine((x - 1), y, (x - 1), (y + h));

	    	//right edge
			g.drawLine((x + w + 1), y, (x + w + 1), (y + h));

	    	//top edge
			g.drawLine(x, (y - 1), (x + w), (y - 1));

	    	//bottom edge
			g.drawLine(x, (y + h + 1), (x + w), (y + h + 1));

			g.setColor(Color.BLACK);

			for(int yi = y; yi < (y + h + 1); yi++){
				g.drawLine(x, yi, (x + w), yi);
			}

			game.g3D.R = new Point3D(0,0,50);
			game.g3D.currentCamera.setViewVector(0,0,100);
			//Graphics3D.currentLight.distance = 170;

			player.model.drawObject(g, width, height);

			game.g3D.R = new Point3D(0,0,2*Game.WIDTH);
			game.g3D.currentCamera.setViewVector(-100,0,1);
			Graphics3D.currentLight.distance = 70;


			//draw desctiption
			f = new Font("Monospaced", Font.PLAIN, 17);

			g.setFont(f);

			fm = g.getFontMetrics();

			g.setColor(Color.WHITE);

			y = y + h;

			//System.out.println(player.description);

			for(String s : player.description.split("\n")){
				//System.out.println(x + " " + y + fm.getHeight());
				g.drawString(s,x, y += fm.getHeight());
			}

			super.render(g, width, height);
		}
		else if(menuIndex == 3){ //about

			g.setFont(new Font("Monospaced", Font.PLAIN, 17));

			fm = g.getFontMetrics();

			g.setColor(Color.WHITE);

	    	int x = 100;
	    	int y = 50;

			//System.out.println(player.description);

			String about =
				"Welcome to Space Voyage!\n"+
				"    You are the captian of a ship plying vast streches of uninhabited\n"+
				"    space. Your goal is to navigate trecherous asteroid fields and\n"+
				"    make it to the next space station.\n\n" +
				"Controls:\n" +
				"    Arrow Keys: Move\n"+
				"         Space: Fire laser\n"+
				"             P: Pause\n\n"+
				"About Space Voyage:\n"+
				"    I created this game in the Fall of 2013 for an Advanced\n"+
				"    Computer Graphics and Game Design Class at LA Tech. The\n"+
				"    actual graphics portions were written in Winter 2012-2013.\n"+
				"    I had more fun than I thought possible making this game and\n"+
				"    I hope you enjoy it just as much as I did.\n"+
				"    Special thanks to Dr. Mike O'Neal.\n"+
				"        -Andrew Duryea";

			for(String s : about.split("\n")){
				//System.out.println(x + " " + y + fm.getHeight());
				g.drawString(s,x, y += fm.getHeight());
			}

			super.render(g, width, height);
		}
	}

    public void submit(){
    	//System.out.println(selectedIndex);
		//System.out.println(menuIndex + ", " + selectedIndex);
		if(menuIndex == 0){ //the main menu
			switch(selectedIndex){
				case 0: //start, advance to mode select
	    			Game.playSound(Game.SOUND_M_CONFIRM);
					menuIndex++;
					//System.out.println(menuIndex);
					break;
				case 1: //about
	    			Game.playSound(Game.SOUND_M_CONFIRM);
					menuIndex = 3;
					break;
				case 2: //quit
					System.exit(0);
					break;
				default:
					break;
			}
		}
		else if(menuIndex == 1){ //the game type menu
			switch(selectedIndex){
				case 0: //Level Mode
	    			Game.playSound(Game.SOUND_M_CONFIRM);
					//game.pushState(this, new LevelGameState(game, 1));
					gs = new LevelGameState(game, 1);
					menuIndex++;

					player = new NormalShip(game, gs);
					player.model.translate(200,75,100);

					break;
				case 1: //Endless Mode
	    			Game.playSound(Game.SOUND_M_CONFIRM);
					//game.pushState(this, new EndlessGameState(game));
					gs = new EndlessGameState(game);
					menuIndex++;

					player = new NormalShip(game, gs);
					player.model.translate(200,75,100);

					break;
				case 2: //back, return to main menu
	    			Game.playSound(Game.SOUND_M_CONFIRM);
					menuIndex--;
					break;
				default:
					break;
			}

			selectedIndex = 0;
		}
		else if(menuIndex == 2){ //the ship select menu
			if(selectedIndex < 3){
    			Game.playSound(Game.SOUND_M_CONFIRM);
				selectedIndex = 0;
				gs.addPlayer(player);
				game.pushState(this, gs);
			}
			else if(selectedIndex == 3){
    			Game.playSound(Game.SOUND_M_CONFIRM);
				menuIndex--;
				selectedIndex = 0;
			}
		}
		else if(menuIndex == 3){ //the about screen
			Game.playSound(Game.SOUND_M_CONFIRM);
			menuIndex = 0;
			selectedIndex = 0;
		}
    }

	public void keyPressed(KeyEvent e){
		if(titleAlpha < 255){
			titleAlpha = 255;
    		drawMenu = true;
		}
		super.keyPressed(e);
	}

	public void mouseClicked(MouseEvent e){
		if(titleAlpha < 255){
			titleAlpha = 255;
    		drawMenu = true;
		}
		super.mouseClicked(e);
	}
}