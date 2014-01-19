/*
 * Andrew Duryea
 * October 30, 2013
 * LevelGameState.java
 *
 * A game with three pre defined levels.
 */

package Game;

import java.awt.event.*;

public class LevelGameState extends GameState{

	public Timeline timeline;

	public static int levelNum = 1;

    public LevelGameState(Game g, int levelNum){
    	super(g);

    	this.levelNum = levelNum;
    }

    public void addPlayer(Player player){
    	super.addPlayer(player);

    	loadLevel(levelNum);
    }

	public void update(long delta){
		super.update(delta);

    	timeline.update(delta);
	}

	public void levelEnd(){
		switch(levelNum){
			case 1:
				game.pushState(new LevelOverState(game));
				break;
			case 2:
				game.pushState(new LevelOverState(game));
				break;
			case 3:
				//game over, you win
				game.pushState(new GameWinState(game));
				break;
			default:
				break;
		}
	}

	public void loadLevel(int levelNum){
		reset();

		switch(levelNum){
			case 1:
				loadLevel1();
				break;
			case 2:
				loadLevel2();
				break;
			case 3:
				loadLevel3();
				break;
			default:
				break;
		}
	}

	public void loadNextLevel(){
		reset();

		switch(levelNum){
			case 1:
				loadLevel2();
				break;
			case 2:
				loadLevel3();
				break;
			case 3:
				//no more levels to load
				break;
			default:
				break;
		}
	}
/*
	//delete this later
    public void keyReleased(KeyEvent e){
    	super.keyReleased(e);

    	if(e.getKeyCode() == KeyEvent.VK_Q){
    		levelEnd();
    	}
    }
*/
	public void loadLevel1(){
		levelNum = 1;
		//introString = "Level 1";
		displayMessage("Level 1");

		timeline = new Timeline(this);

		//timeline.addObject(new Station(this, 0));
		//425, 330

		//100 in 1 sec
		//1000 in 10 sec

		double vel = 2 * Player.VELOCITY;

		//add timeline events
		//(int health, GameState gs, double x, double y, double z, double vx, double vy, double vz, long time)
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 200));

		//square
		timeline.addObject(new Asteroid(this, 1, 200,165,GameState.DISTANCE, 0,0,-vel, 700));
		timeline.addObject(new Asteroid(this, 2, -200,165,GameState.DISTANCE, 0,0,-vel, 700));
		timeline.addObject(new Asteroid(this, 3, -200,-165,GameState.DISTANCE, 0,0,-vel, 700));
		timeline.addObject(new Asteroid(this, 1, 200,-165,GameState.DISTANCE, 0,0,-vel, 700));

		timeline.addObject(new Asteroid(this, 1, 75,102,GameState.DISTANCE, 0,0,-vel, 1200));
		timeline.addObject(new Asteroid(this, 1, -48,72,GameState.DISTANCE, 0,0,-vel, 1200));
		timeline.addObject(new Asteroid(this, 1, 62,-200,GameState.DISTANCE, 0,0,-vel, 1200));

		//random
		timeline.addObject(new Asteroid(this, 1, 1500));
		timeline.addObject(new Asteroid(this, 1, 1600));
		timeline.addObject(new Asteroid(this, 1, 1700));
		timeline.addObject(new Asteroid(this, 1, 1800));
		timeline.addObject(new Asteroid(this, 1, 1900));
		timeline.addObject(new Asteroid(this, 1, 2000));
		timeline.addObject(new Asteroid(this, 1, 2100));

		//diamond
		timeline.addObject(new Asteroid(this, 1, 0,165,GameState.DISTANCE, 0,0,-vel, 2500));
		timeline.addObject(new Asteroid(this, 1, 165,0,GameState.DISTANCE, 0,0,-vel, 2500));
		timeline.addObject(new Asteroid(this, 1, 0,-165,GameState.DISTANCE, 0,0,-vel, 2500));
		timeline.addObject(new Asteroid(this, 1, -165,0,GameState.DISTANCE, 0,0,-vel, 2500));

		//small square
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-(vel + 0.1), 3000));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-(vel + 0.1), 3000));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-(vel + 0.1), 3000));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-(vel + 0.1), 3000));

		//random
		timeline.addObject(new Asteroid(this, 3500));
		timeline.addObject(new Asteroid(this, 3600));
		timeline.addObject(new Asteroid(this, 3700));
		timeline.addObject(new Asteroid(this, 3700));
		timeline.addObject(new Asteroid(this, 3800));
		timeline.addObject(new Asteroid(this, 3900));
		timeline.addObject(new Asteroid(this, 3950));
		timeline.addObject(new Asteroid(this, 4000));

		//small square
		timeline.addObject(new Asteroid(this, 1, 180,180,GameState.DISTANCE, 0,0,-(vel + 0.1), 4500));
		timeline.addObject(new Asteroid(this, 1, -180,180,GameState.DISTANCE, 0,0,-(vel + 0.1), 4600));
		timeline.addObject(new Asteroid(this, 1, -180,-180,GameState.DISTANCE, 0,0,-(vel + 0.1), 4700));
		timeline.addObject(new Asteroid(this, 1, 180,-180,GameState.DISTANCE, 0,0,-(vel + 0.1), 4800));

		//larger square
		timeline.addObject(new Asteroid(this, 1, -230,230,GameState.DISTANCE, 0,0,-(vel + 0.1), 5200));
		timeline.addObject(new Asteroid(this, 1, 230,-230,GameState.DISTANCE, 0,0,-(vel + 0.1), 5300));
		timeline.addObject(new Asteroid(this, 1, 230,230,GameState.DISTANCE, 0,0,-(vel + 0.1), 5400));
		timeline.addObject(new Asteroid(this, 1, -230,-230,GameState.DISTANCE, 0,0,-(vel + 0.1), 5500));

		//X
		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 6200));

		timeline.addObject(new Asteroid(this, 1, -100,100,GameState.DISTANCE, 0,0,-vel, 6500));
		timeline.addObject(new Asteroid(this, 1, -100,-100,GameState.DISTANCE, 0,0,-vel, 6500));
		timeline.addObject(new Asteroid(this, 1, 100,-100,GameState.DISTANCE, 0,0,-vel, 6500));
		timeline.addObject(new Asteroid(this, 1, 100,100,GameState.DISTANCE, 0,0,-vel, 6500));

		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 6700));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 6700));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 6700));
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 6700));

		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 6800));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 6800));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 6800));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 6800));

		//random
		timeline.addObject(new Asteroid(this, 7300));
		timeline.addObject(new Asteroid(this, 7600));
		timeline.addObject(new Asteroid(this, 7600));
		timeline.addObject(new Asteroid(this, 7900));
		timeline.addObject(new Asteroid(this, 8200));

		//diamond
		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 8300));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 8300));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 8300));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 8300));

		//line
		timeline.addObject(new Asteroid(this, 3, 0,0,GameState.DISTANCE, 0,0,-(vel + 0.2), 8600));
		timeline.addObject(new Asteroid(this, 3, 0,0,GameState.DISTANCE, 0,0,-(vel + 0.2), 8700));
		timeline.addObject(new Asteroid(this, 3, 0,0,GameState.DISTANCE, 0,0,-(vel + 0.2), 8800));

		//plus
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 9000));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 9000));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 9000));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 9000));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 9200));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 9200));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 9200));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 9200));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 9400));

		//random
		timeline.addObject(new Asteroid(this, 2, 9600));
		timeline.addObject(new Asteroid(this, 2, 9700));
		timeline.addObject(new Asteroid(this, 2, 9750));

		//big random
		timeline.addObject(new Asteroid(this, 10400));
		timeline.addObject(new Asteroid(this, 10600));
		timeline.addObject(new Asteroid(this, 10700));
		timeline.addObject(new Asteroid(this, 10850));
		timeline.addObject(new Asteroid(this, 10900));
		timeline.addObject(new Asteroid(this, 11100));

		//small square
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-(vel + 0.1), 11400));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-(vel + 0.1), 11400));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-(vel + 0.1), 11400));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-(vel + 0.1), 11400));

		//goal ~2 min, 12000
		timeline.addObject(new Station(this, 11500));
	}

	public void loadLevel2(){
		levelNum = 2;
		//introString = "Level 2";
		displayMessage("Level 2");

		timeline = new Timeline(this);

		//add timeline events
		//timeline.addObject(new Station(this, 0));

		//425, 330

		//100 in 1 sec
		//1000 in 10 sec

		double vel = 2 * Player.VELOCITY;

		//add timeline events
		//(int health, GameState gs, double x, double y, double z, double vx, double vy, double vz, long time)
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 200));

		//X
		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 500));

		timeline.addObject(new Asteroid(this, 1, -100,100,GameState.DISTANCE, 0,0,-vel, 800));
		timeline.addObject(new Asteroid(this, 1, -100,-100,GameState.DISTANCE, 0,0,-vel, 800));
		timeline.addObject(new Asteroid(this, 1, 100,-100,GameState.DISTANCE, 0,0,-vel, 800));
		timeline.addObject(new Asteroid(this, 1, 100,100,GameState.DISTANCE, 0,0,-vel, 800));

		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 1100));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 1100));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 1100));
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 1100));

		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 1400));

		//horizontal lines
		timeline.addObject(new Asteroid(this, 1, -400,-100,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, -200,-100,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 0,-100,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 200,-100,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 400,-100,GameState.DISTANCE, 0,0,-vel, 1800));

		timeline.addObject(new Asteroid(this, 1, -400,173,GameState.DISTANCE, 0,0,-vel, 2200));
		timeline.addObject(new Asteroid(this, 1, -200,173,GameState.DISTANCE, 0,0,-vel, 2200));
		timeline.addObject(new Asteroid(this, 1, 0,173,GameState.DISTANCE, 0,0,-vel, 2200));
		timeline.addObject(new Asteroid(this, 1, 200,173,GameState.DISTANCE, 0,0,-vel, 2200));
		timeline.addObject(new Asteroid(this, 1, 400,173,GameState.DISTANCE, 0,0,-vel, 2200));

		timeline.addObject(new Asteroid(this, 1, -400,22,GameState.DISTANCE, 0,0,-vel, 2600));
		timeline.addObject(new Asteroid(this, 1, -200,22,GameState.DISTANCE, 0,0,-vel, 2600));
		timeline.addObject(new Asteroid(this, 1, 0,22,GameState.DISTANCE, 0,0,-vel, 2600));
		timeline.addObject(new Asteroid(this, 1, 200,22,GameState.DISTANCE, 0,0,-vel, 2600));
		timeline.addObject(new Asteroid(this, 1, 400,22,GameState.DISTANCE, 0,0,-vel, 2600));

		timeline.addObject(new Asteroid(this, 1, -400,80,GameState.DISTANCE, 0,0,-vel, 3000));
		timeline.addObject(new Asteroid(this, 1, -200,80,GameState.DISTANCE, 0,0,-vel, 3000));
		timeline.addObject(new Asteroid(this, 1, 0,80,GameState.DISTANCE, 0,0,-vel, 3000));
		timeline.addObject(new Asteroid(this, 1, 200,80,GameState.DISTANCE, 0,0,-vel, 3000));
		timeline.addObject(new Asteroid(this, 1, 400,80,GameState.DISTANCE, 0,0,-vel, 3000));
