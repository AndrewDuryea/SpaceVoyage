/*
 * Andrew Duryea
 * September 16, 2013
 * Asteroid.java
 *
 * Randomly generated asteroids that fly at the player.
 */

package Game;

import java.lang.Math;
import java.util.Random;
import Graphics3D.Object3D;
import Game.GameState;

public class Asteroid extends TimeObject
{
	Object3D model;

	GameState gameState;

	//x, y, z
	//should only have a negative z velocity
	public double velocity[] = {0,0,0};
	double acceleration[] = {0,0,0};

	static final double MAX_VEL = (10 * Player.VELOCITY);
	static final double MIN_VEL = (Player.VELOCITY);
	static final double MAX_ROT = 0.5;

	double thetaX;
	double thetaY;
	double thetaZ;

	int health; //how many hits the asteroid can take

	int score; //how much the asteroid is worth

	int damage; //how much damage the asteroid can do to the player

	//set velocity and position, for use with level mode, random rotation
	public Asteroid(GameState gs, int health, double x, double y, double z, double vx, double vy, double vz, double dist){
		this.gameState = gs;

		this.health = health;

		this.score = health * 100;

		this.damage = health * 10;

		this.makeModel();

		model.translate(x, y, z);

		velocity[0] = vx;
		velocity[1] = vy;
		velocity[2] = vz;

    	Random r = new Random();

    	//random rotations
    	thetaX = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);
    	thetaY = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);
    	thetaZ = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);

    	this.dist = dist;
	}

	//time event with randomized asteroid with a specified health
	public Asteroid(GameState gs, int health, long dist){
		//super(time);
		this(gs, health, true);
		this.dist = dist;
	}

	//time event with randomized asteroid with a random health
	public Asteroid(GameState gs, long dist){
		//super(time);
		this(gs, 0, true);
		this.dist = dist;
	}

	//randomizes velocity and position, for use with endless mode
	// if bModel is true a model is made
    public Asteroid(GameState gs, int health, boolean bModel) {
    	this.gameState = gs;

    	if(health > 0)
			this.health = health;
		else{
			Random r = new Random();
			double chance = r.nextDouble();

			if(chance <= 0.55)
				this.health = 1;
			else if(chance <= 0.85)
				this.health = 2;
			else
				this.health = 3;
		}

		this.score = this.health * 100;

		this.damage = this.health * 10;

		if(!bModel)
			return;

    	Random r = new Random();

    	//velocity[2] = 0 - (r.nextInt((MAX_VEL - MIN_VEL) + 1) + MIN_VEL);
    	velocity[2] = 0 - ((r.nextDouble() * (MAX_VEL - MIN_VEL)) + MIN_VEL);

		/*
    	int width = gameState.game.g3D.canvas.getWidth();
    	int height = gameState.game.g3D.canvas.getHeight();
    	*/

		this.makeModel();


    	//start at a random place
    	int x = r.nextInt(Game.WIDTH + 1) - Game.WIDTH / 2;
    	int y = r.nextInt(Game.HEIGHT + 1) - Game.HEIGHT / 2;
    	model.translate(x, y, GameState.DISTANCE);

    	//random rotations
    	thetaX = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);
    	thetaY = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);
    	thetaZ = (r.nextDouble() * MAX_ROT) - (MAX_ROT / 2);
    }

    public void makeModel(){
    	//create model
    	//(name, Kdr, Kdg, Kdb, n, Ks)

    	//change color based on health
    	switch(health){
    		case 1:
    			this.model = gameState.game.g3D.addObject("asteroid", 0.4, 0.2, 0, 100, 0);
    			break;
    		case 2:
    			this.model = gameState.game.g3D.addObject("asteroid", 0.8, 0.2, 0, 100, 0);
    			break;
    		case 3:
    			this.model = gameState.game.g3D.addObject("asteroid", 0, 0.2, 0.8, 100, 0);
    			break;
    		default:
    			break;
    	}

    	//gs.objects.add(model);

    	//points
    	/*model.addPoint(-50,50,10);	//0
		model.addPoint(-50,-50,10);	//1
		model.addPoint(50,-50,10);	//2
		model.addPoint(50,50,10);	//3
		model.addPoint(-50,50,110);	//4
		model.addPoint(-50,-50,110);	//5
		model.addPoint(50,-50,110);	//6
		model.addPoint(50,50,110);	//7

		//counter-clockwise for each face

		//body
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
		model.addTriangle(2,5,6);*/

		//points
    	model.addPoint(0,50,10);	//0
		model.addPoint(-50,0,10);	//1
		model.addPoint(0,-50,10);	//2
		model.addPoint(50,0,10);	//3
		model.addPoint(-50,50,60);	//4
		model.addPoint(-50,-50,60);	//5
		model.addPoint(50,-50,60);	//6
		model.addPoint(50,50,60);	//7
    	model.addPoint(0,50,110);	//8
		model.addPoint(-50,0,110);	//9
		model.addPoint(0,-50,110);	//10
		model.addPoint(50,0,110);	//11

		//counter-clockwise for each face

		//body
		model.addTriangle(0,1,3);
		model.addTriangle(1,2,3);

		model.addTriangle(7,3,11);
		model.addTriangle(3,6,11);

		model.addTriangle(8,11,9);
		model.addTriangle(11,10,9);

		model.addTriangle(4,9,1);
		model.addTriangle(9,5,1);

		model.addTriangle(8,4,7);
		model.addTriangle(4,0,7);

		model.addTriangle(2,5,6);
		model.addTriangle(5,10,6);

		//corners
		model.addTriangle(0,3,7);
		model.addTriangle(0,4,1);
		model.addTriangle(1,5,2);
		model.addTriangle(3,2,6);
		model.addTriangle(4,8,9);
		model.addTriangle(7,11,8);
		model.addTriangle(6,10,11);
		model.addTriangle(5,9,10);
    }

    public void update(long delta){
    	//System.out.println(velocity[0] + " " + velocity[1] + " " + velocity[2]);
    	//System.out.println(model.getCenter().toString());

    	model.translateDyn(velocity[0]*delta,velocity[1]*delta,velocity[2]*delta);

    	model.rotateDyn(thetaX, thetaY, thetaZ);
    }

	//just adds this asteroid to the list
    public void timeRun(GameState g){
    	g.asteroidList.add(this);
    }
}