/*
 * Andrew Duryea
 * September 16, 2013
 * GameState.java
 *
 * The state where gameplay occurs.
 *
 * Added Music support through the functions playSound and playMusic.
 */

package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.File;

import Game.State;
import Game.Asteroid;
import Game.Background;
import Game.Player;
import Game.Wall;
import Game.Reticle;
import Game.PauseState;
import Graphics3D.Object3D;
import Graphics3D.Point3D;
//import Graphics3D.Graphics3D;

public abstract class GameState extends State
{
	public boolean showWalls = false;

	public Player p;
	//Wall w;
	public ArrayList<Wall> border;

	public Reticle reticle;

	ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();

	ArrayList<Shot> shotList = new ArrayList<Shot>();

	Background back;

	static int DISTANCE = 3000;

	public int shotTimer = 0;

	public static int score;

	boolean rightPressed = false;
	boolean leftPressed = false;
	boolean upPressed = false;
	boolean downPressed = false;
	boolean spacePressed = false;

	public int alpha = 255;

	public String messageString = "";

	public int stringX;
	public int stringY;


	public GameState(Game g){
    	super(g);

		//p = new Player(game, this);
		//p = new NormalShip(game, this);
		//p.setShields(1);
		//w = new Wall(game);
		border = makeBorder();

		g.g3D.bWireframe = false;
		g.g3D.facetShading = true;

		score = 0;

		musicURL = getClass().getResource("/Sounds/DST_DistantMessage.wav");
	}

	public void addPlayer(Player player){

		this.p = player;

		this.p.resetModel();

		back = new Background(game, 2*Player.VELOCITY, GameState.DISTANCE);

		reticle = new Reticle(this, p.firingPoint);
	}

    /*public GameState(Game g)
    {
    	super(g);

		//p = new Player(game, this);
		p = new NormalShip(game, this);
		//p.setShields(1);
		//w = new Wall(game);
		border = makeBorder();

		g.g3D.bWireframe = false;
		g.g3D.facetShading = true;

		System.out.println("here1 " + Player.VELOCITY);
		back = new Background(game, 2*Player.VELOCITY, GameState.DISTANCE);

		reticle = new Reticle(this, p.firingPoint);

		score = 0;

		musicFile = new File("Sounds/DST_DistantMessage.wav");
    }*/

	public void show(){
		super.show();
		game.g3D.R = new Point3D(0,0,50);
		game.g3D.currentCamera.setViewVector(0,-25,100);
		//game.g3D.currentCamera.setViewVector(0,0,1);
	}

    public void update(long delta){
		//if the player is dead, push the game over state
		if(p.getShields() <= 0){
			game.pushState(this, new GameOverState(game));
			return;
		}

    	if(!showWalls){
			back.update(delta);

			synchronized(asteroidList){
				/*if(asteroidList.size() < MAX_ASTER){
					makeAsteroid();
				}*/

				//use an iterator to avoid concurrent modification exception
		    	Iterator itr = asteroidList.iterator();
		    	while(itr.hasNext()){
		    		Asteroid a = (Asteroid) itr.next();
		    		a.update(delta);

					if(a.model.getCenter().Z < 100){
						//the goal is off the screen, so it's impossible to complete the level
						if(a.getClass().equals(Station.class))
							game.pushState(this, new GameOverState(game));

						itr.remove();
					}
		    	}
			}

			synchronized(shotList){
				if(shotTimer > 0)
					shotTimer--;

				if(spacePressed && shotTimer == 0){
    				shotList.add(new Shot(this, p.SHOT_POWER, p.firingPoint));
    				shotTimer = 31; // approx. 1/2 sec.

    				Game.playSound(Game.SOUND_SHOOT);
				}

				//use an iterator to avoid concurrent modification exception
		    	Iterator itr = shotList.iterator();
		    	while(itr.hasNext()){
		    		Shot s = (Shot) itr.next();
		    		s.update(delta);

					if(s.model.getCenter().Z > GameState.DISTANCE || s.destroy){
						itr.remove();
					}
		    	}
			}
    	}

		p.update(delta);

    	if(alpha > 0){
    		alpha -= 2;
    	}
    }