/*
		//random
		timeline.addObject(new Asteroid(this, 2, 3000));
		timeline.addObject(new Asteroid(this, 2, 3100));
		timeline.addObject(new Asteroid(this, 2, 3100));
		timeline.addObject(new Asteroid(this, 2, 3200));*/

		//vertical lines
		timeline.addObject(new Asteroid(this, 1, 100,-300,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,-150,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,0,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,150,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,300,GameState.DISTANCE, 0,0,-vel, 3600));

		timeline.addObject(new Asteroid(this, 1, -75,-300,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, -75,-150,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, -75,0,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, -75,150,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, -75,300,GameState.DISTANCE, 0,0,-vel, 4000));

		timeline.addObject(new Asteroid(this, 1, -200,-300,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, -200,-150,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, -200,150,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, -200,300,GameState.DISTANCE, 0,0,-vel, 4400));

		timeline.addObject(new Asteroid(this, 1, 150,-300,GameState.DISTANCE, 0,0,-vel, 4700));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 4700));
		timeline.addObject(new Asteroid(this, 1, 150,0,GameState.DISTANCE, 0,0,-vel, 4700));
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 4700));
		timeline.addObject(new Asteroid(this, 1, 150,300,GameState.DISTANCE, 0,0,-vel, 4700));

		timeline.addObject(new Asteroid(this, 1, 40,-300,GameState.DISTANCE, 0,0,-vel, 4900));
		timeline.addObject(new Asteroid(this, 1, 40,-40,GameState.DISTANCE, 0,0,-vel, 4900));
		timeline.addObject(new Asteroid(this, 1, 40,0,GameState.DISTANCE, 0,0,-vel, 4900));
		timeline.addObject(new Asteroid(this, 1, 40,40,GameState.DISTANCE, 0,0,-vel, 4900));
		timeline.addObject(new Asteroid(this, 1, 40,300,GameState.DISTANCE, 0,0,-vel, 4900));
/*
		//random
		timeline.addObject(new Asteroid(this, 2, 5300));
		timeline.addObject(new Asteroid(this, 2, 5400));
		timeline.addObject(new Asteroid(this, 2, 5500));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 5000));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 5000));
*/

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 1, 200,175,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, -100,175,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, -100,-125,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, 200,-125,GameState.DISTANCE, 0,0,-vel, 5300));

		timeline.addObject(new Asteroid(this, 1, 50,-175,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, 50,225,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, 250,25,GameState.DISTANCE, 0,0,-vel, 5300));
		timeline.addObject(new Asteroid(this, 1, -150,25,GameState.DISTANCE, 0,0,-vel, 5300));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 50,100,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, -250,100,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, -250,-200,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, 50,-200,GameState.DISTANCE, 0,0,-vel, 5700));

		timeline.addObject(new Asteroid(this, 2, -100,-250,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, -100,150,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, 100,-50,GameState.DISTANCE, 0,0,-vel, 5700));
		timeline.addObject(new Asteroid(this, 2, -300,-50,GameState.DISTANCE, 0,0,-vel, 5700));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 3, 130,200,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, -170,200,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, -170,-100,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, 130,-100,GameState.DISTANCE, 0,0,-vel, 6100));

		timeline.addObject(new Asteroid(this, 3, -20,-150,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, -20,250,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, 180,50,GameState.DISTANCE, 0,0,-vel, 6100));
		timeline.addObject(new Asteroid(this, 3, -220,50,GameState.DISTANCE, 0,0,-vel, 6100));

		//z-lines
		timeline.addObject(new Asteroid(this, 1, 100,-50,GameState.DISTANCE, 0,0,-vel, 6600));
		timeline.addObject(new Asteroid(this, 1, 100,-50,GameState.DISTANCE, 0,0,-vel, 6700));
		timeline.addObject(new Asteroid(this, 1, 100,-50,GameState.DISTANCE, 0,0,-vel, 6800));
		timeline.addObject(new Asteroid(this, 1, 100,-50,GameState.DISTANCE, 0,0,-vel, 6900));

		timeline.addObject(new Asteroid(this, 1, 10,5,GameState.DISTANCE, 0,0,-vel, 7100));
		timeline.addObject(new Asteroid(this, 1, 10,5,GameState.DISTANCE, 0,0,-vel, 7200));
		timeline.addObject(new Asteroid(this, 1, 10,5,GameState.DISTANCE, 0,0,-vel, 7300));
		timeline.addObject(new Asteroid(this, 1, 10,5,GameState.DISTANCE, 0,0,-vel, 7400));

		timeline.addObject(new Asteroid(this, 1, -20,50,GameState.DISTANCE, 0,0,-vel, 7600));
		timeline.addObject(new Asteroid(this, 1, -20,50,GameState.DISTANCE, 0,0,-vel, 7700));
		timeline.addObject(new Asteroid(this, 1, -20,50,GameState.DISTANCE, 0,0,-vel, 7800));
		timeline.addObject(new Asteroid(this, 1, -20,50,GameState.DISTANCE, 0,0,-vel, 7900));

		timeline.addObject(new Asteroid(this, 1, 25,50,GameState.DISTANCE, 0,0,-vel, 8100));
		timeline.addObject(new Asteroid(this, 1, 50,75,GameState.DISTANCE, 0,0,-vel, 8200));
		timeline.addObject(new Asteroid(this, 1, 75,100,GameState.DISTANCE, 0,0,-vel, 8300));
		timeline.addObject(new Asteroid(this, 1, 100,125,GameState.DISTANCE, 0,0,-vel, 8400));

		timeline.addObject(new Asteroid(this, 1, -140,-100,GameState.DISTANCE, 0,0,-vel, 8600));
		timeline.addObject(new Asteroid(this, 1, -140,-75,GameState.DISTANCE, 0,0,-vel, 8700));
		timeline.addObject(new Asteroid(this, 1, -140,-50,GameState.DISTANCE, 0,0,-vel, 8800));
		timeline.addObject(new Asteroid(this, 1, -140,-25,GameState.DISTANCE, 0,0,-vel, 8900));

		timeline.addObject(new Asteroid(this, 1, 320,-20,GameState.DISTANCE, 0,0,-vel, 9100));
		timeline.addObject(new Asteroid(this, 1, 300,-10,GameState.DISTANCE, 0,0,-vel, 9200));
		timeline.addObject(new Asteroid(this, 1, 280,0,GameState.DISTANCE, 0,0,-vel, 9300));
		timeline.addObject(new Asteroid(this, 1, 260,10,GameState.DISTANCE, 0,0,-vel, 9400));

		timeline.addObject(new Asteroid(this, 1, -100,0,GameState.DISTANCE, 0,0,-vel, 9600));
		timeline.addObject(new Asteroid(this, 1, -50,0,GameState.DISTANCE, 0,0,-vel, 9700));
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 9800));
		timeline.addObject(new Asteroid(this, 1, 50,0,GameState.DISTANCE, 0,0,-vel, 9900));
		timeline.addObject(new Asteroid(this, 1, 50,0,GameState.DISTANCE, 0,0,-vel, 10000));
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 10100));
		timeline.addObject(new Asteroid(this, 1, -50,0,GameState.DISTANCE, 0,0,-vel, 10200));
		timeline.addObject(new Asteroid(this, 1, -100,0,GameState.DISTANCE, 0,0,-vel, 10300));

		//random
		timeline.addObject(new Asteroid(this, 10800));
		timeline.addObject(new Asteroid(this, 11000));
		timeline.addObject(new Asteroid(this, 11200));
		timeline.addObject(new Asteroid(this, 11200));
		timeline.addObject(new Asteroid(this, 11200));
		timeline.addObject(new Asteroid(this, 11400));
		timeline.addObject(new Asteroid(this, 11600));
		timeline.addObject(new Asteroid(this, 11600));
		timeline.addObject(new Asteroid(this, 11700));