    public void render(Graphics g, int width, int height){
		if(showWalls){
			for(Wall w : border){
				w.model.drawObject(g, width, height);
			}
		}
		else{
			back.render(g, width, height);

			ArrayList<Object3D> objects = new ArrayList<Object3D>();

			synchronized(asteroidList){
				//use an iterator to avoid concurrent modification exception
		    	Iterator itr = asteroidList.iterator();
		    	while(itr.hasNext()){
		    		Asteroid a = (Asteroid) itr.next();
		    		objects.add(a.model);
		    	}
			}

			synchronized(shotList){
	    		//use an iterator to avoid concurrent modification exception
		    	Iterator itr = shotList.iterator();
		    	while(itr.hasNext()){
		    		Shot s = (Shot) itr.next();
		    		objects.add(s.model);
		    	}
			}

			//sort the asteroids on their z center
			Collections.sort(objects, new Comparator<Object3D>(){
				public int compare(Object3D a, Object3D b){
					return (int)(b.getCenter().Z - a.getCenter().Z);
				}
			});

			Iterator itr = objects.iterator();
			while(itr.hasNext()){
				Object3D o = (Object3D) itr.next();
				o.drawObject(g, width, height);
			}
		}

		reticle.render(g, width, height);

		p.model.drawObject(g, width, height);

		//GUI
		GUI.drawShieldBar(p.shields, p.MAX_SHIELDS, g, width, height);
		GUI.drawScore(score, g, width, height);

		//draw level number
    	if(alpha > 0){
			Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

			g.setFont(f);

			FontMetrics fm = g.getFontMetrics();

	    	g.setColor(new Color(255,255,255,alpha));

	    	double offset = fm.stringWidth(messageString) / 2;
			g.drawString(messageString, (int)(stringX-offset),(stringY-fm.getHeight()));
    	}
    	else{
    		alpha = 0;
    	}
   	}

    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
    		rightPressed = true;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_LEFT){
    		leftPressed = true;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_UP){
    		upPressed = true;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_DOWN){
    		downPressed = true;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_SPACE){
    		spacePressed = true;
    	}
    }

    public void keyReleased(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
    		rightPressed = false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_LEFT){
    		leftPressed = false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_UP){
    		upPressed = false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_DOWN){
    		downPressed = false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_SPACE){
    		spacePressed = false;
    	}
    	if(e.getKeyCode() == KeyEvent.VK_P){
    		game.pushState(this, new PauseState(game));
    	}
    	/*if(e.getKeyCode() == KeyEvent.VK_D){
    		p.setShields(0);
    	}
    	if(e.getKeyCode() == KeyEvent.VK_C){
    		if(game.g3D.currentCamera.viewVec.equals(0,0,1)){
    			game.g3D.currentCamera.setViewVector(100,0,1);
    		}
    		else if(game.g3D.currentCamera.viewVec.equals(100,0,1)){
    			game.g3D.currentCamera.setViewVector(-100,0,1);
    		}
    		else if(game.g3D.currentCamera.viewVec.equals(-100,0,1)){
    			game.g3D.currentCamera.setViewVector(0,100,1);
    		}
    		else if(game.g3D.currentCamera.viewVec.equals(0,100,1)){
    			game.g3D.currentCamera.setViewVector(0,-100,1);
    		}
    		else if(game.g3D.currentCamera.viewVec.equals(0,-100,1)){
    			game.g3D.currentCamera.setViewVector(0,0,1);
    		}
    	}
    	if(e.getKeyCode() == KeyEvent.VK_W){
    		showWalls = !showWalls;
    		if(showWalls){
	    		game.g3D.canvas.setBackground(Color.WHITE);
	    		game.g3D.canvas.stringColor = Color.BLACK;
    		}
    		else{
	    		game.g3D.canvas.setBackground(Color.BLACK);
	    		game.g3D.canvas.stringColor = Color.WHITE;
    		}
    	}*/
    }

    public ArrayList<Wall> makeBorder(){
		ArrayList<Wall> border = new ArrayList<Wall>();
		Wall wall;
		Object3D model;

		//right wall
		wall = new Wall();
		//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = game.g3D.addObject("right", 1, 0, 1, 10, 0.75);

    	//add points order is important
    	model.addPoint(Game.WIDTH,Game.HEIGHT,0);	//0
		model.addPoint(Game.WIDTH,-Game.HEIGHT,0);	//1
		model.addPoint(Game.WIDTH + 10,-Game.HEIGHT,0);	//2
		model.addPoint(Game.WIDTH + 10,Game.HEIGHT,0);	//3
		model.addPoint(Game.WIDTH,Game.HEIGHT,200);	//4
		model.addPoint(Game.WIDTH,-Game.HEIGHT,200);	//5
		model.addPoint(Game.WIDTH + 10,-Game.HEIGHT,200);	//6
		model.addPoint(Game.WIDTH + 10,Game.HEIGHT,200);	//7

		//counter-clockwise for each face
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);

		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(4,5,0);
		model.addTriangle(0,5,1);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		wall.setModel(model);
		border.add(wall);

		//left wall
		wall = new Wall();
		//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = game.g3D.addObject("left", 1, 0, 1, 10, 0.75);

    	//add points order is important
    	model.addPoint(-Game.WIDTH - 10,Game.HEIGHT,0);	//0
		model.addPoint(-Game.WIDTH - 10,-Game.HEIGHT,0);	//1
		model.addPoint(-Game.WIDTH,-Game.HEIGHT,0);	//2
		model.addPoint(-Game.WIDTH,Game.HEIGHT,0);	//3
		model.addPoint(-Game.WIDTH - 10,Game.HEIGHT,200);	//4
		model.addPoint(-Game.WIDTH - 10,-Game.HEIGHT,200);	//5
		model.addPoint(-Game.WIDTH,-Game.HEIGHT,200);	//6
		model.addPoint(-Game.WIDTH,Game.HEIGHT,200);	//7

		//counter-clockwise for each face
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);

		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(4,5,0);
		model.addTriangle(0,5,1);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		wall.setModel(model);
		border.add(wall);

		//bottom wall
		wall = new Wall();
		//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = game.g3D.addObject("bottom", 1, 0, 1, 10, 0.75);

    	//add points order is important
    	model.addPoint(-Game.WIDTH,-Game.HEIGHT,0);	//0
		model.addPoint(-Game.WIDTH,-Game.HEIGHT - 10,0);	//1
		model.addPoint(Game.WIDTH,-Game.HEIGHT - 10,0);	//2
		model.addPoint(Game.WIDTH,-Game.HEIGHT,0);	//3
		model.addPoint(-Game.WIDTH,-Game.HEIGHT,200);	//4
		model.addPoint(-Game.WIDTH,-Game.HEIGHT - 10,200);	//5
		model.addPoint(Game.WIDTH,-Game.HEIGHT - 10,200);	//6
		model.addPoint(Game.WIDTH,-Game.HEIGHT,200);	//7

		//counter-clockwise for each face
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);

		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(4,5,0);
		model.addTriangle(0,5,1);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		wall.setModel(model);
		border.add(wall);

		//top wall
		wall = new Wall();
		//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)
    	model = game.g3D.addObject("top", 1, 0, 1, 10, 0.75);

    	//add points order is important
    	model.addPoint(-Game.WIDTH,Game.HEIGHT + 10,0);	//0
		model.addPoint(-Game.WIDTH,Game.HEIGHT,0);	//1
		model.addPoint(Game.WIDTH,Game.HEIGHT,0);	//2
		model.addPoint(Game.WIDTH,Game.HEIGHT + 10,0);	//3
		model.addPoint(-Game.WIDTH,Game.HEIGHT + 10,200);	//4
		model.addPoint(-Game.WIDTH,Game.HEIGHT,200);	//5
		model.addPoint(Game.WIDTH,Game.HEIGHT,200);	//6
		model.addPoint(Game.WIDTH,Game.HEIGHT + 10,200);	//7

		//counter-clockwise for each face
		model.addTriangle(0,1,3);
		model.addTriangle(3,1,2);

		model.addTriangle(3,2,7);
		model.addTriangle(7,2,6);

		model.addTriangle(7,6,4);
		model.addTriangle(4,6,5);

		model.addTriangle(4,0,7);
		model.addTriangle(7,0,3);

		model.addTriangle(4,5,0);
		model.addTriangle(0,5,1);

		model.addTriangle(1,5,2);
		model.addTriangle(2,5,6);

		wall.setModel(model);
		border.add(wall);

		return border;
	}

	public void reset(){
		asteroidList.clear();
		shotList.clear();

		shotTimer = 0;

		rightPressed = false;
		leftPressed = false;
		upPressed = false;
		downPressed = false;
		spacePressed = false;

		alpha = 255;

		if(musicClip != null && musicClip.isOpen()){
			musicClip.setFramePosition(0);
		}

		back = new Background(game, 2*Player.VELOCITY, DISTANCE);

		p.model.translate(0,0,100);
	}

	public void displayMessage(String s, int x, int y){
		this.messageString = s;
		this.stringX = x;
		this.stringY = y;
		this.alpha = 255;
	}

	public void displayMessage(String s){
		this.messageString = s;
		this.stringX = Game.WIDTH;
		this.stringY = Game.HEIGHT;
		this.alpha = 255;
	}
}