/*
		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 50,50,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, -250,50,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, -250,-250,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, 50,-250,GameState.DISTANCE, 0,0,-vel, 11100));

		timeline.addObject(new Asteroid(this, 2, -100,-300,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, -100,100,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, 100,-100,GameState.DISTANCE, 0,0,-vel, 11100));
		timeline.addObject(new Asteroid(this, 2, -300,-100,GameState.DISTANCE, 0,0,-vel, 11100));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 250,150,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, -50,150,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, -50,-150,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, 250,-150,GameState.DISTANCE, 0,0,-vel, 11400));

		timeline.addObject(new Asteroid(this, 2, 100,-200,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, 100,200,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, 300,0,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 2, -100,0,GameState.DISTANCE, 0,0,-vel, 11400));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 125,170,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, -175,170,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, -175,-130,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, 175,-130,GameState.DISTANCE, 0,0,-vel, 11800));

		timeline.addObject(new Asteroid(this, 2, -25,-180,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, -25,220,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, 175,20,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 2, -225,20,GameState.DISTANCE, 0,0,-vel, 11800));*/

		//octagon (square + diamond), in a line
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 12000));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 12000));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 12500));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 12500));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 12500));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 13000));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 13000));
/*
		//random
		timeline.addObject(new Asteroid(this, 13000));
		timeline.addObject(new Asteroid(this, 13050));
		timeline.addObject(new Asteroid(this, 13100));
		timeline.addObject(new Asteroid(this, 13150));
		timeline.addObject(new Asteroid(this, 13200));*/

		//plus cage
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 13500));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 13800));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 13800));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 13800));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 13800));

		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 14100));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 14100));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 14100));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 14100));

		//diamond
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 14400));

		//diamond
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 14700));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 14700));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 14700));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 14700));

		//plus
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 15000));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 15000));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 15000));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 15000));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 15300));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 15300));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 15300));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 15300));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 15700));

		//random
		timeline.addObject(new Asteroid(this, 2, 16200));
		timeline.addObject(new Asteroid(this, 2, 16200));
		timeline.addObject(new Asteroid(this, 2, 16200));
		timeline.addObject(new Asteroid(this, 2, 16400));
		timeline.addObject(new Asteroid(this, 2, 16500));
		timeline.addObject(new Asteroid(this, 2, 16600));
		timeline.addObject(new Asteroid(this, 2, 16600));
		timeline.addObject(new Asteroid(this, 2, 16900));


		//octagon (square + diamond)
		/*timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 17400));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 17400));*/
/*
		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 1, 150,150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -150,150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 17400));

		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 0,200,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 17400));
		timeline.addObject(new Asteroid(this, 1, -200,0,GameState.DISTANCE, 0,0,-vel, 17400));*/

		//goal ~3 min, 18000
		timeline.addObject(new Station(this, 17400));
	}

	public void loadLevel3(){
		levelNum = 3;
		//introString = "Level 3";
		displayMessage("Level 3");

		timeline = new Timeline(this);

		//add timeline events
		//timeline.addObject(new Station(this, 0));

		//425, 330

		//100 in 1 sec
		//1000 in 10 sec

		double vel = 2 * Player.VELOCITY;

		//add timeline events
		//(int health, GameState gs, double x, double y, double z, double vx, double vy, double vz, long time)
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 200));

		//octagon (square + diamond), in a line
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 600));

		timeline.addObject(new Asteroid(this, 1, 0,-250,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, 0,250,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, 250,0,GameState.DISTANCE, 0,0,-vel, 600));
		timeline.addObject(new Asteroid(this, 1, -250,0,GameState.DISTANCE, 0,0,-vel, 600));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 600));

		//octagon (square + diamond), in a line
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 1000));

		timeline.addObject(new Asteroid(this, 1, 0,-250,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, 0,250,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, 250,0,GameState.DISTANCE, 0,0,-vel, 1000));
		timeline.addObject(new Asteroid(this, 1, -250,0,GameState.DISTANCE, 0,0,-vel, 1000));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 1000));

		//octagon (square + diamond), in a line
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 1400));

		timeline.addObject(new Asteroid(this, 1, 0,-250,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, 0,250,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, 250,0,GameState.DISTANCE, 0,0,-vel, 1400));
		timeline.addObject(new Asteroid(this, 1, -250,0,GameState.DISTANCE, 0,0,-vel, 1400));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 1400));

		//octagon (square + diamond), in a line
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 1800));

		timeline.addObject(new Asteroid(this, 1, 0,-250,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 0,250,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, 250,0,GameState.DISTANCE, 0,0,-vel, 1800));
		timeline.addObject(new Asteroid(this, 1, -250,0,GameState.DISTANCE, 0,0,-vel, 1800));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 1800));

		//random
		timeline.addObject(new Asteroid(this, 2, 2800));
		timeline.addObject(new Asteroid(this, 2, 2900));
		timeline.addObject(new Asteroid(this, 2, 3000));
		timeline.addObject(new Asteroid(this, 2, 3100));
		timeline.addObject(new Asteroid(this, 2, 3200));

		//X cage
		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 3400));

		timeline.addObject(new Asteroid(this, 1, -100,100,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, -100,-100,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,-100,GameState.DISTANCE, 0,0,-vel, 3600));
		timeline.addObject(new Asteroid(this, 1, 100,100,GameState.DISTANCE, 0,0,-vel, 3600));

		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 3800));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 3800));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 3800));
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 3800));

		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 4000));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 4000));

		//square
		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 4200));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 4200));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 4200));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 4200));

		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 4200));

		//square
		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 4400));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 4400));

		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 4400));

		//square
		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 4600));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 4600));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 4600));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 4600));

		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 4600));

		//X
		timeline.addObject(new Asteroid(this, 1, -300,300,GameState.DISTANCE, 0,0,-vel, 4800));
		timeline.addObject(new Asteroid(this, 1, -300,-300,GameState.DISTANCE, 0,0,-vel, 4800));
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 4800));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 4800));

		timeline.addObject(new Asteroid(this, 1, -200,200,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, 200,-200,GameState.DISTANCE, 0,0,-vel, 5000));
		timeline.addObject(new Asteroid(this, 1, 200,200,GameState.DISTANCE, 0,0,-vel, 5000));

		timeline.addObject(new Asteroid(this, 1, -100,100,GameState.DISTANCE, 0,0,-vel, 5200));
		timeline.addObject(new Asteroid(this, 1, -100,-100,GameState.DISTANCE, 0,0,-vel, 5200));
		timeline.addObject(new Asteroid(this, 1, 100,-100,GameState.DISTANCE, 0,0,-vel, 5200));
		timeline.addObject(new Asteroid(this, 1, 100,100,GameState.DISTANCE, 0,0,-vel, 5200));

		timeline.addObject(new Asteroid(this, 2, 0,0,GameState.DISTANCE, 0,0,-vel, 5400));

		//random
		timeline.addObject(new Asteroid(this, 6400));
		timeline.addObject(new Asteroid(this, 6500));
		timeline.addObject(new Asteroid(this, 6700));
		timeline.addObject(new Asteroid(this, 6700));

		//spiral cage
		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 5400));

		timeline.addObject(new Asteroid(this, 1, 50,50,GameState.DISTANCE, 0,0,-vel, 5600));
		timeline.addObject(new Asteroid(this, 1, 100,0,GameState.DISTANCE, 0,0,-vel, 5800));
		timeline.addObject(new Asteroid(this, 1, 150,-150,GameState.DISTANCE, 0,0,-vel, 6000));
		timeline.addObject(new Asteroid(this, 1, 0,-200,GameState.DISTANCE, 0,0,-vel, 6200));
		timeline.addObject(new Asteroid(this, 1, -225,-225,GameState.DISTANCE, 0,0,-vel, 6400));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 6600));
		timeline.addObject(new Asteroid(this, 1, -250,250,GameState.DISTANCE, 0,0,-vel, 6800));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 7000));

		timeline.addObject(new Asteroid(this, 1, 250,250,GameState.DISTANCE, 0,0,-vel, 7200));
		timeline.addObject(new Asteroid(this, 7300));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 7400));
		timeline.addObject(new Asteroid(this, 7500));
		timeline.addObject(new Asteroid(this, 1, 250,-250,GameState.DISTANCE, 0,0,-vel, 7600));
		timeline.addObject(new Asteroid(this, 7700));
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 7800));
		timeline.addObject(new Asteroid(this, 7900));
		timeline.addObject(new Asteroid(this, 1, -250,-250,GameState.DISTANCE, 0,0,-vel, 8000));
		timeline.addObject(new Asteroid(this, 8100));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 8200));
		timeline.addObject(new Asteroid(this, 8300));
		timeline.addObject(new Asteroid(this, 1, -250,250,GameState.DISTANCE, 0,0,-vel, 8400));
		timeline.addObject(new Asteroid(this, 8500));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 8600));
		timeline.addObject(new Asteroid(this, 8700));

		timeline.addObject(new Asteroid(this, 1, 250,250,GameState.DISTANCE, 0,0,-vel, 8800));
		timeline.addObject(new Asteroid(this, 8900));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 9000));
		timeline.addObject(new Asteroid(this, 9100));
		timeline.addObject(new Asteroid(this, 1, 250,-250,GameState.DISTANCE, 0,0,-vel, 9200));
		timeline.addObject(new Asteroid(this, 9300));
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 9400));
		timeline.addObject(new Asteroid(this, 9500));
		timeline.addObject(new Asteroid(this, 1, -250,-250,GameState.DISTANCE, 0,0,-vel, 9600));
		timeline.addObject(new Asteroid(this, 9700));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 9800));
		timeline.addObject(new Asteroid(this, 9900));
		timeline.addObject(new Asteroid(this, 1, -250,250,GameState.DISTANCE, 0,0,-vel, 10000));
		timeline.addObject(new Asteroid(this, 10100));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 10200));
		timeline.addObject(new Asteroid(this, 10300));

		timeline.addObject(new Asteroid(this, 1, 250,250,GameState.DISTANCE, 0,0,-vel, 10400));
		timeline.addObject(new Asteroid(this, 10500));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 10600));
		timeline.addObject(new Asteroid(this, 10700));
		timeline.addObject(new Asteroid(this, 1, 250,-250,GameState.DISTANCE, 0,0,-vel, 10800));
		timeline.addObject(new Asteroid(this, 10900));
		timeline.addObject(new Asteroid(this, 1, 0,-300,GameState.DISTANCE, 0,0,-vel, 11000));
		timeline.addObject(new Asteroid(this, 11100));
		timeline.addObject(new Asteroid(this, 1, -250,-250,GameState.DISTANCE, 0,0,-vel, 11200));
		timeline.addObject(new Asteroid(this, 11300));
		timeline.addObject(new Asteroid(this, 1, -300,0,GameState.DISTANCE, 0,0,-vel, 11400));
		timeline.addObject(new Asteroid(this, 11500));
		timeline.addObject(new Asteroid(this, 1, -250,250,GameState.DISTANCE, 0,0,-vel, 11600));
		timeline.addObject(new Asteroid(this, 11700));
		timeline.addObject(new Asteroid(this, 1, 0,300,GameState.DISTANCE, 0,0,-vel, 11800));
		timeline.addObject(new Asteroid(this, 11900));

		timeline.addObject(new Asteroid(this, 1, 250,250,GameState.DISTANCE, 0,0,-vel, 12000));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 12200));
		timeline.addObject(new Asteroid(this, 1, 250,-250,GameState.DISTANCE, 0,0,-vel, 12400));
		timeline.addObject(new Asteroid(this, 1, 0,-250,GameState.DISTANCE, 0,0,-vel, 12600));
		timeline.addObject(new Asteroid(this, 1, -200,-200,GameState.DISTANCE, 0,0,-vel, 12800));
		timeline.addObject(new Asteroid(this, 1, -150,0,GameState.DISTANCE, 0,0,-vel, 13000));
		timeline.addObject(new Asteroid(this, 1, -100,100,GameState.DISTANCE, 0,0,-vel, 13200));
		timeline.addObject(new Asteroid(this, 1, 0,50,GameState.DISTANCE, 0,0,-vel, 13400));

		timeline.addObject(new Asteroid(this, 1, 0,0,GameState.DISTANCE, 0,0,-vel, 13600));

		//horizontal line
		timeline.addObject(new Asteroid(this, 1, -400,80,GameState.DISTANCE, 0,0,-vel, 14000));
		timeline.addObject(new Asteroid(this, 1, -200,80,GameState.DISTANCE, 0,0,-vel, 14000));
		timeline.addObject(new Asteroid(this, 1, 0,80,GameState.DISTANCE, 0,0,-vel, 14000));
		timeline.addObject(new Asteroid(this, 1, 200,80,GameState.DISTANCE, 0,0,-vel, 14000));
		timeline.addObject(new Asteroid(this, 1, 400,80,GameState.DISTANCE, 0,0,-vel, 14000));

		//vertical line
		timeline.addObject(new Asteroid(this, 1, 300,-300,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 300,-150,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 300,0,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 300,150,GameState.DISTANCE, 0,0,-vel, 14400));
		timeline.addObject(new Asteroid(this, 1, 300,300,GameState.DISTANCE, 0,0,-vel, 14400));

		//horizontal line
		timeline.addObject(new Asteroid(this, 1, -400,-280,GameState.DISTANCE, 0,0,-vel, 14800));
		timeline.addObject(new Asteroid(this, 1, -200,-280,GameState.DISTANCE, 0,0,-vel, 14800));
		timeline.addObject(new Asteroid(this, 1, 0,-280,GameState.DISTANCE, 0,0,-vel, 14800));
		timeline.addObject(new Asteroid(this, 1, 200,-280,GameState.DISTANCE, 0,0,-vel, 14800));
		timeline.addObject(new Asteroid(this, 1, 400,-280,GameState.DISTANCE, 0,0,-vel, 14800));

		//horizontal line
		timeline.addObject(new Asteroid(this, 1, -400,-130,GameState.DISTANCE, 0,0,-vel, 15200));
		timeline.addObject(new Asteroid(this, 1, -200,-130,GameState.DISTANCE, 0,0,-vel, 15200));
		timeline.addObject(new Asteroid(this, 1, 0,-130,GameState.DISTANCE, 0,0,-vel, 15200));
		timeline.addObject(new Asteroid(this, 1, 200,-130,GameState.DISTANCE, 0,0,-vel, 15200));
		timeline.addObject(new Asteroid(this, 1, 400,-130,GameState.DISTANCE, 0,0,-vel, 15200));

		//vertical line
		timeline.addObject(new Asteroid(this, 1, 100,-300,GameState.DISTANCE, 0,0,-vel, 15600));
		timeline.addObject(new Asteroid(this, 1, 100,-150,GameState.DISTANCE, 0,0,-vel, 15600));
		timeline.addObject(new Asteroid(this, 1, 100,0,GameState.DISTANCE, 0,0,-vel, 15600));
		timeline.addObject(new Asteroid(this, 1, 100,150,GameState.DISTANCE, 0,0,-vel, 15600));
		timeline.addObject(new Asteroid(this, 1, 100,300,GameState.DISTANCE, 0,0,-vel, 15600));

		//horizontal line
		timeline.addObject(new Asteroid(this, 1, -400,-80,GameState.DISTANCE, 0,0,-vel, 16000));
		timeline.addObject(new Asteroid(this, 1, -200,-80,GameState.DISTANCE, 0,0,-vel, 16000));
		timeline.addObject(new Asteroid(this, 1, 0,-80,GameState.DISTANCE, 0,0,-vel, 16000));
		timeline.addObject(new Asteroid(this, 1, 200,-80,GameState.DISTANCE, 0,0,-vel, 16000));
		timeline.addObject(new Asteroid(this, 1, 400,-80,GameState.DISTANCE, 0,0,-vel, 16000));

		//vertical line
		timeline.addObject(new Asteroid(this, 1, -30,-300,GameState.DISTANCE, 0,0,-vel, 16400));
		timeline.addObject(new Asteroid(this, 1, -30,-150,GameState.DISTANCE, 0,0,-vel, 16400));
		timeline.addObject(new Asteroid(this, 1, -30,0,GameState.DISTANCE, 0,0,-vel, 16400));
		timeline.addObject(new Asteroid(this, 1, -30,150,GameState.DISTANCE, 0,0,-vel, 16400));
		timeline.addObject(new Asteroid(this, 1, -30,300,GameState.DISTANCE, 0,0,-vel, 16400));

		//vertical line
		timeline.addObject(new Asteroid(this, 1, -220,-300,GameState.DISTANCE, 0,0,-vel, 16800));
		timeline.addObject(new Asteroid(this, 1, -220,-150,GameState.DISTANCE, 0,0,-vel, 16800));
		timeline.addObject(new Asteroid(this, 1, -220,0,GameState.DISTANCE, 0,0,-vel, 16800));
		timeline.addObject(new Asteroid(this, 1, -220,150,GameState.DISTANCE, 0,0,-vel, 16800));
		timeline.addObject(new Asteroid(this, 1, -220,300,GameState.DISTANCE, 0,0,-vel, 16800));

		//vertical line
		timeline.addObject(new Asteroid(this, 1, 200,-300,GameState.DISTANCE, 0,0,-vel, 17200));
		timeline.addObject(new Asteroid(this, 1, 200,-150,GameState.DISTANCE, 0,0,-vel, 17200));
		timeline.addObject(new Asteroid(this, 1, 200,0,GameState.DISTANCE, 0,0,-vel, 17200));
		timeline.addObject(new Asteroid(this, 1, 200,150,GameState.DISTANCE, 0,0,-vel, 17200));
		timeline.addObject(new Asteroid(this, 1, 200,300,GameState.DISTANCE, 0,0,-vel, 17200));

		//horizontal line
		timeline.addObject(new Asteroid(this, 1, -400,80,GameState.DISTANCE, 0,0,-vel, 17600));
		timeline.addObject(new Asteroid(this, 1, -200,80,GameState.DISTANCE, 0,0,-vel, 17600));
		timeline.addObject(new Asteroid(this, 1, 0,80,GameState.DISTANCE, 0,0,-vel, 17600));
		timeline.addObject(new Asteroid(this, 1, 200,80,GameState.DISTANCE, 0,0,-vel, 17600));
		timeline.addObject(new Asteroid(this, 1, 400,80,GameState.DISTANCE, 0,0,-vel, 17600));

		//random
		timeline.addObject(new Asteroid(this, 18100));
		timeline.addObject(new Asteroid(this, 18150));
		timeline.addObject(new Asteroid(this, 18200));
		timeline.addObject(new Asteroid(this, 18230));
		timeline.addObject(new Asteroid(this, 18260));
		timeline.addObject(new Asteroid(this, 18300));
		timeline.addObject(new Asteroid(this, 18400));
		timeline.addObject(new Asteroid(this, 18450));
		timeline.addObject(new Asteroid(this, 18500));
		timeline.addObject(new Asteroid(this, 18600));
		timeline.addObject(new Asteroid(this, 18600));
		timeline.addObject(new Asteroid(this, 18700));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 175,175,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 2, -125,175,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 2, -125,-125,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 2, 175,-125,GameState.DISTANCE, 0,0,-vel, 19000));

		timeline.addObject(new Asteroid(this, 1, 25,-175,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 1, 25,225,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 1, 225,25,GameState.DISTANCE, 0,0,-vel, 19000));
		timeline.addObject(new Asteroid(this, 1, -175,25,GameState.DISTANCE, 0,0,-vel, 19000));

		timeline.addObject(new Asteroid(this, 3, 25,25,GameState.DISTANCE, 0,0,-vel, 19000));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 0,200,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 2, -300,200,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 2, -300,-100,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 2, 0,-100,GameState.DISTANCE, 0,0,-vel, 19500));

		timeline.addObject(new Asteroid(this, 1, -150,-150,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 1, -150,250,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 1, 50,50,GameState.DISTANCE, 0,0,-vel, 19500));
		timeline.addObject(new Asteroid(this, 1, -350,50,GameState.DISTANCE, 0,0,-vel, 19500));

		timeline.addObject(new Asteroid(this, 3, -150,50,GameState.DISTANCE, 0,0,-vel, 19500));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 100,50,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 2, -200,50,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 2, -200,-250,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 2, 100,-250,GameState.DISTANCE, 0,0,-vel, 20000));

		timeline.addObject(new Asteroid(this, 1, -50,-300,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 1, -50,100,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 1, 150,-100,GameState.DISTANCE, 0,0,-vel, 20000));
		timeline.addObject(new Asteroid(this, 1, -250,-100,GameState.DISTANCE, 0,0,-vel, 20000));

		timeline.addObject(new Asteroid(this, 3, -50,-100,GameState.DISTANCE, 0,0,-vel, 20000));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 350,250,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 2, 50,250,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 2, 50,-50,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 2, 350,-50,GameState.DISTANCE, 0,0,-vel, 20500));

		timeline.addObject(new Asteroid(this, 1, 200,-100,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 1, 200,300,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 1, 400,100,GameState.DISTANCE, 0,0,-vel, 20500));
		timeline.addObject(new Asteroid(this, 1, 0,100,GameState.DISTANCE, 0,0,-vel, 20500));

		timeline.addObject(new Asteroid(this, 3, 200,100,GameState.DISTANCE, 0,0,-vel, 20500));

		//octagon (square + diamond)
		timeline.addObject(new Asteroid(this, 2, 175,120,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 2, -125,120,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 2, -125,-180,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 2, 175,-180,GameState.DISTANCE, 0,0,-vel, 21000));

		timeline.addObject(new Asteroid(this, 1, 25,-230,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 1, 25,170,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 1, 225,-30,GameState.DISTANCE, 0,0,-vel, 21000));
		timeline.addObject(new Asteroid(this, 1, -175,-30,GameState.DISTANCE, 0,0,-vel, 21000));

		timeline.addObject(new Asteroid(this, 3, 25,-30,GameState.DISTANCE, 0,0,-vel, 21000));

		//random
		timeline.addObject(new Asteroid(this, 21500));
		timeline.addObject(new Asteroid(this, 21550));
		timeline.addObject(new Asteroid(this, 21600));
		timeline.addObject(new Asteroid(this, 21650));
		timeline.addObject(new Asteroid(this, 21700));
		timeline.addObject(new Asteroid(this, 21750));
		timeline.addObject(new Asteroid(this, 21800));

		//goal ~4 min, 24000
		timeline.addObject(new Station(this, 21900));
	}
}